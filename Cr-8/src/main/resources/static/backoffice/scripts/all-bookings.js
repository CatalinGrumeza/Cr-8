document.addEventListener("DOMContentLoaded", function () {
  const container = document.getElementById("booking-container");
  const statusFilter = document.getElementById("status-filter");
  const searchBar = document.getElementById("search-bar");
  const dateFilter = document.getElementById("date-filter");

  let allBookings = [];

  // Fetch data from the API
  fetch("/api/all-bookings")
    .then((response) => response.json())
    .then((data) => {
      allBookings = data;
      renderCards(data);
    })
    .catch((error) => console.error("Error fetching data:", error));

  // Function to format date
  function formatDate(dateString) {
    const date = new Date(dateString);
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}-${String(date.getDate()).padStart(2, "0")}`;
  }

  // Function to render booking cards
  function renderCards(data) {
    container.innerHTML = ""; // Clear the container
    data.forEach((booking) => {
      const card = document.createElement("div");
      card.classList.add("card", "card-row");

      const {
        id,
        dataFrom,
        dataTo,
        additionalDetails,
        participantNumber,
        bookType,
        vistorType,
        status,
        reference,
        bookedDate,
        numberOfDays,
        labsSet,
        createdAt,
      } = booking;

      // Create card content
      card.innerHTML = `
        <div>
          <h2 class="card-title">${reference.lastName} ${reference.firstName}</h2>
          <p><span class="bold">Email</span>: ${reference.email}</p>
          <p><span class="bold">Telefono</span>: ${reference.phoneNumber}</p>
          <p><span class="bold">Tipo di Visitatore</span>: ${vistorType}</p>
          <p><span class="bold">Numero di Partecipanti</span>: ${participantNumber}</p>
          <p><span class="bold">Tipo di Prenotazione</span>: ${bookType}</p>
          <p><span class="bold">Periodo</span>: dal ${formatDate(dataFrom)} al ${formatDate(dataTo)}</p>
          <p><span class="bold">Dettagli Aggiuntivi</span>: ${additionalDetails}</p>
          <p><span class="bold">Numero di Giorni</span>: ${numberOfDays}</p>
          <p><span class="bold">Stato</span>: ${status.name}</p>
          <p><span class="bold">Creata il</span> ${formatDate(createdAt)}</p>
           <div class="labs-set">
              <h3>Laboratori</h3>
              ${labsSet.map(lab => `
                <div class="lab">
                  <h4>${lab.name}</h4>
                </div>
              `).join('')}
            </div>
          
          ${status.name === "Completed" ? `
            <button class="btn-cancel" data-id="${id}" data-status="${status.name}">Annulla Prenotazione</button>
            <p><span class="bold">Data di prenotazione</span>: ${formatDate(bookedDate.date)} al ${formatDate(bookedDate.toDate)}</p>
          ` : ''}

          ${status.name === "Pending" || status.name === "Cancelled" ? `
            <form class="book-date-form" data-id="${id}">
              <label for="start-date">Data di inizio</label>
              <input type="date" id="start-date" name="start-date" required>
              <label for="end-date">Data di fine</label>
              <input type="date" id="end-date" name="end-date" required>
              <label><input type="checkbox" name="morning" id="morning" />Mattina</label>
        	  <label><input type="checkbox" name="fullDay" id="fullDay" />Giorno completo</label>
              <button type="submit">Aggiorna Prenotazione</button>
            </form>
          ` : ''}
        </div>
      `;

      // Append card to container
      container.appendChild(card);

      // Add event listener to the cancel button
      const cancelButton = card.querySelector(".btn-cancel");
      if (cancelButton) {
        cancelButton.addEventListener("click", function () {
          const bookingId = this.getAttribute("data-id");

          // Send request to update the status to "Cancelled"
          fetch(`/api/update-booking-status?bookingId=${bookingId}&status=Cancelled`, {
            method: "POST",
          })
          .then((response) => {
            if (response.ok) {
              // Refresh the page to reflect changes
              window.location.reload();
            } else {
              alert("Errore durante l'annullamento della prenotazione.");
            }
          })
          .catch((error) => console.error("Error:", error));
        });
      }

      // Add event listener to the booking date form
      const form = card.querySelector(".book-date-form");
      if (form) {
        form.addEventListener("submit", function (event) {
          event.preventDefault();

          const startDate = form.querySelector("#start-date").value;
          const endDate = form.querySelector("#end-date").value;

          const bookingId = form.getAttribute("data-id");
		  
          // Send request to update the booking date
          fetch("/api/book-date", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              date: startDate,
              toDate: endDate,
			  morning:Boolean(morning),
  			  fullDay:Boolean(fullDay),
              idBookingRequest: bookingId,
            }),
          })
          .then((response) => {
            if (response.ok) {
              // Refresh the page to reflect changes
              window.location.reload();
            } else {
              alert("Errore durante l'aggiornamento della prenotazione.");
            }
          })
          .catch((error) => console.error("Error:", error));
        });
      }
    });
  }

  // Function to filter and sort bookings
  function filterAndSortBookings() {
    let filteredBookings = [...allBookings];

    // Filter by status
    const selectedStatus = statusFilter.value;
    if (selectedStatus !== "all") {
      filteredBookings = filteredBookings.filter(
        (booking) => booking.status.name === selectedStatus
      );
    }

    // Sort by date
    const sortOrder = dateFilter.value;
    filteredBookings.sort((a, b) => {
      const dateA = new Date(a.createdAt);
      const dateB = new Date(b.createdAt);
      return sortOrder === "asc" ? dateA - dateB : dateB - dateA;
    });

    // Filter by search term
    const searchTerm = searchBar.value.toLowerCase();
    if (searchTerm) {
      filteredBookings = filteredBookings.filter((booking) => {
        const firstName = booking.reference.firstName.toLowerCase();
        const lastName = booking.reference.lastName.toLowerCase();
        const email = booking.reference.email.toLowerCase();
        const bookType = booking.bookType.toLowerCase();
        return (
          firstName.includes(searchTerm) ||
          lastName.includes(searchTerm) ||
          email.includes(searchTerm) ||
          bookType.includes(searchTerm)
        );
      });
    }

    // Render the filtered and sorted data
    renderCards(filteredBookings);
  }

  // Add event listeners to filters and search bar
  dateFilter.addEventListener("change", filterAndSortBookings);
  statusFilter.addEventListener("change", filterAndSortBookings);
  searchBar.addEventListener("input", filterAndSortBookings);
});

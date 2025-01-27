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
    const monthNames = [
      "Gen",
      "Feb",
      "Mar",
      "Apr",
      "Mag",
      "Giu",
      "Lug",
      "Ago",
      "Set",
      "Ott",
      "Nov",
      "Dic",
    ];

    const date = new Date(dateString);
    return `${String(date.getDate()).padStart(
      2,
      "0"
    )} ${String(monthNames[date.getMonth()])} ${date.getFullYear()}`;
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
        <div class="book-content">
          <h2 class="card-title">${reference.lastName} ${
        reference.firstName
      }</h2>
          <p><span class="bold">Email</span>: ${reference.email}</p>
          <p><span class="bold">Telefono</span>: ${reference.phoneNumber}</p>
          <p><span class="bold">Tipo di Visitatore</span>: ${vistorType}</p>
          <p><span class="bold">Numero di Partecipanti</span>: ${participantNumber}</p>
          <p><span class="bold">Periodo</span>: dal ${formatDate(
            dataFrom
          )} al ${formatDate(dataTo)}</p>
          <p><span class="bold">Numero di Giorni</span>: ${numberOfDays}</p>
          <p><span class="bold">Periodo della giornata</span>: ${bookType}</p>
          <p><span class="bold">Dettagli Aggiuntivi</span>: ${additionalDetails}</p>
          <p><span class="bold">Creata il</span> ${formatDate(createdAt)}</p>
          <p><span class="bold">Stato</span>: ${status.name}</p>
           <div class="labs-set">
              <h3>Laboratori scelti</h3>
              ${labsSet
                .map(
                  (lab) => `
                <div class="lab">
                  <p>${lab.name}</p>
                </div>
              `
                )
                .join("")}
            </div>
          
          ${
            status.name === "Completed"
              ? `
              <p><span class="bold">Data di prenotazione</span>: dal ${formatDate(
                bookedDate.date
              )} al ${formatDate(bookedDate.toDate)}</p>
            <button class="btn cancel btn-cancel" data-id="${id}" data-status="${
                  status.name
                }">Annulla Prenotazione</button>
          `
              : ""
          }

          ${
            status.name === "Pending" || status.name === "Cancelled"
              ? `
            <form class="book-date-form" data-id="${id}">
              <h3>Conferma la visita</h3>
              <label for="start-date">Data di inizio</label>
              <input type="date" id="start-date" name="start-date" required>
              <span id="start-date-error" class="error"></span>
              <label for="end-date">Data di fine</label>
              <input type="date" id="end-date" name="end-date" required>
              <span id="end-date-error" class="error"></span>
              <div id=dayFraction>
                <label class="day-fraction"><input type="checkbox" id="morning" name="morning" value="Mattina" />Mattino</label>
                <label class="day-fraction"><input type="checkbox" id="fullDay" name="fullDay" value="fullDay" />Giorno completo</label>
              </div>
              <div class="btn-container">
              <button class="btn update btn-update">Conferma Prenotazione</button>
              </div>
              </form>
          `
              : ""
          }
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
          fetch(
            `/api/update-booking-status?bookingId=${bookingId}&status=Cancelled`,
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                "X-CSRF-TOKEN": csrfToken, // Add the token to the header
              },
            }
          )
            .then((response) => {
              if (response.ok) {
                // Refresh the page to reflect changes
                alert("Prenotazione annullata correttamente");
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

          const startDateInput = form.querySelector("#start-date");
          const endDateInput = form.querySelector("#end-date");

          const startDate = startDateInput.value;
          const endDate = endDateInput.value;

          let valid = true;

          // Validazione "start-date"
          if (new Date(startDate) < new Date()) {
            valid = false;
            const errorSpan = form.querySelector("#start-date-error");
            errorSpan.textContent =
              "La data deve essere successiva o uguale a oggi.";
          } else {
            form.querySelector("#start-date-error").textContent = "";
          }

          // Validazione "end-date"
          if (new Date(endDate) <= new Date(startDate)) {
            valid = false;
            const errorSpan = form.querySelector("#end-date-error");
            errorSpan.textContent =
              "La data deve essere successiva alla data di inizio.";
          } else {
            form.querySelector("#end-date-error").textContent = "";
          }

          // Se la validazione fallisce, non procedere con la richiesta
          if (!valid) {
            return;
          }

          const bookingId = form.getAttribute("data-id");

          // Send request to update the booking date
          fetch("/api/book-date", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "X-CSRF-TOKEN": csrfToken, // Add the token to the header
            },
            body: JSON.stringify({
              date: startDate,
              toDate: endDate,
              morning: Boolean(morning),
              fullDay: Boolean(fullDay),
              idBookingRequest: bookingId,
            }),
          })
            .then((response) => {
              if (response.ok) {
                // Refresh the page to reflect changes
                alert("Prenotazione aggiornata correttamente!");
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

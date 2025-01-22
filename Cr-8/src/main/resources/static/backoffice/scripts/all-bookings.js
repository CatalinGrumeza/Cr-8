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
      } = booking;

      // Create card content
      card.innerHTML = `
              <div>
                  <h2 class="card-title">${reference.lastName} ${
        reference.firstName
      }</h2>
                  <p><span class="bold">Email</span>: ${reference.email}</p>
                  <p><span class="bold">Telefono</span>: ${
                    reference.phoneNumber
                  }</p>
                  <p><span class="bold">Tipo di Visitatore</span>: ${vistorType}</p>
                  <p><span class="bold">Numero di Partecipanti</span>: ${participantNumber}</p>
                  <p><span class="bold">Tipo di Prenotazione</span>: ${bookType}</p>
                  <p><span class="bold">Periodo</span>: dal ${formatDate(
                    dataFrom
                  )} al ${formatDate(dataTo)}</p>
                  <p><span class="bold">Dettagli Aggiuntivi</span>: ${additionalDetails}</p>
                  <span class="info-status">${status.name}</span>
                  <div class="btn-container">
                      <button class="btn-complete ${
                        status.name === "Completed" ? "btn-green" : "btn-red"
                      }" 
                      data-id="${id}" data-status="${status.name}">
                      ${
                        status.name === "Completed"
                          ? "Completata"
                          : "Da Revisionare"
                      }
                      </button>
                  </div>
                  <div class="separator"></div>
                  <p class="center"><span class="bold">Creata il</span> ${formatDate(
                    booking.createdAt
                  )}</p>
              </div>
          `;

      // Append card to container
      container.appendChild(card);

      // Add event listener to the button
      const toggleButton = card.querySelector(".btn-complete");
      toggleButton.addEventListener("click", function () {
        const bookingId = this.getAttribute("data-id");
        const currentStatus = this.getAttribute("data-status");
        const newStatus =
          currentStatus === "Completed" ? "Pending" : "Completed";

        // Send request to update the status
        fetch(
          `/api/update-booking-status?id=${bookingId}&status=${newStatus}`,
          {
            method: "POST",
          }
        )
          .then((response) => {
            if (response.ok) {
              // Update the UI in real-time
              const statusElement = card.querySelector(".info-status");
              statusElement.textContent = newStatus;

              // Update button label and data-status attribute
              toggleButton.textContent =
                newStatus === "Completed" ? "Completata" : "Da Revisionare";
              toggleButton.setAttribute("data-status", newStatus);

              // Update button color
              if (newStatus === "Completed") {
                toggleButton.classList.remove("btn-red");
                toggleButton.classList.add("btn-green");
              } else {
                toggleButton.classList.remove("btn-green");
                toggleButton.classList.add("btn-red");
              }

              // Update the data in allBookings
              const updatedBooking = allBookings.find(
                (item) => item.id == bookingId
              );
              if (updatedBooking) {
                updatedBooking.status.name = newStatus;
              }
            } else {
              alert("Errore durante l'aggiornamento dello stato.");
            }
          })
          .catch((error) => console.error("Error:", error));
      });
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

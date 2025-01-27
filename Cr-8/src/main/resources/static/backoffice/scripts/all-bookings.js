/**
 * @file: all-bookings.js
 * @author: CR-8
 * This code includes the logic for the all-bookings.html page
 */

document.addEventListener("DOMContentLoaded", function () {
  const container = document.getElementById("booking-container");
  const statusFilter = document.getElementById("status-filter");
  const searchBar = document.getElementById("search-bar");
  const dateFilter = document.getElementById("date-filter");

  let allBookings = [];

  /**
   * Checks if two date ranges overlap, including consideration of time slots (morning or full-day bookings).
   *
   * @param {string} start1 - Start date of the first range in YYYY-MM-DD format.
   * @param {string} end1 - End date of the first range in YYYY-MM-DD format.
   * @param {string} start2 - Start date of the second range in YYYY-MM-DD format.
   * @param {string} end2 - End date of the second range in YYYY-MM-DD format.
   * @param {boolean} morning1 - Indicates if the first range is a morning booking.
   * @param {boolean} fullDay1 - Indicates if the first range is a full-day booking.
   * @param {boolean} morning2 - Indicates if the second range is a morning booking.
   * @param {boolean} fullDay2 - Indicates if the second range is a full-day booking.
   * @returns {boolean} `true` if the date ranges overlap, `false` otherwise.
   */
  function datesOverlap(
    start1,
    end1,
    start2,
    end2,
    morning1,
    fullDay1,
    morning2,
    fullDay2
  ) {
    const startDate1 = new Date(start1);
    const endDate1 = new Date(end1);
    const startDate2 = new Date(start2);
    const endDate2 = new Date(end2);

    // If dates don't overlap at all, return false
    if (endDate1 < startDate2 || startDate1 > endDate2) {
      return false;
    }

    // If dates are the same, check time slots
    if (
      startDate1.getTime() === startDate2.getTime() ||
      endDate1.getTime() === endDate2.getTime()
    ) {
      // If either booking is for full day, there's an overlap
      if (fullDay1 || fullDay2) {
        return true;
      }
      // If both are morning slots, there's an overlap
      if (morning1 && morning2) {
        return true;
      }
      // If neither is morning slot, there's an overlap (afternoon implied)
      if (!morning1 && !morning2) {
        return true;
      }
    }

    // If date ranges overlap, consider it an overlap regardless of time slots
    return true;
  }

  /**
   * Checks if a booking overlaps with any existing bookings by fetching booked dates from the server.
   *
   * @param {string} startDate - The start date of the booking in YYYY-MM-DD format.
   * @param {string} endDate - The end date of the booking in YYYY-MM-DD format.
   * @param {boolean} morning - Indicates if the booking is for the morning slot.
   * @param {boolean} fullDay - Indicates if the booking is for a full day.
   * @returns {Promise<Object>} A promise resolving to an object with:
   *   - `{boolean} hasOverlap` - `true` if there is an overlap, `false` otherwise.
   *   - `{Object|null} conflictingBooking` - The conflicting booking, if any.
   */
  function checkBookingOverlap(startDate, endDate, morning, fullDay) {
    return fetch("/api/all-booked-dates")
      .then((response) => response.json())
      .then((bookedDates) => {
        for (const booking of bookedDates) {
          if (
            datesOverlap(
              startDate,
              endDate,
              booking.date,
              booking.toDate,
              morning,
              fullDay,
              booking.morning,
              booking.allDay
            )
          ) {
            return {
              hasOverlap: true,
              conflictingBooking: booking,
            };
          }
        }
        return { hasOverlap: false };
      });
  }

  // Fetch data from the API
  fetch("/api/all-bookings")
    .then((response) => response.json())
    .then((data) => {
      allBookings = data;
      renderCards(data);
    })
    .catch((error) => console.error("Error fetching data:", error));

  /**
   * Formats a date string into a human-readable format.
   *
   * @param {string} dateString - A date string in a format recognized by the `Date` constructor.
   * @returns {string} The formatted date string in the format `DD Mon YYYY`.
   */
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

  /**
   * Renders booking cards with detailed information and interaction options.
   *
   * This function dynamically creates and populates booking cards based on the provided data. Each card includes:
   * - Reference details (name, email, phone number).
   * - Booking details (visitor type, number of participants, date range, additional details, etc.).
   * - Laboratory selection and booking status.
   *
   * Depending on the booking status, it provides options to:
   * - Cancel a booking if it's "Completed."
   * - Confirm a pending or canceled booking by filling in the date range and time slot.
   *
   * @param {Array} data - Array of booking objects, where each object includes:
   * - `id`: Unique identifier for the booking.
   * - `dataFrom` and `dataTo`: Start and end dates of the requested booking.
   * - `additionalDetails`: Additional notes provided by the user.
   * - `participantNumber`: Number of participants in the booking.
   * - `bookType`: Time slot for the booking (e.g., morning or full day).
   * - `vistorType`: Type of visitor (e.g., student, professional).
   * - `status`: Object representing the current status of the booking (e.g., pending, completed, canceled).
   * - `reference`: Object containing user details (name, email, phone).
   * - `bookedDate`: Object representing confirmed booking dates, if applicable.
   * - `numberOfDays`: Total number of days requested for the booking.
   * - `labsSet`: Array of selected laboratories.
   * - `createdAt`: Date when the booking request was created.
   *
   * @returns {void}
   */
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
          <h2>${reference.lastName} ${reference.firstName}</h2>
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
        form.addEventListener("submit", async function (event) {
          event.preventDefault();

          const startDateInput = form.querySelector("#start-date");
          const endDateInput = form.querySelector("#end-date");
          const morningCheckbox = form.querySelector("#morning");
          const fullDayCheckbox = form.querySelector("#fullDay");

          const startDate = startDateInput.value;
          const endDate = endDateInput.value;
          const morning = morningCheckbox.checked;
          const fullDay = fullDayCheckbox.checked;

          // Clear previous error messages
          form.querySelector("#start-date-error").textContent = "";
          form.querySelector("#end-date-error").textContent = "";

          let valid = true;

          // Basic date validation
          if (new Date(startDate) < new Date()) {
            valid = false;
            form.querySelector("#start-date-error").textContent =
              "La data deve essere successiva o uguale a oggi.";
          }

          if (new Date(endDate) <= new Date(startDate)) {
            valid = false;
            form.querySelector("#end-date-error").textContent =
              "La data deve essere successiva alla data di inizio.";
          }

          // Time slot validation
          if (!morning && !fullDay) {
            valid = false;
            alert("Seleziona almeno un'opzione tra Mattino e Giorno completo.");
            return;
          }

          if (morning && fullDay) {
            valid = false;
            alert("Seleziona solo un'opzione tra Mattino e Giorno completo.");
            return;
          }

          if (!valid) return;

          try {
            // Check for booking overlaps
            const overlapCheck = await checkBookingOverlap(
              startDate,
              endDate,
              morning,
              fullDay
            );

            if (overlapCheck.hasOverlap) {
              alert(
                `Periodo non disponibile: esiste già una prenotazione per ${
                  overlapCheck.conflictingBooking.referenceName
                } dal ${formatDate(
                  overlapCheck.conflictingBooking.date
                )} al ${formatDate(overlapCheck.conflictingBooking.toDate)}`
              );
              return;
            }

            // If no overlaps, proceed with booking
            const bookingId = form.getAttribute("data-id");
            const response = await fetch("/api/book-date", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                "X-CSRF-TOKEN": csrfToken,
              },
              body: JSON.stringify({
                date: startDate,
                toDate: endDate,
                morning: morning,
                fullDay: fullDay,
                idBookingRequest: bookingId,
              }),
            });

            if (response.ok) {
              alert("Prenotazione aggiornata correttamente!");
              window.location.reload();
            } else {
              alert("Errore durante l'aggiornamento della prenotazione.");
            }
          } catch (error) {
            console.error("Error:", error);
            alert(
              "Si è verificato un errore durante la verifica della disponibilità."
            );
          }
        });
      }
    });
  }

  /**
   * This function applies multiple filters and sorting to the `allBookings` array:
   * - Filters bookings by status (e.g., "Completed", "Pending").
   * - Sorts bookings by the `createdAt` date in ascending or descending order.
   * - Filters bookings based on a search term that matches the first name, last name, email, or booking type.
   *
   * Once the filters and sorting are applied, the function passes the filtered and sorted list of bookings to the `renderCards` function to update the displayed cards.
   *
   * @returns {void}
   */
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

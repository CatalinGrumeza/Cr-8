/**
 * @file: dashboard.js
 * @author: CR-8
 * This code includes the logic for the dashboard.html page
 */

document.addEventListener("DOMContentLoaded", () => {
  const API_INFO_URL = "/api/all-info";
  const API_BOOKINGS_URL = "/api/all-bookings";
  const API_BOOKED_DATES_URL = "/api/all-booked-dates";
  const dashboardCards = document.getElementById("dashboard-cards");

  // Fetch data for both APIs
  Promise.all([
    fetch(API_INFO_URL).then((res) => res.json()),
    fetch(API_BOOKINGS_URL).then((res) => res.json()),
  ])
    .then(([infoData, bookingsData]) => {
      processAllInfoData(infoData);
      processAllBookingsData(bookingsData);
    })
    .catch((error) => console.error("Errore nel fetch:", error));

  // Fetch booked dates for the calendar
  fetch(API_BOOKED_DATES_URL)
    .then((res) => res.json())
    .then((data) => {
      initializeCalendar(data);
    })
    .catch((error) => console.error("Errore nel fetch del calendario:", error));

  /**
   * Processes the data fetched from /api/all-info to generate statistics on the "Completed" and "Pending" items.
   *
   * This function takes the fetched data and calculates the count of items with the status "Completed" and "Pending".
   * It then calls the `createCard` function to generate a card that displays these statistics with the respective counts.
   *
   * @param {Array} data - An array of objects representing the information entries. Each object contains:
   *   - status: An object that includes a `name` property (e.g., "Completed" or "Pending").
   */
  function processAllInfoData(data) {
    const completedCount = data.filter(
      (item) => item.status && item.status.name === "Completed"
    ).length;
    const pendingCount = data.filter(
      (item) => item.status && item.status.name === "Pending"
    ).length;

    createCard(
      "Statistiche Info",
      completedCount,
      pendingCount,
      0,
      ["Completed", "Pending"],
      ["#fc931c", "#ed008a"] // Orange and Purple colors
    );
  }

  /**
   * Processes the data fetched from /api/all-bookings to generate statistics on the "Completed", "Pending", and "Cancelled" bookings.
   *
   * This function processes the booking data by filtering and calculating the count of items with the status "Completed", "Pending", and "Cancelled".
   * It then calls the `createCard` function to generate a card displaying these statistics with the respective counts.
   *
   * @param {Array} data - An array of objects representing the booking entries. Each object contains:
   *   - status: An object that includes a `name` property (e.g., "Completed", "Pending", or "Cancelled").
   *
   */
  function processAllBookingsData(data) {
    const completedCount = data.filter(
      (item) => item.status && item.status.name === "Completed"
    ).length;
    const pendingCount = data.filter(
      (item) => item.status && item.status.name === "Pending"
    ).length;
    const cancelledCount = data.filter(
      (item) => item.status && item.status.name === "Cancelled"
    ).length;

    createCard(
      "Statistiche Prenotazioni",
      completedCount,
      pendingCount,
      cancelledCount,
      ["Completed", "Pending", "Cancelled"],
      ["#fc931c", "#ed008a", "#3c3c3b"] // Orange, Purple, black colors
    );
  }

  /**
   * Creates and inserts a card containing a pie chart with statistics for completed, pending, and cancelled items.
   *
   * This function generates a statistics card, displaying counts for "Completed", "Pending", and "Cancelled" items along with a pie chart visualizing these counts.
   * It creates an HTML structure for the card, inserts it into the dashboard, and then renders a pie chart using the Chart.js library.
   *
   * @param {string} title - The title to display at the top of the card (e.g., "Statistiche Prenotazioni").
   * @param {number} completedCount - The count of items with the status "Completed".
   * @param {number} pendingCount - The count of items with the status "Pending".
   * @param {number} cancelledCount - The count of items with the status "Cancelled".
   * @param {Array} labels - The labels for the chart (e.g., ["Completed", "Pending", "Cancelled"]).
   * @param {Array} colors - The colors associated with each status (e.g., ["#fc931c", "#ed008a", "#3c3c3b"]).
   */
  function createCard(
    title,
    completedCount,
    pendingCount,
    cancelledCount,
    labels,
    colors
  ) {
    const cardId = `chart-${Math.random().toString(36).substring(2, 9)}`;

    // Create a data array and corresponding color array
    const dataValues = [completedCount, pendingCount, cancelledCount];
    const filteredData = dataValues.filter((value) => value > 0);
    const filteredColors = colors.filter((_, index) => dataValues[index] > 0);

    const cardHtml = `
    <div class="col-md-6">
      <div class="card">
        <div class="card-body">
          <h2>${title}</h2>
          <p class="text-center">
            <span class="text-green">Completed: ${completedCount}</span> | 
            <span class="text-red">Pending: ${pendingCount}</span>
            ${
              cancelledCount > 0
                ? ` | <span class="text-orange">Cancelled: ${cancelledCount}</span>`
                : ""
            }
          </p>
          <div class="chart-container">
            <canvas id="${cardId}"></canvas>
          </div>
        </div>
      </div>
    </div>
  `;
    dashboardCards.insertAdjacentHTML("beforeend", cardHtml);

    // Create the pie chart
    const ctx = document.getElementById(cardId).getContext("2d");
    new Chart(ctx, {
      type: "pie",
      data: {
        labels: labels.filter((_, index) => dataValues[index] > 0), // Only labels of non-zero data
        datasets: [
          {
            data: filteredData,
            backgroundColor: filteredColors,
          },
        ],
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: "top",
          },
        },
      },
    });
  }

  /**
   * Initializes the FullCalendar with customized styling and event data.
   *
   * This function sets up the FullCalendar library with a customized layout, adding events from the provided `bookedDates` array.
   * Each event is represented as a booking, with the title, start and end dates, and customized colors based on the `allDay` property.
   * The calendar is then rendered with an initial view of the month, and additional toolbar options are provided for navigation and view switching.
   *
   * @param {Array} bookedDates - An array of booked date objects, each containing booking details such as reference name, start date, end date, and allDay flag.
   *
   */
  function initializeCalendar(bookedDates) {
    if (typeof FullCalendar === "undefined") {
      console.error(
        "FullCalendar non è definito. Verifica che sia stato caricato correttamente."
      );
      return;
    }

    const calendarEl = document.getElementById("calendar");
    const events = bookedDates.map((booking) => ({
      title: booking.referenceName,
      start: booking.date,
      end: booking.toDate
        ? new Date(new Date(booking.toDate).getTime() + 24 * 60 * 60 * 1000)
            .toISOString()
            .split("T")[0]
        : null,
      allDay: true,
      backgroundColor: booking.allDay ? "#fc931c" : "#ed008a", // Using Aurora theme colors
      borderColor: "#fc931c",
    }));

    const calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: "dayGridMonth",
      headerToolbar: {
        left: "prev,next today",
        center: "title",
        right: "dayGridMonth,timeGridWeek,timeGridDay",
      },
      events: events,
    });

    calendar.render();
  }
});

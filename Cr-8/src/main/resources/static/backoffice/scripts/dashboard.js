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

  // Process data from /api/all-info
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

  // Process data from /api/all-bookings
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

  // Function to create a card with a pie chart
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
          <h5 class="card-title text-center">${title}</h5>
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

  // Initialize the FullCalendar with customized styling
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

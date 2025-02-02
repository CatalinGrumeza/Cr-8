/**
 * @file: all-info.js
 * @author: CR-8
 * This code includes the logic for the all-info.html page
 */

fetch("/csrf-token")
  .then((response) => response.json())
  .then((data) => {
    csrfToken = data.token;
  });

document.addEventListener("DOMContentLoaded", function () {
  const container = document.getElementById("info-container");
  const dateFilter = document.getElementById("date-filter");
  const statusFilter = document.getElementById("status-filter");
  const searchBar = document.getElementById("search-bar");

  if (!container) {
    console.error(
      "Error: The container element with id 'info-container' is missing."
    );
    return;
  }

  let allData = [];

  // Fetch data from the API
  fetch("/api/all-info")
    .then((response) => response.json())
    .then((data) => {
      allData = data;
      renderCards(data);
    })
    .catch((error) => console.error("Error fetching data:", error));

  /**
   * Formats a date string into a human-readable date and time format.
   *
   * @param {string} dateString - The date string to be formatted.
   * @returns {string} A formatted date and time string.
   */
  function formatDateTime(dateString) {
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
    const year = date.getFullYear();
    const month = String(monthNames[date.getMonth()]);
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    return `${day} ${month} ${year} alle ${hours}:${minutes}`;
  }

  /**
   * Renders a list of information cards and displays them in the container.
   *
   * This function dynamically creates HTML cards based on the data provided,
   * displays key information such as name, email, phone number, request text,
   * and status, and provides a button to toggle the status between "Completed"
   * and "Pending". It also updates the status in the backend and reflects
   * changes in the UI in real-time.
   *
   * @param {Array} data - An array of objects representing the data to be displayed on the cards.
   * Each object contains the following properties:
   *   - id: The unique identifier of the information entry.
   *   - status: The current status of the information (either "Completed" or "Pending").
   *   - reference: An object containing the reference details, including firstName, lastName, email, and phoneNumber.
   *   - text: The request text associated with the information.
   *   - createdAt: The date when the information was created.
   *
   */
  function renderCards(data) {
    container.innerHTML = ""; // Clear the container
    data.forEach((info) => {
      const card = document.createElement("div");
      card.classList.add("card", "card-row");

      const statusName = info.status.name;
      const reference = info.reference;
      const firstName = reference.firstName;
      const lastName = reference.lastName;
      const email = reference.email;
      const phoneNumber = reference.phoneNumber;
      const text = info.text;
      const date = formatDateTime(info.createdAt);

      // Create card content
      card.innerHTML = `
                <div class="info-content">
                    <h2>${lastName} ${firstName}</h5>
                    <p><span class="bold">Email</span>: ${email}</p>
                    <p><span class="bold">Cellulare</span>: ${phoneNumber}</p>
                    <p><span class="bold">Richiesta</span>: ${text}</p>
                    <p><span class="bold">Stato</span><span class="info-status">: ${statusName}</span></p>
                    <div class="btn-container">
                    <button class="btn btn-complete ${
                      statusName === "Completed" ? "btn-green" : "btn-red"
                    }" 
                    data-id="${info.id}" data-status="${statusName}">
                    ${
                      statusName === "Completed"
                        ? "Completato"
                        : "Da Revisionare"
                    }
                    </button>
                   </div>
                    <div class="separator"></div>
                    <p class="center"><span class="bold">Ricevuta</span>: ${date}</p>
                </div>
            `;

      // Append card to container
      container.appendChild(card);

      // Add event listener to the button
      const toggleButton = card.querySelector(".btn-complete");
      toggleButton.addEventListener("click", function () {
        const infoId = this.getAttribute("data-id");
        const currentStatus = this.getAttribute("data-status");
        const newStatus =
          currentStatus === "Completed" ? "Pending" : "Completed";

        // Send request to update the status
        fetch(`/api/update-info-status?infoId=${infoId}&status=${newStatus}`, {
          method: "POST",
          headers: {
            "X-CSRF-TOKEN": csrfToken, // Add the token to the header
          },
        })
          .then((response) => {
            if (response.ok) {
              // Update the UI in real-time
              const statusElement = card.querySelector(".info-status");
              statusElement.textContent = newStatus;

              // Update button label and data-status attribute
              toggleButton.textContent =
                newStatus === "Completed" ? "Completato" : "Da Revisionare";
              toggleButton.setAttribute("data-status", newStatus);

              // Update button color
              if (newStatus === "Completed") {
                toggleButton.classList.remove("btn-red");
                toggleButton.classList.add("btn-green");
              } else {
                toggleButton.classList.remove("btn-green");
                toggleButton.classList.add("btn-red");
              }

              // Update the data in allData
              const updatedInfo = allData.find((item) => item.id === info.id);
              if (updatedInfo) {
                updatedInfo.status.name = newStatus;
              }
            } else {
              alert("Failed to update status.");
            }
          })
          .catch((error) => console.error("Error:", error));
      });
    });
  }

  /**
   * Filters and sorts the data based on the selected criteria (status, date, and search term).
   *
   * @param {Array} allData - An array of objects containing the data to be filtered and sorted.
   * Each object contains the following properties:
   *   - id: The unique identifier of the information entry.
   *   - status: An object representing the status of the information (with a `name` property).
   *   - reference: An object containing the reference details, including firstName, lastName, and email.
   *   - createdAt: The date when the information was created.
   *
   * After filtering and sorting the data, the function calls `renderCards` to display the results.
   */
  function filterAndSortData() {
    let filteredData = [...allData];

    // Filter by status
    const selectedStatus = statusFilter.value;
    if (selectedStatus !== "all") {
      filteredData = filteredData.filter(
        (info) => info.status.name === selectedStatus
      );
    }

    // Sort by date
    const sortOrder = dateFilter.value;
    filteredData.sort((a, b) => {
      const dateA = new Date(a.createdAt);
      const dateB = new Date(b.createdAt);
      return sortOrder === "asc" ? dateA - dateB : dateB - dateA;
    });

    // Filter by search term
    const searchTerm = searchBar.value.toLowerCase();
    if (searchTerm) {
      filteredData = filteredData.filter((info) => {
        const firstName = info.reference.firstName.toLowerCase();
        const lastName = info.reference.lastName.toLowerCase();
        const email = info.reference.email.toLowerCase();
        return (
          firstName.includes(searchTerm) ||
          lastName.includes(searchTerm) ||
          email.includes(searchTerm)
        );
      });
    }

    // Render the filtered and sorted data
    renderCards(filteredData);
  }

  // Add event listeners to filters and search bar
  dateFilter.addEventListener("change", filterAndSortData);
  statusFilter.addEventListener("change", filterAndSortData);
  searchBar.addEventListener("input", filterAndSortData);
});

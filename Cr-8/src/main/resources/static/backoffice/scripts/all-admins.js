/**
 * @file: all-admins.js
 * @author: CR-8
 * This code includes the logic for the all-admins.html page
 */

fetch("/csrf-token")
  .then((response) => response.json())
  .then((data) => {
    csrfToken = data.token;
  });

document.addEventListener("DOMContentLoaded", function () {
  const container = document.getElementById("admin-container");
  const searchBar = document.getElementById("search-bar");

  if (!container) {
    console.error(
      "Error: The container element with id 'admin-container' is missing."
    );
    return;
  }

  let allAdmins = [];

  // Fetch data from the API
  fetch("/api/super/get-all-admin")
    .then((response) => response.json())
    .then((data) => {
      allAdmins = data;
      renderCards(data);
    })
    .catch((error) => console.error("Error fetching data:", error));

  /**
   * Renders admin cards in the specified container.
   * For each admin object in the data array:
   * - Creates a card element displaying the admin's name, email, code, and role.
   * - If the role is not "SUPER_ADMIN", adds a delete button to the card.
   * - The delete button sends a DELETE request to remove the admin and updates the card list upon success.
   *
   * @param {Array<Object>} data - Array of admin objects to render. Each object must include:
   *   - {number} id - The unique identifier of the admin.
   *   - {string} name - The name of the admin.
   *   - {string} email - The email address of the admin.
   *   - {string} code - A code associated with the admin.
   *   - {Object} role - An object containing the admin's role information.
   *     - {string} role.name - The name of the role (e.g., "ADMIN", "SUPER_ADMIN").
   * @returns {void}
   */
  function renderCards(data) {
    container.innerHTML = ""; // Clear the container
    data.forEach((admin) => {
      const card = document.createElement("div");
      card.classList.add("card", "card-row");

      const { id, name, email, code, role } = admin;

      // Create card content
      card.innerHTML = `
              <div>
                  <h2>${name}</h2>
                  <p><span class="bold">Email</span>: ${email}</p>
                  <p><span class="bold">Codice</span>: ${code}</p>
                  <p><span class="bold">Ruolo</span>: ${role.name}</p>
                  <div class="btn-container"></div>
              </div>
          `;

      // Append card to container
      container.appendChild(card);

      // Add delete button only if the role is not SUPER_ADMIN
      if (role.name !== "SUPER_ADMIN") {
        const deleteButton = document.createElement("button");
        deleteButton.classList.add("btn", "btn-complete", "btn-red");
        deleteButton.textContent = "Elimina";
        deleteButton.setAttribute("data-id", id);

        card.querySelector(".btn-container").appendChild(deleteButton);

        // Add event listener to the delete button
        deleteButton.addEventListener("click", function () {
          const adminId = this.getAttribute("data-id");

          if (confirm(`Sei sicuro di voler eliminare questo ADMIN?`)) {
            // Send request to delete the admin
            fetch(`/api/super/delete-admin?id=${adminId}`, {
              method: "DELETE",
              headers: {
                "Content-Type": "application/json",
                "X-CSRF-TOKEN": csrfToken, // Add the token to the header
              },
            })
              .then((response) => {
                if (response.ok) {
                  alert("Admin eliminato con successo.");
                  allAdmins = allAdmins.filter((admin) => admin.id != adminId);
                  renderCards(allAdmins);
                } else {
                  alert("Errore durante l'eliminazione dell'admin.");
                }
              })
              .catch((error) => console.error("Error:", error));
          }
        });
      }
    });
  }

  /**
   * Filters the list of admins based on the search term entered in the search bar.
   * The filter is applied by checking if the admin's name or email contains the search term (case-insensitive).
   * The filtered list is then rendered using the `renderCards` function.
   *
   * @returns {void}
   */
  function filterAdmins() {
    const searchTerm = searchBar.value.toLowerCase();
    const filteredAdmins = allAdmins.filter((admin) => {
      return (
        admin.name.toLowerCase().includes(searchTerm) ||
        admin.email.toLowerCase().includes(searchTerm)
      );
    });

    renderCards(filteredAdmins);
  }

  // Add event listener to the search bar
  searchBar.addEventListener("input", filterAdmins);
});

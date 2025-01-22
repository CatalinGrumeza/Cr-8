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

  // Function to render admin cards
  function renderCards(data) {
    container.innerHTML = ""; // Clear the container
    data.forEach((admin) => {
      const card = document.createElement("div");
      card.classList.add("card", "card-row");

      const { id, name, email, code, role } = admin;

      // Create card content
      card.innerHTML = `
              <div>
                  <h2 class="card-title">${name}</h2>
                  <p><span class="bold">Email</span>: ${email}</p>
                  <p><span class="bold">Codice</span>: ${code}</p>
                  <p><span class="bold">Ruolo</span>: ${role.name}</p>
                  <div class="btn-container">
                      <button class="btn-complete btn-red" data-id="${id}">
                          Elimina
                      </button>
                  </div>
              </div>
          `;

      // Append card to container
      container.appendChild(card);

      // Add event listener to the delete button
      const deleteButton = card.querySelector(".btn-complete");
      deleteButton.addEventListener("click", function () {
        const adminId = this.getAttribute("data-id");

        if (confirm(`Sei sicuro di voler eliminare questo ADMIN?`)) {
          // Send request to delete the admin
          fetch(`/api/super/delete-admin?id=${adminId}`, {
            method: "DELETE",
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
    });
  }

  // Function to filter admins by search term
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

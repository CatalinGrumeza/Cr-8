let csrfToken = null;

// Fetch CSRF token on page load
fetch("/csrf-token")
  .then((response) => response.json())
  .then((data) => {
    csrfToken = data.token;
  })
  .catch((error) => console.error("Error fetching CSRF token:", error));

const logoutBtn = document.getElementById("logout");

const gestioneAdminLi = document.getElementById("gestioneAdmin");

if (sessionStorage.getItem("adminRole") === "ADMIN") {
  gestioneAdminLi.className = "hidden";
}

logoutBtn.addEventListener("click", function (event) {
  event.preventDefault();

  fetch("/logout", {
    method: "POST",
    credentials: "include",
    headers: {
      "X-CSRF-TOKEN": csrfToken,
    },
  })
    .then((response) => {
      if (response.ok) {
        sessionStorage.clear();
        window.location.href = "/?logout";
      }
    })
    .catch((error) => {
      console.error("Logout failed:", error);
    });
});

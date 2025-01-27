/**
 * @file: register.js
 * @author: CR-8
 * This code includes the logic for the create-admin.html page
 */

fetch("/csrf-token")
  .then((response) => response.json())
  .then((data) => {
    csrfToken = data.token;
  });

document
  .getElementById("registerForm")
  .addEventListener("submit", async function (event) {
    event.preventDefault();

    const formData = new FormData(this);

    try {
      const response = await fetch("/api/super/register", {
        method: "POST",
        headers: {
          "X-CSRF-TOKEN": csrfToken, // Add the token to the header
        },
        body: formData,
      });

      const result = await response.text(); // Get the response message

      // Display the message in the popup
      const popupMessage = document.getElementById("popupMessage");
      popupMessage.textContent = result;

      // Show the popup
      const popup = document.getElementById("popup");
      popup.style.display = "flex";

      if (response.ok) {
        // Redirect to the dashboard after a short delay
        setTimeout(() => {
          window.location.href = "/super/all-admins";
        }, 2000); // 2 seconds delay
      }
    } catch (error) {
      console.error("Error:", error);
      alert("Qualcosa è andato storto, riprova più tardi."); // Handle network errors
    }
  });

// Close the popup when the close button is clicked
document.getElementById("closePopup").addEventListener("click", function () {
  const popup = document.getElementById("popup");
  popup.style.display = "none";
});

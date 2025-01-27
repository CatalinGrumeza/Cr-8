/**
 * @file: new-password.js
 * @author: CR-8
 * This code includes the logic for the new-password.html page
 */

/**
 * Validates a password against a regex pattern.
 * The password must meet the following criteria:
 * - At least one lowercase letter.
 * - At least one uppercase letter.
 * - At least one digit.
 * - At least one special character (@, $, !, %, *, ?, &).
 * - Minimum length of 8 characters.
 * @param {string} password - The password to validate.
 * @returns {boolean} True if the password matches the criteria, false otherwise.
 */
function validatePasswordRegex(password) {
  // La regex per la password
  const regex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  // Testa la password con la regex
  return regex.test(password);
}

// Esempio di utilizzo
document
  .getElementById("confirm-password")
  .addEventListener("focus", function (event) {
    event.preventDefault(); // Evita il comportamento predefinito del form
    const passwordError = document.getElementById("passwordError");
    const password = document.getElementById("password").value;

    if (!validatePasswordRegex(password)) {
      passwordError.style.display = "block";
    } else {
      passwordError.style.display = "none";
    }
  });

/**
 * Validates and updates a user's password by confirming the input fields and making an API request.
 *
 * The function performs the following steps:
 * - Ensures both password fields are filled.
 * - Confirms the two passwords match.
 * - Retrieves a CSRF token via an API call.
 * - Sends a POST request to update the password using the provided code and new password.
 * - Displays success or error messages in the UI.
 * - Redirects to the login page if the update is successful.
 *
 * @returns {Promise<void>} A promise that resolves when the function completes.
 */
async function validatePassword() {
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirm-password").value;
  const message = document.getElementById("message");
  const code = localStorage.getItem("code");

  // Validate inputs
  if (!password || !confirmPassword) {
    message.textContent = "Devi riempire entrambi i campi!";
    message.style.color = "red";
    return;
  }

  if (password !== confirmPassword) {
    message.textContent = "Le password non coincidono!";
    message.style.color = "red";
    return;
  }

  try {
    // Fetch CSRF token
    const csrfResponse = await fetch("/csrf-token");
    const csrfData = await csrfResponse.json();
    const csrfToken = csrfData.token;

    // Make the POST request to update the password
    const url = `/api/pub/newpassword?code=${code}&password=${encodeURIComponent(
      password
    )}`;
    const res = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "X-CSRF-TOKEN": csrfToken, // Add the token to the header
      },
    });

    if (!res.ok) {
      const errorMessage = await res.text();
      throw new Error(errorMessage || "Impossibile aggiornare la password");
    }

    // Success: Password updated
    message.style.color = "green";
    message.textContent =
      "Nuova password impostata! Reinderizzamento a Login...";

    // Redirect to login after a short delay
    setTimeout(() => {
      window.location.href = "/login";
    }, 2000);
  } catch (error) {
    console.error("Error:", error);
    message.style.color = "red";
    message.textContent =
      error.message || "Errore durante l'aggiornamento della password";
  }
}

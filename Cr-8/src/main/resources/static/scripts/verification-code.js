/**
 * @file: verification-code.js
 * @author: CR-8
 * This code includes the logic for the verification-code.html page
 */

// Select the button and input field
const email = localStorage.getItem("userEmail");
const messageAlert = document.getElementById("message-code");
const text = `Inserisci il codice a 6 cifre che hai ricevuto all'indirizzo ${email}`;
document.getElementById("email-display").textContent = text;
const sendButton = document.getElementById("btn");
const resendLink = document.getElementById("resendLink");
// Add an event listener for the click on the button
sendButton.addEventListener("click", async () => {
  const inputs = document.querySelectorAll(".code-inputs input");
  const code = Array.from(inputs)
    .map((input) => input.value)
    .join("");
  const Upper = code.toUpperCase();
  console.log(Upper);

  const url = `/api/pub/verification-code?code=${Upper}`;
  try {
    // Fetch CSRF token
    const csrfResponse = await fetch("/csrf-token");
    const csrfData = await csrfResponse.json();
    const csrfToken = csrfData.token; // Define csrfToken here

    // Make the GET request with the CSRF token
    const res = await fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "X-CSRF-TOKEN": csrfToken,
      },
    });

    const message = await res.text();

    if (!res.ok) {
      messageAlert.style.color = "red";
      messageAlert.textContent = "Codice errato!";

      clearTimeout(500);
    } else {
      localStorage.setItem("code", Upper);
      window.location.href = "/newpassword"; // Redirect on success
    }
  } catch (error) {
    console.error("Error:", error);
    alert("Errore durante la verifica del codice."); // Display a generic error message
  }
});
resendLink.addEventListener("click", async (event) => {
  event.preventDefault(); // Evita il comportamento predefinito del link
  if (!email) {
    console.error("Impossibile reinviare il codice, nessuna mail inserita");
    return;
  }

  const url2 = `/api/pub/forget-password?email=${email}`;
  try {
    // Fetch CSRF token
    const csrfResponse = await fetch("/csrf-token");
    const csrfData = await csrfResponse.json();
    const csrfToken = csrfData.token; // Define csrfToken here
    const res2 = await fetch(url2, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "X-CSRF-TOKEN": csrfToken, // Use the token in the header
      },
    });

    if (res2.ok) {
      alert("Codice reinviato con successo!");
    } else {
      console.error("Reinvio del codice fallito:", res2.status);
    }
  } catch (error) {
    console.error("Errore durante il reinvio del codice:", error);
  }
});

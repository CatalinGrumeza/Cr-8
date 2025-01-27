// Seleziona il bottone e il campo di input
const sendButton = document.getElementById("send-button");
const emailInput = document.getElementById("email-input");
const emailnotvalid = document.getElementById("message");
// Aggiungi un gestore di eventi al click sul bottone
sendButton.addEventListener("click", async () => {
  const email = emailInput.value; // Ottieni il valore dell'email
  if (!email) {
    console.log("Nessuna email inserita");
    return;
  }

  console.log("Email inserita:", email);

  const url = `/api/pub/forget-password?email=${encodeURIComponent(email)}`;

  try {
    // Fetch CSRF token
    const csrfResponse = await fetch("/csrf-token");
    const csrfData = await csrfResponse.json();
    const csrfToken = csrfData.token;

    // Make the POST request
    const res = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "X-CSRF-TOKEN": csrfToken, // Add the token to the header
      },
    });

    if (!res.ok) {
      emailnotvalid.style.color = "red";
      emailnotvalid.textContent = "Email not registered ! !";
    } else {
      // Handle the response as plain text
      const responseText = await res.text();
      console.log("Success:", responseText);

      localStorage.setItem("userEmail", email);

      setTimeout(() => {
        window.location.href = "/code-verification";
      }, 100);
    }
  } catch (error) {
    console.error("Error:", error);
  }
});


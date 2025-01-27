// Select the button and input field
const email = localStorage.getItem("userEmail");
const text = `Please enter the 4 digit code sent to ${email}`;
document.getElementById("email-display").textContent = text;
const sendButton = document.getElementById("btn");

// Add an event listener for the click on the button
sendButton.addEventListener("click", async () => {
  const inputs = document.querySelectorAll(".code-inputs input");
  const code = Array.from(inputs)
    .map((input) => input.value)
    .join("");
  const Upper = code.toUpperCase();
  console.log(Upper);

  const url = `/api/pub/code?code=${Upper}`;
  try {
    // Fetch CSRF token
    const csrfResponse = await fetch('/csrf-token');
    const csrfData = await csrfResponse.json();
    const csrfToken = csrfData.token; // Define csrfToken here

    // Make the GET request with the CSRF token
    const res = await fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        'X-CSRF-TOKEN': csrfToken, // Use the token in the header
      },
    });

    const message = await res.text();

    if (!res.ok) {
      alert(message);
    } else {
      localStorage.setItem("code", Upper);
      window.location.href = "/newpassword"; // Redirect on success
    }
  } catch (error) {
    console.error("Error:", error);
    alert("An error occurred while trying to verify the code."); // Display a generic error message
  }
});
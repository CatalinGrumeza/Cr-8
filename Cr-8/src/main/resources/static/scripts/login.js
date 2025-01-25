document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("loginForm");

  loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
      const response = await fetch("/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({ email, password }),
      });

      const message = await response.text();

      if (response.ok) {
        // convertire oggetto JSON in oggetto javascript con proprietà e salvare i valori in localStorage da mostrare nel logout page
        window.location.href = "/dashboard";
      } else {
        alert("Credenziali errate. Riprova.");
      }
    } catch (error) {
      console.error("Errore durante il login:", error);
      alert("Si è verificato un errore. Riprova più tardi.");
    }
  });
});

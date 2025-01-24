document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
  
    loginForm.addEventListener("submit", async (event) => {
      event.preventDefault(); // Evita il comportamento predefinito del form
  
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
          
  
        if (response.ok) {
          // Login avvenuto con successo
          window.location.href = "/dashboard"; // Reindirizza alla dashboard
        } else {
          // Mostra un messaggio di errore
          alert("Credenziali errate. Riprova.");
        }
      } catch (error) {
        console.error("Errore durante il login:", error);
        alert("Si è verificato un errore. Riprova più tardi.");
      }
    });
  });
  
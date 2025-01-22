document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("loginForm");
  
    form.addEventListener("submit", async (event) => {
      event.preventDefault(); // Impedisce il comportamento predefinito del form (ricaricare la pagina)
  
      // Ottieni i valori dei campi email e password
      const email = document.getElementById("nome").value;
      const password = document.getElementById("cognome").value;
  
      // Crea un oggetto con i dati del form
      const loginData = {
        email: email,
        password: password,
      };
  
      try {
        // Invia una richiesta POST a /login
        const response = await fetch("/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(loginData),
        });
  
        // Gestione della risposta
        if (response.ok) {
          const data = await response.json();
          console.log("Login effettuato con successo:", data);
          // Esempio: reindirizza l'utente alla dashboard
          window.location.href = "./backoffice/dashboard.html";
        } else {
          console.error("Errore durante il login:", response.status);
          alert("Login fallito. Controlla le tue credenziali.");
        }
      } catch (error) {
        console.error("Errore durante la richiesta:", error);
        alert("Si è verificato un errore. Riprova più tardi.");
      }
    });
  });
  
// Fetch CSRF token on page load
document.addEventListener("DOMContentLoaded", () => {
    fetch('/csrf-token')
        .then(response => response.json())
        .then(data => {
            document.getElementById('csrfToken').value = data.token;
        })
        .catch(error => {
            console.error("Error fetching CSRF token:", error);
        });

    const loginForm = document.getElementById("loginForm");

    loginForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const csrfToken = document.getElementById('csrfToken').value;

        try {
            const response = await fetch("/login", {
                method: "POST",
                headers: {
                    'X-CSRF-TOKEN': csrfToken, // Add the token to the header
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({ email, password }),
            });

            if (response.ok) {
                alert(response.text())
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
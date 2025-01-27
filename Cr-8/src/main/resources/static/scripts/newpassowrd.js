
function validatePasswordRegex(password) {
    // La regex per la password
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    
    // Testa la password con la regex
    return regex.test(password);
}

// Esempio di utilizzo
document.getElementById('confirm-password').addEventListener('focus', function(event) {
    event.preventDefault(); // Evita il comportamento predefinito del form
	const passwordError = document.getElementById('passwordError');
    const password = document.getElementById('password').value;
    
    if (!validatePasswordRegex(password)) {
      passwordError.style.display="block";
    }else{
		passwordError.style.display="none";
	}
});
async function validatePassword() {
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirm-password").value;
  const message = document.getElementById("message");
  const code = localStorage.getItem("code");

  // Validate inputs
  if (!password || !confirmPassword) {
    message.textContent = "Both fields are required!";
    message.style.color = "red";
    return;
  }

  if (password !== confirmPassword) {
    message.textContent = "Passwords do not match!";
    message.style.color = "red";
    return;
  }

  try {
    // Fetch CSRF token
    const csrfResponse = await fetch('/csrf-token');
    const csrfData = await csrfResponse.json();
    const csrfToken = csrfData.token;

    // Make the POST request to update the password
    const url = `/api/pub/newpassword?code=${code}&password=${encodeURIComponent(password)}`;
    const res = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': csrfToken, // Add the token to the header
      },
    });

    if (!res.ok) {
      const errorMessage = await res.text();
      throw new Error(errorMessage || "Failed to update password");
    }

    // Success: Password updated
    message.style.color = "green";
    message.textContent = "Password saved successfully! Redirecting to login...";

    // Redirect to login after a short delay
    setTimeout(() => {
      window.location.href = "/login";
    }, 2000);

  } catch (error) {
    console.error("Error:", error);
    message.style.color = "red";
    message.textContent = error.message || "An error occurred while updating the password.";
  }
}
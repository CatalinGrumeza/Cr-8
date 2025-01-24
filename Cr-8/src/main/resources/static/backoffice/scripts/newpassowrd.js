function validatePassword() {
  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirm-password").value;
  const message = document.getElementById("message");
  const code = localStorage.getItem("code");
  if (!password || !confirmPassword) {
    message.textContent = "Both fields are required!";
    return;
  }

  if (password !== confirmPassword) {
    message.textContent = "Passwords do not match!";
  } else {
    try {
      const url = `/api/pub/newpassword?code=${code}&password=${password}`;
      const res = fetch(url, {
        method:'POST',
        headers:{
          'Content-Type':'application/json'
        }
      });
      if(!res.ok){}
      
    } catch  {
      
    }
    message.style.color = "green";
    message.textContent = "Passwords match! Saving...";
    // Simulate saving
    setTimeout(() => {
      alert("Password saved successfully!");
      message.textContent = "";
      window.location.href = "/login";
    }, 1000);
  }
}


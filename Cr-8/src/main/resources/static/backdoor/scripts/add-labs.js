document.getElementById('additionForm').addEventListener('submit', async function (event) {
    event.preventDefault(); // Prevent the default form submission

    const formData = new FormData(this); // Get form data
    const data = Object.fromEntries(formData.entries());
	data.targetDescription = data.targetDescription.split(",").map(item => item.trim()); //converts a string separated by commas into an array
	
	Object.keys(data).forEach(key => {
		if (data[key] == "") {
			data[key] = null;
		}
	});
	
    try {
        const response = await fetch('http://localhost:8080/api/add-new-labs', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
       });

        const result = await response.text(); // Get the response message

        // Display the message in the popup
        const popupMessage = document.getElementById('popupMessage');
        popupMessage.textContent = result;

        // Show the popup
        const popup = document.getElementById('popup');
        popup.style.display = 'flex';

        if (response.ok) {
            // Redirect to the dashboard after a short delay
            setTimeout(() => {
                window.location.href = '/dashboard/add-labs';
            }, 2000); // 2 seconds delay
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred. Please try again.'); // Handle network errors
    }
});

// Close the popup when the close button is clicked
document.getElementById('closePopup').addEventListener('click', function () {
    const popup = document.getElementById('popup');
    popup.style.display = 'none';
});
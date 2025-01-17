document.addEventListener('DOMContentLoaded', function () {
    const container = document.getElementById('info-container');

    if (!container) {
        console.error("Error: The container element with id 'info-container' is missing.");
        return;
    }

    // Fetch data from the API
    fetch('http://localhost:8080/api/all-info')
        .then(response => response.json())
        .then(data => {
            data.forEach(info => {
                const card = document.createElement('div');
                card.classList.add('card', 'card-row');

                const statusName = info.status.name;
                const reference = info.reference;
                const firstName = reference.firstName;
                const lastName = reference.lastName;
                const email = reference.email;
                const phoneNumber = reference.phoneNumber;
                const text=info.text;

                // Create card content
                card.innerHTML = `
                    <div>
                        <h5 class="card-title">Info ID: ${info.id}</h5>
                        <p><strong>Status:</strong> <span class="info-status">${statusName}</span></p>
                        <p><strong>Reference:</strong> ${firstName} ${lastName}, 
                        Email: ${email}, Phone: ${phoneNumber}</p>
                        <br>
                        <p><strong>Messaggio</strong>: ${text}</p>
                        <button class="btn-complete" data-id="${info.id}" data-status="${statusName}">
                            ${statusName === 'Completed' ? 'Segna come non completo' : 'Segna come completato'}
                        </button>
                    </div>
                `;

                // Append card to container
                container.appendChild(card);

                // Add event listener to the button
                const toggleButton = card.querySelector('.btn-complete');
                toggleButton.addEventListener('click', function () {
                    const infoId = this.getAttribute('data-id');
                    const currentStatus = this.getAttribute('data-status');
                    const newStatus = currentStatus === 'Completed' ? 'Pending' : 'Completed';

                    // Send request to update the status
                    fetch(`http://localhost:8080/api/update-info-status?infoId=${infoId}&status=${newStatus}`, {
                        method: 'POST'
                    })
                        .then(response => {
                            if (response.ok) {
                                // Update the UI in real-time
                                const statusElement = card.querySelector('.info-status');
                                statusElement.textContent = newStatus;

                                // Update button label and data-status attribute
                                toggleButton.textContent = newStatus === 'Completed' 
                                    ? 'Segna come non completo' 
                                    : 'Segna come completato';
                                toggleButton.setAttribute('data-status', newStatus);
                            } else {
                                alert('Failed to update status.');
                            }
                        })
                        .catch(error => console.error('Error:', error));
                });
            });
        })
        .catch(error => console.error('Error fetching data:', error));
});

document.addEventListener('DOMContentLoaded', function () {
    const container = document.getElementById('info-container');
    const dateFilter = document.getElementById('date-filter');
    const statusFilter = document.getElementById('status-filter');
    const searchBar = document.getElementById('search-bar');

    if (!container) {
        console.error("Error: The container element with id 'info-container' is missing.");
        return;
    }

    let allData = []; // Store all fetched data

    // Fetch data from the API
    fetch('http://localhost:8080/api/all-info')
        .then(response => response.json())
        .then(data => {
            allData = data; // Save the data
            renderCards(data); // Render cards initially
        })
        .catch(error => console.error('Error fetching data:', error));

    // Function to format date and time
    function formatDateTime(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${year}-${month}-${day} ${hours}:${minutes}`;
    }

    // Function to render cards
    function renderCards(data) {
        container.innerHTML = ''; // Clear the container
        data.forEach(info => {
            const card = document.createElement('div');
            card.classList.add('card', 'card-row');

            const statusName = info.status.name;
            const reference = info.reference;
            const firstName = reference.firstName;
            const lastName = reference.lastName;
            const email = reference.email;
            const phoneNumber = reference.phoneNumber;
            const text = info.text;
            const date = formatDateTime(info.createdAt); // Format the date

            // Create card content
            card.innerHTML = `
                <div>
                    <h5 class="card-title">Info ID: ${info.id}</h5>
                    <p><strong>Status:</strong> <span class="info-status">${statusName}</span></p>
                    <p><strong>Date:</strong> ${date}</p>
                    <p><strong>Reference:</strong> ${firstName} ${lastName}, 
                    Email: ${email}, Phone: ${phoneNumber}</p>
                    <br>
                    <p><strong>Messaggio</strong>: ${text}</p>
                    <button class="btn-complete ${statusName === 'Completed' ? 'btn-red' : 'btn-green'}" 
                            data-id="${info.id}" data-status="${statusName}">
                        ${statusName === 'Completed' ? 'Segna come non completato' : 'Segna come completato'}
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
                                ? 'Segna come non completato' 
                                : 'Segna come completato';
                            toggleButton.setAttribute('data-status', newStatus);

                            // Update button color
                            if (newStatus === 'Completed') {
                                toggleButton.classList.remove('btn-green');
                                toggleButton.classList.add('btn-red');
                            } else {
                                toggleButton.classList.remove('btn-red');
                                toggleButton.classList.add('btn-green');
                            }

                            // Update the data in allData
                            const updatedInfo = allData.find(item => item.id === info.id);
                            if (updatedInfo) {
                                updatedInfo.status.name = newStatus;
                            }
                        } else {
                            alert('Failed to update status.');
                        }
                    })
                    .catch(error => console.error('Error:', error));
            });
        });
    }

    // Function to filter and sort data
    function filterAndSortData() {
        let filteredData = [...allData];

        // Filter by status
        const selectedStatus = statusFilter.value;
        if (selectedStatus !== 'all') {
            filteredData = filteredData.filter(info => info.status.name === selectedStatus);
        }

        // Sort by date
        const sortOrder = dateFilter.value;
        filteredData.sort((a, b) => {
            const dateA = new Date(a.createdAt);
            const dateB = new Date(b.createdAt);
            return sortOrder === 'asc' ? dateA - dateB : dateB - dateA;
        });

        // Filter by search term
        const searchTerm = searchBar.value.toLowerCase();
        if (searchTerm) {
            filteredData = filteredData.filter(info => {
                const firstName = info.reference.firstName.toLowerCase();
                const lastName = info.reference.lastName.toLowerCase();
                const email = info.reference.email.toLowerCase();
                return firstName.includes(searchTerm) || lastName.includes(searchTerm) || email.includes(searchTerm);
            });
        }

        // Render the filtered and sorted data
        renderCards(filteredData);
    }

    // Add event listeners to filters and search bar
    dateFilter.addEventListener('change', filterAndSortData);
    statusFilter.addEventListener('change', filterAndSortData);
    searchBar.addEventListener('input', filterAndSortData);
});
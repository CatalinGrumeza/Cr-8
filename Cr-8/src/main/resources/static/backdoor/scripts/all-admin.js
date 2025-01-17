document.addEventListener('DOMContentLoaded', function () {
    const container = document.getElementById('admins-container');

    if (!container) {
        console.error("Error: The container element with id 'admins-container' is missing.");
        return;
    }

    // Fetch data from the API
    fetch('http://localhost:8080/api/super/get-all-admin')
        .then(response => response.json())
        .then(admins => {
            admins.forEach(admin => {
                const row = document.createElement('div');
                row.classList.add('row', 'mb-4');

                const adminName = admin.name;
                const adminEmail = admin.email;
                const adminRole = admin.role.name;
                const adminId = admin.id;

                // Create row content
                row.innerHTML = `
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">${adminName}</h5>
                                <p><strong>Email:</strong> ${adminEmail}</p>
                                <p><strong>Role:</strong> ${adminRole}</p>
                                ${adminRole !== 'SUPER_ADMIN' ? 
                                    `<button class="btn btn-danger btn-delete" data-id="${adminId}">
                                        Delete Admin
                                    </button>` 
                                    : ''}
                            </div>
                        </div>
                    </div>
                `;

                // Append row to container
                container.appendChild(row);

                // Add event listener to delete button (if exists)
                const deleteButton = row.querySelector('.btn-delete');
                if (deleteButton) {
                    deleteButton.addEventListener('click', function () {
                        const adminId = this.getAttribute('data-id');

                        // Send request to delete the admin
                        fetch(`http://localhost:8080/api/super/delete-admin?id=${adminId}`, {
                            method: 'DELETE'
                        })
                        .then(response => {
                            if (response.ok) {
                                alert('Admin deleted successfully');
                                row.remove(); // Remove the admin row from the UI
                            } else {
                                alert('Failed to delete admin.');
                            }
                        })
                        .catch(error => console.error('Error:', error));
                    });
                }
            });
        })
        .catch(error => console.error('Error fetching data:', error));
});

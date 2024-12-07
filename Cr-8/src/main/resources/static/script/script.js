async function fetchData() {
        try {
            // Fetching JSON data from the API
            const response = await fetch('http://localhost:8080/api/index');
            
            // Check if the response is ok (status code 200-299)
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            
            // Parsing the JSON data
            const data = await response.json();
            
            // Getting the first div in the DOM
            const firstDiv = document.querySelector('div');
            
            // Clearing the div before displaying new content
            firstDiv.innerHTML = '';

            // Iterating over each object in the data array
            data.forEach(item => {
                // Create a new div for each item
                const itemDiv = document.createElement('div');
                itemDiv.innerText = JSON.stringify(item, null, 2); // Displaying item as a string

                // Append the itemDiv to the first div
                firstDiv.appendChild(itemDiv);
            });
            
        } catch (error) {
            console.error('Failed to fetch data: ', error);
        }
    }

    // Call fetchData function when the page is loaded
    window.onload = fetchData;
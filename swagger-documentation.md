# How to Use the Swagger User Interface  

The **Swagger UI** provides a user-friendly interface for testing and interacting with the various API endpoints of our application. Follow this guide to learn how to access, explore, and use the Swagger interface effectively.  

---

## Accessing Swagger UI  

To open the Swagger UI:  

1. Start the project server.  
2. Open your web browser and navigate to [http://localhost:8080/swagger](http://localhost:8080/swagger).  
3. You’ll see a page resembling this:  
   ![Swagger UI](./immagini/swagger1.png)  

At this point, you can click on any **Endpoint** to view its details and interact with it.  

---

## Endpoint Groups  

Endpoints in the Swagger UI are organized into the following groups:  

- **Dashboard Endpoint**: Restricted to admins and callable through the dashboard.  
- **Public Endpoint**: Openly accessible to all visitors.  
- **auth-controller**: Contains endpoints for verifying authentication codes.  

![Endpoint Groups](./immagini/swagger3.png)  

---

## GET Calls  

GET requests are used to retrieve information from the server.  

### How to Send a GET Request  

1. Expand the desired endpoint by clicking on it.  
2. Press the **Try it out** button.  
   ![GET Endpoint](./immagini/swagger4.png)  

3. Click **Execute** to send the request.  
   ![Execute GET](./immagini/swagger5.png)  

4. The server’s response will appear at the bottom of the endpoint box. A **200** response code indicates success.  
   ![Response GET](./immagini/swagger6.png)  

---

## POST Calls  

POST requests are used to send data to the server.  

### How to Send a POST Request  

1. Expand the endpoint and press the **Try it out** button.  
   ![POST Endpoint](./immagini/swagger2.png)  

2. Fill in the required data:  
   - **Individual Fields**: Enter values directly into the provided input fields.  
   - **JSON Payload**: Edit the example JSON provided by Swagger.
     
   ![Field POST](./immagini/swagger7.png)  
   ![JSON POST](./immagini/swagger8.png)  

3. Press **Execute** to send the request. The server’s response will include a status code:  
   - **200**: The operation was successful.  
     ![Success POST](./immagini/swagger10.png)  
   - **400**: Client error (e.g., incorrect or improperly formatted data).  
     ![Client Error POST](./immagini/swagger11.png)  
   - **500**: Server error (e.g., unresolved internal issues).  
     ![Server Error POST](./immagini/swagger12.png)  

---

## DELETE Calls  

DELETE requests are used to remove data from the server’s database.  

### How to Send a DELETE Request  

1. Expand the endpoint and press the **Try it out** button.  
   ![DELETE Endpoint](./immagini/swagger13.png)  

2. Enter the ID of the entry you want to delete in the required field.  
   ![Field DELETE](./immagini/swagger14.png)  

3. Click **Execute** to perform the operation. The server’s response will indicate the result:  
   - **200**: The operation was successful.  
     ![Success DELETE](./immagini/swagger15.png)  
   - **400**: Client error (e.g., incorrect or missing data).  
     ![Client Error DELETE](./immagini/swagger16.png)  
   - **500**: Server error (e.g., unresolved issues in the server code).  
     ![Server Error DELETE](./immagini/swagger17.png)  

---

## Final Notes  

- Ensure that the server is running before accessing the Swagger UI.  
- Always validate your inputs before executing any request.  
- For a complete guide on using Swagger, refer to the [official documentation](https://swagger.io/tools/swagger-ui/).

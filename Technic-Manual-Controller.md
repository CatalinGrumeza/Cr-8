
```markdown
# AdminController Technical Manual

## Overview

The `AdminController` class is a Spring REST controller that manages administrative operations related to the admin entities in the application. It provides endpoints for retrieving all administrators and deleting an administrator by their ID. This controller relies on the `AdminService` for performing all business logic operations.
```
## Package Declaration

```java
package com.Cr_8.controllers;
```

## Controller Annotations

- `@RestController`: Indicates that this class is a RESTful controller.
- `@RequestMapping("/api")`: Configures a base URL of `/api` for all endpoints in this controller.

## Dependencies

This controller depends on the following components:

- `AdminService`: The service layer used for business logic related to admin entities.

## Endpoints

### 1. Get All Administrators

- **Endpoint**: `GET /api/super/get-all-admin`
- **Description**: Retrieves a list of all administrators in the system.
- **Response**: Returns a `ResponseEntity<List<Admin>>` containing a list of all admin entities.
- **Tags**: `Dashboard Endpoint`


#### Example Response:
```json
[
  {
    "id": 0,
    "name": "string",
    "email": "string",
    "password": "string",
    "code": "string",
    "role": {
      "id": 0,
      "name": "string"
    }
  }
]
```

### 2. Delete Administrator

- **Endpoint**: `DELETE /api/super/delete-admin`
- **Description**: Deletes an administrator identified by their ID. This operation can only be invoked by super admins.
- **Request Parameter**: 
  - `id` (Long): The ID of the administrator to be deleted.
- **Response**: Returns a `ResponseEntity<String>` containing a success message if deletion is successful or an error message if deletion fails.
- **Tags**: `Dashboard Endpoint`
  
#### Example Request:
```http
DELETE /api/super/delete-admin?id=1 HTTP/1.1
Host: {your-host}
```

#### Example Response (Success):
```json
"Admin eliminato con successo"
```

#### Example Response (Error):
```json
"Error message detailing why the deletion failed"
```

## Exception Handling

- If an error occurs during the deletion process (e.g., if the admin ID does not exist), a descriptive error message will be returned as part of the HTTP response.

## Access Control

- The endpoints are designed to be accessible only to admins with the appropriate roles. Specifically, the `deleteAdmin` method can only be called by super admins.


----





```markdown
# AuthController Technical Manual

## Overview

The `AuthController` class provides RESTful endpoints for handling user registration, password resets, and verification code generation. It integrates with the `AdminService` and `RoleService` to perform the necessary business logic operations.

## Package Declaration
```
```java
package com.Cr_8.controllers;
```

## Controller Annotations

- `@RestController`: Indicates that this class is a RESTful controller.
- `@RequestMapping("/api")`: Configures a base URL of `/api` for all endpoints in this controller.

## Dependencies

This controller depends on the following components:

- `AdminService`: Handles business logic related to admin entities.
- `RoleService`: Manages role-related operations.

## Endpoints

### 1. User Registration

- **Endpoint**: `POST /api/super/register`
- **Description**: Registers a new user (Admin/Super Admin) with the required fields.
- **Request**: 
  - Body: `RegisterRequestDTO` with fields:
      - `name`: User's name.
      - `email`: User's email.
- **Response**: Returns `ResponseEntity<String>` with a success message or validation errors.
- **Tags**: `Dashboard Endpoint`
- **Validation**: 
  - Password must be at least 8 characters long and include an uppercase letter, a lowercase letter, a number, and a special character.(The default password is **Cascinacaccia2025!**)
  - Regex for password validation: `^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$`


#### Example Response (Success):
```json
"Registered successfully"
```

#### Example Response (Validation Error):
```json
"[...validation error messages...]"
```

---

### 2. Generate Verification Code

- **Endpoint**: `POST /api/pub/forget-password`
- **Description**: Sends a verification code to the user’s email for password reset.
- **Request**: 
  - Query Parameter: `email` (String): The email address associated with the account.
- **Response**: Returns `ResponseEntity<String>` with a success or error message.
- **Tags**: `Public Endpoint`

#### Example Request:
```http
POST /api/pub/forget-password?email=johndoe@example.com HTTP/1.1
Host: {your-host}
```

#### Example Response (Success):
```json
"Verification code sent successfully"
```

---

### 3. Set New Password

- **Endpoint**: `POST /api/pub/newpassword`
- **Description**: Updates the user’s password using a verification code.
- **Request**: 
  - Query Parameters:
    - `code` (String): The verification code sent to the user's email.
    - `password` (String): The new password.
- **Response**: Returns `ResponseEntity<String>` with success or error message.
- **Tags**: `Public Endpoint`

#### Example Request:
```http
POST /api/pub/newpassword?code=VERIFICATION_CODE_ABC&password=NewSecurePassword123 HTTP/1.1
```

#### Example Response (Success):
```json
"Password updated successfully"
```

---

### 4. Check Verification Code

- **Endpoint**: `GET /api/pub/code`
- **Description**: Checks if a given verification code exists and is valid.
- **Request**: 
  - Query Parameter: `code` (String): The verification code to check.
- **Response**: Returns `ResponseEntity<String>` indicating the status of the verification code (valid/invalid).
  
#### Example Request:
```http
GET /api/pub/code?code=VERIFICATION_CODE_ABC HTTP/1.1
Host: {your-host}
```

#### Example Response (Invalid Code):
```json
"Codice errato!"
```

#### Example Response (Valid Code):
```json
"Valid verification code"
```

---

## Exception Handling

- Validation errors during registration will return bad request responses with error details.
- The application handles invalid verification codes by returning appropriate error messages.




```markdown
# BookingController Technical Manual

## Overview

The `BookingController` class provides RESTful API endpoints for managing booking requests, including creating, retrieving, and updating bookings. It utilizes various services to handle business logic related to bookings, emails, and statuses.
```
## Package Declaration

```java
package com.Cr_8.controllers;
```

## Controller Annotations

- `@RestController`: Indicates that this class is a RESTful controller.
- `@RequestMapping("/api")`: Configures a base URL of `/api` for all endpoints in this controller.

## Dependencies

This controller depends on the following components:

- `BookingService`: Manages booking request operations.
- `MailService`: Handles email sending for booking confirmations and notifications.
- `BookedDatesService`: Manages booked date operations.
- `StatusService`: Manages booking request statuses.

## Endpoints

### 1. Retrieve All Booking Requests

- **Endpoint**: `GET /api/all-bookings`
- **Description**: Retrieves a list of all booking requests.
- **Response**: Returns `ResponseEntity<List<BookingRequest>>` containing all booking requests.
- **Tags**: `Dashboard Endpoint`


#### Example Response:
```json
[
  {
    "id": 1,
    "dataFrom": "2025-01-25",
    "dataTo": "2025-01-25",
    "additionalDetails": "string",
    "participantNumber": 1,
    "bookType": "string",
    "numberOfDays": 0,
    "vistorType": "string",
    "status": {
      "id": 4,
      "name": "Cancelled"
    },
    "reference": {
      "id": 1,
      "firstName": "string",
      "lastName": "string",
      "phoneNumber": "string",
      "email": "string@string.com"
    },
    "bookedDate": {
      "id": 1,
      "date": null,
      "toDate": "2025-01-26",
      "dayFractions": null
    },
    "labsSet": [
      {
        "id": 3,
        "name": "Laboratorio 3 - Introduzione al tema della mafia"
      }
    ],
    "createdAt": "2025-01-25"
  },
  ...
]
```

---

### 2. Create a New Booking Request

- **Endpoint**: `POST /api/pub/create-booking`
- **Description**: Creates a new booking request and sends confirmation emails to both the user and the admin.
- **Request**: 
  - Body: `BookingFormRequest` containing booking details.
- **Response**: Returns `ResponseEntity<String>` indicating the result of the operation or validation errors.
- **Tags**: `Public Endpoint`
```json
{
  "name": "string",
  "surname": "string",
  "phone": "string",
  "email": "string",
  "numberOfDays": 0,
  "additionalDetails": "string",
  "dataFrom": "2025-01-25",
  "dataTo": "2025-01-25",
  "participantNumber": 1,
  "bookType": "string",
  "visitorType": "string",
  "labs": [
    "string"
  ],
  "createdAt": "2025-01-25"
}
```

#### Example Response (Success):
```json
"Book request created successfully!"
```

#### Example Response (Validation Error):
```json
"[...validation error messages...]"
```

---

### 3. Retrieve All Booked Dates

- **Endpoint**: `GET /api/all-booked-dates`
- **Description**: Retrieves a list of all booked dates.
- **Response**: Returns `ResponseEntity<List<BookedDTO>>` containing all booked dates.
- **Tags**: `Dashboard Endpoint`


#### Example Response:
```json
[
  {
    "idBookingRequest": 0,
    "idBookedDate": 0,
    "idReference": 0,
    "date": "2025-01-25",
    "toDate": "2025-01-25",
    "morning": true,
    "allDay": true,
    "referenceName": "string"
  },
  ...
]
```

---

### 4. Create a New Booked Date

- **Endpoint**: `POST /api/book-date`
- **Description**: Creates a new booked date and updates the associated booking request's status to "completed". Sends a confirmation email to the user.
- **Request**: 
  - Body: `BookedDateDTO` containing booked date details.
- **Response**: Returns `ResponseEntity<String>` indicating the result of the operation.

```json
{
    "idBookingRequest": 2,
    "date": "2023-10-10",
    "toDate": "2023-10-10",
    "allDay": true,
    "morning": false
}
```

#### Example Response (Success):
```json
"Book date created successfully!"
```

---

### 5. Update Booking Status

- **Endpoint**: `POST /api/update-booking-status`
- **Description**: Updates the status of a booking request based on the provided booking ID and status.
- **Request**: 
  - Query Parameters:
    - `bookingId` (Long): The ID of the booking request to update.
    - `status` (String): The new status for the booking request (Pending, Completed, Cancelled).
- **Response**: Returns `ResponseEntity<String>` indicating the result of the operation.


#### Example Response (Success):
```json
"Status changed successfully!"
```

#### Example Response (Invalid Status):
```json
"Invalid status"
```

---

## Exception Handling

- Validation errors during booking requests will return bad request responses with error details.
- The status update endpoint validates booking IDs and statuses before performing updates, returning appropriate error messages for invalid inputs.



```markdown
# InfoController Technical Manual

## Overview

The `InfoController` class provides RESTful API endpoints for managing information requests, including creating, retrieving, and updating information requests. It utilizes various services to handle business logic related to information requests and emails.
```
## Package Declaration

```java
package com.Cr_8.controllers;
```

## Controller Annotations

- `@RestController`: Indicates that this class is a RESTful controller.
- `@RequestMapping("/api")`: Configures a base URL of `/api` for all endpoints in this controller.

## Dependencies

This controller depends on the following components:

- `InfoService`: Manages information request operations.
- `MailService`: Handles email sending for information request confirmations and notifications.

## Endpoints

### 1. Retrieve All Information Requests

- **Endpoint**: `GET /api/all-info`
- **Description**: Retrieves a list of all information requests.
- **Response**: Returns `ResponseEntity<List<Info>>` containing all information requests.
- **Tags**: `Dashboard Endpoint`


#### Example Response:
```json
[
    {
        "id": 1,
        "name": "John",
        "surname": "Doe",
        "email": "johndoe@example.com",
        "phone": "1234567890",
        "text": "Some text about the info request."
    },
    ...
]
```

---

### 2. Create a New Information Request

- **Endpoint**: `POST /api/pub/create-info`
- **Description**: Creates a new information request and sends confirmation emails to both the user and the admin.
- **Request**:
  - Body: `InfoFormRequest` containing information request details.
- **Response**: Returns `ResponseEntity<?>` indicating the result of the operation or validation errors.
- **Tags**: `Public Endpoint`

#### Example Request:
```json
{
    "name": "John",
    "surname": "Doe",
    "phone": "1234567890",
    "email": "johndoe@example.com",
    "text": "Some text about the info request."
}
```

#### Example Response (Success):
```json
"Info created successfully!"
```

#### Example Response (Validation Error):
```json
"[...validation error messages...]"
```

---

### 3. Update Information Request Status

- **Endpoint**: `POST /api/update-info-status`
- **Description**: Updates the status of an information request based on the provided information ID and status.
- **Request**:
  - Query Parameters:
    - `infoId` (Long): The ID of the information request to update.
    - `status` (String): The new status for the information request (Pending, Completed).
- **Response**: Returns `ResponseEntity<String>` indicating the result of the operation or an error response.
- **Tags**: `Dashboard Endpoint`


#### Example Response (Success):
```json
"Status changed successfully!"
```

#### Example Response (Invalid Status or ID):
```json
"Invalid Status or ID"
```

---

## Exception Handling

- Validation errors during information request creation will return bad request responses with error details.
- The status update endpoint validates information IDs and statuses before performing updates, returning appropriate error messages for invalid inputs.




```markdown
# LabsController Technical Manual

## Overview

The `LabsController` class provides RESTful API endpoints for managing labs, including displaying all labs.

## Package Declaration

```java
package com.Cr_8.controllers;
```

## Controller Annotations

- `@RestController`: Indicates that this class is a RESTful controller.
- `@RequestMapping("/api")`: Configures a base URL of `/api` for all endpoints in this controller.

## Dependencies

This controller depends on the following components:

- `LabsService`: Manages lab operations.

## Endpoints

### 1. Retrieve All Labs

- **Endpoint**: `GET /api/pub/labs`
- **Description**: Retrieves a list of all labs.
- **Response**: Returns `ResponseEntity<?>` containing all labs.
- **Tags**: `Public Endpoint`


#### Example Response:
```json
[
    {
        "id": 1,
        "name": "Lab 1",
        "description": "This is lab 1 description",
    },
    ...
]
```

---

### 2. Add a New Lab (Temporary Endpoint, not implemented)

- **Endpoint**: `POST /api/add-new-labs` (Temporary, not implemented)
- **Description**: Adds a new lab to the database.
- **Request**:
  - Body: `LabsDTO` containing lab's information.
- **Response**: Returns `ResponseEntity<?>` containing a success message.
- **Tags**: `Dashboard Endpoint`

#### Example Request:
```http
POST /api/add-new-labs HTTP/1.1
Host: {your-host}
Content-Type: application/json

{
    "name": "Lab 2",
    "description": "This is lab 2 description",
    ...
}
```

#### Example Response:
```json
"Lab added successfully!"
```

---



The `LabsController` class currently provides a single public endpoint for retrieving all labs. A temporary endpoint for adding a new lab has been included.

```java
// Add the following annotations to the addNewLab method to enable it
// @PostMapping("/add-new-labs")
// public ResponseEntity<?> addNewLab(@RequestBody LabsDTO labsDTO) {
//     ...
// }
// 
```

```java
// Update the implementation in the LabsService class to add a new lab
// public String addNewLabs(LabsDTO labsDTO) {
//     Labs lab = new Labs();
//     // Set the lab's properties here
//     labsRepository.save(lab);
//     return "Lab added successfully!";
// }
// 
```



```markdown
# ReferenceController Technical Manual

## Overview

The `ReferenceController` class provides a RESTful API endpoint for retrieving references from a database. This class leverages the `ReferenceService` to access reference data.

## Package Declaration

```java
package com.Cr_8.controllers;
```

## Controller Annotations

- `@RestController`: Indicates that this class is a RESTful controller.
- `@RequestMapping("/api")`: Configures a base URL of `/api` for all endpoints in this controller.

## Dependencies

This controller depends on the following components:

- `ReferenceService`: Manages operations related to references.

## Endpoints

### 1. Retrieve All References

- **Endpoint**: `GET /api/all-references`
- **Description**: Retrieves a list of all references from the database.
- **Response**: Returns `ResponseEntity<List<Reference>>` containing all reference objects.
- **Tags**: `Dashboard Endpoint`

#### Example Response:
```json
[
  {
    "id": 1,
    "firstName": "string",
    "lastName": "string",
    "phoneNumber": "string",
    "email": "string@string.com"
  }
]
```

---

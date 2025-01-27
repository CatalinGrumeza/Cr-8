# **Technical Manual: Services**

## Services package :

- AdminDetailsService
- AdminService
- BookedDatesService
- InfoService
- LabsService
- MailService
- ReferenceService
- StatusService
- TargetService **


# Admin Details Service

## **1. Overview**
The `AdminDetailsService` is a Spring Security service implementation of the `UserDetailsService` interface. It provides the logic to retrieve admin user credentials and role information, which is essential for the authentication and authorization process in a Spring Security context.

## **1. Key Methods**

###  `loadUserByUsername(String email)`

**Description:**  
Retrieves the admin's credentials and roles based on their email.

- **Input:**
  - `email` (String): The email of the admin (used as the username).
- **Output:**
  - `UserDetails`: A Spring Security object containing admin credentials and roles.
- **Exceptions:**
  - Throws `UsernameNotFoundException` if no admin is found with the given email.

---

### **Method Workflow:**

1. **Retrieve Admin from Database:**

   - The method calls `AdminRepo.findByEmail(email)` to search for an admin entity by their email.
   - If no admin is found, it throws a `UsernameNotFoundException` with a message: `"Admin non trovato"`.

2. **Build `UserDetails`:**

   - Uses `User.builder()` to construct a `UserDetails` object:
     - **Username:** Sets the admin's email as the username.
     - **Password:** Sets the admin's password (should be encoded).
     - **Roles:** Retrieves the role from the `Admin` entity and sets it in the `UserDetails` object.

3. **Return `UserDetails`:**
   - Returns the constructed `UserDetails` object to be used by Spring Security for authentication.

---
# Booking Service
## **1. Overview**

The `BookingService` is a core component of the booking system that handles business logic related to creating, managing, and updating booking requests. It connects to repositories, manages transactional operations, and interacts with related services for sending notifications and managing labs.

---

## **2. Dependencies**

The `BookingService` relies on the following Spring components and custom repositories/services:

### Injected Repositories and Services:

| Dependency        | Type       | Description                                       |
| ----------------- | ---------- | ------------------------------------------------- |
| `BookingRepo`     | Repository | Manages CRUD operations for `BookingRequest`.     |
| `StatusRepo`      | Repository | Retrieves and updates booking statuses.           |
| `ReferenceRepo`   | Repository | Handles `Reference` entities (e.g., users).       |
| `BookedDatesRepo` | Repository | Manages dates linked to bookings.                 |
| `LabsRepo`        | Repository | Manages `Labs` entities.                          |
| `MailService`     | Service    | Sends email notifications (e.g., cancellations).  |
| `LabsService`     | Service    | Adds new labs dynamically if not already present. |

## **3. Key Methods**

###  `createBooking(BookingFormRequest booking)`

**Description:**  
Creates a new booking request, including references, labs, and booked dates.

- **Input:** `BookingFormRequest` - Contains booking details (e.g., date range, labs, user info).
- **Output:** None (void).
- **Key Operations:**
  - Populates a `BookingRequest` object with details.
  - Creates or updates a `Reference` (user) based on the provided email.
  - Dynamically adds labs if not already present in the database.
  - Saves the new booking request in the `BookingRepo`.

---

### `getAllbookRequest()`

**Description:**  
Retrieves all booking requests.

- **Output:** `List<BookingRequest>` - All booking requests in the system.
- **Key Operations:**
  - Calls `BookingRepo.findAll()`.

---
### `updateBookRequest(Long bookId, String statusName)`

**Description:**  
Updates the status of a booking request and sends notifications for cancellations.

- **Inputs:**
  - `bookId` (Long) - ID of the booking to update.
  - `statusName` (String) - New status name (e.g., "Cancelled").
- **Key Operations:**
  - Validates the booking ID and retrieves the `BookingRequest`.
  - Clears associated dates for cancellations and sends an email notification.
  - Updates the booking status using `StatusRepo`.

---
2. **Transaction Management:**

   - Enable transaction management in the application:
     ```java
     @EnableTransactionManagement
     ```

3. **Mail Service Configuration:**
   - Define email properties in `application.properties` or `application.yml`:
     ```properties
     spring.mail.host=smtp.example.com
     spring.mail.port=587
     spring.mail.username=your-email@example.com
     spring.mail.password=your-password
     spring.mail.protocol=smtp
     ```

---

## **6. Error Handling**

Common exceptions and recommended handling:

- **`IllegalArgumentException`:**  
  Replace with custom exceptions like `BookingNotFoundException`.

- **Validation Errors:**  
  Use `@Valid` with `@RequestBody` for input validation.

- **Database Errors:**  
  Catch `DataAccessException` for database-related issues.

---

# AdminService
## **1. Overview**
The `AdminService` is a Spring-managed service class that handles the business logic for managing `Admin` entities. It provides methods for registering, authenticating, and managing admins, including password handling and email-based password recovery.


---

## **2. Dependencies**
The `AdminService` class uses the following dependencies:

| Dependency         | Type             | Description                                  |
|--------------------|------------------|----------------------------------------------|
| `AdminRepo`        | Repository       | Handles CRUD operations for `Admin` entities.|
| `PasswordEncoder`  | Bean             | Encrypts and verifies passwords.            |
| `MailService`      | Service          | Sends email for password recovery.          |

---
---
## **2. Methods**

###  `register(Admin admin)`

Registers a new admin in the system with encrypted credentials.

- **Input:**
  - `admin`: The `Admin` entity containing the registration details.
- **Behavior:**
  - Verifies if an admin with the same email already exists.
  - Encodes the password and saves the new admin.
  - Throws `DuplicateResourceException` if an admin with the same email exists.
- **Exceptions:**
  - `DuplicateResourceException`: Admin email already exists.

---

### `passwordChange(Admin admin, String pass, String oldPass, Principal principal)`

Updates the password for the currently authenticated admin.

- **Input:**
  - `admin`: The `Admin` entity to update.
  - `pass`: The new password.
  - `oldPass`: The old password for validation.
  - `principal`: The authenticated user's details.
- **Output:**
  - Returns a success or error message.
- **Behavior:**
  - Validates the old password.
  - Updates the password if validation succeeds.

---

###  `reset(String email)`

Sends a password reset code to the admin's email.

- **Input:**
  - `email`: The email of the admin.
- **Output:**
  - Returns a success message.
- **Behavior:**
  - Generates a reset code.
  - Sends the code via email.
  - Saves the reset code to the admin entity.

---

### `setnewPassword(String code, String pass)`

Sets a new password using the reset code.

- **Input:**
  - `code`: The reset code.
  - `pass`: The new password.
- **Output:**
  - Returns a success message.
- **Behavior:**
  - Validates the reset code.
  - Updates the password and clears the reset code.

---

# Mail Service
## Overview

The `MailService` class is responsible for sending various types of emails to users and administrators. It uses `JavaMailSender` to send emails and relies on the `AdminRepo` repository to retrieve admin email addresses. The service offers several methods to handle emails related to booking requests, confirmations, cancellations, password resets, and more.
## Dependencies

1. **JavaMailSender**: Handles email sending.
2. **AdminRepo**: A repository for fetching the email addresses of administrators.

## Methods

### `sendEmail`
- **Description**: Sends a generic email to a recipient with a personalized message.
- **Parameters**:
  - `recipient`: The recipient's email address.
  - `body`: Content of the email body.
  - `subject`: The subject of the email.
  - `name`: Recipient's first name.
  - `surname`: Recipient's last name.
  - `type`: Type of request (e.g., booking, info).
- **Usage**: Typically used for sending user-related notifications.

### `sendEmailBooking`
- **Description**: Sends a confirmation email when a booking request is received.
- **Parameters**:
  - `bookRequest`: The booking request object containing details like visitor type, number of participants, etc.
  - `type`: Type of booking (e.g., visit, conference).
- **Usage**: Used for confirming the receipt of a booking.

### `sendEmailConfirmBooked`
- **Description**: Sends an email to confirm a booking.
- **Parameters**:
  - `bookingRequest`: The confirmed booking details.
- **Usage**: Sent after the booking is accepted.

### `sendEmailCancelledBooked`
- **Description**: Sends an email to notify the user that their booking has been canceled.
- **Parameters**:
  - `bookingRequest`: The booking request object with cancellation details.
- **Usage**: Sent when a booking is canceled.

### `sendEmailToAdmin`
- **Description**: Sends an email to all administrators with the details of a user request.
- **Parameters**:
  - `user`: The user's email.
  - `bodyMessage`: Content of the user's request.
  - `subject`: Subject of the email.
  - `name`: User's first name.
  - `surname`: User's last name.
  - `type`: Type of request.
  - `phone`: User's phone number.
- **Usage**: Used for notifying admins about user requests.

### `sendPasswordEmailRest`
- **Description**: Sends a verification code for password reset.
- **Parameters**:
  - `email`: The user's email address.
  - `code`: The verification code.
- **Usage**: Used for sending password reset verification codes to users.
# InfoService 

## Overview

The `InfoService` class provides methods for managing `Info` entities, such as retrieving all records, creating new records, and updating the status of existing records. It uses several services (`InfoRepo`, `ReferenceService`, and `StatusService`) to interact with the database and ensure data integrity.

## Dependencies

1. **InfoRepo**: Repository for managing `Info` entities.
2. **ReferenceService**: Service for managing `Reference` entities.
3. **StatusService**: Service for managing `Status` entities.
## Methods

### `getAllInfo`
- **Description**: Retrieves all `Info` records from the database.
- **Returns**: A list of all `Info` entities.

### `createInfo`

  Description: Creates a new `Info` record with the provided details. If an exception occurs during the transaction, it rolls back.
  Parameters:
- **name**: First name of the user.
- **surname**: Last name of the user.
- **phone**: Phone number of the user.
- **email**: Email address of the user.
- **Text**: Text content of the Info.
Transactional: This method ensures that if an exception occurs, the transaction is rolled back, ensuring data consistency.

# Booked Dates Service 

## Overview

The `BookedDatesService` class is responsible for managing `BookedDate` entities, which store the booking details, including the date, time slots, and associated `BookingRequest`. It interacts with several repositories and services to retrieve and update data.

## Dependencies

1. **BookedDatesRepo**: Repository for managing `BookedDate` entities.
2. **DayFractionRepo**: Repository for managing `DayFraction` entities.
3. **BookingService**: Service for handling booking-related logic.


## Methods

### `getAllBookedDates`
- **Description**: Retrieves all `BookedDate` records from the database that have a non-null date.
- **Returns**: A list of `BookedDate` entities where the date is not null.

### `createBookedDates`

 - **Description**: Creates or updates a BookedDate based on the details provided in the BookedDateDTO. The method retrieves a BookingRequest and updates its associated BookedDate.
 - **Parameters**:
      - **bookedDayDTO**: The Data Transfer Object (DTO) containing the booking details.
 - **Throws**:
        A `ResourceNotFoundException` is thrown if the `BookingRequest` is not found.


## Notes

- **Day Fraction Assignment**: The service assigns day fractions based on the flags in the BookedDateDTO. The available fractions are:
  - **Morning** (ID = 1)
  - **Full Day** (ID = 2)
- **Booking Request Validation**: The service checks whether the BookingRequest exists before proceeding. If the booking request is not found, an exception is thrown.
- **Handling Multiple Days**: If the BookingRequest involves more than one day, the full-day fraction is assigned to the BookedDate.
---
** not implementate service
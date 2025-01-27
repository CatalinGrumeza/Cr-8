# Entity Classes

This document provides an overview of the enitity classes within the project, summarizing their properties.

---

## `Admin`

Represents an admin user with attributes for login and role management.

### Fields:
- `id` (Long) - Primary key.
- `name` (String) - The name of the admin.
- `email` (String) - Unique email for the admin. Validated using the `@Email` annotation.
- `password` (String) - Hashed password for the admin.
- `code` (String) - Temporary code used for forgotten password.
- `role` (Role) - Many-to-one relationship with the `Role` entity. Represents the role of the admin.

### Annotations:
- `@Entity` - Marks the class as a JPA entity.
- `@Table(name="admin")` - Specifies the table name in the database.

---

## `BookedDate`

Represents a booked date or set of dates associated with a booking request.

### Fields:
- `id` (Long) - Primary key.
- `date` (LocalDate) - Start date of the booking.
- `toDate` (LocalDate) - End date of the booking.
- `bookingRequest` (BookingRequest) - One-to-one relationship with the `BookingRequest` entity.
- `dayFractions` (DayFraction) - Many-to-one relationship with the `DayFraction` entity.

### Annotations:
- `@Entity` - Marks the class as a JPA entity.
- `@Table(name="booked")` - Specifies the table name in the database.

---

## `BookingRequest`

Represents a booking request with details about the booking, participants, and related entities.

### Fields:
- `id` (Long) - Primary key.
- `dataFrom` (LocalDate) - Start date of the booking.
- `dataTo` (LocalDate) - End date of the booking.
- `CreatedAt` (LocalDate) - Date the booking was created.
- `additionalDetails` (String) - Additional details about the booking.
- `participantNumber` (int) - Number of participants in the booking.
- `bookType` (String) - Type of booking (half-day, full-day, etc.).
- `numberOfDays` (int) - Number of days for the booking.
- `vistorType` (String) - Type of visitor (e.g., school, organization).
- `status` (Status) - Many-to-one relationship with the `Status` entity.
- `reference` (Reference) - Many-to-one relationship with the `Reference` entity.
- `bookedDate` (BookedDate) - One-to-one relationship with the `BookedDate` entity.
- `labsSet` (List\<Labs>) - Many-to-many relationship with the `Labs` entity.

### Annotations:
- `@Entity` - Marks the class as a JPA entity.
- `@Table(name="booking")` - Specifies the table name in the database.

---

## `DayFraction`

Represents a fraction of the day for a booking (e.g., morning, full day).

### Fields:
- `id` (Long) - Primary key.
- `name` (String) - Name of the day fraction.

### Methods:
- `isMorning()` - Returns `true` if the fraction is morning.
- `isFullDay()` - Returns `true` if the fraction represents a full day.

### Annotations:
- `@Entity` - Marks the class as a JPA entity.

---

## `Info`

Represents an information request related to a reference.

### Fields:
- `id` (Long) - Primary key.
- `text` (String) - Content of the info.
- `createdAt` (LocalDateTime) - Timestamp of when the info was created.
- `reference` (Reference) - Many-to-one relationship with the `Reference` entity.
- `status` (Status) - Many-to-one relationship with the `Status` entity.

### Annotations:
- `@Entity` - Marks the class as a JPA entity.
- `@Table(name="info")` - Specifies the table name in the database.

---

## `Labs`

Represents a laboratory with various properties like name, description, and lab duration.

### Fields:
- `id` (int) - Primary key.
- `name` (String) - Name of the lab.
- `bookingRequest` (List\<BookingRequest>) - Many-to-many relationship with the `BookingRequest` entity.

### Annotations:
- `@Entity` - Marks the class as a JPA entity.
- `@Table(name="labs")` - Specifies the table name in the database.

---

## `Reference`

Represents the reference who submitted a request, including their personal information.

### Fields:
- `id` (Long) - Primary key.
- `firstName` (String) - First name of the reference.
- `lastName` (String) - Last name of the reference.
- `phoneNumber` (String) - Phone number of the reference.
- `email` (String) - Email of the reference.
- `infos` (List\<Info>) - One-to-many relationship with the `Info` entity.

### Annotations:
- `@Entity` - Marks the class as a JPA entity.
- `@Table(name="reference")` - Specifies the table name in the database.

---

## `Role`

Represents a role for an admin user.

### Fields:
- `id` (Integer) - Primary key.
- `name` (String) - Name of the role.

### Annotations:
- `@Entity` - Marks the class as a JPA entity.
- `@Table(name="role")` - Specifies the table name in the database.

---

## `Status`

Represents the status of a booking or information request.

### Fields:
- `id` (int) - Primary key.
- `name` (String) - Name of the status.

### Annotations:
- `@Entity` - Marks the class as a JPA entity.
- `@Table(name="status")` - Specifies the table name in the database.

---

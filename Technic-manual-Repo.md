# Repository Classes

This document provides an overview of the repository classes within the project, summarizing their functionalities and key methods.

---

## `AdminRepo`

- Extends: `JpaRepository<Admin, Long>`
- Manages `Admin` entities.

### Methods:
- `Optional<Admin> findByEmail(String email)`: Finds an admin by email.
- `Optional<Admin> findByCode(String code)`: Finds an admin by a temporary code.
- `boolean existsById(Long id)`: Checks if an admin exists with the given ID.

---

## `BookedDatesRepo`

- Extends: `JpaRepository<BookedDate, Long>`
- Manages `BookedDate` entities.

### Methods:
- `List<BookedDate> findByDateIsNotNull()`: Retrieves all booked dates where the date is not null.

---

## `BookingRepo`

- Extends: `JpaRepository<Booking, Long>`
- Manages `Booking` entities.

### Methods:
- Inherits standard JPA repository methods.

---

## `DayFractionRepo`

- Extends: `JpaRepository<DayFraction, Long>`
- Manages `DayFraction` entities.

### Methods:
- Inherits standard JPA repository methods.

---

## `InfoRepo`

- Extends: `JpaRepository<Info, Long>`
- Manages `Info` entities.

### Methods:
- Inherits standard JPA repository methods.

---

## `LabsRepo`

- Extends: `JpaRepository<Labs, Integer>`
- Manages `Labs` entities.

### Methods:
- `Optional<Labs> findByName(String name)`: Finds a lab by its name.

---

## `ReferenceRepo`

- Extends: `JpaRepository<Reference, Long>`
- Manages `Reference` entities.

### Methods:
- `Optional<Reference> findById(Long id)`: Finds a reference by ID.
- `Optional<Reference> findByEmail(String email)`: Finds a reference by email.
- `Optional<Reference> findByPhoneNumber(String phone)`: Finds a reference by phone number.
- `List<Reference> findAll()`: Retrieves all references.

---

## `RoleRepo`

- Extends: `JpaRepository<Role, Integer>`
- Manages `Role` entities.

### Methods:
- `Optional<Role> findByName(String name)`: Finds a role by its name.

---

## `StatusRepo`

- Extends: `JpaRepository<Status, Integer>`
- Manages `Status` entities.

### Methods:
- `Status findById(int id)`: Finds a status by its ID.
- `Optional<Status> findByName(String name)`: Finds a status by its name.

---

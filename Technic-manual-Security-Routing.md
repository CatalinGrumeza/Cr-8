

```markdown
# SecurityConfig Technical Manual

## Overview

The `SecurityConfig` class is a Spring configuration class that defines security settings for the application. It configures authentication, authorization, password encoding, session management, and custom success handling for logins.

```
## Package Declaration
```java
package com.Cr_8.config;
```

## Class Annotations

- `@Configuration`: Indicates that this class is a source of bean definitions.
- `@EnableWebSecurity`: Enables Spring Security’s web security support and provides the Spring MVC integration.

## Dependencies

This class depends on the following components:

- `AdminService`: Service for managing admin-related operations.

## Constructor

```java
public SecurityConfig(@Lazy AdminService adminService)//lazy use to prevent circular dependency
```

- **Parameters**:
  - `AdminService adminService`: The service used to access admin entities.
  
- The use of `@Lazy` is to prevent circular dependency issues, allowing the `AdminService` to be injected.

## Beans

### 1. PasswordEncoder

```java
@Bean
public PasswordEncoder passwordEncoder()
```

- **Description**: Provides a `PasswordEncoder` bean to encrypt passwords using the BCrypt hashing function, enhancing security by preventing plain-text password storage.
- **Return Type**: `PasswordEncoder` instance.

### 2. AuthenticationManager

```java
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
```

- **Parameters**:
  - `AuthenticationConfiguration authenticationConfiguration`: Configuration for authentication.
  
- **Description**: Provides an `AuthenticationManager` bean for handling authentication processes.
- **Return Type**: `AuthenticationManager` instance.

### 3. SecurityFilterChain

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
```

- **Parameters**:
  - `HttpSecurity http`: The `HttpSecurity` object used to configure web-based security.
  
- **Description**: Configures the `SecurityFilterChain` that dictates how requests are handled with respect to security, including CSRF protection, authorization rules, login form configuration, and session management settings.
- **Return Type**: `SecurityFilterChain` instance.

#### Authorization Configuration:

- `auth.anyRequest().permitAll()`: Allows all requests.
- Login configuration:
  - Custom login page: `/login`
  - Default redirect on successful logins: `/`
  - Methods for handling login and logout.
  
### 4. Custom Authentication Success Handler

```java
@Bean
public AuthenticationSuccessHandler customAuthenticationSuccessHandler()
```

- **Description**: Creates a custom `AuthenticationSuccessHandler` to handle login success events. It retrieves the authenticated user's details and sends a JSON response containing the user's name and role.
- **Return Type**: `AuthenticationSuccessHandler`

### 5. ScheduledExecutorService

```java
@Bean
public ScheduledExecutorService scheduledExecutorService()
```

- **Description**: Provides a `ScheduledExecutorService` bean with a fixed thread pool of size 5. This can be used for executing asynchronous tasks on scheduled intervals.
- **Return Type**: `ScheduledExecutorService` instance.

## Example Response Handling

The custom AuthenticationSuccessHandler writes a JSON response containing the user's name and role after a successful login:

```json
{
    "name": "Admin Name",
    "role": "ADMIN"
}
```

### Example Failure Handling

In case of a failed login, the handler sets the HTTP status to 401 and produces a JSON response indicating the failure.



```markdown
# WebConfig Technical Manual

## Overview

The `WebConfig` class is a Spring configuration class that implements `WebMvcConfigurer`. It is responsible for configuring view controllers and redirect mappings for the web application. This class helps to define route mappings for various URLs, forwarding them to specific view templates.
```
## Package Declaration

```java
package com.Cr_8.config;
```

## Class Annotations

- `@Configuration`: Indicates that this class contains Spring configuration.
- `implements WebMvcConfigurer`: Indicates that this class provides configuration for MVC (Model-View-Controller) support.

## Method Overview

### 1. addViewControllers

```java
@Override
public void addViewControllers(ViewControllerRegistry registry)
```

- **Parameters**:
  - `ViewControllerRegistry registry`: The registry to which view controllers are added.

- **Description**: This method allows the external mapping of specific paths to view templates directly, facilitating easier navigation within the application. The `ViewControllerRegistry` is used to register view controllers for specific URLs.

### Registered View Controllers

The following mappings have been defined:

- **Swagger UI Redirect**:
  - `"/swagger"` -> Redirects to `"/swagger-ui.html"`

- **Root URL**:
  - `"/"` -> Forwards to `"/index.html"`

- **Login Page**:
  - `"/login"` -> Forwards to `"/login.html"`

- **FAQ Page**:
  - `"/faq"` -> Forwards to `"/faq.html"`

- **Laboratory Page**:
  - `"/laboratori"` -> Forwards to `"/laboratori.html"`

- **Dashboard Registration Page**:
  - `"/dashboard/register"` -> Forwards to `"/backoffice/register.html"`

- **Dashboard Information Page**:
  - `"/dashboard/all-info"` -> Forwards to `"/backoffice/all-info.html"`

- **Super Admin - All Admins Page**:
  - `"/super/all-admins"` -> Forwards to `"/backoffice/all-admins.html"`

- **Forgot Password Page**:
  - `"/forgot-password"` -> Forwards to `"/forgot-password.html"`

- **Code Verification Page**:
  - `"/CodeVerification"` -> Forwards to `"/backoffice/code2.html"`

- **New Password Page**:
  - `"/newpassword"` -> Forwards to `"/backoffice/newpassowrd.html"`

- **All Bookings Page**:
  - `"/dashboard/all-bookings"` -> Forwards to `"/backoffice/all-bookings.html"`

- **Dashboard Page**:
  - `"/dashboard"` -> Forwards to `"/backoffice/dashboard.html"`

### Comments on Additional Mappings

There are commented sections indicating potential mappings for:
- Login page redirection
- Specific dashboard functionalities (`delete-labs`, `add-labs`)

These mappings are commented out, indicating that they may be used in the future or were considered but not implemented.

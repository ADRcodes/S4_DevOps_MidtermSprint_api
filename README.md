# Event Management REST API

## Overview

This is a Spring Boot REST API for managing:

- Users
- Events
- Venues
- Registrations

It supports typical CRUD operations and provides JSON responses, making it perfect for integration with your CLI or other frontend tools.

---

## How to Run

1. **Clone the repo**

   ```bash
   git clone <your-repo-url>
   cd S4_DevOps_MidtermSprint_api
   ```

2. **Edit your application.properties**

   In `src/main/resources/application.properties`, ensure your MySQL config matches your local credentials:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
   spring.datasource.username=your_mysql_user
   spring.datasource.password=your_mysql_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Run the application**

   ```bash
   ./mvnw spring-boot:run
   ```

   Or open in IntelliJ / VS Code and run the `EventManagementApplication` main class.

4. **Test with Postman**

   - `GET http://localhost:8080/api/users`
   - `GET http://localhost:8080/api/events`
   - `GET http://localhost:8080/api/venues`

   You can also test POST, PUT, and DELETE requests.

5. **Test with associated CLI program**
   - For efficient testing, clone https://github.com/ADRcodes/S4_DevOps_MidtermSprint_cli
   - This will allow testers to see calls to the API in action

---

## Running Tests

Run all unit and integration tests with:

```bash
./mvnw test
```

---

## Endpoints Summary

| Entity        | Endpoint             | Example                  |
| ------------- | -------------------- | ------------------------ |
| Users         | `/api/users`         | `GET /api/users`         |
| Events        | `/api/events`        | `GET /api/events`        |
| Venues        | `/api/venues`        | `GET /api/venues`        |
| Registrations | `/api/registrations` | `GET /api/registrations` |

---

## Done!

Use this API as the backend for your CLI, web app, or other clients.

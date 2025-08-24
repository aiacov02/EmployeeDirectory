# Employee Directory

This is a Spring Boot application that provides a REST API for managing employees.  
It follows a layered architecture (Controller → Service → DAO) and uses JPA with an embedded H2 database.

## Requirements

- Java 17 or later
- Maven 3.9+
- Git

## Getting Started

Clone the repository:

```bash
git clone https://github.com/your-username/employee-directory.git
cd employee-directory
```

### Compile

Build the project with Maven:

```bash
mvn clean package
```

### Run

Start the application:

```bash
mvn spring-boot:run
```

By default, the application will start on port `8080`.

The H2 database console is available at:
```
http://localhost:8080/h2-console
```

Default credentials:
- JDBC URL: `jdbc:h2:file:./data/employees`
- Username: `sa`
- Password: (leave blank)

### Test

Run unit and integration tests:

```bash
mvn test
```

## API Endpoints

Base URL: `/api/employees`

### Get all employees
```
GET /api/employees
```
Returns a list of all employees.

---

### Get employee by ID
```
GET /api/employees/{id}
```
Returns a single employee.  
Response: `404 Not Found` if the employee does not exist.

---

### Create a new employee
```
POST /api/employees
```
Request body (JSON):
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "johndoe@example.com"
}
```
Response: `201 Created`

---

### Update an existing employee
```
PUT /api/employees/{id}
```
Request body (JSON):
```json
{
  "firstName": "Jane",
  "lastName": "Smith",
  "email": "janesmith@example.com"
}
```
Response: `200 OK` with the updated employee.

---

### Delete an employee
```
DELETE /api/employees/{id}
```
Responses:  
- `204 No Content` if deleted successfully  
- `404 Not Found` if the employee does not exist  

---

## Swagger / OpenAPI

This project includes SpringDoc OpenAPI. After starting the application, the interactive API documentation is available at:

```
http://localhost:8080/swagger-ui.html
```

From there you can view and test all endpoints directly in the browser.

---

## Notes

- The application uses an H2 database that persists to the local file system (`./data/employees`). Restarting the application will keep existing records unless the data directory is cleared.  
- The service layer throws `EmployeeNotFoundException` when an employee cannot be found. This is automatically translated into a `404 Not Found` response.

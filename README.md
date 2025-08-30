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

## Basic Authentication

- The application uses **HTTP Basic authentication**.
- Credentials are stored in the H2 database (table `MEMBERS`) with `{bcrypt}` hashed passwords.

### Roles and Allowed Operations

| Role     | Allowed Endpoints / Operations |
|----------|--------------------------------|
| ADMIN    | GET /api/employees, POST /api/employees, PUT /api/employees/{id}, DELETE /api/employees/{id} |
| MANAGER  | GET /api/employees, PUT /api/employees/{id} |
| EMPLOYEE | GET /api/employees |

---

## API Endpoints

Base URL: `/api/employees`

### Get all employees
```
GET /api/employees
```
Responses:
Responses:
- `200 OK` with the list of employees
- `404 Not Found` if the employee does not exist
- `403 Forbidden` if the user does not have permission
- `401 Unauthorized` if not authenticated

### Get employee by ID
```
GET /api/employees/{id}
```
Responses:
- `404 Not Found` if the employee does not exist
- `403 Forbidden` if the user does not have permission
- `401 Unauthorized` if not authenticated

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
Responses:
- `200 OK` with the updated employee if successful
- `403 Forbidden` if the user does not have permission
- `401 Unauthorized` if not authenticated

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

Responses:
- `200 OK` with the updated employee if successful
- `404 Not Found` if the employee does not exist
- `403 Forbidden` if the user does not have permission
- `401 Unauthorized` if not authenticated

### Delete an employee
```
DELETE /api/employees/{id}
```
Responses:  
- `204 No Content` if deleted successfully  
- `404 Not Found` if the employee does not exist
- `403 Forbidden` if the user does not have permission
- `401 Unauthorized` if not authenticated

---

## Swagger / OpenAPI

This project includes SpringDoc OpenAPI. After starting the application, the interactive API documentation is available at:

```
http://localhost:8080/swagger-ui.html
```

From there you can view and test all endpoints directly in the browser.

---

## H2 Database Schema

### MEMBERS Table
```sql
CREATE TABLE MEMBERS (
   username VARCHAR(50) NOT NULL PRIMARY KEY,
   password VARCHAR(100) NOT NULL,
   enabled BOOLEAN NOT NULL
);
```

### ROLES Table
```sql
CREATE TABLE ROLES (
    serno INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_roles_members FOREIGN KEY (user_id) REFERENCES members(username),
    CONSTRAINT uq_user_role UNIQUE (user_id, role)
);
```

### Example Inserts
```sql
INSERT INTO MEMBERS (username, password, enabled) VALUES
('jane', '{bcrypt}<hash>', TRUE),
('marios', '{bcrypt}<hash>', TRUE),
('kostas', '{bcrypt}<hash>', TRUE);

INSERT INTO ROLES (user_id, role) VALUES
('jane', 'ROLE_ADMIN'),
('marios', 'ROLE_MANAGER'),
('kostas', 'ROLE_EMPLOYEE');
```

---
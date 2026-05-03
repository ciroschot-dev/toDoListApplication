# Todo List REST API

Secure task-management REST API built with Java and Spring Boot. The project focuses on backend fundamentals that are common in production APIs: authentication, authorization, persistence, service-layer business logic and authenticated user ownership.

## Backend highlights

- User registration and login.
- JWT-based authentication for protected endpoints.
- OAuth2 login flow with Google/GitHub client support.
- Spring Security filter chain with stateless session configuration.
- User-owned tasks: authenticated users can only access their own tasks.
- Spring Data JPA repositories for persistence.
- Global exception handling for API errors.
- Password hashing with BCrypt.

## Tech stack

- Java 25
- Spring Boot 4
- Spring MVC
- Spring Security
- OAuth2 Client
- JWT
- Spring Data JPA
- MySQL
- Maven
- Lombok
- Postman for API testing

## API endpoints

Base URL:

```text
http://localhost:8080/todo-app
```

### Authentication

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/register` | Register a new user |
| `POST` | `/login` | Authenticate and receive a JWT |

### Tasks

All task endpoints require an authenticated user.

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/create-task` | Create a task for the authenticated user |
| `GET` | `/` | List tasks for the authenticated user |
| `GET` | `/find-task/{id}` | Find one task owned by the authenticated user |
| `PUT` | `/update-task/{id}` | Update one task owned by the authenticated user |
| `DELETE` | `/delete-task/{id}` | Delete one task owned by the authenticated user |

Example task request:

```json
{
  "title": "Prepare backend interview",
  "completed": false
}
```

## Project structure

```text
src/main/java/com/proyecto/todolistapplication
|-- config       # Spring Security, JWT filter and OAuth2 success handler
|-- controller   # REST controllers
|-- exception    # API exception handling
|-- model        # JPA entities and UserDetails adapter
|-- repository   # Spring Data repositories
`-- service      # Authentication, JWT and task business logic
```

## Local setup

### 1. Clone the repository

```bash
git clone https://github.com/ciroschot-dev/toDoListApplication.git
cd toDoListApplication
```

### 2. Configure environment variables

The application reads database, JWT and OAuth2 credentials from environment variables:

```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/todo_app
DB_USER=root
DB_PASSWORD=your_password
JWT_SECRET=your_long_jwt_secret
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret
```

Load these variables through your IDE run configuration, terminal session or an env-file plugin before starting the app.

### 3. Run the API

```bash
./mvnw spring-boot:run
```

The API will run at:

```text
http://localhost:8080
```

## What this project demonstrates

- Building secured REST APIs with Spring Boot.
- Designing controller-service-repository layers.
- Using `@AuthenticationPrincipal` to scope data to the authenticated user.
- Generating and validating JWTs.
- Integrating OAuth2 login into a backend API.
- Testing API flows with Postman.

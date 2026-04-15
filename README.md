# College Portal Backend (Attendance & Academic Management System)

A **Spring Boot backend application** designed to manage academic activities within a college environment.
The system provides functionality for managing **students, teachers, attendance, grades, leave requests, notifications, and authentication** through a secure REST API.

The project demonstrates backend engineering concepts such as **JWT authentication, role-based authorization, database relationships, Redis caching, and scalable API design**.

---

## Features

### Authentication & Security

* JWT based authentications
* Refresh token system with secure cookies
* OTP based password reset using email
* Role based authorization (Student / Teacher / Admin)
* Spring Security configuration with method-level access control
* Filtering , Sorting and Pagination of data

### Student Functionality

* View attendance records(with filters)
* Track attendance percentage
* View academic results and grades(with filters)
* Submit leave requests
* Receive notifications
* View timetable

### Teacher Functionality

* Mark student attendance
* Manage student leave requests
* Publish notifications
* View class schedules
* Manage student academic data

### Academic Management

* Semester management
* Subject management
* Section management
* Grade tracking
* Attendance analytics

### System Enhancements

* Pagination for large datasets
* Filtering for attendance and leave records
* OTP verification with Redis
* Rate limiting support
* Proper exception handling and validation

---

## Technology Stack

| Layer             | Technology            |
| ----------------- | --------------------- |
| Backend Framework | Spring Boot           |
| Security          | Spring Security + JWT |
| Database          | PostgreSQL            |
| Caching / Tokens  | Redis                 |
| Build Tool        | Maven                 |
| Language          | Java                  |
| Containerization  | Docker                |
| Email Service     | SMTP (Gmail)          |

---

## Project Architecture

The application follows a **layered architecture** to ensure separation of concerns.

Controller Layer
Handles HTTP requests and responses.

Service Layer
Contains business logic and application workflows.

Repository Layer
Handles database interactions using Spring Data JPA.

Entity Layer
Represents database models and relationships.

DTO Layer
Transfers data between layers without exposing internal entities.

---

## Database Entities

Key entities used in the system include:

* User
* Student
* Teacher
* Section
* Semester
* Subject
* Attendance (StudentSubject)
* Grade
* Leave
* Notification
* TimeTable

The entities are connected using JPA relationships such as:

* OneToMany
* ManyToOne
* OneToOne

---

## Authentication Flow

1. User logs in with username and password
2. Server validates credentials
3. Access token (JWT) is generated
4. Refresh token is stored in Redis and sent via HTTP-only cookie
5. Access token is used for API authentication
6. Refresh token is used to generate new access tokens when expired

---

## API Modules

Authentication APIs

* Login
* Signup
* Refresh Token
* Forgot Password
* Reset Password

Student APIs

* Attendance dashboard
* Results dashboard
* Leave management
* Notifications

Teacher APIs

* Attendance marking
* Leave approval
* Notifications
* Timetable management

---

## Security Features

* Stateless authentication with JWT
* Refresh token rotation
* HTTP-only cookies for token protection
* Role-based authorization using `@PreAuthorize`
* Password encryption using BCrypt
* OTP expiration with Redis

---

## Running the Project

### Clone the repository

```bash
git clone https://github.com/sainilesh/College-Portal-Backend.git
cd College-Portal-Backend
```

### Configure environment

Update `application.properties` with:

* Database credentials
* Redis configuration
* Email SMTP credentials
* JWT secret

### Run the application

```bash
mvn spring-boot:run
```

The API will start on:

```
http://localhost:8080
```

---

## Example API Request

Login

```http
POST /auth/login
Content-Type: application/json
```

Request body

```json
{
  "username": "rahul",
  "password": "password"
}
```

Response

```json
{
  "accessToken": "JWT_TOKEN",
  "userId": 1
}
```

---

## Future Improvements

* API documentation using Swagger/OpenAPI
* Unit and integration tests
* Monitoring with Prometheus and Grafana
* Cloud deployment (AWS / GCP)
* Frontend integration

---

## Author

Nilesh Sai
Backend Developer | Computer Science Engineer

---

## License

This project is intended for educational and demonstration purposes.

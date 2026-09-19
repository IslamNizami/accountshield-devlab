# 🔐 AccountShield — Secure User Account Management API

AccountShield is a backend security and identity management service.

The project focuses on implementing secure authentication, role-based authorization, account protection, input validation, and a clean REST API following common enterprise backend practices.

---

## ✨ Key Features

* **JWT Authentication** — Stateless authentication using signed JSON Web Tokens with HMAC-SHA256.
* **Role-Based Access Control** — Separate permissions for `ROLE_USER` and `ROLE_ADMIN`.
* **Account Lockout** — Tracks failed login attempts and automatically locks accounts after reaching the configured threshold.
* **Input Validation** — Request validation using Jakarta Bean Validation.
* **RESTful API** — Structured DTO-based requests and responses with appropriate HTTP status codes.
* **PostgreSQL Persistence** — Relational data storage using Spring Data JPA and Hibernate.
* **MapStruct Mapping** — Compile-time mapping between entities and DTOs.
* **Global Exception Handling** — Centralized handling of application and validation errors.
* **OpenAPI Documentation** — Interactive API documentation through Swagger UI.
* **Stateless Security** — No server-side HTTP sessions; each request is independently authenticated.

---

## 🛠️ Tech Stack

| Component             | Technology                     |
| --------------------- | ------------------------------ |
| Language              | Java 21                        |
| Framework             | Spring Boot 3                  |
| Web                   | Spring Web / REST              |
| Security              | Spring Security                |
| Authentication        | JJWT / JWT                     |
| Database              | PostgreSQL                     |
| Persistence           | Spring Data JPA / Hibernate    |
| Validation            | Jakarta Bean Validation        |
| Mapping               | MapStruct                      |
| Boilerplate Reduction | Lombok                         |
| Build Tool            | Gradle                         |
| API Documentation     | SpringDoc OpenAPI / Swagger UI |

---

## 🔑 Authentication

Authentication is implemented using **JWT access tokens**.

After successful authentication, the server returns an access token:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer"
}
```

The token can then be supplied with protected requests:

```http
Authorization: Bearer <access-token>
```

The application uses stateless session management, meaning authentication state is not stored in an HTTP session.

---

## 🔐 Authorization

AccountShield uses **Role-Based Access Control (RBAC)** through Spring Security authorities.

| Role         | Access                           |
| ------------ | -------------------------------- |
| `ROLE_USER`  | Authenticated user functionality |
| `ROLE_ADMIN` | Administrative functionality     |

Roles stored in the application domain are translated into Spring Security authorities using `SimpleGrantedAuthority`.

This allows endpoint-level access restrictions to be enforced through the Spring Security filter chain.

---

## 🛡️ Account Protection

The application includes defensive mechanisms against repeated failed authentication attempts.

The login process tracks failed attempts and can automatically lock an account after the configured threshold is reached.

Administrative endpoints also provide mechanisms for managing account status and verification state.

---

# 📡 API Endpoints

## Authentication — `/api/auth`

| Method | Endpoint             | Description                   | Access | Status   |
| ------ | -------------------- | ----------------------------- | ------ | -------- |
| `POST` | `/api/auth/register` | Register a new user account   | Public | `200 OK` |
| `POST` | `/api/auth/login`    | Authenticate and obtain a JWT | Public | `200 OK` |

### Login Response

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer"
}
```

---

## User Management — `/api/users`

| Method | Endpoint             | Description                               | Access        | Status   |
| ------ | -------------------- | ----------------------------------------- | ------------- | -------- |
| `GET`  | `/api/users/profile` | Retrieve the authenticated user's profile | Authenticated | `200 OK` |
| `PUT`  | `/api/users/profile` | Update the authenticated user's profile   | Authenticated | `200 OK` |

---

## Administration — `/api/admin`

| Method   | Endpoint                       | Description               | Access       | Status           |
| -------- | ------------------------------ | ------------------------- | ------------ | ---------------- |
| `GET`    | `/api/admin/users`             | List registered accounts  | `ROLE_ADMIN` | `200 OK`         |
| `PATCH`  | `/api/admin/users/{id}/role`   | Update a user's role      | `ROLE_ADMIN` | `200 OK`         |
| `PATCH`  | `/api/admin/users/{id}/status` | Update account status     | `ROLE_ADMIN` | `200 OK`         |
| `DELETE` | `/api/admin/users/{id}`        | Permanently delete a user | `ROLE_ADMIN` | `204 No Content` |

---

# 🚀 Getting Started

## Prerequisites

Make sure the following are installed:

* **JDK 17 or later**
* **PostgreSQL**
* **Git**

Alternatively, PostgreSQL can be run using Docker.

---

## ⚙️ Configuration

Create or configure:

```text
src/main/resources/application.properties
```

Example configuration:

```properties
spring.application.name=AccountShield-Devlab

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/accountshield_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT Configuration
jwt.secret=${JWT_SECRET}
```

### Environment Variables

The application expects sensitive configuration to be supplied externally:

```text
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

For example:

```bash
export DB_USERNAME=postgres
export DB_PASSWORD=your_password
export JWT_SECRET=your_secure_secret
```

> **Security:** Never commit real database credentials or JWT secrets to source control. The JWT secret should be sufficiently long and generated specifically for the deployment environment.

---

# 📥 Installation

Clone the repository:

```bash
git clone https://github.com/IslamNizami/accountshield-devlab.git
cd accountshield-devlab
```

---

# 🔨 Build

Build the project using Gradle:

```bash
./gradlew clean build
```

To build without running tests:

```bash
./gradlew clean build -x test
```

---

# ▶️ Run

Start the application with:

```bash
./gradlew bootRun
```

By default, the application runs on:

```text
http://localhost:8080
```

---

# 📚 API Documentation

Once the application is running, interactive API documentation is available through Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

The OpenAPI specification can also be accessed through:

```text
http://localhost:8080/v3/api-docs
```

---

# 🔒 Security Implementation

### JWT Filter

Incoming requests containing a Bearer token are processed by the JWT authentication filter.

The filter:

1. Extracts the JWT from the `Authorization` header.
2. Validates the token.
3. Extracts the authenticated user's identity.
4. Loads the corresponding security context.
5. Allows Spring Security to perform authorization checks.

---

### Stateless Session Management

The application uses:

```java
SessionCreationPolicy.STATELESS
```

No server-side HTTP session is maintained for authentication.

Each protected request must therefore provide valid authentication credentials.

---

### Endpoint Protection

Administrative endpoints are protected at the Spring Security layer, preventing users without the required authority from accessing administrative resources.

For example:

```text
ROLE_ADMIN
    │
    ├── GET    /api/admin/users
    ├── PATCH  /api/admin/users/{id}/role
    ├── PATCH  /api/admin/users/{id}/status
    └── DELETE /api/admin/users/{id}
```

---

# 🧩 Project Structure

The project separates responsibilities across several layers:

| Layer        | Responsibility                         |
| ------------ | -------------------------------------- |
| `controller` | HTTP request/response handling         |
| `service`    | Business logic                         |
| `repository` | Database access                        |
| `model`      | Domain entities and enums              |
| `dto`        | API request and response models        |
| `mapper`     | Entity ↔ DTO conversion                |
| `config`     | Security and application configuration |
| `exception`  | Centralized exception handling         |

This separation keeps business logic independent from HTTP and persistence concerns and makes the application easier to maintain and extend.

---

The project serves as a practical implementation of security-focused backend development using the Spring ecosystem.

---

# 👤 Author

**Islam Nizami**

* GitHub: [@IslamNizami](https://github.com/IslamNizami)

---

# 📄 License

This project was developed for educational and portfolio purposes as part of the DevLab internship/project work.

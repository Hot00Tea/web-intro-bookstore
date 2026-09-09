# Bookstore API

A RESTful backend application for an online bookstore, built with Java and Spring Boot.

The project provides a complete backend solution for managing books and categories, user authentication and authorization, and shopping cart functionality.

The main goal of the project was to build a structured and secure REST API using modern Java and Spring technologies, while following common backend development practices.

---

## Features

### Authentication & Authorization

- User registration
- User authentication
- JWT-based authentication
- Password encryption with BCrypt
- Role-based access control
- `USER` and `ADMIN` roles
- Protected endpoints using Spring Security
- Method-level authorization with `@PreAuthorize`

### Book Management

Users can:

- Get a paginated list of books
- Get a book by ID
- Search for books

Administrators can:

- Create books
- Update books
- Delete books

### Category Management

Users can:

- Get a paginated list of categories
- Get a category by ID
- Get books belonging to a category

Administrators can:

- Create categories
- Update categories
- Delete categories

### Shopping Cart

Authenticated users can:

- View their shopping cart
- Add books to the cart
- Update book quantity
- Remove books from the cart

---

## Technologies

- **Java**
- **Spring Boot**
- **Spring Web**
- **Spring Security**
- **JWT**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **Liquibase**
- **MapStruct**
- **Bean Validation**
- **Swagger / OpenAPI**
- **Maven**
- **JUnit 5**
- **Mockito**
- **MockMvc**
- **Testcontainers**
- **Docker**
- **Git / GitHub**

---

## Project Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
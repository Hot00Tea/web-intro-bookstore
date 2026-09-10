# Web Intro Bookstore

A RESTful bookstore application built with Spring Boot. The project provides book and category management, user registration and authentication, shopping cart functionality, order management, role-based authorization, database migrations, API documentation, and automated tests.

## Features

- User registration and authentication
- JWT-based authentication
- Role-based authorization (`USER`, `ADMIN`)
- Book management
- Category management
- Many-to-many relationship between books and categories
- Shopping cart
- Cart item quantity management
- Order management
- Bean Validation
- Database migrations with Liquibase
- REST API
- Swagger / OpenAPI documentation
- Unit and integration tests
- Testcontainers-based integration testing
- Docker support
- AWS deployment with Docker, EC2, ECR Public and RDS

---

## Technologies

| Technology | Version |
|---|---|
| Java | 17 |
| Spring Boot | 3.5.6 |
| Hibernate ORM | 6.6.29.Final |
| MySQL | 8.4.9 |
| Liquibase | 4.31.1 |
| Testcontainers | 1.21.3 |
| JUnit 5 | 5.x |
| Mockito | 5.x |
| MapStruct | 1.x |
| Maven | 3.x |
| Docker | 25.x |
| Spring Security | 6.x |

> Exact dependency versions can be found in `pom.xml`. The versions above include the main versions used by the project and deployment environment.

---

## Architecture

The application follows a layered architecture:

```text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
MySQL Database
```

Additional layers/components:

- DTOs for API requests and responses
- MapStruct mappers for entity/DTO conversion
- Spring Security for authentication and authorization
- JWT for stateless authentication
- Liquibase for database schema management
- Validation for request data
- Global exception handling

---

## Database Model

The main entities are:

- `User`
- `Role`
- `Book`
- `Category`
- `ShoppingCart`
- `CartItem`
- `Order`
- `OrderItem`

Main relationships:

```text
User
 ├── many-to-many ── Role
 ├── one-to-one ─── ShoppingCart
 └── one-to-many ── Order

ShoppingCart
 └── one-to-many ── CartItem

CartItem
 └── many-to-one ── Book

Book
 └── many-to-many ── Category

Order
 └── one-to-many ── OrderItem

OrderItem
 └── many-to-one ── Book
```

### Entity Relationship Diagram

The repository can contain the database diagram at:

```text
docs/database-model.png
```

If the diagram is added to the repository, it can be displayed here:

![Database Model](docs/database-model.png)

---

## API Endpoints

The application uses the `/api` context path.

### Authentication

```text
POST /api/auth/registration
```

Registers a new user.

Authentication endpoints return/use JWT tokens according to the security configuration.

### Books

Typical book endpoints include:

```text
GET    /api/books
GET    /api/books/{id}
POST   /api/books
PUT    /api/books/{id}
DELETE /api/books/{id}
```

### Categories

Typical category endpoints include:

```text
GET    /api/categories
GET    /api/categories/{id}
POST   /api/categories
PUT    /api/categories/{id}
DELETE /api/categories/{id}
```

### Shopping Cart

Shopping cart functionality includes:

```text
GET    /api/cart
POST   /api/cart
PUT    /api/cart/cart-items/{cartItemId}
DELETE /api/cart/cart-items/{cartItemId}
```

### Orders

Order functionality includes endpoints for creating and retrieving orders according to the application's controller configuration.

> For the complete and authoritative list of endpoints, use Swagger/OpenAPI or check the controllers in `src/main/java`.

---

## Swagger / OpenAPI

The application uses Swagger/OpenAPI for API documentation and manual API testing.

When the application is running locally:

```text
Swagger UI:
http://localhost:8080/api/swagger-ui/index.html
```

OpenAPI specification:

```text
http://localhost:8080/api/v3/api-docs
```

Swagger UI allows you to:

- View available endpoints
- Inspect request and response models
- Review authorization requirements
- Send requests directly to the application

---

## Postman Collection

A Postman collection can be stored in:

```text
docs/postman/bookstore.postman_collection.json
```

To use it:

1. Open Postman.
2. Select **Import**.
3. Choose `bookstore.postman_collection.json`.
4. Configure the API base URL.
5. Execute the requests.

The collection should contain requests for the main API functionality, including authentication, books, categories, shopping cart and orders.

---

## Environment Variables

Sensitive configuration is stored outside the source code.

Create a `.env` file in the project root:

```env
MYSQLDB_DATABASE=bookstore
MYSQLDB_USER=admin
MYSQLDB_PASSWORD=your_password
MYSQLDB_ROOT_PASSWORD=your_root_password
MYSQL_LOCAL_PORT=3306
MYSQLDB_DOCKER_PORT=3306
SPRING_LOCAL_PORT=8080
SPRING_DOCKER_PORT=8080
JWT_SECRET=your_jwt_secret
```

Do not commit real passwords, JWT secrets, access keys, or other credentials to Git.

The `.env` file should be included in `.gitignore`.

---

## How to Launch the Project

### Prerequisites

Install:

- Java 17+
- Maven
- Docker
- Docker Compose
- Git

### 1. Clone the repository

```bash
git clone https://github.com/Hot00Tea/web-intro-bookstore.git
cd web-intro-bookstore
```

### 2. Configure environment variables

Create the `.env` file in the project root and provide the required values.

### 3. Start the application with Docker Compose

```bash
docker compose up --build
```

The application will be available at:

```text
http://localhost:8080/api
```

Swagger UI:

```text
http://localhost:8080/api/swagger-ui/index.html
```

### 4. Stop the application

```bash
docker compose down
```

To stop the containers and remove their associated volumes:

```bash
docker compose down -v
```

> Removing the volume deletes the local MySQL Docker data.

---

## Run Without Docker

You can also run the Spring Boot application directly from IntelliJ IDEA or Maven.

Make sure MySQL is running and the required environment variables are configured.

Run:

```bash
mvn spring-boot:run
```

Or build the project:

```bash
mvn clean package
```

Then run the generated JAR file.

---

## Database Migrations

Liquibase is used to manage database schema changes.

Migration files are located under:

```text
src/main/resources/db/changelog
```

The current migration history includes:

- Books table
- Users table
- Roles table
- Users-roles relationship
- Initial roles
- Initial users
- User-role assignments
- Categories table
- Books-categories relationship
- Shopping carts
- Cart items
- Shopping cart soft-delete field
- Orders
- Order items

When the application starts, Liquibase checks the database changelog and applies pending changesets automatically.

---

## Security

Spring Security is used to protect the REST API.

The project uses:

- BCrypt password hashing
- JWT authentication
- `UserDetailsService`
- Role-based access control
- Protected endpoints
- Public registration endpoint
- Validation of authentication requests

JWT configuration is provided through an environment variable:

```env
JWT_SECRET=your_jwt_secret
```

Secrets must never be committed to the repository.

---

## Testing

The project contains tests for the repository, service and controller layers.

The tested functionality includes:

- Book repository
- Book service
- Book controller
- Category service
- Category controller
- Validation
- Authorization
- Negative scenarios
- Database integration

### Run tests

```bash
mvn test
```

### Testcontainers

Integration tests for controllers use Testcontainers with MySQL.

This provides a real MySQL environment for integration testing instead of relying only on mocks.

The tests verify:

- HTTP endpoints
- Request validation
- Response status codes
- Authentication/authorization scenarios
- Database interaction
- Liquibase database initialization

---

## Test Coverage

The project includes coverage for the main service and controller layers.

The required minimum coverage target is:

- `BookController` — 50%+
- `CategoryController` — 50%+
- `BookService` — 50%+
- `CategoryService` — 50%+

Coverage can be generated and reviewed using IntelliJ IDEA's built-in coverage tools.

A coverage screenshot can be added to the repository documentation or Pull Request description.

---

## Docker

The application can be packaged into a Docker image.

Build the image:

```bash
docker build -t web-intro-bookstore-app:latest .
```

Run the container locally:

```bash
docker run -p 8080:8080 web-intro-bookstore-app:latest
```

For local development with MySQL, Docker Compose is recommended:

```bash
docker compose up --build
```

---

## AWS Deployment

The application was deployed using the following AWS services:

```text
                    Internet
                       |
                       v
                 AWS EC2
                       |
                       v
                Docker Container
                       |
                       v
                Spring Boot API
                       |
                    TCP 3306
                       |
                       v
                 AWS RDS MySQL
```

The Docker image is stored in Amazon ECR Public.

Public ECR image:

```text
public.ecr.aws/w3k0b8z0/bookstore-api:v01
```

The EC2 instance maps:

```text
EC2 port 80 -> Docker port 8080
```

The RDS MySQL database is used as the persistent database for the deployed application.

Liquibase automatically applies database migrations when the application starts.

### Deployment components

- AWS EC2
- Docker
- Amazon ECR Public
- Amazon RDS for MySQL
- Security Groups
- Spring Boot
- Liquibase

### Network configuration

The RDS Security Group allows MySQL traffic on port `3306` from the EC2 Security Group.

This allows the following communication:

```text
EC2 Security Group
        |
        | TCP 3306
        v
RDS Security Group
```

---

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── mate/
│   │       └── academy/
│   │           └── webintrobookstore/
│   │               ├── controller/
│   │               ├── dto/
│   │               ├── exception/
│   │               ├── mapper/
│   │               ├── model/
│   │               ├── repository/
│   │               ├── security/
│   │               ├── service/
│   │               └── WebIntroBookstoreApplication.java
│   │
│   └── resources/
│       ├── db/
│       │   └── changelog/
│       └── application.properties
│
└── test/
    └── java/
        └── mate/
            └── academy/
                └── webintrobookstore/
```

Additional project files:

```text
Dockerfile
docker-compose.yml
pom.xml
README.md
.env
```

---

## Challenges & Solutions

### Database migrations

Liquibase was used to keep database schema changes versioned and reproducible.

### Authentication

Spring Security and JWT were implemented to provide stateless authentication and role-based authorization.

### Entity relationships

The project contains several JPA relationships, including:

- `@ManyToMany`
- `@OneToOne`
- `@OneToMany`
- `@ManyToOne`

### Integration testing

Testcontainers was used to run integration tests against a real MySQL container.

### Docker deployment

The application was packaged into a Docker image and deployed to AWS EC2. Amazon RDS was used as the remote MySQL database.

### AWS networking

Security Groups were configured to allow the EC2 instance to access the RDS database through TCP port `3306`.

---

## API Testing Workflow

A typical authentication flow is:

```text
1. Register user
       |
       v
2. Authenticate
       |
       v
3. Receive JWT
       |
       v
4. Add JWT to Authorization header
       |
       v
5. Access protected endpoints
```

Authorization header:

```text
Authorization: Bearer <JWT_TOKEN>
```

---

## Documentation

Useful project documentation:

- Swagger / OpenAPI
- Postman collection
- Database model
- Source code
- Liquibase changelogs
- Tests

---

## Future Improvements

Possible future improvements include:

- Pagination and sorting for books
- Advanced book search and filtering
- Improved exception handling
- More comprehensive test coverage
- CI/CD pipeline
- Monitoring and logging
- Production-ready secret management
- Automated AWS deployment
- Improved API documentation

---

## Author

**Tea / Hot00Tea**

GitHub:

https://github.com/Hot00Tea

---

## License

This project was created for educational purposes as part of a backend development project.

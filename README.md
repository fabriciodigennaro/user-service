# User Service

## Overview

User Service is a Java-based microservice designed to manage user-related functionalities such as registration, login, and user information. This project utilizes Spring Boot, and includes support for database migrations, API documentation, and testing frameworks.

## Features

- User registration
- User login
- User information retrieval
- Database migrations with Flyway
- API documentation with SpringDoc OpenAPI
- Comprehensive testing setup with JUnit, AssertJ, Mockito, and Testcontainers

## Technologies

- Java 17
- Spring Boot 3.1.1
- PostgreSQL
- Flyway
- SpringDoc OpenAPI
- JUnit 5
- AssertJ
- Mockito
- Testcontainers
- Lombok

## Getting Started

### Prerequisites

- Java 17
- Gradle
- PostgreSQL

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/your-username/user-service.git
   cd user-service
   ```

2. Configure the database connection in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/your-database
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. Run the application:

   ```sh
   ./gradlew bootRun
   ```

## Usage

The API endpoints for user management include:

- `POST /api/users/register`: Register a new user
- `POST /api/users/login`: Login a user
- `GET /api/users/{id}`: Get user information by ID

### API Documentation

API documentation is available via SpringDoc OpenAPI. Once the application is running, you can access the API documentation at:

```
http://localhost:8080/swagger-ui.html
```

## Testing

The project includes unit, integration, contract, and component tests. To run the tests, use the following commands:

- Unit tests:

  ```sh
  ./gradlew test
  ```

- Integration tests:

  ```sh
  ./gradlew integrationTest
  ```

- Contract tests:

  ```sh
  ./gradlew contractTest
  ```

- Component tests:

  ```sh
  ./gradlew componentTest
  ```

## Build and Deployment

To build the project, run:

```sh
./gradlew build
```

The resulting JAR file will be located in the `build/libs` directory. You can run it with:

```sh
java -jar build/libs/user-service-0.0.1-SNAPSHOT.jar
```

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements

This project utilizes the following libraries and frameworks:

- Spring Boot
- SpringDoc OpenAPI
- Flyway
- JUnit
- AssertJ
- Mockito
- Testcontainers
- Lombok

For more information, please refer to the official documentation of each library.

---
**Note:** Ensure that the necessary database and environment configurations are properly set before running the application.
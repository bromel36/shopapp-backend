# ShopApp

ShopApp is an intelligent system designed for managing an e-commerce platform. It provides features for user authentication, product management, and secure access control, ensuring a seamless shopping experience for customers and administrators.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Features

- **User Authentication**: Secure login, registration, and email verification.
- **Role-Based Access Control**: Separate roles for customers and administrators.
- **Product Management**: Browse products, brands, and categories.
- **Token Management**: JWT-based authentication with token refresh and expiration handling.
- **Swagger Integration**: API documentation for developers.
- **Error Handling**: Custom exceptions for better debugging and user feedback.

## Technologies Used

- **Java**: Core programming language.
- **Spring Boot**: Backend framework for building RESTful APIs.
- **JWT (JSON Web Tokens)**: Secure token-based authentication.
- **Nimbus JOSE + JWT**: Library for JWT encoding and decoding.
- **Swagger**: API documentation and testing.
- **Maven**: Dependency management and build tool.

## Getting Started

### Prerequisites

Ensure you have the following installed on your system:

- Java 17 or higher
- Maven 3.8 or higher
- A database (e.g., MySQL, PostgreSQL)
- An IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/shopapp.git
   cd shopapp
   ```

2. Configure the application properties:
   - Navigate to `src/main/resources/application.properties`.
   - Update the database connection details and JWT secret key.

3. Install dependencies:
   ```bash
   mvn clean install
   ```

### Running the Application

1. Start the application:
   ```bash
   mvn spring-boot:run
   ```

2. Access the application:
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

## API Documentation

The API documentation is available via Swagger. Visit `http://localhost:8080/swagger-ui.html` to explore and test the endpoints.

## Project Structure

```
shopapp/
├── src/
│   ├── main/
│   │   ├── java/vn/ptithcm/shopapp/
│   │   │   ├── controller/    # REST controllers
│   │   │   ├── model/         # Entity and DTO classes
│   │   │   ├── service/       # Business logic
│   │   │   ├── util/          # Utility classes
│   │   └── resources/
│   │       ├── application.properties  # Configuration
│   └── test/                 # Unit and integration tests
├── pom.xml                   # Maven configuration
└── README.md                 # Project documentation
```

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes and push the branch.
4. Submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

**Developed with ❤️ by PTIT HCM Team**
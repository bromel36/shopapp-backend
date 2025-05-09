# ShopApp

ShopApp is a comprehensive e-commerce platform designed to streamline online shopping experiences. It integrates advanced features such as user authentication, role-based access control, product management, and payment processing. The platform is built with scalability, security, and performance in mind, making it suitable for both small and large-scale businesses.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [System Architecture](#system-architecture)
- [Environment Setup](#environment-setup)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Features

- **User Management**:
  - Secure login, registration, and email verification.
  - Password reset and change functionality.
  - Role-based access control (Admin, Customer).
- **Product Management**:
  - CRUD operations for products, categories, and brands.
  - Advanced filtering and search capabilities.
- **Cart and Order Management**:
  - Add, update, and remove items from the cart.
  - Place orders and track order status.
- **Payment Integration**:
  - VNPay integration for secure online payments.
- **File Management**:
  - Upload and download files/images using Cloudinary and local storage.
- **API Documentation**:
  - Swagger UI for exploring and testing APIs.
- **Security**:
  - JWT-based authentication with token refresh and expiration handling.
  - Permission-based API access control.
- **Database Initialization**:
  - Automatic seeding of roles, permissions, and default users.

## Technologies Used

- **Backend**:
  - Java 17
  - Spring Boot (Web, Security, Data JPA, Validation)
  - Hibernate (ORM)
- **Authentication**:
  - JWT (JSON Web Tokens)
  - Nimbus JOSE + JWT
- **Database**:
  - MySQL
- **File Storage**:
  - Cloudinary (for image uploads)
  - Local file storage
- **API Documentation**:
  - SpringDoc OpenAPI (Swagger)
- **Messaging**:
  - SendGrid (Email service)
- **Build Tool**:
  - Maven
- **Containerization**:
  - Docker

## System Architecture

The system follows a layered architecture:
- **Controller Layer**: Handles HTTP requests and responses.
- **Service Layer**: Contains business logic.
- **Repository Layer**: Manages database interactions.
- **Utility Layer**: Provides helper functions for security, pagination, etc.

## Environment Setup

### Prerequisites

Ensure you have the following installed:
- Java 17 or higher
- Maven 3.8 or higher
- MySQL (or any compatible database)
- Docker (optional, for containerized deployment)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/shopapp.git
   cd shopapp
   ```

2. Configure environment variables:
   - Update `application.yaml` or `application.properties` with your database, JWT, Cloudinary, and VNPay credentials.

3. Install dependencies:
   ```bash
   mvn clean install
   ```

### Running the Application

1. Start the backend:
   ```bash
   mvn spring-boot:run
   ```

2. Access the application:
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

### Docker Deployment

1. Build the Docker image:
   ```bash
   docker build -t shopapp .
   ```

2. Run the container:
   ```bash
   docker run -p 8080:8080 shopapp
   ```

## API Documentation

The API documentation is available via Swagger. Visit `http://localhost:8080/swagger-ui.html` to explore and test the endpoints.

## Project Structure

```
shopapp/
├── src/
│   ├── main/
│   │   ├── java/vn/ptithcm/shopapp/
│   │   │   ├── config/         # Configuration files
│   │   │   ├── controller/     # REST controllers
│   │   │   ├── converter/      # DTO converters
│   │   │   ├── error/          # Custom exceptions and handlers
│   │   │   ├── model/          # Entity and DTO classes
│   │   │   ├── repository/     # Data access layer
│   │   │   ├── service/        # Business logic
│   │   │   ├── util/           # Utility classes
│   │   └── resources/
│   │       ├── application.yaml  # Configuration
│   │       ├── application.properties  # Optional properties
        └── test/                   # Unit and integration 
├── Dockerfile                  # Docker configuration
├── pom.xml                     # Maven configuration
└── README.md                   # Project documentation
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

# Rest-Assured API Testing Framework

A comprehensive API testing framework built with Rest-Assured, TestNG, and Java 21. This framework provides a structured approach to API automation testing with support for database integration, dynamic payload generation, and detailed reporting.

---

## Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Dependencies](#dependencies)
- [Framework Components](#framework-components)
  - [Request Builder](#request-builder)
  - [Payload Builder](#payload-builder)
  - [Base URIs](#base-uris)
  - [Utility Classes](#utility-classes)
- [Test Examples](#test-examples)
- [Running Tests](#running-tests)
- [Reporting](#reporting)
- [Database Integration](#database-integration)
- [Best Practices](#best-practices)

---

## Overview

This framework is designed for testing RESTful APIs using the BDD-style syntax provided by Rest-Assured. It follows a modular architecture with separation of concerns, making it easy to maintain and scale.

### Key Features

- **Modular Architecture**: Separated concerns with dedicated packages for requests, payloads, utilities, and tests
- **TestNG Integration**: Leverages TestNG for test execution, assertions, and test configuration
- **Dynamic Data Generation**: Uses JavaFaker for generating realistic test data
- **Database Support**: MySQL connectivity for data-driven testing
- **Allure Reporting**: Comprehensive test reports with Allure integration
- **JSON Schema Validation**: Built-in support for JSON response validation

---

## Project Structure

```
RestAssuredFirstGroup2026/
├── pom.xml                              # Maven configuration file
├── README.md                            # Project documentation
└── src/
    ├── main/
    │   └── java/
    │       └── utils/
    │           └── PayloadStore.java    # Payload templates as strings
    └── test/
        └── java/
            ├── basic/
            │   └── UserRegistration.java    # Basic test examples
            ├── common/
            │   └── BaseURIs.java            # Base URL configurations
            ├── payloadBuilder/
            │   └── PayloadBuilder.java      # JSON payload builders
            ├── requestBuilder/
            │   └── ApiRequestBuilder.java   # API request methods
            ├── tests/
            │   ├── ImprovedAPITests.java    # Improved test structure
            │   └── UserRegistrationTest.java# Complete user flow tests
            └── utils/
                ├── DatabaseConnection.java  # MySQL database utilities
                └── Requests.java            # HTTP request wrappers
```

---

## Prerequisites

Before setting up the project, ensure you have the following installed:

| Tool | Version | Description |
|------|---------|-------------|
| **JDK** | 21+ | Java Development Kit |
| **Maven** | 3.8+ | Build automation tool |
| **IntelliJ IDEA** | Latest | Recommended IDE |

---

## Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd RestAssuredFirstGroup2026
   ```

2. **Import the project**
   - Open IntelliJ IDEA
   - Select `File > Open`
   - Navigate to the project directory and open it
   - IntelliJ will automatically detect the Maven project and download dependencies

3. **Build the project**
   ```bash
   mvn clean install
   ```

---

## Dependencies

The framework uses the following key dependencies:

| Dependency | Version | Purpose |
|------------|---------|---------|
| **Rest-Assured** | 6.0.0 | API testing library |
| **TestNG** | 7.11.0 | Testing framework |
| **JSON Simple** | 1.1.1 | JSON parsing and creation |
| **Gson** | 2.13.0 | JSON serialization/deserialization |
| **Allure TestNG** | 2.29.1 | Test reporting |
| **JavaFaker** | 1.0.2 | Fake data generation |
| **JSON Schema Validator** | 5.5.1 | Response schema validation |
| **MySQL Connector** | 8.0.33 | Database connectivity |

---

## Framework Components

### Request Builder

The `ApiRequestBuilder` class encapsulates all API request logic, providing reusable methods for common operations:

```java
// Login request
Response response = ApiRequestBuilder.loginUserResponse("email@test.com", "password");

// Register user request
Response response = ApiRequestBuilder.registerUserResponse(
    "John", "Doe", "john@test.com", "password123", "groupId"
);

// Approve user registration
Response response = ApiRequestBuilder.approveUserRegistrationResponse();
```

### Payload Builder

The `PayloadBuilder` class creates JSON payloads using `json-simple`:

```java
// Create login payload
JSONObject loginPayload = PayloadBuilder.loginUserPayload("email@test.com", "password");

// Create registration payload
JSONObject registerPayload = PayloadBuilder.registerUserPayload(
    "John", "Doe", "john@test.com", "password123", "groupId"
);
```

### Base URIs

The `BaseURIs` class stores all base URL configurations:

```java
public class BaseURIs {
    public static String baseURL = "https://ndosiautomation.co.za";
}
```

### Utility Classes

#### Requests.java
Provides generic HTTP request methods:

```java
// GET request
Response response = Requests.get(url);

// POST request
Response response = Requests.post(url, payload);

// PUT request
Response response = Requests.put(url, payload);

// DELETE request
Response response = Requests.delete(url);
```

#### DatabaseConnection.java
Handles MySQL database connections for data-driven testing:

```java
// Establish connection and retrieve test data
DatabaseConnection.dbConnection();
String email = DatabaseConnection.getEmail;
String password = DatabaseConnection.getPassword;
```

---

## Test Examples

### Basic Test Structure

```java
@Test
public void loginTest() {
    Response response = Requests.post(baseURL + "/APIDEV/login", payload);
    Assert.assertEquals(response.getStatusCode(), 200);
}
```

### Complete User Registration Flow

```java
@Test
public void adminLoginTest() {
    ApiRequestBuilder.loginUserResponse(email, password)
        .then()
        .assertThat()
        .statusCode(200)
        .body("success", equalTo(true));
}

@Test(dependsOnMethods = "adminLoginTest")
public void userRegistration() {
    String email = Faker.instance().internet().emailAddress();
    ApiRequestBuilder.registerUserResponse("John", "Doe", email, "password", "groupId")
        .then()
        .assertThat()
        .statusCode(201)
        .body("success", equalTo(true));
}
```

---

## Running Tests

### Using Maven

```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=UserRegistrationTest

# Run with clean build
mvn clean test
```

### Using IntelliJ IDEA

1. Right-click on a test class or method
2. Select `Run '<TestName>'`

### Using TestNG XML

Create a `testng.xml` file to configure test suites and run specific tests.

---

## Reporting

### Allure Reports

The framework integrates with Allure for comprehensive test reporting.

1. **Generate Allure results**
   ```bash
   mvn clean test
   ```

2. **Generate and serve report**
   ```bash
   allure serve target/allure-results
   ```

3. **Generate static report**
   ```bash
   allure generate target/allure-results -o target/allure-report
   ```

---

## Database Integration

The framework supports MySQL database integration for:
- Retrieving test credentials
- Data-driven testing
- Test data setup and cleanup

### Configuration

Update the database connection details in `DatabaseConnection.java`:

```java
String dbURL = "jdbc:mysql://host:port/database";
String dbUsername = "username";
String dbPassword = "password";
```

---

## Best Practices

1. **Use Builder Pattern**: Leverage `PayloadBuilder` and `ApiRequestBuilder` for consistent requests
2. **Centralize Configuration**: Keep URLs and credentials in dedicated configuration classes
3. **Generate Dynamic Data**: Use JavaFaker for unique test data to avoid conflicts
4. **Use Test Dependencies**: Utilize TestNG's `dependsOnMethods` for sequential test execution
5. **Validate Responses**: Always validate status codes and response body content
6. **Enable Logging**: Use `.log().all()` for debugging during development
7. **Clean Up Test Data**: Implement teardown methods to clean test data after execution

---

## API Endpoints

The framework is configured to test the following API endpoints:

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/APIDEV/login` | POST | User authentication |
| `/APIDEV/register` | POST | User registration |
| `/APIDEV/admin/users/{id}/approve` | PUT | Approve user registration |

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Open a Pull Request

---

## License

This project is for educational purposes.

---

## Contact

For questions or support, please contact the project maintainers.

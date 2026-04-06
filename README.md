# REST Assured API Test Automation Framework

A Java-based API test automation framework built with **REST Assured** and **TestNG** for testing the Ndosi Automation platform's user management APIs (registration, approval, role assignment, login, and deletion).

---

## Table of Contents

- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Flow](#test-flow)
- [Allure Reporting](#allure-reporting)
- [Key Classes](#key-classes)
- [Database Support](#database-support)

---

## Tech Stack

| Technology           | Version | Purpose                              |
|----------------------|---------|--------------------------------------|
| Java                 | 21      | Programming language                 |
| Maven                | —       | Build & dependency management        |
| REST Assured         | 6.0.0   | HTTP client for API testing          |
| TestNG               | 7.11.0  | Test runner & assertions             |
| Allure TestNG        | 2.29.1  | Test reporting                       |
| JSON Simple          | 1.1.1   | JSON payload construction            |
| Gson                 | 2.13.0  | JSON serialisation/deserialisation   |
| Java Faker           | 1.0.2   | Random test data generation          |
| dotenv-java          | 3.2.0   | Environment variable management      |
| MySQL Connector/J    | 8.0.33  | Database connectivity                |
| JSON Schema Validator| 5.5.1   | JSON schema validation               |

---

## Project Structure

```
RestAssuredFirstGroup2026/
├── pom.xml                                         # Maven configuration & dependencies
├── README.md                                       # This file
├── allure-results/                                 # Allure test result files (auto-generated)
├── src/
│   ├── main/java/
│   │   └── utils/
│   │       └── PayloadStore.java                   # Utility for building raw JSON payloads
│   └── test/
│       ├── java/
│       │   ├── basic/
│       │   │   └── UserRegistration.java           # Standalone hardcoded registration test (reference)
│       │   ├── common/
│       │   │   └── BaseURIs.java                   # Base URI constants for the API
│       │   ├── constants/
│       │   │   └── Endpoints.java                  # API endpoint paths (loaded from config.properties)
│       │   ├── payloadBuilder/
│       │   │   └── APIPayloadBuilder.java          # JSON payload factory methods (login, register, role update)
│       │   ├── requestBuilder/
│       │   │   └── APIRequestBuilder.java          # REST Assured request methods for each API operation
│       │   ├── tests/
│       │   │   └── RegistrationTest.java           # End-to-end TestNG test class for the user lifecycle
│       │   └── utils/
│       │       ├── DatabaseConnections.java        # MySQL database connection & queries
│       │       ├── RandomDataGenerator.java        # Java Faker wrapper for random names & emails
│       │       ├── ReadConfigPropertyFile.java     # Reads config.properties for endpoints & settings
│       │       ├── ReadEnvFile.java                # Reads creds.env for sensitive credentials
│       │       └── Requests.java                   # Generic HTTP helper methods (GET, POST, PUT, DELETE)
│       └── resources/
│           ├── configFiles/
│           │   └── config.properties               # API endpoints, base URI, DB URL, group ID, role type
│           ├── envFiles/
│           │   └── creds.env                       # Credentials (admin email/password, user password, DB creds)
│           └── testData/                           # Placeholder for external test data files
```

---

## Prerequisites

- **Java 21** (or later)
- **Apache Maven 3.8+**
- **Allure CLI** (optional, for generating HTML reports)

Verify installations:

```bash
java -version
mvn -version
allure --version   # optional
```

---

## Setup & Installation

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd RestAssuredFirstGroup2026
   ```

2. **Install dependencies:**

   ```bash
   mvn clean install -DskipTests
   ```

3. **Configure credentials** (see [Configuration](#configuration) below).

---

## Configuration

### API & Database Settings

Edit `src/test/resources/configFiles/config.properties`:

```properties
# API
api.baseuri         = https://ndosiautomation.co.za
api.registerendpoint= /APIDEV/register
api.loginendpoint   = /APIDEV/login
api.adminendpoint   = /APIDEV/admin/users/
api.approveusersendpoint = /approve
api.roleendpoint    = /role
api.deleteuserendpoint   = /APIDEV/admin/users/
api.groupid         = <your-group-id>
api.roletype        = admin

# Database
db.baseurl = jdbc:mysql://<host>:<port>/<database>
```

### Sensitive Credentials

Edit `src/test/resources/envFiles/creds.env`:

```dotenv
ADMIN_EMAIL=<admin-email>
ADMIN_PASSWORD=<admin-password>
USER_PASSWORD=<user-password>
DB_USERNAME=<db-username>
DB_PASSWORD=<db-password>
```

> **Note:** The `creds.env` file is excluded from version control via `.gitignore`. Each team member must create their own copy locally.

You can also override any value by setting the corresponding **system environment variable** (e.g., `export ADMIN_EMAIL=...`); the framework checks system env vars first before falling back to the `.env` file.

---

## Running Tests

### Run all tests

```bash
mvn clean test
```

### Run a specific test class

```bash
mvn -Dtest=RegistrationTest test
```

### Run a specific test method

```bash
mvn -Dtest=RegistrationTest#adminLoginTest test
```

---

## Test Flow

The `RegistrationTest` class executes an end-to-end user lifecycle in the following order, enforced by TestNG `dependsOnMethods`:

```
┌─────────────────────┐
│  1. adminLoginTest   │  Admin logs in → auth token stored
└──────────┬──────────┘
           ▼
┌──────────────────────────┐
│ 2. userRegistrationTest  │  Register new user (random data) → user ID stored
└──────────┬───────────────┘
           ▼
┌─────────────────────┐
│  3. approveUserTest  │  Admin approves the registered user
└──────────┬──────────┘
           ▼
┌──────────────────────────┐
│  4. updateUserRoleTest   │  Admin updates user role to "admin"
└──────────┬───────────────┘
           ▼
┌─────────────────────┐
│   5. userLoginTest   │  Newly registered user logs in successfully
└──────────┬──────────┘
           ▼
┌──────────────────────────┐
│  6. adminLoginAgainTest  │  Admin re-authenticates (refresh token for delete)
└──────────┬───────────────┘
           ▼
┌─────────────────────┐
│  7. deleteUserTest   │  Admin deletes the registered user
└─────────────────────┘
```

If any test fails, all downstream dependent tests are **automatically skipped** by TestNG.

---

## Allure Reporting

Test results are written to the `allure-results/` directory automatically via the `allure-testng` dependency.

### Generate & open the HTML report

```bash
allure serve allure-results
```

### Generate a static report

```bash
allure generate allure-results -o allure-report --clean
allure open allure-report
```

---

## Key Classes

### `APIRequestBuilder`

Central class that builds and sends all REST Assured requests. Manages shared state (`authToken`, `userId`) across the test flow.

| Method                    | HTTP Method | Description                              |
|---------------------------|-------------|------------------------------------------|
| `loginUserResponse()`     | POST        | Logs in a user and captures the auth token |
| `registerUserResponse()`  | POST        | Registers a new user and captures the user ID |
| `approveUserResponse()`   | PUT         | Approves a pending user registration     |
| `updateUserRoleResponse()`| PUT         | Updates a user's role                    |
| `deleteUserResponse()`    | DELETE      | Deletes a user by the stored user ID     |

### `APIPayloadBuilder`

Factory methods that return `JSONObject` payloads:

| Method                  | Description                                   |
|-------------------------|-----------------------------------------------|
| `loginUserPayload()`    | Creates `{ email, password }` login payload   |
| `registerUserPayload()` | Creates registration payload with all fields  |
| `updateUserPayload()`   | Creates `{ role }` update payload             |

### `RandomDataGenerator`

Uses **Java Faker** to generate random test data:

| Method              | Returns                    |
|---------------------|----------------------------|
| `generateEmail()`   | Random email address       |
| `generateFirstName()` | Random first name        |
| `generateLastName()` | Random last name          |

### `ReadConfigPropertyFile`

Loads values from `config.properties`. Used by `Endpoints` and `DatabaseConnections`.

### `ReadEnvFile`

Loads sensitive credentials from `creds.env` (with system environment variable override support).

### `DatabaseConnections`

Provides direct MySQL database access for data verification outside the API layer.

---

## Contributing

1. Create a feature branch from `main`.
2. Follow the existing package/class naming conventions.
3. Add new endpoints to `config.properties` and reference them in `Endpoints.java`.
4. Add new request methods to `APIRequestBuilder.java`.
5. Add new payloads to `APIPayloadBuilder.java`.
6. Write tests in the `tests` package with proper `dependsOnMethods` ordering.
7. Never commit credentials — use the `creds.env` file locally.

---

## License

This project is for educational and training purposes.


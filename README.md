ChronoBlog

ChronoBlog is a simple blog application built using Spring Boot and follows best practices for building a RESTful API. The project includes features like CRUD operations for articles, validation, exception handling, and unit/integration testing.

🚀 Tech Stack

Java 17

Spring Boot 3.4.2

Spring Data JPA (Hibernate, H2 Database)

Spring Validation

Gradle (build automation tool)

JUnit, Mockito (unit tests)

Lombok (reduces boilerplate code)

MapStruct (DTO mapping)

📌 Features

✅ Create, Read, Update, Delete (CRUD) Articles✅ Validation with Custom Error Handling✅ Exception Handling (Global Exception Handling)✅ Unit & Integration Testing✅

⚙️ Setup & Installation

1️⃣ Clone the repository

git clone https://github.com/yourusername/chrono-blog.git
cd chrono-blog

2️⃣ Configure Database

The application uses H2 Database. Update the database configuration in application.yml:

spring:
datasource:
url: jdbc:h2:mem:chronoblogdb
driver-class-name: org.h2.Driver
username: sa
password:
jpa:
hibernate:
ddl-auto: update

If running tests, use H2 in-memory database (application-test.yml).

3️⃣ Run the application

./gradlew bootRun

4️⃣ Build the project

./gradlew clean build

🔍 API Endpoints

HTTP Method

Endpoint

Description

POST

/api/articles

Create a new article

GET

/api/articles

Get all articles (with pagination)

GET

/api/articles/{id}

Get article by ID

PUT

/api/articles/{id}

Update article by ID

DELETE

/api/articles/{id}

Delete article by ID

🧪 Running Tests

./gradlew test

Unit Tests: Cover service and repository layers

Integration Tests: Test API endpoints using Testcontainers

🎯 Project Structure

chrono-blog/
├── src/main/java/com/chrono/blog/
│   ├── controller/       # REST controllers
│   ├── service/          # Business logic
│   ├── repository/       # Database operations
│   ├── model/            # Entities & DTOs
│   ├── exception/        # Global exception handling
├── src/test/java/com/chrono/blog/ # Unit & Integration tests
├── build.gradle          # Gradle dependencies
├── README.md             # Documentation



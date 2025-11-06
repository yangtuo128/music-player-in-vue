# Music Player Backend

This is the backend service for the music player application, built with Spring Boot.

## Prerequisites

- Java 8 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

## Installation

1. Install MySQL and create a database named `music_player`.
2. Run the SQL script `src/main/resources/schema.sql` to create the necessary tables and sample data.
3. Update the database configuration in `src/main/resources/application.properties` if needed.

## Running the Application

### Using Maven

```bash
mvn spring-boot:run
```

### Using IDE

1. Import the project as a Maven project in your IDE.
2. Run the `MusicPlayerApplication.java` class.

## API Endpoints

### User Management

- `POST /api/register` - Register a new user
- `POST /api/login` - Login with phone number and password
- `GET /api/user/{id}` - Get user information by ID
- `PUT /api/user/{id}` - Update user information

## Database Schema

### Users Table

| Column Name | Data Type | Constraints |
|-------------|-----------|-------------|
| id          | BIGINT    | PRIMARY KEY, AUTO_INCREMENT |
| phone       | VARCHAR(20) | NOT NULL, UNIQUE |
| password    | VARCHAR(100) | NOT NULL |
| username    | VARCHAR(50) | |
| email       | VARCHAR(100) | |
| gender      | VARCHAR(10) | |
| created_at  | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP |
| updated_at  | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP |

## Technologies Used

- Spring Boot 2.5.4
- Spring Data JPA
- MySQL
- Lombok

## License

MIT
# Gym Management System

Java-based gym management application demonstrating clean architecture,
database interaction, and object-oriented design principles.

## Implemented Features

### Design Patterns
- **Singleton Pattern**: Implemented in `ValidationService` to ensure a single validation instance.
- **Repository Pattern**: Used to separate database access logic from business logic.

### SOLID Principles
- **Single Responsibility Principle**:  
  Controllers, repositories, services, and DTOs are separated into different layers,
  each responsible for a single part of the application logic.

### SQL & JOIN
- Data aggregation from **four related database tables** is implemented using JOIN logic.
- The `FullTrainingSessionDTO` combines data from:
  - Training Sessions
  - Members
  - Trainers
  - Memberships

### Security
- Role-based access control is implemented.
- Supported roles:
  - Admin
  - Manager
  - Trainer
  - Member

### Validation
- Input validation for email, phone number, and prices
- Implemented using lambda expressions and custom exceptions

## Database
The database schema is provided in the `schema.sql` file located in the project root directory.

## How to Run
1. Create the database using `schema.sql`
2. Open the project in IntelliJ IDEA
3. Run the application from `Main.java`

## Technologies
- Java
- JDBC
- SQL

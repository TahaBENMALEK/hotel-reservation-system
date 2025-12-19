# Hotel Reservation System

A robust Java-based hotel booking system managing rooms, users, and reservations with comprehensive validation and Test-Driven Development methodology.

---

## Features

- **Room Management**: Create and update rooms with different types (Standard, Junior, Suite)
- **User Management**: Manage users with balance tracking
- **Smart Booking System**:
    - Book rooms for specific date ranges with automatic validation
    - Real-time balance verification
    - Date overlap detection to prevent double bookings
    - Historical data snapshots at booking time
- **Robust Exception Handling**: Invalid dates, insufficient balance, room conflicts

---

## Tech Stack

| Component | Technology |
|-----------|------------|
| **Language** | Java 17 |
| **Build Tool** | Maven 3.x |
| **Testing Framework** | JUnit 5 |
| **Date/Time API** | Java Time API (LocalDate, ChronoUnit) |
| **Architecture** | Repository Pattern + SOLID Principles |
| **Methodology** | Test-Driven Development (TDD) |

---

## Quick Start

### Prerequisites

- Java 17 or higher ([Download here](https://www.oracle.com/java/technologies/downloads/))
- Maven 3.6+ ([Installation guide](https://maven.apache.org/install.html))
- Git for version control

### Installation & Setup

```bash
# 1. Clone the repository
git clone https://github.com/TahaBENMALEK/hotel-reservation-system.git
cd hotel-reservation-system

# 2. Build the project
mvn clean install

# 3. Run all tests
mvn clean test

# 4. Compile and run the application
mvn clean compile
java -cp target/classes com.hotelreservation.Main
```

### Expected Output

```
✗ User 1 booking Room 2 failed: Insufficient balance. Required: 14000, Available: 5000
✗ User 1 booking Room 2 failed: Check-out date must be after check-in date
✓ User 1 booked Room 1
✗ User 2 booking Room 1 failed: Room is already booked for these dates
✓ User 2 booked Room 3

=== ALL ROOMS (Latest First) ===
Room 3 | Type: SUITE | Price: 3000/night
Room 2 | Type: JUNIOR | Price: 2000/night
Room 1 | Type: SUITE | Price: 10000/night

=== ALL BOOKINGS (Latest First) ===
Booking | User: 2 | Room: 3 | 2026-07-07 to 2026-07-08 (1 nights)
  → Booked as: SUITE @ 3000/night | Total: 3000 | User balance was: 10000
Booking | User: 1 | Room: 1 | 2026-07-07 to 2026-07-08 (1 nights)
  → Booked as: STANDARD @ 1000/night | Total: 1000 | User balance was: 5000

=== ALL USERS (Latest First) ===
User 2 | Balance: 7000
User 1 | Balance: 4000
```

---

## Project Architecture

```
hotel-reservation-system/
├── src/
│   ├── main/java/com/hotelreservation/
│   │   ├── entities/              # Domain models with encapsulation
│   │   │   ├── Room.java          # Room entity with type & pricing
│   │   │   ├── User.java          # User entity with balance
│   │   │   └── Booking.java       # Booking with historical snapshots
│   │   ├── enums/
│   │   │   └── RoomType.java      # STANDARD, JUNIOR, SUITE
│   │   ├── exceptions/            # Custom exception hierarchy
│   │   │   ├── InsufficientBalanceException.java
│   │   │   ├── InvalidBookingException.java
│   │   │   ├── RoomNotFoundException.java
│   │   │   └── UserNotFoundException.java
│   │   ├── repositories/          # Data access layer
│   │   │   ├── RoomRepository.java
│   │   │   ├── UserRepository.java
│   │   │   └── BookingRepository.java
│   │   ├── services/
│   │   │   └── Service.java       # Business logic layer
│   │   └── Main.java              # Application entry point
│   └── test/java/com/hotelreservation/services/
│       ├── RoomServiceTest.java       # Room management tests
│       ├── UserServiceTest.java       # User management tests
│       ├── BookingServiceTest.java    # Booking validation tests
│       └── IntegrationTest.java       # Full system test
├── pom.xml                            # Maven configuration
├── .gitignore                         # Git exclusions
└── README.md                          # Project documentation
```

---

## Key Design Decisions

### 1. Snapshot Pattern for Historical Data

Store snapshots of room and user data at booking time to ensure historical accuracy:

```java
public class Booking {
    private final RoomType roomTypeAtBooking;     // Snapshot
    private final int pricePerNightAtBooking;     // Snapshot
    private final int userBalanceAtBooking;       // Snapshot
}
```

**Why?** Ensures booking history remains accurate even when room prices or types are updated.

### 2. Repository Pattern

Separation of concerns with three layers:
- **Service Layer**: Business logic and validation
- **Repository Layer**: Data access and storage
- **Entity Layer**: Domain models

**Benefits:** Testable, maintainable, follows SOLID principles.

### 3. Date Overlap Detection Algorithm

```java
boolean overlaps = !(checkOut.isBefore(existingCheckIn) ||
                     checkOut.isEqual(existingCheckIn) ||
                     checkIn.isAfter(existingCheckOut) ||
                     checkIn.isEqual(existingCheckOut));
```

Prevents double bookings by detecting date range overlaps.

---

## Test Coverage

| Test Suite | Tests | Status |
|-----------|-------|--------|
| RoomServiceTest | 4 | ✅ Passing |
| UserServiceTest | 3 | ✅ Passing |
| BookingServiceTest | 5 | ✅ Passing |
| IntegrationTest | 1 | ✅ Passing |
| **Total** | **13** | **✅ 100%** |

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=BookingServiceTest

# Run with coverage report
mvn clean test jacoco:report
```

---

## Test Scenarios

| Scenario | Expected Result |
|----------|----------------|
| User books room with sufficient balance | ✅ Success, balance deducted |
| User books with invalid date range | ❌ RuntimeException thrown |
| User books without enough balance | ❌ RuntimeException thrown |
| User books already occupied room | ❌ RuntimeException thrown |
| Room price updated after booking | ✅ Old bookings preserve original price |

---

## Development Methodology

This project follows **Test-Driven Development (TDD)**:

1. **RED Phase**: Write failing tests first
2. **GREEN Phase**: Implement minimal code to pass tests
3. **REFACTOR Phase**: Improve design while keeping tests green

See Git history for complete TDD cycle documentation with separate branches for each phase.

---

## Additional Commands

### Build Without Tests
```bash
mvn clean install -DskipTests
```

### Generate JavaDoc
```bash
mvn javadoc:javadoc
```

### Package as JAR
```bash
mvn clean package
```

---

## Design Patterns Used

- **Repository Pattern**: Data access abstraction
- **Snapshot Pattern**: Historical data preservation
- **Factory Pattern**: Entity creation
- **Immutable Objects**: Thread-safe Booking entities

---

## SOLID Principles Applied

- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: Extensible without modification
- **Liskov Substitution**: Proper inheritance hierarchy
- **Interface Segregation**: Minimal, focused interfaces
- **Dependency Inversion**: Depend on abstractions, not concretions

---

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## Author

**Taha BENMALEK**  
Full Stack Java Developer

- 📧 Email: benmalektaha.inpt@gmail.com
- 📱 Phone: +212 618 987 792
- 💼 LinkedIn: [linkedin.com/in/taha-benmalek-](https://linkedin.com/in/taha-benmalek-/)
- 🐙 GitHub: [@TahaBENMALEK](https://github.com/TahaBENMALEK)

---

## License

© 2024 Taha BENMALEK. All rights reserved.  
This project is licensed under the MIT License - feel free to use for learning and educational purposes.

---

## Acknowledgments

- Java & Oracle Documentation for comprehensive API references
- Maven Community for excellent build tooling
- JUnit team for the testing framework

---

**Made with ☕ and dedication by Taha BENMALEK**
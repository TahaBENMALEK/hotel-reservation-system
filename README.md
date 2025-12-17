# Hotel Reservation System 🏨

A robust Java-based hotel booking system that manages rooms, users, and reservations with comprehensive validation and error handling.

---

## 📋 Features

- **Room Management**: Create and update rooms with different types (Standard, Junior Suite, Master Suite)
- **User Management**: Manage users with balance tracking
- **Smart Booking System**: 
  - Book rooms for specific date ranges with automatic validation
  - Real-time balance verification
  - Date overlap detection to prevent double bookings
  - Historical data snapshots at booking time
- **Robust Exception Handling**: Invalid dates, insufficient balance, room conflicts

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| **Language** | Java 17 |
| **Build Tool** | Maven 3.x |
| **Testing Framework** | JUnit 5 |
| **Date/Time API** | Java Time API (LocalDate, ChronoUnit) |
| **Data Structures** | ArrayList (in-memory storage) |
| **Design Pattern** | Service Layer Pattern |

---

## 🚀 Quick Start

### Prerequisites
- ☕ Java 17 or higher ([Download here](https://www.oracle.com/java/technologies/downloads/))
- 📦 Maven 3.6+ ([Installation guide](https://maven.apache.org/install.html))
- 💻 IDE (IntelliJ IDEA recommended) or any text editor

### Installation & Setup

```bash
# 1. Clone the repository
git clone https://github.com/your-username/hotel-reservation-system-java.git
cd hotel-reservation-system-java

# 2. Build the project
mvn clean install

# 3. Run the application
mvn exec:java -Dexec.mainClass="com.hotelreservation.Main"

# 4. Run unit tests
mvn test

# 5. Generate test coverage report (optional)
mvn clean test jacoco:report
```

### Expected Output
```
=== Hotel Reservation System ===

Creating rooms...
Creating users...

--- Testing Bookings ---

User 1 booking Room 2 (7 nights)...
✓ Booking successful

User 1 booking Room 2 (invalid dates)...
✗ Booking failed: Check-out date must be after check-in date

[... more test results ...]

=== PRINT ALL ===
[Bookings and rooms data displayed here]

=== PRINT ALL USERS ===
[User data displayed here]
```

---

## 📂 Project Architecture

```
hotel-reservation-system-java/
├── src/
│   ├── main/
│   │   └── java/com/hotelreservation/
│   │       ├── entities/              # Domain models
│   │       │   ├── Room.java          # Room entity with type & pricing
│   │       │   ├── User.java          # User entity with balance
│   │       │   └── Booking.java       # Booking with data snapshots
│   │       ├── enums/
│   │       │   └── RoomType.java      # STANDARD, JUNIOR_SUITE, MASTER_SUITE
│   │       ├── exceptions/            # Custom exception hierarchy
│   │       │   ├── InsufficientBalanceException.java
│   │       │   ├── InvalidBookingException.java
│   │       │   ├── RoomNotFoundException.java
│   │       │   └── UserNotFoundException.java
│   │       ├── Service.java           # Business logic layer
│   │       └── Main.java              # Application entry point & demo
│   └── test/
│       └── java/com/hotelreservation/
│           └── ServiceTest.java       # Unit tests with JUnit 5
├── pom.xml                            # Maven configuration
├── .gitignore                         # Git exclusions
└── README.md                          # This file
```

---

## 🎯 Key Design Decisions

### 1. **Booking Entity Architecture**
Store snapshots of room and user data at booking time to ensure historical accuracy:
```java
Booking booking = new Booking(userId, roomNumber, checkIn, checkOut,
    room.getRoomType(),      // Snapshot of room type
    room.getPricePerNight(), // Snapshot of price
    user.getBalance()        // Snapshot of balance
);
```

### 2. **Date Overlap Detection**
```java
// Two bookings overlap if they DON'T satisfy either condition:
boolean noOverlap = newCheckOut.isBefore(existingCheckIn) ||
                    newCheckIn.isAfter(existingCheckOut);
```

### 3. **Exception Handling**
Custom exceptions for specific scenarios with descriptive messages for easier debugging.

---

## 📊 Test Scenarios

| Scenario | Expected Result |
|----------|----------------|
| User books room with sufficient balance | ✅ Success, balance deducted |
| User books with invalid date range | ❌ InvalidBookingException |
| User books without enough balance | ❌ InsufficientBalanceException |
| User books already occupied room | ❌ InvalidBookingException |
| Room price updated after booking | ✅ Old bookings unaffected |

---

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Manual Testing
Run `Main.java` to see the full booking workflow with console output.

---

## 📸 Sample Output

```
=== Hotel Reservation System ===

Creating rooms...
Creating users...

--- Testing Bookings ---

User 1 booking Room 2 (7 nights)...
✓ Booking successful

User 1 booking Room 2 (invalid dates)...
✗ Booking failed: Check-out date must be after check-in date
```

---

## 🔄 Future Enhancements

- [ ] REST API with Spring Boot
- [ ] Persistent storage with JPA
- [ ] React-based frontend
- [ ] Payment integration
- [ ] Email notifications

---

## 📚 Additional Commands

### Running Specific Tests
```bash
mvn test -Dtest=ServiceTest
```

### Generating JavaDoc
```bash
mvn javadoc:javadoc
```

### Build Without Tests
```bash
mvn clean install -DskipTests
```

---

## 🤝 Contributing

Feedback and suggestions are welcome!

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 👤 Author & Contact

**Taha BENMALEK**  
Full Stack Java Developer | React Enthusiast

📧 **Email**: [benmalektaha.inpt@gmail.com](mailto:benmalektaha.inpt@gmail.com)  
📱 **Phone**: +212 618 987 792  
💼 **LinkedIn**: [linkedin.com/in/taha-benmalek](https://linkedin.com/in/taha-benmalek-/)  
🐙 **GitHub**: [@TahaBENMALEK](https://github.com/TahaBENMALEK)

---

## 📄 License

© 2024 Taha BENMALEK. All rights reserved.
MIT license, you can use this project to learn and educate!

---

## 🙏 Acknowledgments

- Java & Oracle Documentation for comprehensive references
- Maven Community for excellent build tooling

---

<div align="center">

**⭐ If you found this project interesting, please consider giving it a star! ⭐**

Made with ☕ and 💻 by Taha BENMALEK

</div>
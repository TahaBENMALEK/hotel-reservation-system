# Hotel Reservation System

A Java-based hotel booking system that manages rooms, users, and reservations with comprehensive validation and error handling.

## 📋 Features

- **Room Management**: Create and update rooms with different types (Standard, Junior Suite, Master Suite)
- **User Management**: Manage users with balance tracking
- **Booking System**: 
  - Book rooms for specific date ranges
  - Automatic balance validation
  - Date overlap detection to prevent double bookings
  - Snapshot of room/user data at booking time
- **Comprehensive Exception Handling**: Invalid dates, insufficient balance, room conflicts

## 🛠️ Tech Stack

- **Language:** Java 17
- **Build Tool:** Maven 3.x
- **Testing:** JUnit 5
- **Date/Time:** Java Time API (LocalDate)

## 🚀 How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Clone the Repository
```bash
git clone https://github.com/your-username/hotel-reservation-system-java.git
cd hotel-reservation-system-java
```

### Build the Project
```bash
mvn clean install
```

### Run the Application
```bash
mvn exec:java -Dexec.mainClass="com.hotelreservation.Main"
```

### Run Tests
```bash
mvn test
```

## 📂 Project Structure
```
hotel-reservation-system-java/
├── src/
│   ├── main/java/com/hotelreservation/
│   │   ├── entities/
│   │   │   ├── Room.java
│   │   │   ├── User.java
│   │   │   └── Booking.java
│   │   ├── enums/
│   │   │   └── RoomType.java
│   │   ├── exceptions/
│   │   │   ├── InsufficientBalanceException.java
│   │   │   ├── InvalidBookingException.java
│   │   │   ├── RoomNotFoundException.java
│   │   │   └── UserNotFoundException.java
│   │   ├── Service.java
│   │   └── Main.java
│   └── test/java/com/hotelreservation/
│       └── ServiceTest.java
├── pom.xml
└── README.md
```

## 🎯 Key Design Decisions

### 1. Booking Entity Design
- Stores snapshots of room and user data at booking time
- Ensures `setRoom()` doesn't affect previous bookings
- Immutable booking records for data integrity

### 2. Date Overlap Detection
- Uses Java Time API for robust date handling
- Checks for conflicts before confirming bookings
- Prevents double bookings on the same room

### 3. Exception Handling
- Custom exceptions for different error scenarios
- Clear error messages for debugging
- Validation at multiple levels

## 📊 Test Cases

The system is tested with the following scenarios:
1. Valid booking with sufficient balance
2. Invalid booking with reversed dates
3. Multiple bookings on different rooms
4. Overlapping booking attempts (should fail)
5. Room updates that don't affect existing bookings

## 📸 Test Results

(Screenshots will be added after implementation)

## 👤 Author & Contact

Taha BENMALEK
benmalektaha.inpt@gmail.com | +212-618987792


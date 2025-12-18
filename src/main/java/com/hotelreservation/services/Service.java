package com.hotelreservation.services;

import com.hotelreservation.entities.Booking;
import com.hotelreservation.entities.Room;
import com.hotelreservation.entities.User;
import com.hotelreservation.enums.RoomType;
import com.hotelreservation.exceptions.InsufficientBalanceException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Main service handling room, user, and booking operations.
 * Phase 1: Simple implementation to pass tests.
 */
public class Service {
    // Package-private for test access
    ArrayList<Room> rooms;
    ArrayList<User> users;
    ArrayList<Booking> bookings;

    public Service() {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    /**
     * Creates or updates a room. Does not affect existing bookings.
     */
    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        // Find existing room
        Room existingRoom = null;
        for (Room r : rooms) {
            if (r.roomNumber == roomNumber) {
                existingRoom = r;
                break;
            }
        }

        if (existingRoom != null) {
            // Update existing room
            existingRoom.roomType = roomType;
            existingRoom.pricePerNight = roomPricePerNight;
        } else {
            // Create new room
            rooms.add(new Room(roomNumber, roomType, roomPricePerNight));
        }
    }

    /**
     * Creates or updates a user with balance.
     */
    public void setUser(int userId, int balance) {
        // Find existing user
        User existingUser = null;
        for (User u : users) {
            if (u.userId == userId) {
                existingUser = u;
                break;
            }
        }

        if (existingUser != null) {
            // Update existing user
            existingUser.balance = balance;
        } else {
            // Create new user
            users.add(new User(userId, balance));
        }
    }

    /**
     * Books a room for a user with comprehensive validation.
     * Throws RuntimeException for validation failures.
     */
    public void bookRoom(int userId, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        // Validation 1: Check dates
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new RuntimeException("Check-out date must be after check-in date");
        }

        // Find room
        Room room = null;
        for (Room r : rooms) {
            if (r.roomNumber == roomNumber) {
                room = r;
                break;
            }
        }
        if (room == null) {
            throw new RuntimeException("Room not found: " + roomNumber);
        }

        // Find user
        User user = null;
        for (User u : users) {
            if (u.userId == userId) {
                user = u;
                break;
            }
        }
        if (user == null) {
            throw new RuntimeException("User not found: " + userId);
        }

        // Validation 2: Check for overlapping bookings
        for (Booking b : bookings) {
            if (b.roomNumber == roomNumber) {
                // Check if dates overlap
                boolean overlaps = !(checkOut.isBefore(b.checkIn) ||
                        checkOut.isEqual(b.checkIn) ||
                        checkIn.isAfter(b.checkOut) ||
                        checkIn.isEqual(b.checkOut));
                if (overlaps) {
                    throw new RuntimeException("Room is already booked for these dates");
                }
            }
        }

        // Validation 3: Check balance
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        int totalCost = (int) nights * room.pricePerNight;

        if (user.balance < totalCost) {
            throw new RuntimeException("Insufficient balance. Required: " + totalCost +
                    ", Available: " + user.balance);
        }

        // All validations passed - create booking
        user.balance -= totalCost;

        // Store snapshot of data at booking time
        Booking booking = new Booking(
                userId,
                roomNumber,
                checkIn,
                checkOut,
                room.roomType,           // Snapshot
                room.pricePerNight,      // Snapshot
                user.balance + totalCost // Original balance before deduction
        );

        bookings.add(booking);
    }

    /**
     * Prints all rooms and bookings (latest first).
     */
    public void printAll() {
        System.out.println("\n=== ALL ROOMS (Latest First) ===");
        for (int i = rooms.size() - 1; i >= 0; i--) {
            Room r = rooms.get(i);
            System.out.printf("Room %d | Type: %s | Price: %d/night%n",
                    r.roomNumber, r.roomType, r.pricePerNight);
        }

        System.out.println("\n=== ALL BOOKINGS (Latest First) ===");
        for (int i = bookings.size() - 1; i >= 0; i--) {
            Booking b = bookings.get(i);
            long nights = ChronoUnit.DAYS.between(b.checkIn, b.checkOut);
            int cost = (int) nights * b.pricePerNightAtBooking;

            System.out.printf("Booking | User: %d | Room: %d | %s to %s (%d nights)%n",
                    b.userId, b.roomNumber, b.checkIn, b.checkOut, nights);
            System.out.printf("  → Booked as: %s @ %d/night | Total: %d | User balance was: %d%n",
                    b.roomTypeAtBooking, b.pricePerNightAtBooking, cost, b.userBalanceAtBooking);
        }
    }

    /**
     * Prints all users (latest first).
     */
    public void printAllUsers() {
        System.out.println("\n=== ALL USERS (Latest First) ===");
        for (int i = users.size() - 1; i >= 0; i--) {
            User u = users.get(i);
            System.out.printf("User %d | Balance: %d%n", u.userId, u.balance);
        }
    }
}
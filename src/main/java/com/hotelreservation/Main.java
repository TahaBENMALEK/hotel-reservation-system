package com.hotelreservation;

import com.hotelreservation.services.Service;
import com.hotelreservation.enums.RoomType;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();

        // Create 3 rooms
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.SUITE, 3000);

        // Create 2 users
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        // User 1 tries booking Room 2 (7 nights - WILL FAIL, not enough balance)
        try {
            service.bookRoom(1, 2, LocalDate.of(2026, 6, 30), LocalDate.of(2026, 7, 7));
            System.out.println("✓ User 1 booked Room 2");
        } catch (RuntimeException e) {
            System.out.println("✗ User 1 booking Room 2 failed: " + e.getMessage());
        }

        // User 1 tries booking Room 2 (invalid dates)
        try {
            service.bookRoom(1, 2, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 6, 30));
            System.out.println("✓ User 1 booked Room 2");
        } catch (RuntimeException e) {
            System.out.println("✗ User 1 booking Room 2 failed: " + e.getMessage());
        }

        // User 1 tries booking Room 1 (1 night - SHOULD SUCCEED)
        try {
            service.bookRoom(1, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));
            System.out.println("✓ User 1 booked Room 1");
        } catch (RuntimeException e) {
            System.out.println("✗ User 1 booking Room 1 failed: " + e.getMessage());
        }

        // User 2 tries booking Room 1 (overlap - WILL FAIL)
        try {
            service.bookRoom(2, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 9));
            System.out.println("✓ User 2 booked Room 1");
        } catch (RuntimeException e) {
            System.out.println("✗ User 2 booking Room 1 failed: " + e.getMessage());
        }

        // User 2 tries booking Room 3 (SHOULD SUCCEED)
        try {
            service.bookRoom(2, 3, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));
            System.out.println("✓ User 2 booked Room 3");
        } catch (RuntimeException e) {
            System.out.println("✗ User 2 booking Room 3 failed: " + e.getMessage());
        }

        // Update Room 1
        service.setRoom(1, RoomType.SUITE, 10000);

        // Print results
        service.printAll();
        service.printAllUsers();
    }
}
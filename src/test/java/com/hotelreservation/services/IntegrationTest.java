package com.hotelreservation.services;

import com.hotelreservation.enums.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

/**
 * Full system integration test covering all features.
 * Tests complete workflow: room/user creation, bookings, validations, and updates.
 */
class IntegrationTest {
    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
    }

    @Test
    void fullSystemScenarioTest() {
        // Setup: Create 3 rooms and 2 users
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.SUITE, 3000);
        service.setUser(1, 20000);  // High balance for testing
        service.setUser(2, 10000);

        // Scenario 1: Successful booking
        service.bookRoom(1, 2, LocalDate.of(2026, 6, 30), LocalDate.of(2026, 7, 7));

        // Scenario 2: Invalid dates (should fail)
        assertThrows(RuntimeException.class, () -> {
            service.bookRoom(1, 2, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 6, 30));
        });

        // Scenario 3: Another successful booking
        service.bookRoom(1, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));

        // Scenario 4: Overlapping booking (should fail)
        assertThrows(RuntimeException.class, () -> {
            service.bookRoom(2, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 9));
        });

        // Scenario 5: Booking different room (should succeed)
        service.bookRoom(2, 3, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));

        // Verify: 3 bookings created
        assertEquals(3, service.getBookings().size());

        // Update room (should not affect past bookings)
        service.setRoom(1, RoomType.SUITE, 10000);

        // Print results for verification
        service.printAll();
        service.printAllUsers();

        // Final verifications
        assertEquals(3, service.getRooms().size());
        assertEquals(RoomType.SUITE, service.getRooms().get(0).getRoomType());
    }
}
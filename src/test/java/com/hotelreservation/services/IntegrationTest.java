package com.hotelreservation.services;

import com.hotelreservation.enums.RoomType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration test using the exact scenario from requirements.
 */
class IntegrationTest {

    @Test
    void fullSystemScenarioTest() {
        Service service = new Service();

        // Create 3 rooms
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.SUITE, 3000);

        // Create 2 users
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        // User 1 tries booking Room 2 (7 nights = 14000) - should FAIL
        assertThrows(RuntimeException.class, () ->
                service.bookRoom(1, 2,
                        LocalDate.of(2026, 6, 30),
                        LocalDate.of(2026, 7, 7))
        );

        // User 1 tries invalid dates - should FAIL
        assertThrows(RuntimeException.class, () ->
                service.bookRoom(1, 2,
                        LocalDate.of(2026, 7, 7),
                        LocalDate.of(2026, 6, 30))
        );

        // User 1 books Room 1 (1 night) - should SUCCEED
        service.bookRoom(1, 1,
                LocalDate.of(2026, 7, 7),
                LocalDate.of(2026, 7, 8));

        assertEquals(1, service.bookings.size());
        assertEquals(4000, service.users.get(0).balance);

        // User 2 tries booking Room 1 (overlaps) - should FAIL
        assertThrows(RuntimeException.class, () ->
                service.bookRoom(2, 1,
                        LocalDate.of(2026, 7, 7),
                        LocalDate.of(2026, 7, 9))
        );

        // User 2 books Room 3 (1 night) - should SUCCEED
        service.bookRoom(2, 3,
                LocalDate.of(2026, 7, 7),
                LocalDate.of(2026, 7, 8));

        assertEquals(2, service.bookings.size());
        assertEquals(7000, service.users.get(1).balance);

        // Update Room 1
        service.setRoom(1, RoomType.SUITE, 10000);

        // Verify
        assertEquals(3, service.rooms.size());
        assertEquals(RoomType.SUITE, service.rooms.get(0).roomType);
        assertEquals(10000, service.rooms.get(0).pricePerNight);
        assertEquals(2, service.bookings.size());

        // Print (should not throw)
        service.printAll();
        service.printAllUsers();
    }
}
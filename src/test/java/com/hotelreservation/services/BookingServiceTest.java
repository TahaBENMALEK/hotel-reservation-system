package com.hotelreservation.services;

import com.hotelreservation.enums.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Tests for booking business rules and validation logic.
 * Verifies balance checks, date validation, overlap detection, and snapshot preservation.
 */
class BookingServiceTest {
    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setUser(1, 20000);  // High balance for testing success scenarios
    }

    @Test
    void shouldBookRoomWhenBalanceIsEnough() {
        // 7 nights × 2000 = 14000, user has 20000
        service.bookRoom(1, 2, LocalDate.of(2026, 6, 30), LocalDate.of(2026, 7, 7));
        assertEquals(1, service.getBookings().size());
        assertTrue(service.getUsers().get(0).getBalance() < 20000);
    }

    @Test
    void shouldFailBookingWhenBalanceIsInsufficient() {
        // 183 nights × 2000 = way too expensive
        assertThrows(RuntimeException.class, () -> {
            service.bookRoom(1, 2, LocalDate.of(2026, 6, 30), LocalDate.of(2026, 12, 30));
        });
    }

    @Test
    void shouldFailForInvalidDates() {
        // Checkout before checkin
        assertThrows(RuntimeException.class, () -> {
            service.bookRoom(1, 2, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 6, 30));
        });
    }

    @Test
    void shouldStoreBookingSnapshot() {
        service.bookRoom(1, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));

        // Verify initial snapshot
        assertEquals(RoomType.STANDARD, service.getBookings().get(0).getRoomTypeAtBooking());

        // Update room after booking
        service.setRoom(1, RoomType.SUITE, 5000);

        // Snapshot should remain unchanged
        assertEquals(RoomType.STANDARD, service.getBookings().get(0).getRoomTypeAtBooking());
    }

    @Test
    void shouldFailWhenRoomIsAlreadyBooked() {
        // First booking: July 7-10
        service.bookRoom(1, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 10));

        // Overlapping booking: July 8-11 (should fail)
        assertThrows(RuntimeException.class, () -> {
            service.bookRoom(1, 1, LocalDate.of(2026, 7, 8), LocalDate.of(2026, 7, 11));
        });
    }
}
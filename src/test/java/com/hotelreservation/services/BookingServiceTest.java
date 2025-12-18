package com.hotelreservation.services;

import com.hotelreservation.enums.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests booking business rules.
 */
class BookingServiceTest {

    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setUser(1, 5000);
    }

    /**
     * Booking succeeds when balance is sufficient and room is free.
     */
    @Test
    void shouldBookRoomWhenBalanceIsEnough() {
        service.bookRoom(
                1,
                1,
                LocalDate.of(2026, 7, 7),
                LocalDate.of(2026, 7, 8)
        );

        assertEquals(1, service.bookings.size());
        // 1 night * 1000 = 1000, balance: 5000 - 1000 = 4000
        assertEquals(4000, service.users.get(0).balance);
    }

    /**
     * Booking fails if balance is insufficient.
     */
    @Test
    void shouldFailBookingWhenBalanceIsInsufficient() {
        // User has 5000, tries to book 13 nights (13000 cost)
        Exception exception = assertThrows(RuntimeException.class, () ->
                service.bookRoom(
                        1,
                        1,
                        LocalDate.of(2026, 7, 7),
                        LocalDate.of(2026, 7, 20)
                )
        );

        assertTrue(exception.getMessage().toLowerCase().contains("balance") ||
                exception.getMessage().toLowerCase().contains("insufficient"));
    }

    /**
     * Booking fails if checkout date is before checkin date.
     */
    @Test
    void shouldFailForInvalidDates() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                service.bookRoom(
                        1,
                        1,
                        LocalDate.of(2026, 7, 10),
                        LocalDate.of(2026, 7, 7)
                )
        );

        assertTrue(exception.getMessage().toLowerCase().contains("date") ||
                exception.getMessage().toLowerCase().contains("invalid"));
    }

    /**
     * Booking should store snapshot of room and user data at booking time.
     */
    @Test
    void shouldStoreBookingSnapshot() {
        service.bookRoom(
                1,
                1,
                LocalDate.of(2026, 7, 7),
                LocalDate.of(2026, 7, 8)
        );

        // Update room after booking
        service.setRoom(1, RoomType.SUITE, 10000);

        // Booking should still have original data
        assertEquals(1, service.bookings.size());
        assertEquals(RoomType.STANDARD, service.bookings.get(0).roomTypeAtBooking);
        assertEquals(1000, service.bookings.get(0).pricePerNightAtBooking);
    }

    /**
     * Cannot book room if it overlaps with existing booking.
     */
    @Test
    void shouldFailWhenRoomIsAlreadyBooked() {
        service.setUser(2, 10000);

        // User 1 books room 1 from July 7-9
        service.bookRoom(1, 1,
                LocalDate.of(2026, 7, 7),
                LocalDate.of(2026, 7, 9));

        // User 2 tries to book same room with overlap (July 7-9)
        Exception exception = assertThrows(RuntimeException.class, () ->
                service.bookRoom(2, 1,
                        LocalDate.of(2026, 7, 7),
                        LocalDate.of(2026, 7, 9))
        );

        assertTrue(exception.getMessage().toLowerCase().contains("book") ||
                exception.getMessage().toLowerCase().contains("available") ||
                exception.getMessage().toLowerCase().contains("occupied"));
    }
}
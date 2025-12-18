package com.hotelreservation.services;

import com.hotelreservation.enums.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

/**
 * Tests booking business rules.
 */
class BookingServiceTest {
    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setUser(1, 5000);
    }

    @Test
    void shouldBookRoomWhenBalanceIsEnough() {
        service.bookRoom(1, 2, LocalDate.of(2026, 6, 30), LocalDate.of(2026, 7, 7));

        assertEquals(1, service.getBookings().size());

        assertTrue(service.getUsers().get(0).getBalance() < 5000);
    }

    @Test
    void shouldFailBookingWhenBalanceIsInsufficient() {
        assertThrows(RuntimeException.class, () -> {
            service.bookRoom(1, 2, LocalDate.of(2026, 6, 30), LocalDate.of(2026, 12, 30));
        });
    }

    @Test
    void shouldFailForInvalidDates() {
        assertThrows(RuntimeException.class, () -> {
            service.bookRoom(1, 2, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 6, 30));
        });
    }

    @Test
    void shouldStoreBookingSnapshot() {
        service.bookRoom(1, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));

        assertEquals(1, service.getBookings().size());
        assertEquals(RoomType.STANDARD, service.getBookings().get(0).getRoomTypeAtBooking());
        assertTrue(service.getBookings().get(0).getPricePerNightAtBooking() == 1000);

        service.setRoom(1, RoomType.SUITE, 5000);

        assertEquals(RoomType.STANDARD, service.getBookings().get(0).getRoomTypeAtBooking());
        assertTrue(service.getBookings().get(0).getPricePerNightAtBooking() == 1000);
    }

    @Test
    void shouldFailWhenRoomIsAlreadyBooked() {
        service.bookRoom(1, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 10));

        assertThrows(RuntimeException.class, () -> {
            service.bookRoom(1, 1, LocalDate.of(2026, 7, 8), LocalDate.of(2026, 7, 11));
        });
    }
}
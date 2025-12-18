package com.hotelreservation.services;

import com.hotelreservation.enums.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests room creation and updates.
 */
class RoomServiceTest {

    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
    }

    /**
     * A room should be created if it does not already exist.
     */
    @Test
    void shouldCreateRoomIfNotExists() {
        service.setRoom(1, RoomType.STANDARD, 1000);

        assertNotNull(service.rooms);
        assertEquals(1, service.rooms.size());
        assertEquals(1, service.rooms.get(0).roomNumber);
        assertEquals(RoomType.STANDARD, service.rooms.get(0).roomType);
        assertEquals(1000, service.rooms.get(0).pricePerNight);
    }

    /**
     * Rooms can share the same type but have different prices.
     */
    @Test
    void shouldAllowSameRoomTypeWithDifferentPrices() {
        service.setRoom(1, RoomType.SUITE, 3000);
        service.setRoom(2, RoomType.SUITE, 5000);

        assertEquals(2, service.rooms.size());
        assertEquals(RoomType.SUITE, service.rooms.get(0).roomType);
        assertEquals(RoomType.SUITE, service.rooms.get(1).roomType);
        assertEquals(3000, service.rooms.get(0).pricePerNight);
        assertEquals(5000, service.rooms.get(1).pricePerNight);
    }

    /**
     * Updating a room must not remove existing bookings.
     */
    @Test
    void shouldUpdateRoomWithoutDeletingBookings() {
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setUser(1, 5000);

        service.bookRoom(
                1,
                1,
                LocalDate.of(2026, 6, 30),
                LocalDate.of(2026, 7, 1)
        );

        // Update room 1 to SUITE type with new price
        service.setRoom(1, RoomType.SUITE, 10000);

        // Booking should still exist
        assertEquals(1, service.bookings.size());

        // Room should be updated
        assertEquals(RoomType.SUITE, service.rooms.get(0).roomType);
        assertEquals(10000, service.rooms.get(0).pricePerNight);
    }

    /**
     * setRoom should update existing room, not create duplicate.
     */
    @Test
    void shouldUpdateExistingRoomNotDuplicate() {
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(1, RoomType.SUITE, 10000);

        assertEquals(1, service.rooms.size());
        assertEquals(RoomType.SUITE, service.rooms.get(0).roomType);
    }
}
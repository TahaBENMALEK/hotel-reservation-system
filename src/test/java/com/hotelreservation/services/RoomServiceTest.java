package com.hotelreservation.services;

import com.hotelreservation.enums.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

/**
 * Tests room creation and updates.
 */
class RoomServiceTest {
    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
    }

    @Test
    void shouldCreateRoomIfNotExists() {
        service.setRoom(1, RoomType.STANDARD, 1000);

        assertEquals(1, service.getRooms().size());
        assertEquals(1, service.getRooms().get(0).getRoomNumber());
        assertEquals(RoomType.STANDARD, service.getRooms().get(0).getRoomType());
        assertEquals(1000, service.getRooms().get(0).getPricePerNight());
        assertTrue(service.getRooms().get(0).getRoomNumber() == 1);
    }

    @Test
    void shouldAllowSameRoomTypeWithDifferentPrices() {
        service.setRoom(1, RoomType.JUNIOR, 2000);
        service.setRoom(2, RoomType.JUNIOR, 3000);

        assertEquals(2, service.getRooms().size());
        assertEquals(RoomType.JUNIOR, service.getRooms().get(0).getRoomType());
        assertEquals(RoomType.JUNIOR, service.getRooms().get(1).getRoomType());
        assertTrue(service.getRooms().get(0).getPricePerNight() != service.getRooms().get(1).getPricePerNight());
        assertTrue(service.getRooms().get(0).getRoomNumber() != service.getRooms().get(1).getRoomNumber());
    }

    @Test
    void shouldUpdateRoomWithoutDeletingBookings() {
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setUser(1, 5000);
        service.bookRoom(1, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));

        assertEquals(1, service.getBookings().size());

        service.setRoom(1, RoomType.SUITE, 5000);
        assertEquals(RoomType.SUITE, service.getRooms().get(0).getRoomType());
        assertTrue(service.getRooms().get(0).getPricePerNight() == 5000);
    }

    @Test
    void shouldUpdateExistingRoomNotDuplicate() {
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(1, RoomType.JUNIOR, 2000);

        assertEquals(1, service.getRooms().size());
        assertEquals(RoomType.JUNIOR, service.getRooms().get(0).getRoomType());
    }
}
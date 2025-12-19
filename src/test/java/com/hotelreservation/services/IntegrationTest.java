package com.hotelreservation.services;

import com.hotelreservation.enums.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class IntegrationTest {
    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
    }

    @Test
    void fullSystemScenarioTest() {
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.SUITE, 3000);

        service.setUser(1, 20000);
        service.setUser(2, 10000);

        service.bookRoom(1, 2, LocalDate.of(2026, 6, 30), LocalDate.of(2026, 7, 7));

        assertThrows(RuntimeException.class, () -> {
            service.bookRoom(1, 2, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 6, 30));
        });

        service.bookRoom(1, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));

        assertThrows(RuntimeException.class, () -> {
            service.bookRoom(2, 1, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 9));
        });

        service.bookRoom(2, 3, LocalDate.of(2026, 7, 7), LocalDate.of(2026, 7, 8));

        assertEquals(3, service.getBookings().size());
        assertTrue(service.getUsers().get(0).getBalance() < 20000);
        service.setRoom(1, RoomType.SUITE, 10000);

        service.printAll();
        service.printAllUsers();

        assertEquals(3, service.getBookings().size());
        assertTrue(service.getUsers().get(0).getBalance() < 20000);

        assertEquals(3, service.getRooms().size());
        assertEquals(RoomType.SUITE, service.getRooms().get(0).getRoomType());
        assertTrue(service.getRooms().get(0).getPricePerNight() == 10000);
        assertEquals(3, service.getBookings().size());
    }
}
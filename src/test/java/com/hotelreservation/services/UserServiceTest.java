package com.hotelreservation.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests user creation and data handling.
 */
class UserServiceTest {
    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
    }

    @Test
    void shouldCreateUserIfNotExists() {
        service.setUser(1, 5000);

        assertEquals(1, service.getUsers().size());
        assertEquals(1, service.getUsers().get(0).getUserId());
        assertEquals(5000, service.getUsers().get(0).getBalance());
        assertTrue(service.getUsers().get(0).getUserId() == 1);
    }

    @Test
    void shouldUpdateUserNotDuplicate() {
        service.setUser(1, 5000);
        service.setUser(1, 10000);

        assertEquals(1, service.getUsers().size());
        assertTrue(service.getUsers().get(0).getBalance() == 10000);
    }

    @Test
    void shouldStoreMultipleUsers() {
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        assertEquals(2, service.getUsers().size());
        assertEquals(1, service.getUsers().get(0).getUserId());
        assertEquals(2, service.getUsers().get(1).getUserId());
    }
}

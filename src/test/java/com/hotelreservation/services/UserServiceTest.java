package com.hotelreservation.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests user creation and data handling.
 */
class UserServiceTest {

    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
    }

    /**
     * A user should be created if it does not exist.
     */
    @Test
    void shouldCreateUserIfNotExists() {
        service.setUser(1, 5000);

        assertNotNull(service.users);
        assertEquals(1, service.users.size());
        assertEquals(1, service.users.get(0).userId);
        assertEquals(5000, service.users.get(0).balance);
    }

    /**
     * Creating a user with the same ID should update balance, not duplicate.
     */
    @Test
    void shouldUpdateUserNotDuplicate() {
        service.setUser(1, 5000);
        service.setUser(1, 10000);

        assertEquals(1, service.users.size());
        assertEquals(10000, service.users.get(0).balance);
    }

    /**
     * Users should be stored in order (for printing from latest to oldest).
     */
    @Test
    void shouldStoreMultipleUsers() {
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        assertEquals(2, service.users.size());
        assertEquals(1, service.users.get(0).userId);
        assertEquals(2, service.users.get(1).userId);
    }
}
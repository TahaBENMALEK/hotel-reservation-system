package com.hotelreservation.entities;

/**
 * Represents a user with balance for bookings.
 */
public class User {
    // Public fields for cross-package test access
    public int userId;
    public int balance;

    public User(int userId, int balance) {
        this.userId = userId;
        this.balance = balance;
    }
}
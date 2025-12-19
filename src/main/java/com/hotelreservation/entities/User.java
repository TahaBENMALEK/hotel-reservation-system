package com.hotelreservation.entities;

/**
 * Represents a user with proper encapsulation.
 */
public class User {
    private final int userId;  // Immutable
    private int balance;

    public User(int userId, int balance) {
        this.userId = userId;
        this.balance = balance;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public int getBalance() {
        return balance;
    }

    // Setter
    public void setBalance(int balance) {
        this.balance = balance;
    }

    // Business method
    public void deductBalance(int amount) {
        this.balance -= amount;
    }
}
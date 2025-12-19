package com.hotelreservation.exceptions;

/**
 * Thrown when a user attempts to book a room with insufficient balance.
 */
public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
package com.hotelreservation.exceptions;

/**
 * Thrown when attempting to access a user that doesn't exist.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
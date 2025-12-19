package com.hotelreservation.exceptions;

/**
 * Thrown when attempting to access a room that doesn't exist.
 */
public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String message) {
        super(message);
    }
}
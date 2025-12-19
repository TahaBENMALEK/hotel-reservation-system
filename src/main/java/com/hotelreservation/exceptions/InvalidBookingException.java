package com.hotelreservation.exceptions;

/**
 * Thrown when booking validation fails (invalid dates, room unavailable, etc.).
 */
public class InvalidBookingException extends RuntimeException {
    public InvalidBookingException(String message) {
        super(message);
    }
}
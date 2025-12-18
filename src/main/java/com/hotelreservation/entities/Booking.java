package com.hotelreservation.entities;

import com.hotelreservation.enums.RoomType;
import java.time.LocalDate;

/**
 * Represents a booking with snapshots of room/user data at booking time.
 */
public class Booking {
    // Public fields for cross-package test access
    public int userId;
    public int roomNumber;
    public LocalDate checkIn;
    public LocalDate checkOut;

    // Snapshots to preserve historical data
    public RoomType roomTypeAtBooking;
    public int pricePerNightAtBooking;
    public int userBalanceAtBooking;

    public Booking(int userId, int roomNumber, LocalDate checkIn, LocalDate checkOut,
                   RoomType roomTypeAtBooking, int pricePerNightAtBooking, int userBalanceAtBooking) {
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomTypeAtBooking = roomTypeAtBooking;
        this.pricePerNightAtBooking = pricePerNightAtBooking;
        this.userBalanceAtBooking = userBalanceAtBooking;
    }
}
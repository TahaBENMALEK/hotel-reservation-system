package com.hotelreservation.entities;

import com.hotelreservation.enums.RoomType;
import java.time.LocalDate;

/**
 * Immutable booking with historical snapshots.
 */
public class Booking {
    private final int userId;
    private final int roomNumber;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final RoomType roomTypeAtBooking;
    private final int pricePerNightAtBooking;
    private final int userBalanceAtBooking;

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

    // Getters only (immutable)
    public int getUserId() {
        return userId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public RoomType getRoomTypeAtBooking() {
        return roomTypeAtBooking;
    }

    public int getPricePerNightAtBooking() {
        return pricePerNightAtBooking;
    }

    public int getUserBalanceAtBooking() {
        return userBalanceAtBooking;
    }
}
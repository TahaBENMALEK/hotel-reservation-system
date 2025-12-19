package com.hotelreservation.repositories;

import com.hotelreservation.entities.Booking;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles Booking data access and storage.
 * Single Responsibility: Only manages Booking entities.
 */
public class BookingRepository {
    private final ArrayList<Booking> bookings = new ArrayList<>();

    public void save(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> findByRoomNumber(int roomNumber) {
        return bookings.stream()
                .filter(b -> b.getRoomNumber() == roomNumber)
                .toList();
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }
}
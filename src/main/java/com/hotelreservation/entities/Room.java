package com.hotelreservation.entities;

import com.hotelreservation.enums.RoomType;

/**
 * Represents a hotel room with type and pricing.
 */
public class Room {
    // Public fields for cross-package test access
    public int roomNumber;
    public RoomType roomType;
    public int pricePerNight;

    public Room(int roomNumber, RoomType roomType, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }
}
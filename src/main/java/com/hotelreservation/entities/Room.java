package com.hotelreservation.entities;

import com.hotelreservation.enums.RoomType;

public class Room {
    private int roomNumber;
    private RoomType roomType;
    private int pricePerNight;

    public Room(int roomNumber, RoomType roomType, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }

    // TODO: Add getters/setters
}
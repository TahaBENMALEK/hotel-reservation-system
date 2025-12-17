package com.hotelreservation.services;

import com.hotelreservation.entities.*;
import com.hotelreservation.enums.RoomType;
import java.time.LocalDate;
import java.util.ArrayList;

public class Service {
    ArrayList<Room> rooms;
    ArrayList<User> users;
    ArrayList<Booking> bookings;

    public Service() {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        // TODO: Implement
    }

    public void setUser(int userId, int balance) {
        // TODO: Implement
    }

    public void bookRoom(int userId, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        // TODO: Implement
    }

    public void printAll() {
        // TODO: Implement
    }

    public void printAllUsers() {
        // TODO: Implement
    }
}
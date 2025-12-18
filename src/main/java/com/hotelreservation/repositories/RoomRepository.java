package com.hotelreservation.repositories;

import com.hotelreservation.entities.Room;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

/**
 * Handles Room data access and storage.
 * Single Responsibility: Only manages Room entities.
 */
public class RoomRepository {
    private final ArrayList<Room> rooms = new ArrayList<>();

    public void save(Room room) {
        rooms.add(room);
    }

    public Optional<Room> findByRoomNumber(int roomNumber) {
        return rooms.stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst();
    }

    public List<Room> findAll() {
        return new ArrayList<>(rooms);
    }
}
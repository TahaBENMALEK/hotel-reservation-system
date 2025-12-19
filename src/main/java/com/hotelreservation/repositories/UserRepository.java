package com.hotelreservation.repositories;

import com.hotelreservation.entities.User;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

/**
 * Handles User data access and storage.
 * Single Responsibility: Only manages User entities.
 */
public class UserRepository {
    private final ArrayList<User> users = new ArrayList<>();

    public void save(User user) {
        users.add(user);
    }

    public Optional<User> findByUserId(int userId) {
        return users.stream()
                .filter(u -> u.getUserId() == userId)
                .findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}
package com.hotelreservation.services;

import com.hotelreservation.entities.Booking;
import com.hotelreservation.entities.Room;
import com.hotelreservation.entities.User;
import com.hotelreservation.enums.RoomType;
import com.hotelreservation.repositories.RoomRepository;
import com.hotelreservation.repositories.UserRepository;
import com.hotelreservation.repositories.BookingRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Service layer with proper separation of concerns.
 * Phase 3: REFACTOR - OOP principles applied.
 */
public class Service {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public Service() {
        this.roomRepository = new RoomRepository();
        this.userRepository = new UserRepository();
        this.bookingRepository = new BookingRepository();
    }

    /**
     * Creates or updates a room without affecting bookings.
     */
    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        Optional<Room> existingRoom = roomRepository.findByRoomNumber(roomNumber);

        if (existingRoom.isPresent()) {
            Room room = existingRoom.get();
            room.setRoomType(roomType);
            room.setPricePerNight(roomPricePerNight);
        } else {
            roomRepository.save(new Room(roomNumber, roomType, roomPricePerNight));
        }
    }

    /**
     * Creates or updates a user.
     */
    public void setUser(int userId, int balance) {
        Optional<User> existingUser = userRepository.findByUserId(userId);

        if (existingUser.isPresent()) {
            existingUser.get().setBalance(balance);
        } else {
            userRepository.save(new User(userId, balance));
        }
    }

    /**
     * Books a room with comprehensive validation.
     */
    public void bookRoom(int userId, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        validateDates(checkIn, checkOut);

        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomNumber));

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        validateNoOverlap(roomNumber, checkIn, checkOut);

        int totalCost = calculateCost(room, checkIn, checkOut);
        validateBalance(user, totalCost);

        // Deduct balance and save booking
        user.deductBalance(totalCost);

        Booking booking = new Booking(
                userId, roomNumber, checkIn, checkOut,
                room.getRoomType(),
                room.getPricePerNight(),
                user.getBalance() + totalCost  // Original balance
        );

        bookingRepository.save(booking);
    }

    /**
     * Prints all rooms and bookings (latest first).
     */
    public void printAll() {
        List<Room> rooms = roomRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();

        System.out.println("\n=== ALL ROOMS (Latest First) ===");
        for (int i = rooms.size() - 1; i >= 0; i--) {
            Room r = rooms.get(i);
            System.out.printf("Room %d | Type: %s | Price: %d/night%n",
                    r.getRoomNumber(), r.getRoomType(), r.getPricePerNight());
        }

        System.out.println("\n=== ALL BOOKINGS (Latest First) ===");
        for (int i = bookings.size() - 1; i >= 0; i--) {
            Booking b = bookings.get(i);
            long nights = ChronoUnit.DAYS.between(b.getCheckIn(), b.getCheckOut());
            int cost = (int) nights * b.getPricePerNightAtBooking();

            System.out.printf("Booking | User: %d | Room: %d | %s to %s (%d nights)%n",
                    b.getUserId(), b.getRoomNumber(), b.getCheckIn(), b.getCheckOut(), nights);
            System.out.printf("  → Booked as: %s @ %d/night | Total: %d | User balance was: %d%n",
                    b.getRoomTypeAtBooking(), b.getPricePerNightAtBooking(), cost, b.getUserBalanceAtBooking());
        }
    }

    /**
     * Prints all users (latest first).
     */
    public void printAllUsers() {
        List<User> users = userRepository.findAll();

        System.out.println("\n=== ALL USERS (Latest First) ===");
        for (int i = users.size() - 1; i >= 0; i--) {
            User u = users.get(i);
            System.out.printf("User %d | Balance: %d%n", u.getUserId(), u.getBalance());
        }
    }

    // ===== Private Validation Methods =====

    private void validateDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new RuntimeException("Check-out date must be after check-in date");
        }
    }

    private void validateNoOverlap(int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> existingBookings = bookingRepository.findByRoomNumber(roomNumber);

        for (Booking b : existingBookings) {
            boolean overlaps = !(checkOut.isBefore(b.getCheckIn()) ||
                    checkOut.isEqual(b.getCheckIn()) ||
                    checkIn.isAfter(b.getCheckOut()) ||
                    checkIn.isEqual(b.getCheckOut()));
            if (overlaps) {
                throw new RuntimeException("Room is already booked for these dates");
            }
        }
    }

    private int calculateCost(Room room, LocalDate checkIn, LocalDate checkOut) {
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        return (int) nights * room.getPricePerNight();
    }

    private void validateBalance(User user, int totalCost) {
        if (user.getBalance() < totalCost) {
            throw new RuntimeException("Insufficient balance. Required: " + totalCost +
                    ", Available: " + user.getBalance());
        }
    }
    // For testing purposes - expose repository data
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }
}
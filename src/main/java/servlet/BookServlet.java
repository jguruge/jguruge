package servlet;

import model.Booking;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookHandle {
    // Write a single booking to book.txt
    public static void writeBooking(Booking booking, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(booking.toString());
            writer.newLine();
        }
    }

    // Read all bookings from book.txt
    public static List<Booking> readBookings(String filePath) throws IOException {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Booking booking = parseBooking(line);
                    if (booking != null) {
                        bookings.add(booking);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing booking line: " + line);
                }
            }
        }
        return bookings;
    }

    // Parse a line from book.txt into a Booking object
    private static Booking parseBooking(String line) {
        try {
            // Expected format: Booking=1, Room=101, User=user@example.com, Checkin=2025-05-20, Checkout=2025-05-25
            String[] parts = line.split(", ");
            if (parts.length != 5) return null;

            // Extract bookingId
            String bookingIdStr = parts[0].substring("Booking=".length());
            int bookingId = Integer.parseInt(bookingIdStr);

            // Extract roomId
            String roomIdStr = parts[1].substring("Room=".length());
            int roomId = Integer.parseInt(roomIdStr);

            // Extract userEmail
            String userEmail = parts[2].substring("User=".length());

            // Extract checkInDate
            String checkInStr = parts[3].substring("Checkin=".length());
            LocalDate checkInDate = LocalDate.parse(checkInStr);

            // Extract checkOutDate
            String checkOutStr = parts[4].substring("Checkout=".length());
            LocalDate checkOutDate = LocalDate.parse(checkOutStr);

            return new Booking(bookingId, userEmail, roomId, checkInDate, checkOutDate);
        } catch (Exception e) {
            return null; // Skip malformed lines
        }
    }
}
//hi
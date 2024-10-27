package listener;

import context.DBContext;

import java.sql.*;
import java.util.Date;

public class BookingCleanupTask extends DBContext implements Runnable {

    private static final long EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes in milliseconds

    @Override
    public void run() {
        try {
            // Use connection from DBContext
            String selectQuery = "SELECT bookingId, timestamp FROM Booking WHERE status != 1"; // Pending bookings
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                int bookingId = resultSet.getInt("bookingId");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");

                // Check if the booking is older than 5 minutes
                if (isExpired(timestamp)) {
                    deleteBooking(bookingId);
                }
            }

            resultSet.close();
            selectStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check if the booking timestamp has expired (more than 5 minutes)
    private boolean isExpired(Timestamp bookingTimestamp) {
        long currentTime = new Date().getTime();
        long bookingTime = bookingTimestamp.getTime();
        return currentTime - bookingTime > EXPIRATION_TIME;
    }

    // Method to delete a booking
    private void deleteBooking(int bookingId) {
        try {
            // Delete all tickets associated with the booking first
            String deleteTicketsQuery = "DELETE FROM Ticket WHERE bookingId = ?";
            PreparedStatement deleteTicketsStatement = connection.prepareStatement(deleteTicketsQuery);
            deleteTicketsStatement.setInt(1, bookingId);
            deleteTicketsStatement.executeUpdate();
            deleteTicketsStatement.close();

            // Then delete the booking
            String deleteBookingQuery = "DELETE FROM Booking WHERE bookingId = ?";
            PreparedStatement deleteBookingStatement = connection.prepareStatement(deleteBookingQuery);
            deleteBookingStatement.setInt(1, bookingId);
            deleteBookingStatement.executeUpdate();
            deleteBookingStatement.close();

            System.out.println("Booking " + bookingId + " has been deleted due to expiration.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
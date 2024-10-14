
package repository;

import context.DBContext;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class BookingDAO extends DBContext {
    public Booking getBookingById(int bookingId) {
        Booking booking = null;
        String sql = "SELECT bookingId, totalcost, timestamp, UID, status FROM Booking WHERE bookingId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                booking = new Booking();
                booking.setBookingId(resultSet.getInt("bookingId"));
                booking.setTotalCost(resultSet.getDouble("totalcost"));
                booking.setStatus(resultSet.getInt("status"));
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                if (timestamp != null) {
                    booking.setTimestamp(new Date(timestamp.getTime()));  // Convert to java.util.Date
                }
                AccountDAO accountDAO = new AccountDAO();
                Account user = accountDAO.getAccountById(resultSet.getInt("UID"));
                booking.setUser(user);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }

    public Booking getBookingByUserAndDate(int userId, Date timestamp) {
        Booking booking = null;
        String sql = "SELECT bookingId, totalcost, timestamp, UID, status FROM Booking WHERE UID = ? AND CAST(timestamp AS DATE) = CAST(? AS DATE)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setTimestamp(2, new Timestamp(timestamp.getTime())); // Convert java.util.Date to SQL timestamp
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                booking = new Booking();
                booking.setBookingId(resultSet.getInt("bookingId"));
                booking.setTotalCost(resultSet.getDouble("totalcost"));
                booking.setStatus(resultSet.getInt("status"));
                Timestamp dbTimestamp = resultSet.getTimestamp("timestamp");
                if (dbTimestamp != null) {
                    booking.setTimestamp(new Date(dbTimestamp.getTime())); // Convert to java.util.Date
                }

                AccountDAO accountDAO = new AccountDAO();
                Account user = accountDAO.getAccountById(resultSet.getInt("UID"));
                booking.setUser(user);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }

    public ArrayList<Booking> getBookingHistory(int userId) {

        ArrayList<Booking> bookingHistory = new ArrayList<>();
        String sql = "SELECT bookingId, totalcost, timestamp, UID, status FROM Booking WHERE UID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getInt("bookingId"));
                booking.setTotalCost(resultSet.getDouble("totalcost"));
                booking.setStatus(resultSet.getInt("status"));
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                if (timestamp != null) {
                    booking.setTimestamp(new Date(timestamp.getTime()));  // Convert to java.util.Date
                }
                AccountDAO accountDAO = new AccountDAO();
                Account user = accountDAO.getAccountById(resultSet.getInt("UID"));
                booking.setUser(user);

                bookingHistory.add(booking);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookingHistory;
    }

    public ArrayList<Booking> getListBookingByCinema(int cinemaId) {
        ArrayList<Booking> bookingList = new ArrayList<>();
        String sql = "SELECT b.bookingId, b.totalcost, b.timestamp, b.UID, b.status " +
                "FROM Booking b " +
                "JOIN Ticket t ON b.bookingId = t.bookingId " +
                "JOIN Showtime st ON t.showtimeId = st.showtimeId " +
                "JOIN Room r ON st.roomId = r.roomId " +
                "JOIN Cinema c ON r.cinemaId = c.cinemaId " +
                "WHERE c.cinemaId = ?";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cinemaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getInt("bookingId"));
                booking.setTotalCost(resultSet.getDouble("totalcost"));
                booking.setStatus(resultSet.getInt("status"));
                // Retrieve the timestamp and convert it to java.util.Date
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                if (timestamp != null) {
                    booking.setTimestamp(new Date(timestamp.getTime()));  // Convert to java.util.Date
                }

                // Retrieve Account object by UID
                AccountDAO accountDAO = new AccountDAO();
                Account user = accountDAO.getAccountById(resultSet.getInt("UID"));
                booking.setUser(user);

                // Add the booking to the list
                bookingList.add(booking);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookingList;
    }

    public boolean addBooking(Booking booking) {

        String sql = "INSERT INTO Booking (totalCost, timestamp, UID, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, booking.getTotalCost());
            ps.setTimestamp(2, new Timestamp(booking.getTimestamp().getTime()));
            ps.setInt(3, booking.getUser().getUID());
            ps.setInt(4, booking.getStatus());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean paymentConfirm(int bookingId) {

        try {
            String sql = "UPDATE Booking SET status = ? WHERE bookingId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1); // Set the status to 1
            preparedStatement.setInt(2, bookingId);

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Booking> searchBookingByDate(Date timestamp) {

        ArrayList<Booking> bookings = new ArrayList<>();
        String sql = "SELECT bookingId, totalcost, timestamp, UID, status FROM Booking WHERE DATE(timestamp) = DATE(?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, new Timestamp(timestamp.getTime())); // Convert to SQL timestamp
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setBookingId(resultSet.getInt("bookingId"));
                booking.setTotalCost(resultSet.getDouble("totalcost"));

                Timestamp resultTimestamp = resultSet.getTimestamp("timestamp");
                if (resultTimestamp != null) {
                    booking.setTimestamp(new Date(resultTimestamp.getTime())); // Convert to java.util.Date
                }

                AccountDAO accountDAO = new AccountDAO();
                Account user = accountDAO.getAccountById(resultSet.getInt("UID"));
                booking.setUser(user);

                booking.setStatus(resultSet.getInt("status"));

                bookings.add(booking);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public double getDailyTotalCostByCinema(int cinemaId) {

        // Initialize the total cost to 0.0
        double totalCost = 0.0;

        // SQL query to get the sum of totalCost for bookings made today for a specific cinema
        String sql = "SELECT SUM(b.totalCost) as dailyTotal FROM Booking b " +
                "JOIN Ticket t ON b.bookingId = t.bookingId " +
                "JOIN Showtime st ON t.showtimeId = st.showtimeId " +
                "JOIN Room r ON st.roomId = r.roomId " +
                "JOIN Cinema c ON r.cinemaId = c.cinemaId " +
                "WHERE c.cinemaId = ? AND CAST(b.timestamp AS DATE) = CAST(GETDATE() AS DATE)";

        try {
            // Prepare the SQL statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Set the cinemaId parameter
            preparedStatement.setInt(1, cinemaId);
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // If there is a result, get the total cost
            if (resultSet.next()) {
                totalCost = resultSet.getDouble("dailyTotal");
            }

            // Close the result set and prepared statement
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the total cost for today
        return totalCost;
    }

    public double getTotalCostInCurrentMonthByCinema(int cinemaId) {

        String sql = "SELECT SUM(b.totalCost) as monthlyTotal FROM Booking b " +
                "JOIN Ticket t ON b.bookingId = t.bookingId " +
                "JOIN Showtime st ON t.showtimeId = st.showtimeId " +
                "JOIN Room r ON st.roomId = r.roomId " +
                "JOIN Cinema c ON r.cinemaId = c.cinemaId " +
                "WHERE c.cinemaId = ? " +
                "AND MONTH(b.timestamp) = MONTH(CURRENT_DATE) " +
                "AND YEAR(b.timestamp) = YEAR(CURRENT_DATE)";

        double totalCost = 0.0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cinemaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalCost = resultSet.getDouble("monthlyTotal");
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalCost;
    }

    public static void main(String[] args) {
        BookingDAO bookingDAO = new BookingDAO();
        System.out.println(bookingDAO.getBookingHistory(1));
    }
}

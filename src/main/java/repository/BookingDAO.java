
package repository;

import context.DBContext;
import model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public Booking getNewestBookingByUser(int userId) {
        Booking booking = null;
        String sql = "SELECT TOP 1 bookingId, totalcost, timestamp, UID, status " +
                "FROM Booking " +
                "WHERE UID = ? " +
                "ORDER BY timestamp DESC "; // Only return the newest booking (latest timestamp)

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
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
                "WHERE c.cinemaId = ? " +
                "GROUP BY b.bookingId, b.totalcost, b.timestamp, b.UID, b.status";


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

    public boolean deleteBooking(int bookingId) {
        TicketDAO ticketDAO = new TicketDAO();
        ArrayList<Ticket> tickets = ticketDAO.getTicketByBooking(bookingId);

        // Step 1: Delete all tickets associated with this booking
        for (Ticket ticket : tickets) {
            if (!ticketDAO.deleteTicket(ticket.getTicketId())) {
                return false; // Return false if any ticket deletion fails
            }
        }

        // Step 2: Delete the booking itself
        String sql = "DELETE FROM Booking WHERE bookingId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookingId);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // Return true if booking deletion is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there's an SQL exception
        }
    }

    public boolean paymentPending(int bookingId) {
        try {
            String sql = "UPDATE Booking SET status = ? WHERE bookingId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 0); // Set the status to 0
            preparedStatement.setInt(2, bookingId);

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean paymentFailed(int bookingId) {
        try {
            String sql = "UPDATE Booking SET status = ? WHERE bookingId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 2); // Set the status to 2
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
        String sql = "SELECT SUM(DISTINCT b.totalCost) as dailyTotal FROM Booking b " +
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

        String sql = "SELECT SUM(DISTINCT b.totalCost) as monthlyTotal FROM Booking b " +
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

    public BookingDetail getBookingDetailById(int bookingId) {
        BookingDetail bookingDetail = null;
        String sql = "SELECT b.bookingId, b.timestamp AS bookingDate, a.email AS userEmail, " +
                "b.totalcost, m.name AS filmName, m.poster AS moviePoster, " +
                "s.showdate AS showtimeDate, s.starttime AS showtimeStart, s.endtime AS showtimeEnd, " +
                "r.name AS roomName, seat.seatNum, " +
                "c.name AS cinemaName, c.address AS cinemaAddress, c.logo AS cinemaLogo " +
                "FROM Booking b " +
                "JOIN Account a ON b.UID = a.UID " +
                "JOIN Ticket t ON b.bookingId = t.bookingId " +
                "JOIN Showtime s ON t.showtimeId = s.showtimeId " +
                "JOIN Movie m ON s.movieId = m.movieId " +
                "JOIN Room r ON s.roomId = r.roomId " +
                "JOIN Seat seat ON t.seatId = seat.seatId " +
                "JOIN Cinema c ON r.cinemaId = c.cinemaId " +
                "WHERE b.bookingId = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookingId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {  // Check if the ResultSet is empty
                    return null;  // or throw new BookingNotFoundException("Booking not found for ID: " + bookingId);
                }

                StringBuilder seatNumbersBuilder = new StringBuilder();

                // Process the result set if booking data is found
                if (resultSet.next()) {
                    bookingDetail = new BookingDetail();
                    bookingDetail.setBookingId(resultSet.getInt("bookingId"));
                    bookingDetail.setBookingDate(resultSet.getTimestamp("bookingDate").toString());
                    bookingDetail.setUserEmail(resultSet.getString("userEmail"));
                    bookingDetail.setTotalCost(resultSet.getDouble("totalcost"));
                    bookingDetail.setFilmName(resultSet.getString("filmName"));
                    bookingDetail.setMoviePoster(resultSet.getString("moviePoster"));
                    bookingDetail.setShowtimeDate(resultSet.getString("showtimeDate"));
                    bookingDetail.setShowtimeStart(resultSet.getString("showtimeStart"));
                    bookingDetail.setShowtimeEnd(resultSet.getString("showtimeEnd"));
                    bookingDetail.setRoomName(resultSet.getString("roomName"));
                    bookingDetail.setCinemaName(resultSet.getString("cinemaName"));
                    bookingDetail.setCinemaAddress(resultSet.getString("cinemaAddress"));
                    bookingDetail.setCinemaLogo(resultSet.getString("cinemaLogo"));

                    // Append the first seat number
                    seatNumbersBuilder.append(resultSet.getString("seatNum"));

                    // Collect any additional seat numbers
                    while (resultSet.next()) {
                        seatNumbersBuilder.append(", ").append(resultSet.getString("seatNum"));
                    }

                    bookingDetail.setSeatNumbers(seatNumbersBuilder.toString());  // Set as comma-separated string
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookingDetail;
    }

    public boolean addBookingWithTickets(Booking booking, List<Ticket> tickets) {
        try {
            connection.setAutoCommit(false);
            // Insert booking
            String sqlBooking = "INSERT INTO Booking (totalCost, timestamp, UID, status) VALUES (?, ?, ?, ?)";
            PreparedStatement psBooking = connection.prepareStatement(sqlBooking, Statement.RETURN_GENERATED_KEYS);
            psBooking.setDouble(1, booking.getTotalCost());
            psBooking.setTimestamp(2, new Timestamp(booking.getTimestamp().getTime()));
            psBooking.setInt(3, booking.getUser().getUID());
            psBooking.setInt(4, booking.getStatus());
            int rowsAffected = psBooking.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Creating booking failed, no rows affected.");
            }

            // Get the generated booking ID
            ResultSet generatedKeys = psBooking.getGeneratedKeys();
            if (generatedKeys.next()) {
                booking.setBookingId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating booking failed, no ID obtained.");
            }

            // Prepare ticket insertion
            String sqlTicket = "INSERT INTO Ticket (price, name, email, phone, bookingId, showtimeId, seatId) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psTicket = connection.prepareStatement(sqlTicket);

            for (Ticket ticket : tickets) {
                // Check if seat is already booked
                if (isSeatBooked(connection, ticket.getShowtime().getShowtimeId(), ticket.getSeat().getSeatId())) {
                    // Seat is already booked, rollback and return false
                    connection.rollback();
                    return false;
                }

                // Set ticket parameters
                psTicket.setDouble(1, ticket.getPrice());
                psTicket.setString(2, ticket.getName());
                psTicket.setString(3, ticket.getEmail());
                psTicket.setString(4, ticket.getPhone());
                psTicket.setInt(5, booking.getBookingId());
                psTicket.setInt(6, ticket.getShowtime().getShowtimeId());
                psTicket.setInt(7, ticket.getSeat().getSeatId());
                psTicket.addBatch();
            }

            // Execute batch insert for tickets
            int[] ticketRowsAffected = psTicket.executeBatch();
            for (int i : ticketRowsAffected) {
                if (i == 0) {
                    // Ticket insertion failed, rollback and return false
                    connection.rollback();
                    return false;
                }
            }

            // Commit transaction
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Reset auto-commit
                    connection.close(); // Close connection
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Method to check if a seat is already booked
    private boolean isSeatBooked(Connection connection, int showtimeId, int seatId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Ticket WHERE showtimeId = ? AND seatId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, showtimeId);
            ps.setInt(2, seatId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        }
        return false;
    }
    public boolean isBookingPending(int bookingId) {
        String query = "SELECT status FROM Booking WHERE bookingId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookingId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int status = resultSet.getInt("status");
                return status != 1; // Return true if status is pending (not paid)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void deleteBookingWithTickets(int bookingId) {
        try {
            // Delete associated tickets first
            String deleteTicketsQuery = "DELETE FROM Ticket WHERE bookingId = ?";
            try (PreparedStatement deleteTicketsStatement = connection.prepareStatement(deleteTicketsQuery)) {
                deleteTicketsStatement.setInt(1, bookingId);
                deleteTicketsStatement.executeUpdate();
            }

            // Then delete the booking itself
            String deleteBookingQuery = "DELETE FROM Booking WHERE bookingId = ?";
            try (PreparedStatement deleteBookingStatement = connection.prepareStatement(deleteBookingQuery)) {
                deleteBookingStatement.setInt(1, bookingId);
                deleteBookingStatement.executeUpdate();
            }

            System.out.println("Booking " + bookingId + " has been deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        BookingDAO bookingDAO = new BookingDAO();
        bookingDAO.deleteBookingWithTickets(78);
    }
}

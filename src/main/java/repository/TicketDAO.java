package repository;

import context.DBContext;
import model.Booking;
import model.Seat;
import model.Showtime;
import model.Ticket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketDAO extends DBContext {
    public ArrayList<Ticket> getTicketByBooking(int bookingId) {

        ArrayList<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT ticketId, price, name, email, phone, bookingId, showtimeId, seatId FROM Ticket WHERE bookingId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ticket ticket = new Ticket();

                ticket.setTicketId(resultSet.getInt("ticketId"));
                ticket.setPrice(resultSet.getDouble("price"));
                ticket.setName(resultSet.getString("name"));
                ticket.setEmail(resultSet.getString("email"));
                ticket.setPhone(resultSet.getString("phone"));

                // Get the Booking object by bookingId
                BookingDAO bookingDAO = new BookingDAO();
                Booking booking = bookingDAO.getBookingById(resultSet.getInt("bookingId"));
                ticket.setBooking(booking);

                // Get the Showtime object by showtimeId
                ShowTimeDAO showTimeDAO = new ShowTimeDAO();
                Showtime showtime = showTimeDAO.getShowtimeById(resultSet.getInt("showtimeId"));
                ticket.setShowtime(showtime);

                // Get the Seat object by seatId
                SeatDAO seatDAO = new SeatDAO();
                Seat seat = seatDAO.getSeatById(resultSet.getInt("seatId"));
                ticket.setSeat(seat);

                // Add the ticket to the list
                tickets.add(ticket);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }
    public boolean addTicket(Ticket ticket) {
        String sql = "INSERT INTO Ticket (price, name, email, phone, bookingId, showtimeId, seatId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, ticket.getPrice());
            preparedStatement.setString(2, ticket.getName());
            preparedStatement.setString(3, ticket.getEmail());
            preparedStatement.setString(4, ticket.getPhone());
            preparedStatement.setInt(5, ticket.getBooking().getBookingId());  // Assuming Booking is not null
            preparedStatement.setInt(6, ticket.getShowtime().getShowtimeId()); // Assuming Showtime is not null
            preparedStatement.setInt(7, ticket.getSeat().getSeatId());         // Assuming Seat is not null

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();

            return rowsAffected > 0;  // Returns true if the ticket was added successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there's an error
        }
    }

    public ArrayList<Ticket> getTicketListOfShowtime(int showtimeId) {
        ArrayList<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT ticketId, price, name, email, phone, bookingId, showtimeId, seatId " +
                "FROM Ticket WHERE showtimeId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, showtimeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ticket ticket = new Ticket();

                ticket.setTicketId(resultSet.getInt("ticketId"));
                ticket.setPrice(resultSet.getDouble("price"));
                ticket.setName(resultSet.getString("name"));
                ticket.setEmail(resultSet.getString("email"));
                ticket.setPhone(resultSet.getString("phone"));

                // Get the Booking object by bookingId
                BookingDAO bookingDAO = new BookingDAO();
                Booking booking = bookingDAO.getBookingById(resultSet.getInt("bookingId"));
                ticket.setBooking(booking);

                // Get the Showtime object by showtimeId
                ShowTimeDAO showTimeDAO = new ShowTimeDAO();
                Showtime showtime = showTimeDAO.getShowtimeById(resultSet.getInt("showtimeId"));
                ticket.setShowtime(showtime);

                // Get the Seat object by seatId
                SeatDAO seatDAO = new SeatDAO();
                Seat seat = seatDAO.getSeatById(resultSet.getInt("seatId"));
                ticket.setSeat(seat);

                // Add the ticket to the list
                tickets.add(ticket);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    public int getTicketSoldInCurrentMonth(int cinemaId) {
        int ticketCount = 0;
        String sql = "SELECT COUNT(t.ticketId) AS ticketCount " +
                "FROM Ticket t " +
                "JOIN Showtime st ON t.showtimeId = st.showtimeId " +
                "JOIN Room r ON st.roomId = r.roomId " +
                "JOIN Cinema c ON r.cinemaId = c.cinemaId " +
                "WHERE c.cinemaId = ? " +
                "AND MONTH(st.showdate) = MONTH(CURRENT_DATE()) " +
                "AND YEAR(st.showdate) = YEAR(CURRENT_DATE())";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cinemaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ticketCount = resultSet.getInt("ticketCount");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticketCount;
    }

    public int getAverageTicketSoldInMonth(int cinemaId) {

        int averageTicketCount = 0;
        String sql = "SELECT COUNT(t.ticketId) AS totalTicketCount " +
                "FROM Ticket t " +
                "JOIN Showtime st ON t.showtimeId = st.showtimeId " +
                "JOIN Room r ON st.roomId = r.roomId " +
                "JOIN Cinema c ON r.cinemaId = c.cinemaId " +
                "WHERE c.cinemaId = ? " +
                "AND YEAR(st.showdate) = YEAR(CURRENT_DATE())";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cinemaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int totalTicketCount = resultSet.getInt("totalTicketCount");
                averageTicketCount = totalTicketCount / 12; // Divide by 12 to get the average per month
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return averageTicketCount;
    }

    public static void main(String[] args) {
        TicketDAO dao = new TicketDAO();
        System.out.println(dao.getTicketByBooking(1));
    }
}

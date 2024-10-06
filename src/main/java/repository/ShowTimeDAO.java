
package repository;

import context.DBContext;
import model.Movie;
import model.Room;
import model.Showtime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowTimeDAO extends DBContext {
    public ArrayList<Showtime> getListShowtimes() {
        ArrayList<Showtime> showtimes = new ArrayList<>();

        String sql = "SELECT showtimeId, showdate, starttime, endtime, baseprice, movieId, roomId, seatAvailable, status " +
                "FROM Showtime";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Showtime showtime = new Showtime();
                showtime.setShowtimeId(resultSet.getInt("showtimeId"));
                showtime.setShowDate(resultSet.getDate("showdate"));
                showtime.setStartTime(resultSet.getTime("starttime"));
                showtime.setEndTime(resultSet.getTime("endtime"));
                showtime.setBasePrice(resultSet.getDouble("baseprice"));
                showtime.setSeatAvailable(resultSet.getInt("seatAvailable"));
                showtime.setStatus(resultSet.getInt("status"));

                // Retrieve Movie using movieId and set it in the Showtime object
                MovieDAO movieDAO = new MovieDAO();
                Movie movie = movieDAO.getMovieById(resultSet.getInt("movieId"));
                showtime.setMovie(movie);

                // Retrieve Room using roomId and set it in the Showtime object
                RoomDAO roomDAO = new RoomDAO();
                Room room = roomDAO.getRoomById(resultSet.getInt("roomId"));
                showtime.setRoom(room);

                // Add the populated Showtime object to the list
                showtimes.add(showtime);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimes;
    }

    public ArrayList<Showtime> getListShowtimesByCinemaID(int cinemaId) {
        ArrayList<Showtime> showtimes = new ArrayList<>();

        String sql = "SELECT s.showtimeId, s.showdate, s.starttime, s.endtime, s.baseprice, s.movieId, s.roomId, s.seatAvailable, s.status " +
                "FROM Showtime s " +
                "JOIN Room r ON s.roomId = r.roomId " +
                "WHERE r.cinemaId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cinemaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Showtime showtime = new Showtime();
                showtime.setShowtimeId(resultSet.getInt("showtimeId"));
                showtime.setShowDate(resultSet.getDate("showdate"));
                showtime.setStartTime(resultSet.getTime("starttime"));
                showtime.setEndTime(resultSet.getTime("endtime"));
                showtime.setBasePrice(resultSet.getDouble("baseprice"));
                showtime.setSeatAvailable(resultSet.getInt("seatAvailable"));
                showtime.setStatus(resultSet.getInt("status"));

                // Retrieve Movie using movieId and set it in the Showtime object
                MovieDAO movieDAO = new MovieDAO();
                Movie movie = movieDAO.getMovieById(resultSet.getInt("movieId"));
                showtime.setMovie(movie);

                // Retrieve Room using roomId and set it in the Showtime object
                RoomDAO roomDAO = new RoomDAO();
                Room room = roomDAO.getRoomById(resultSet.getInt("roomId"));
                showtime.setRoom(room);

                // Add the populated Showtime object to the list
                showtimes.add(showtime);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimes;
    }


    public ArrayList<Showtime> getListShowtimesByRoom(int roomId) {
        ArrayList<Showtime> showtimes = new ArrayList<>();

        String sql = "SELECT s.showtimeId, s.showdate, s.starttime, s.endtime, s.baseprice, s.movieId, s.roomId, s.seatAvailable, s.status " +
                "FROM Showtime s " +
                "WHERE s.roomId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Showtime showtime = new Showtime();
                showtime.setShowtimeId(resultSet.getInt("showtimeId"));
                showtime.setShowDate(resultSet.getDate("showdate"));
                showtime.setStartTime(resultSet.getTime("starttime"));
                showtime.setEndTime(resultSet.getTime("endtime"));
                showtime.setBasePrice(resultSet.getDouble("baseprice"));
                showtime.setSeatAvailable(resultSet.getInt("seatAvailable"));
                showtime.setStatus(resultSet.getInt("status"));

                // Retrieve Movie using movieId and set it in the Showtime object
                MovieDAO movieDAO = new MovieDAO();
                Movie movie = movieDAO.getMovieById(resultSet.getInt("movieId"));
                showtime.setMovie(movie);

                // Retrieve Room using roomId and set it in the Showtime object
                RoomDAO roomDAO = new RoomDAO();
                Room room = roomDAO.getRoomById(resultSet.getInt("roomId"));
                showtime.setRoom(room);

                // Add the populated Showtime object to the list
                showtimes.add(showtime);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimes;
    }

    public boolean updateShowtime(int showtimeId, Showtime showtime) {
        String sql = "UPDATE Showtime SET showdate = ?, starttime = ?, endtime = ?, baseprice = ?, movieId = ?, roomId = ?, seatAvailable = ?, status = ? WHERE showtimeId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            java.sql.Date sqlShowDate = new java.sql.Date(showtime.getShowDate().getTime());
            preparedStatement.setDate(1, sqlShowDate);
            preparedStatement.setTime(2, showtime.getStartTime());
            preparedStatement.setTime(3, showtime.getEndTime());
            preparedStatement.setDouble(4, showtime.getBasePrice());
            preparedStatement.setInt(5, showtime.getMovie().getMovieId());
            preparedStatement.setInt(6, showtime.getRoom().getRoomId());
            preparedStatement.setInt(7, showtime.getSeatAvailable());
            preparedStatement.setInt(8, showtime.getStatus());
            preparedStatement.setInt(9, showtimeId);

            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Showtime getShowtimeByMovie(int movieId) {
        
        String sql = "SELECT showtimeId, showdate, starttime, endtime, baseprice, movieId, roomId, seatAvailable, status " +
                "FROM Showtime WHERE movieId = ?";
        
        Showtime showtime = new Showtime();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                showtime.setShowtimeId(resultSet.getInt("showtimeId"));
                showtime.setShowDate(resultSet.getDate("showdate"));
                showtime.setStartTime(resultSet.getTime("starttime"));
                showtime.setEndTime(resultSet.getTime("endtime"));
                showtime.setBasePrice(resultSet.getDouble("baseprice"));
                showtime.setSeatAvailable(resultSet.getInt("seatAvailable"));
                showtime.setStatus(resultSet.getInt("status"));

                // Retrieve Movie using movieId and set it in the Showtime object
                MovieDAO movieDAO = new MovieDAO();
                Movie movie = movieDAO.getMovieById(resultSet.getInt("movieId"));
                showtime.setMovie(movie);

                // Retrieve Room using roomId and set it in the Showtime object
                RoomDAO roomDAO = new RoomDAO();
                Room room = roomDAO.getRoomById(resultSet.getInt("roomId"));
                showtime.setRoom(room);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtime;
    }


    public boolean deleteShowtime(int showtimeId) {
        String sql = "DELETE FROM Showtime WHERE showtimeId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, showtimeId);
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Showtime> getTop5PopularShowtimesInCinema(int cinemaId) {
        ArrayList<Showtime> showtimes = new ArrayList<>();

        String sql = "SELECT TOP 5 s.showtimeId, s.showdate, s.starttime, s.endtime, s.baseprice, s.movieId, s.roomId, s.seatAvailable, s.status, " +
                "COUNT(t.ticketId) as ticketCount " +
                "FROM Showtime s " +
                "JOIN Ticket t ON s.showtimeId = t.showtimeId " +
                "JOIN Room r ON s.roomId = r.roomId " +
                "WHERE r.cinemaId = ? " +
                "GROUP BY s.showtimeId, s.showdate, s.starttime, s.endtime, s.baseprice, s.movieId, s.roomId, s.seatAvailable, s.status " +
                "ORDER BY ticketCount DESC";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cinemaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Showtime showtime = new Showtime();
                showtime.setShowtimeId(resultSet.getInt("showtimeId"));
                showtime.setShowDate(resultSet.getDate("showdate"));
                showtime.setStartTime(resultSet.getTime("starttime"));
                showtime.setEndTime(resultSet.getTime("endtime"));
                showtime.setBasePrice(resultSet.getDouble("baseprice"));
                showtime.setSeatAvailable(resultSet.getInt("seatAvailable"));
                showtime.setStatus(resultSet.getInt("status"));

                // Retrieve Movie using movieId and set it in the Showtime object
                MovieDAO movieDAO = new MovieDAO();
                Movie movie = movieDAO.getMovieById(resultSet.getInt("movieId"));
                showtime.setMovie(movie);

                // Retrieve Room using roomId and set it in the Showtime object
                RoomDAO roomDAO = new RoomDAO();
                Room room = roomDAO.getRoomById(resultSet.getInt("roomId"));
                showtime.setRoom(room);

                // Add the populated Showtime object to the list
                showtimes.add(showtime);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimes;
    }
    public String getMostPopularTimeSlotInCinema(int cinemaId) {
        return null;
    }


    public static void main(String[] args) {
        ShowTimeDAO dao = new ShowTimeDAO();
//        System.out.println(dao.getListShowtimes());
//        ArrayList<Showtime> showtimes = dao.getListShowtimesByCinemaID(1);
        
        ArrayList<Showtime> showtimes = dao.getTop5PopularShowtimesInCinema(1);


        for (Showtime showtime : showtimes) {
            System.out.println(showtime.toString() + "\n"); // Thêm xuống dòng giữa mỗi showtime
        }
    }
}

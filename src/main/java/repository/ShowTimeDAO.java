
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




    public Showtime getShowtimeByMovie(int movieId) {
        return null;
    }

    public static void main(String[] args) {
        ShowTimeDAO dao= new ShowTimeDAO();
//        System.out.println(dao.getListShowtimes());
//        ArrayList<Showtime> showtimes = dao.getListShowtimesByCinemaID(1);
        ArrayList<Showtime> showtimes = dao.getListShowtimesByRoom(1);


        for (Showtime showtime : showtimes) {
            System.out.println(showtime.toString() + "\n"); // Thêm xuống dòng giữa mỗi showtime
        }
    }

}


package repository;

import context.DBContext;
import model.Movie;
import model.Room;
import model.Showtime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class ShowTimeDAO extends DBContext {
    public boolean addShowtime(Showtime Showtime) {
        
        String sql = "INSERT INTO Showtime ( showdate, starttime, endtime, baseprice, movieId, roomId, seatAvailable, status) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(Showtime.getShowDate().getTime())); // converting java.util.Date to java.sql.Date
            preparedStatement.setTime(2, Showtime.getStartTime());
            preparedStatement.setTime(3, Showtime.getEndTime());
            preparedStatement.setDouble(4, Showtime.getBasePrice());
            preparedStatement.setInt(5, Showtime.getMovie().getMovieId());
            preparedStatement.setInt(6, Showtime.getRoom().getRoomId());
            preparedStatement.setInt(7, Showtime.getSeatAvailable());
            preparedStatement.setInt(8, Showtime.getStatus());

            int affectedRows = preparedStatement.executeUpdate();
            preparedStatement.close();

            return affectedRows > 0; // Returns true if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
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
            preparedStatement.setDate(1, new java.sql.Date(showtime.getShowDate().getTime())); // converting java.util.Date to java.sql.Date
            preparedStatement.setTime(2, showtime.getStartTime());
            preparedStatement.setTime(3, showtime.getEndTime());
            preparedStatement.setDouble(4, showtime.getBasePrice());
            preparedStatement.setInt(5, showtime.getMovie().getMovieId());
            preparedStatement.setInt(6, showtime.getRoom().getRoomId());
            preparedStatement.setInt(7, showtime.getSeatAvailable());
            preparedStatement.setInt(8, showtime.getStatus());
            preparedStatement.setInt(9, showtimeId);

            int affectedRows = preparedStatement.executeUpdate();
            preparedStatement.close();

            return affectedRows > 0;  // Returns true if a row was updated
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Showtime> getShowtimeByMovie(int movieId) {
        ArrayList<Showtime> showtimes = new ArrayList<>();

        String sql = "SELECT showtimeId, showdate, starttime, endtime, baseprice, movieId, roomId, seatAvailable, status " +
                "FROM Showtime WHERE movieId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movieId);
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
    public boolean deleteShowtime(int showtimeId) {

        String sql = "DELETE FROM Showtime WHERE showtimeId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, showtimeId);
            int affectedRows = preparedStatement.executeUpdate();

            preparedStatement.close();

            return affectedRows > 0;  // Returns true if a row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<Showtime> getTop5PopularShowtimesInCinema(int cinemaId) {
        ArrayList<Showtime> topShowtimes = new ArrayList<>();

        try {
            // SQL query to get the top 5 popular showtimes based on ticket sales
            String sql = "SELECT TOP 5 s.showtimeId, s.showdate, s.starttime, s.endtime, s.baseprice, s.movieId, s.roomId, s.seatAvailable, COUNT(t.ticketId) AS ticketCount " +
                    "FROM Showtime s " +
                    "LEFT JOIN Ticket t ON s.showtimeId = t.showtimeId " +
                    "WHERE s.roomId IN (SELECT roomId FROM Room WHERE cinemaId = ?) " +
                    "GROUP BY s.showtimeId, s.showdate, s.starttime, s.endtime, s.baseprice, s.movieId, s.roomId, s.seatAvailable " +
                    "ORDER BY ticketCount DESC";

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
                MovieDAO movieDAO = new MovieDAO();
                showtime.setMovie(movieDAO.getMovieById(resultSet.getInt("movieId"))); // Assuming you have a method to get Movie by ID
                RoomDAO roomDAO = new RoomDAO();
                showtime.setRoom(roomDAO.getRoomById(resultSet.getInt("roomId"))); // Assuming you have a method to get Room by ID
                showtime.setSeatAvailable(resultSet.getInt("seatAvailable"));

                topShowtimes.add(showtime);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topShowtimes;
    }
    public String getMostPopularTimeSlotInCinema(int cinemaId) {
        HashMap<String, Integer> timeSlotCount = new HashMap<>();
        HashMap<String, Time> timeSlotEndTime = new HashMap<>(); // To keep track of the end times

        try {
            String sql = "SELECT starttime, endtime FROM Showtime WHERE roomId IN (SELECT roomId FROM Room WHERE cinemaId = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cinemaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Time startTime = resultSet.getTime("starttime");
                Time endTime = resultSet.getTime("endtime");
                String timeSlot = startTime.toString().substring(0, 5); // Extract hour and minute

                // Count occurrences of the time slot
                timeSlotCount.put(timeSlot, timeSlotCount.getOrDefault(timeSlot, 0) + 1);

                // Store the latest end time for the time slot
                if (!timeSlotEndTime.containsKey(timeSlot) || endTime.after(timeSlotEndTime.get(timeSlot))) {
                    timeSlotEndTime.put(timeSlot, endTime);
                }
            }

            resultSet.close();
            preparedStatement.close();

            // Find the time slot with the highest count
            String mostPopularTimeSlot = null;
            int maxCount = 0;
            for (HashMap.Entry<String, Integer> entry : timeSlotCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostPopularTimeSlot = entry.getKey();
                }
            }

            // Get the corresponding end time for the most popular time slot
            if (mostPopularTimeSlot != null) {
                Time endTime = timeSlotEndTime.get(mostPopularTimeSlot);
                // Format the return string as "startTime - endTime"
                return mostPopularTimeSlot + " - " + endTime.toString().substring(0, 5);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public Showtime getShowtimeById(int showtimeId) {
        Showtime showtime = null;

        String sql = "SELECT showtimeId, showdate, starttime, endtime, baseprice, movieId, roomId, seatAvailable, status " +
                "FROM Showtime WHERE showtimeId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, showtimeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                showtime = new Showtime();
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
    public static void main(String[] args) {
        ShowTimeDAO dao = new ShowTimeDAO();
//        System.out.println(dao.getListShowtimes());
//        ArrayList<Showtime> showtimes = dao.getListShowtimesByCinemaID(1);
//        ArrayList<Showtime> showtimes = dao.getListShowtimesByRoom(1);
//        for (Showtime showtime : showtimes) {
//            System.out.println(showtime.toString() + "\n"); // Thêm xuống dòng giữa mỗi showtime
//        }
//        System.out.println(dao.getShowtimeById(1));
//        System.out.println(dao.getMostPopularTimeSlotInCinema(1));
        for(Showtime s: dao.getTop5PopularShowtimesInCinema(1)) {
            System.out.println(s);
        }
    }
}

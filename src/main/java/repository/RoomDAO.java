package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import model.Room;
import model.Cinema;


import context.DBContext;


public class RoomDAO extends DBContext {

    public ArrayList<Room> getListRoom() {
        ArrayList<Room> rooms = new ArrayList<>();

        String sql = "SELECT roomId, name, cinemaId, numberOfRows, numberOfColumns FROM Room";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room();
                room.setRoomId(resultSet.getInt("roomId"));
                room.setName(resultSet.getString("name"));
                room.setNumberOfSeat(getNumberOfSeatsByRoom(room.getRoomId()));
                room.setNumberOfRows(resultSet.getInt("numberOfRows"));
                room.setNumberOfColumns(resultSet.getInt("numberOfColumns"));

                Cinema cinema = new Cinema();
                cinema.setCinemaId(resultSet.getInt("cinemaId"));
                room.setCinema(cinema);

                rooms.add(room);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }


    // Method to add a new Room using Room object
    public boolean addNewRoom(Room room) {
        String sql = "INSERT INTO Room ([name], cinemaId, numberOfRows, numberOfColumns) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, room.getName());
            ps.setInt(2, room.getCinema().getCinemaId());
            ps.setInt(3, room.getNumberOfRows());
            ps.setInt(4, room.getNumberOfColumns());

            ps.executeUpdate();
            System.out.println("New room added successfully!");
            return true;

        } catch (SQLException e) {
            System.out.println("Error when adding new room: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM Room WHERE roomId = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roomId); // Set tham số roomId cho câu lệnh SQL

            int rowsAffected = ps.executeUpdate(); // Thực hiện xóa

            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            // Xử lý ngoại lệ SQL
            System.out.println("Error when deleting room: " + e.getMessage());
            return false;
        }
    }

    public Room getRoomById(int roomId) {
        Room room = null;

        String sql = "SELECT roomId, name, cinemaId, numberOfRows, numberOfColumns FROM Room WHERE roomId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                room = new Room();
                room.setRoomId(resultSet.getInt("roomId"));
                room.setName(resultSet.getString("name"));
                room.setNumberOfSeat(getNumberOfSeatsByRoom(room.getRoomId()));
                room.setNumberOfRows(resultSet.getInt("numberOfRows"));
                room.setNumberOfColumns(resultSet.getInt("numberOfColumns"));

                Cinema cinema = new Cinema();
                cinema.setCinemaId(resultSet.getInt("cinemaId"));
                room.setCinema(cinema);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return room;
    }

    public boolean updateRoom(int roomId, Room room) {
        String sql = "UPDATE Room SET name = ?, cinemaId = ?, numberOfRows = ?, numberOfColumns = ? WHERE roomId = ?";
        try (Connection conn = connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, room.getName());
            ps.setInt(2, room.getCinema().getCinemaId());
            ps.setInt(3, room.getNumberOfRows());
            ps.setInt(4, room.getNumberOfColumns());
            ps.setInt(5, roomId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Room> getListRoomsByCinemaID(int cinemaId) {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT roomId, name, cinemaId, numberOfRows, numberOfColumns FROM Room WHERE cinemaId = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cinemaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("roomId"));
                room.setName(rs.getString("name"));
                room.setNumberOfSeat(getNumberOfSeatsByRoom(room.getRoomId()));
                room.setNumberOfRows(rs.getInt("numberOfRows"));
                room.setNumberOfColumns(rs.getInt("numberOfColumns"));

                Cinema cinema = new Cinema();
                cinema.setCinemaId(rs.getInt("cinemaId"));
                room.setCinema(cinema);

                rooms.add(room);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error in getListRoomsByCinemaID: " + e.getMessage());
        }
        return rooms;
    }

    public int getNumberOfSeatsByRoom(int roomId) {
        String sql = "SELECT COUNT(*) AS numberOfSeats " +
                "FROM Seat " +
                "WHERE roomId = ?";

        int numberOfSeats = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, roomId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                numberOfSeats = resultSet.getInt("numberOfSeats");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numberOfSeats;
    }

    public boolean dimensionsChanged(int roomId, Room newRoom) {
        Room currentRoom = getRoomById(roomId);
        return currentRoom != null &&
                (currentRoom.getNumberOfRows() != newRoom.getNumberOfRows() ||
                        currentRoom.getNumberOfColumns() != newRoom.getNumberOfColumns());
    }

    public boolean validateSeatDimensions(int roomId, int newNumberOfRows, int newNumberOfColumns) {
        String sql = "SELECT [row], col FROM Seat WHERE roomId = ? AND status = 1";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int row = rs.getInt("row");
                int col = rs.getInt("col");
                if (row > newNumberOfRows || col > newNumberOfColumns) {
                    return false; // Seat would be out of bounds
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true; // All seats are within the new dimensions
    }

    public boolean hasActiveBookedSeats(int roomId) {
        String sql = "SELECT COUNT(*) FROM Ticket t JOIN Showtime s ON t.showtimeId = s.showtimeId WHERE s.status = 1 AND t.seatId IN (SELECT seatId FROM Seat WHERE roomId = ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true; // There are booked seats in active showtimes
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // No active booked seats
    }

    public void deleteAllSeatsInRoom(int roomId) {
        String sqlUpdate = "UPDATE Seat SET status = 0 WHERE roomId = ? AND seatId IN (SELECT seatId FROM Ticket)";
        String sqlDelete = "DELETE FROM Seat WHERE roomId = ? AND seatId NOT IN (SELECT seatId FROM Ticket)";

        try {
            // Deactivate seats that have tickets
            PreparedStatement updatePs = connection.prepareStatement(sqlUpdate);
            updatePs.setInt(1, roomId);
            updatePs.executeUpdate();
            updatePs.close();

            // Delete seats without tickets
            PreparedStatement deletePs = connection.prepareStatement(sqlDelete);
            deletePs.setInt(1, roomId);
            deletePs.executeUpdate();
            deletePs.close();

            System.out.println("Seats in room deleted or deactivated as per the specified conditions.");
        } catch (SQLException e) {
            System.out.println("Error when deleting seats in room: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        RoomDAO dao = new RoomDAO();
        Room room = dao.getRoomById(1);
    }
}


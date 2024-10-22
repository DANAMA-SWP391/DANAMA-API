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

        String sql = "SELECT roomId, name, cinemaId FROM Room";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room();
                room.setRoomId(resultSet.getInt("roomId"));
                room.setName(resultSet.getString("name"));
                room.setNumberOfSeat(getNumberOfSeatsByRoom(room.getRoomId()));
                CinemaDAO cinemaDAO = new CinemaDAO();
                Cinema cinema = cinemaDAO.getCinemaById(resultSet.getInt("cinemaId"));
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
        String sql = "INSERT INTO Room ([name], cinemaId) VALUES (?, ?)";

        try {
            // Sử dụng connection từ DBContext
            PreparedStatement ps = connection.prepareStatement(sql);

            // Set các tham số cho truy vấn SQL từ đối tượng Room
            ps.setString(1, room.getName());
            ps.setInt(2, room.getCinema().getCinemaId());  // Lấy cinemaId từ đối tượng Cinema

            // Thực thi câu lệnh SQL để thêm phòng mới
            ps.executeUpdate();
            System.out.println("New room added successfully!");
            return true;

        } catch (SQLException e) {
            // Xử lý ngoại lệ SQL
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

        String sql = "SELECT roomId, name, cinemaId FROM Room WHERE roomId=? ";
        CinemaDAO cinemaDAO = new CinemaDAO();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                room = new Room();
                room.setRoomId(resultSet.getInt("roomId"));
                room.setName(resultSet.getString("name"));
                room.setNumberOfSeat(getNumberOfSeatsByRoom(room.getRoomId()));

                // Fetch the Cinema details using the cinemaId

                Cinema cinema = cinemaDAO.getCinemaById(resultSet.getInt("cinemaId"));
                room.setCinema(cinema);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return room;
    }

    public boolean updateRoom(int roomId , Room room) {
        String sql = "UPDATE Room SET name = ?, cinemaId = ? WHERE roomId = ?";
        try (Connection conn = connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, room.getName());
            ps.setInt(2, room.getCinema().getCinemaId());
            ps.setInt(3, roomId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<Room> getListRoomsByCinemaID(int cinemaId) {
        ArrayList<Room> rooms = new ArrayList<Room>();
        String query = "SELECT roomId, name, cinemaId FROM Room WHERE cinemaId=? ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cinemaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("roomId"));
                room.setName(rs.getString("name"));
                room.setNumberOfSeat(getNumberOfSeatsByRoom(room.getRoomId()));

                Cinema cinema = new Cinema();
                cinema.setCinemaId(rs.getInt("cinemaId"));
                room.setCinema(cinema);
                rooms.add(room);
            }
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

    public static void main(String[] args) {
        RoomDAO dao = new RoomDAO();
//        Cinema cinema = new Cinema(1, "CGV Saigon", "cgv_logo.png", "72 Lê Thánh Tôn, Q.1, HCM",
//                "CGV Cinema nổi tiếng", "cgv_image.png", 101);
//        Room room = new Room("Screen 4", 30 , cinema);
//        dao.addNewRoom(room);
//        dao.deleteRoom(7);
//        System.out.println(dao.getRoomById(1));
        System.out.println(dao.getListRoomsByCinemaID(1));
    }
}


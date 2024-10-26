package repository;

import context.DBContext;
import model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Seat;
import model.Cinema;

public class SeatDAO extends DBContext {

    // Method to add a new seat to the database
    public boolean addSeat(Seat seat) {
        String checkSql = "SELECT seatId FROM Seat WHERE seatNum = ? AND roomId = ? AND status = 0";
        String updateSql = "UPDATE Seat SET status = 1, [type] = ? WHERE seatId = ?";
        String insertSql = "INSERT INTO Seat (seatNum, col, [row], [type], roomId, status) VALUES (?, ?, ?, ?, ?, 1)";

        try {
            // Check if an inactive seat with the same seatNum and roomId exists
            PreparedStatement checkPs = connection.prepareStatement(checkSql);
            checkPs.setString(1, seat.getSeatNum());
            checkPs.setInt(2, seat.getRoom().getRoomId());
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // Reactivate and update the seat type
                int seatId = rs.getInt("seatId");
                PreparedStatement updatePs = connection.prepareStatement(updateSql);
                updatePs.setString(1, seat.getType());
                updatePs.setInt(2, seatId);
                updatePs.executeUpdate();
                System.out.println("Seat reactivated and updated successfully!");
            } else {
                // Insert a new seat
                PreparedStatement insertPs = connection.prepareStatement(insertSql);
                insertPs.setString(1, seat.getSeatNum());
                insertPs.setInt(2, seat.getCol());
                insertPs.setInt(3, seat.getRow());
                insertPs.setString(4, seat.getType());
                insertPs.setInt(5, seat.getRoom().getRoomId());
                insertPs.executeUpdate();
                System.out.println("New seat added successfully!");
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Error when adding new seat: " + e.getMessage());
            return false;
        }
    }

    // Method to "delete" a seat by setting its status to inactive
    public boolean deleteSeat(int seatId) {
        String sql = "UPDATE Seat SET status = 0 WHERE seatId = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, seatId); // Set the seatId parameter

            int rowsAffected = ps.executeUpdate(); // Execute update

            if (rowsAffected > 0) {
                System.out.println("Seat inactivated successfully!");
                return true;
            } else {
                System.out.println("Seat not found.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error when inactivating seat: " + e.getMessage());
            return false;
        }
    }

    // Method to retrieve active seats in a room
    public ArrayList<Seat> getListSeatsInRoom(int roomId) {
        ArrayList<Seat> seats = new ArrayList<>();
        String sql = "SELECT seatId, seatNum, col, [row], [type], roomId FROM Seat WHERE roomId = ? AND status = 1";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roomId); // Set the roomId parameter

            ResultSet rs = ps.executeQuery(); // Execute query

            while (rs.next()) {
                int seatId = rs.getInt("seatId");
                String seatNum = rs.getString("seatNum");
                int col = rs.getInt("col");
                int row = rs.getInt("row");
                String type = rs.getString("type");

                // Assuming Room has a constructor that takes roomId
                Room room = new Room();
                room.setRoomId(roomId);

                Seat seat = new Seat(seatId, seatNum, col, row, type, room);
                seats.add(seat);
            }
            System.out.println("List of seats retrieved successfully!");

        } catch (SQLException e) {
            System.out.println("Error when retrieving list of seats: " + e.getMessage());
        }
        return seats;
    }

    // Method to get a specific active seat by ID
    public Seat getSeatById(int seatId) {
        String sql = "SELECT seatId, seatNum, col, [row], [type], roomId FROM Seat WHERE seatId = ? AND status = 1";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, seatId); // Set the seatId parameter

            ResultSet rs = ps.executeQuery(); // Execute query

            if (rs.next()) {
                int id = rs.getInt("seatId");
                String seatNum = rs.getString("seatNum");
                int col = rs.getInt("col");
                int row = rs.getInt("row");
                String type = rs.getString("type");
                int roomId = rs.getInt("roomId");

                // Assuming RoomDAO has getRoomById
                RoomDAO roomDAO = new RoomDAO();
                Room room = roomDAO.getRoomById(roomId);

                Seat seat = new Seat(id, seatNum, col, row, type, room);
                return seat;
            } else {
                System.out.println("Seat not found.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error when retrieving seat: " + e.getMessage());
            return null;
        }
    }

    public boolean changeSeatTypeByID(int seatId, String newType) {
        String sql = "UPDATE Seat SET [type] = ? WHERE seatId = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newType);  // Set the new seat type
            ps.setInt(2, seatId);      // Set the seatId to find the seat

            int rowsAffected = ps.executeUpdate(); // Execute update

            if (rowsAffected > 0) {
                System.out.println("Seat type updated successfully!");
                return true;
            } else {
                System.out.println("Seat not found.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error when updating seat type: " + e.getMessage());
            return false;
        }
    }
    public static void main(String[] args) {
        SeatDAO seatDAO = new SeatDAO();
//            Cinema cinema = new Cinema(1, "CGV Saigon", "cgv_logo.png", "72 Lê Thánh Tôn, Q.1, HCM",
//                "CGV Cinema nổi tiếng", "cgv_image.png", 101);
//        Room room = new Room(2,"Screen 4", 30 , cinema);
//            Seat seat = new Seat("A13", 2, 3, "VIP" , room);
//
//            seatDAO.addSeat(seat);
//            seatDAO.deleteSeat(6);
//        seatDAO.changeSeatTypeByID(2, "VIP");
//        System.out.println(seatDAO.getListSeatsInRoom(1));
//        System.out.println(seatDAO.getSeatById(1));
        System.out.println(seatDAO.getListSeatsInRoom(1));
    }


}

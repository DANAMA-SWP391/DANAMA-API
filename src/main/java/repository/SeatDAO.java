package repository;

import context.DBContext;
import model.Room;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Seat;
import model.Cinema;

    public class SeatDAO extends DBContext {

        // Method to add a new seat to the database
        public void addSeat(Seat seat) {
            String sql = "INSERT INTO Seat (seatNum, col, [row], [type], roomId) VALUES (?, ?, ?, ?, ?)";
            try  {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, seat.getSeatNum());
                ps.setInt(2, seat.getCol());
                ps.setInt(3, seat.getRow());
                ps.setString(4, seat.getType());
                ps.setInt(5, seat.getRoom().getRoomId()); // Assuming Room has a getRoomId method

                ps.executeUpdate();
                System.out.println("New seat added successfully!");

            } catch (SQLException e) {
                System.out.println("Error when adding new seat: " + e.getMessage());
            }
        }

        public void deleteSeat(int seatId) {
            String sql = "DELETE FROM Seat WHERE seatId = ?";

            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, seatId); // Set the seatId parameter for the SQL statement

                int rowsAffected = ps.executeUpdate(); // Execute delete

                if (rowsAffected > 0) {
                    System.out.println("Seat deleted successfully!");
                } else {
                    System.out.println("Seat not found.");
                }

            } catch (SQLException e) {
                // Handle SQL exception
                System.out.println("Error when deleting seat: " + e.getMessage());
            }
        }

        public void changeSeatTypeByID(int seatId, String newType) {
            String sql = "UPDATE Seat SET [type] = ? WHERE seatId = ?";

            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, newType);  // Set the new seat type
                ps.setInt(2, seatId);      // Set the seatId to find the seat

                int rowsAffected = ps.executeUpdate(); // Execute update

                if (rowsAffected > 0) {
                    System.out.println("Seat type updated successfully!");
                } else {
                    System.out.println("Seat not found.");
                }

            } catch (SQLException e) {
                System.out.println("Error when updating seat type: " + e.getMessage());
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
            seatDAO.changeSeatTypeByID(2,"VIP");

        }



}

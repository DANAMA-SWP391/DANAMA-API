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
        public static void main(String[] args) {
            SeatDAO seatDAO = new SeatDAO();
            Cinema cinema = new Cinema(1, "CGV Saigon", "cgv_logo.png", "72 Lê Thánh Tôn, Q.1, HCM",
                "CGV Cinema nổi tiếng", "cgv_image.png", 101);
        Room room = new Room(2,"Screen 4", 30 , cinema);
            Seat seat = new Seat("A13", 2, 3, "VIP" , room);

            seatDAO.addSeat(seat);
        }

}

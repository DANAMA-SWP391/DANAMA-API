package repository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Room;
import model.Cinema;



import context.DBContext;


public class RoomDAO extends DBContext{


        // Method to add a new Room using Room object
        public void addNewRoom(Room room) {
            String sql = "INSERT INTO Room ([name], numberOfSeat, cinemaId) VALUES (?, ?, ?)";

            try {
                // Sử dụng connection từ DBContext
                PreparedStatement ps = connection.prepareStatement(sql);

                // Set các tham số cho truy vấn SQL từ đối tượng Room
                ps.setString(1, room.getName());
                ps.setInt(2, room.getNumberOfSeat());
                ps.setInt(3, room.getCinema().getCinemaId());  // Lấy cinemaId từ đối tượng Cinema

                // Thực thi câu lệnh SQL để thêm phòng mới
                ps.executeUpdate();
                System.out.println("New room added successfully!");

            } catch (SQLException e) {
                // Xử lý ngoại lệ SQL
                System.out.println("Error when adding new room: " + e.getMessage());
            }
        }

    public void deleteRoom(int roomId) {
        String sql = "DELETE FROM Room WHERE roomId = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roomId); // Set tham số roomId cho câu lệnh SQL

            int rowsAffected = ps.executeUpdate(); // Thực hiện xóa

            if (rowsAffected > 0) {
                System.out.println("Room deleted successfully!");
            } else {
                System.out.println("Room not found.");
            }

        } catch (SQLException e) {
            // Xử lý ngoại lệ SQL
            System.out.println("Error when deleting room: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        RoomDAO dao = new RoomDAO();
        Cinema cinema = new Cinema(1, "CGV Saigon", "cgv_logo.png", "72 Lê Thánh Tôn, Q.1, HCM",
                "CGV Cinema nổi tiếng", "cgv_image.png", 101);
        Room room = new Room("Screen 4", 30 , cinema);
        dao.addNewRoom(room);
//        dao.deleteRoom(7);
    }
}


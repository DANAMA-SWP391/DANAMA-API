package repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import model.Room;
import model.Cinema;



import context.DBContext;


public class RoomDAO extends DBContext{

    public ArrayList<Room> getListRoom() {
        ArrayList<Room> rooms = new ArrayList<>();

        String sql = "SELECT roomId, name, numberOfSeat, cinemaId FROM Room";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room();
                room.setRoomId(resultSet.getInt("roomId"));
                room.setName(resultSet.getString("name"));
                room.setNumberOfSeat(resultSet.getInt("numberOfSeat"));
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
    public Room getRoomById(int roomId) {
        Room room = null;

        String sql = "SELECT roomId, name, numberOfSeat, cinemaId FROM Room WHERE roomId=? ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                room = new Room();
                room.setRoomId(resultSet.getInt("roomId"));
                room.setName(resultSet.getString("name"));
                room.setNumberOfSeat(resultSet.getInt("numberOfSeat"));

                // Fetch the Cinema details using the cinemaId
                CinemaDAO cinemaDAO = new CinemaDAO();
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

    public ArrayList<Room> getListRoomsByCinemaID(int cinemaId) {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT r.roomId, r.name, r.numberOfSeat, c.cinemaId, c.name, c.logo, c.address, c.description, c.image, c.managerId " +
                "FROM Room r " +
                "JOIN Cinema c ON r.cinemaId = c.cinemaId " +
                "WHERE r.cinemaId = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cinemaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Fetch Cinema object
                Cinema cinema = new Cinema(
                        rs.getInt("cinemaId"),
                        rs.getString("name"),
                        rs.getString("logo"),
                        rs.getString("address"),
                        rs.getString("description"),
                        rs.getString("image"),
                        rs.getInt("managerId")
                );

                // Fetch Room object
                Room room = new Room(
                        rs.getInt("roomId"),
                        rs.getString("name"),
                        rs.getInt("numberOfSeat"),
                        cinema
                );

                rooms.add(room); // Add Room object to the list
            }
        } catch (SQLException e) {
            System.out.println("Error in getListRoomsByCinemaID: " + e.getMessage());
        }
        return rooms;
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


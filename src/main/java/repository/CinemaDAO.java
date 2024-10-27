
package repository;

import context.DBContext;
import model.Cinema;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CinemaDAO extends DBContext {
    public ArrayList<Cinema> getListCinemas() {
        ArrayList<Cinema> cinemas = new ArrayList<>();

        String sql = "SELECT cinemaId, name, logo, address, description, image, managerId FROM Cinema";

        try {
            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Loop through the result set and create Cinema objects
            while (resultSet.next()) {
                Cinema cinema = new Cinema();
                cinema.setCinemaId(resultSet.getInt("cinemaId"));
                cinema.setName(resultSet.getString("name"));
                cinema.setLogo(resultSet.getString("logo"));
                cinema.setAddress(resultSet.getString("address"));
                cinema.setDescription(resultSet.getString("description"));
                cinema.setImage(resultSet.getString("image"));
                cinema.setManagerId(resultSet.getInt("managerId"));

                // Add cinema to the list
                cinemas.add(cinema);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinemas;
    }

    public Cinema getCinemaById(int cinemaId) {
        Cinema cinema = null;

        String sql = "SELECT cinemaId, name, logo, address, description, image, managerId FROM Cinema WHERE cinemaId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cinemaId);  // Set the cinemaId parameter

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                cinema = new Cinema();
                cinema.setCinemaId(resultSet.getInt("cinemaId"));
                cinema.setName(resultSet.getString("name"));
                cinema.setLogo(resultSet.getString("logo"));
                cinema.setAddress(resultSet.getString("address"));
                cinema.setDescription(resultSet.getString("description"));
                cinema.setImage(resultSet.getString("image"));
                cinema.setManagerId(resultSet.getInt("managerId"));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinema;
    }
    public Cinema getCinemaByUId(int uid) {
        Cinema cinema = null;

        String sql = "SELECT cinemaId, name, logo, address, description, image, managerId FROM Cinema WHERE managerId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, uid);  // Set the cinemaId parameter

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                cinema = new Cinema();
                cinema.setCinemaId(resultSet.getInt("cinemaId"));
                cinema.setName(resultSet.getString("name"));
                cinema.setLogo(resultSet.getString("logo"));
                cinema.setAddress(resultSet.getString("address"));
                cinema.setDescription(resultSet.getString("description"));
                cinema.setImage(resultSet.getString("image"));
                cinema.setManagerId(resultSet.getInt("managerId"));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinema;
    }

    public boolean updateCinema(Cinema cinema) {
        String sql = "UPDATE Cinema SET name = ?, logo = ?, address = ?, description = ?, image = ?, managerId = ? WHERE cinemaId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Thiết lập các tham số từ đối tượng Cinema
            ps.setString(1, cinema.getName());
            ps.setString(2, cinema.getLogo());
            ps.setString(3, cinema.getAddress());
            ps.setString(4, cinema.getDescription());
            ps.setString(5, cinema.getImage());
            ps.setInt(6, cinema.getManagerId());
            ps.setInt(7, cinema.getCinemaId()); // Lấy cinemaId từ đối tượng Cinema

            // Log để kiểm tra giá trị các tham số
            System.out.println("Updating cinema with ID: " + cinema.getCinemaId());
            System.out.println("Name: " + cinema.getName());
            System.out.println("Address: " + cinema.getAddress());

            int rowsAffected = ps.executeUpdate();

            // Log số hàng bị ảnh hưởng
            System.out.println("Rows affected: " + rowsAffected);

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error during cinema update:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean addCinema(Cinema cinema) {
        String sql = "INSERT INTO Cinema (name, logo, address, description, image, managerId) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, cinema.getName());
            ps.setString(2, cinema.getLogo());
            ps.setString(3, cinema.getAddress());
            ps.setString(4, cinema.getDescription());
            ps.setString(5, cinema.getImage());
            ps.setInt(6, cinema.getManagerId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close(); // Đóng PreparedStatement
            } catch (SQLException e) {
                e.printStackTrace(); // In lỗi nếu xảy ra khi đóng PreparedStatement
            }
        }
    }

    public boolean deleteCinema(int cinemaId) {

        String sql = "DELETE FROM Cinema WHERE cinemaId = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cinemaId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public double getTotalCinemaRevenue(int cinemaId) {
        double totalRevenue = 0.0;

        try {
            // SQL query to calculate the total revenue from tickets sold for the specified cinema
            String sql = "SELECT SUM(t.price) AS totalRevenue " +
                    "FROM Ticket t " +
                    "JOIN Showtime s ON t.showtimeId = s.showtimeId " +
                    "WHERE s.roomId IN (SELECT roomId FROM Room WHERE cinemaId = ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cinemaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalRevenue = resultSet.getDouble("totalRevenue");
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }

    public List<Object[]> getTotalRevenueForAllCinemas_Admin() {
        List<Object[]> cinemaRevenueList = new ArrayList<>();

        String sql = "WITH CinemaRevenue AS (" +
                "    SELECT c.cinemaId, c.[name] AS cinemaName, SUM(t.price) AS totalRevenue " +
                "    FROM Cinema c " +
                "    LEFT JOIN Room r ON c.cinemaId = r.cinemaId " +
                "    LEFT JOIN Showtime s ON r.roomId = s.roomId " +
                "    LEFT JOIN Ticket t ON s.showtimeId = t.showtimeId " +
                "    GROUP BY c.cinemaId, c.[name] " +
                ") " +
                "SELECT cr.cinemaName, ISNULL(cr.totalRevenue, 0) AS totalRevenue " +
                "FROM CinemaRevenue cr " +
                "ORDER BY cr.totalRevenue DESC;";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Duyệt qua kết quả và thêm vào danh sách
            while (rs.next()) {
                String cinemaName = rs.getString("cinemaName");
                double totalRevenue = rs.getDouble("totalRevenue");
                cinemaRevenueList.add(new Object[]{cinemaName, totalRevenue});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinemaRevenueList;
    }





    //Testing
    public static void main(String[] args) {
//        CinemaDAO cinemaDAO = new CinemaDAO();
//        Cinema cinema  = new Cinema();
//        cinema.setName("mmmmm");
//        cinema.setCinemaId(17);
//        cinema.setLogo("logo");
//        cinema.setAddress("address");
//        cinema.setDescription("description");
//        cinema.setImage("image");
//        cinema.setManagerId(1);
////        cinemaDAO.addCinema(cinema);
////
//        cinemaDAO.updateCinemaByID(17, cinema);
/*        CinemaDAO cinemaDAO = new CinemaDAO();

        // Gọi hàm getTotalRevenueForAllCinemas() và lưu kết quả vào danh sách
        List<Object[]> cinemaRevenueList = cinemaDAO.getTotalRevenueForAllCinemas();

        // Kiểm tra kết quả - duyệt qua danh sách và in ra thông tin của từng rạp
        if (cinemaRevenueList.isEmpty()) {
            System.out.println("Không có doanh thu nào được tìm thấy.");
        } else {
            System.out.println("Danh sách doanh thu các rạp:");
            for (Object[] cinemaRevenue : cinemaRevenueList) {
                String cinemaName = (String) cinemaRevenue[0];
                double totalRevenue = (double) cinemaRevenue[1];
                System.out.println("Rạp: " + cinemaName + " - Doanh thu: " + totalRevenue);
            }
        }*/
    }
}

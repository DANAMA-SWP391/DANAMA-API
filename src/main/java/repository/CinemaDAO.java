
package repository;

import context.DBContext;
import model.Cinema;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public boolean updateCinemaByID(int cinemaId, Cinema cinema) {
        String sql = "UPDATE Cinema SET name = ?, logo = ?, address = ?, description = ?, image = ?, managerId = ? WHERE cinemaId = ?";

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, cinema.getName());
            ps.setString(2, cinema.getLogo());
            ps.setString(3, cinema.getAddress());
            ps.setString(4, cinema.getDescription());
            ps.setString(5, cinema.getImage());
            ps.setInt(6, cinema.getManagerId());
            ps.setInt(7, cinemaId);

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
        } catch (SQLException e){
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


    //Testing
    public static void main(String[] args) {
        CinemaDAO cinemaDAO= new CinemaDAO();
        for(Cinema cinema: cinemaDAO.getListCinemas()) {
            System.out.println(cinema);
        }
        System.out.println(cinemaDAO.getCinemaById(1));
    }
}


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

    //Testing
    public static void main(String[] args) {
        CinemaDAO cinemaDAO= new CinemaDAO();
        for(Cinema cinema: cinemaDAO.getListCinemas()) {
            System.out.println(cinema);
        }
        System.out.println(cinemaDAO.getCinemaById(1));
    }
}

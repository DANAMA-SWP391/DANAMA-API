package repository;

import context.DBContext;
import model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO extends DBContext {
    public List<Genre> getGenresByMovieId(int movieId) {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT g.genreId, g.name FROM Genre g " +
                "INNER JOIN MovieGenre mg ON g.genreId = mg.genreId " +
                "WHERE mg.movieId = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Using connection from DBContext
            statement = connection.prepareStatement(sql);
            statement.setInt(1, movieId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int genreId = resultSet.getInt("genreId");
                String name = resultSet.getString("name");

                // Create a new Genre object and add it to the list
                Genre genre = new Genre(genreId, name);
                genres.add(genre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return genres;
    }
    // Hàm để lấy danh sách tất cả các thể loại
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT genreId, name FROM Genre";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int genreId = resultSet.getInt("genreId");
                String name = resultSet.getString("name");

                Genre genre = new Genre(genreId, name);
                genres.add(genre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return genres;
    }

    public static void main(String[] args) {
        GenreDAO genreDAO = new GenreDAO();

        System.out.println(genreDAO.getAllGenres());
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import context.DBContext;
import model.Genre;
import model.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieDAO extends DBContext {
    private GenreDAO genreDAO = new GenreDAO(); // Sử dụng GenreDAO để lấy genres

    // Method to get movies that are currently playing (status = 1)
    public List<Movie> getNowPlayingMovies() {
        List<Movie> nowPlayingMovies = new ArrayList<>();
        String sql = "SELECT * FROM Movie WHERE status = 1";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Using the connection from DBContext
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int movieId = resultSet.getInt("movieId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String poster = resultSet.getString("poster");
                String trailer = resultSet.getString("trailer");
                Date releaseDate = resultSet.getDate("releasedate");
                String country = resultSet.getString("country");
                String director = resultSet.getString("director");
                int ageRestricted = resultSet.getInt("agerestricted");
                String actors = resultSet.getString("actors");
                int duration = resultSet.getInt("duration");
                int status = resultSet.getInt("status");

                // Get genres using GenreDAO
                List<Genre> genres = genreDAO.getGenresByMovieId(movieId);

                // Create a new Movie object and add it to the list
                Movie movie = new Movie(movieId, name, description, poster, trailer, releaseDate, country, director, ageRestricted, actors, duration, status, genres);
                nowPlayingMovies.add(movie);
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

        return nowPlayingMovies;
    }
    // Method to get movies that are coming soon (status = 2 or based on releaseDate)
    public List<Movie> getComingSoonMovies() {
        List<Movie> comingSoonMovies = new ArrayList<>();
        String sql = "SELECT * FROM Movie WHERE status = 2"; // or use a condition based on releaseDate

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Using the connection from DBContext
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int movieId = resultSet.getInt("movieId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String poster = resultSet.getString("poster");
                String trailer = resultSet.getString("trailer");
                Date releaseDate = resultSet.getDate("releasedate");
                String country = resultSet.getString("country");
                String director = resultSet.getString("director");
                int ageRestricted = resultSet.getInt("agerestricted");
                String actors = resultSet.getString("actors");
                int duration = resultSet.getInt("duration");
                int status = resultSet.getInt("status");

                // Get genres using GenreDAO
                List<Genre> genres = genreDAO.getGenresByMovieId(movieId);

                // Create a new Movie object and add it to the list
                Movie movie = new Movie(movieId, name, description, poster, trailer, releaseDate, country, director, ageRestricted, actors, duration, status, genres);
                comingSoonMovies.add(movie);
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

        return comingSoonMovies;
    }

    public static void main(String[] args) {
        MovieDAO movieDAO = new MovieDAO();
        System.out.println(movieDAO.getNowPlayingMovies());
        System.out.println(movieDAO.getComingSoonMovies());
    }
}

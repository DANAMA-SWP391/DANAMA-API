/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import context.DBContext;
import model.Genre;
import model.Movie;
import java.text.SimpleDateFormat;

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
    public List<Movie> getAllMovieList() {
        List<Movie> allMovies = new ArrayList<>();
        String sql = "SELECT * FROM Movie";
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
                allMovies.add(movie);
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
        return allMovies;
    }
    public Movie getMovieById(int movieId) {
        Movie movie = null;
        String sql = "SELECT movieId, name, description, poster, trailer, releasedate, country, director, " +
                "agerestricted, actors, duration, status " +
                "FROM Movie WHERE movieId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                movie = new Movie();
                movie.setMovieId(resultSet.getInt("movieId"));
                movie.setName(resultSet.getString("name"));
                movie.setDescription(resultSet.getString("description"));
                movie.setPoster(resultSet.getString("poster"));
                movie.setTrailer(resultSet.getString("trailer"));
                movie.setReleaseDate(resultSet.getDate("releasedate"));
                movie.setCountry(resultSet.getString("country"));
                movie.setDirector(resultSet.getString("director"));
                movie.setAgeRestricted(resultSet.getInt("agerestricted"));
                movie.setActors(resultSet.getString("actors"));
                movie.setDuration(resultSet.getInt("duration"));
                movie.setStatus(resultSet.getInt("status"));

                // Retrieve genres and set them in the Movie object
                List<Genre> genres = genreDAO.getGenresByMovieId(movie.getMovieId());
                movie.setGenres(genres);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movie;
    }

    public boolean addMovie(Movie movie) {
        String sql = "INSERT INTO Movie (name, description, poster, trailer, releasedate, country, director, agerestricted, actors, duration, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = null;

        try {
            // Chuẩn bị câu lệnh SQL với PreparedStatement
            ps = connection.prepareStatement(sql);
            ps.setString(1, movie.getName());
            ps.setString(2, movie.getDescription());
            ps.setString(3, movie.getPoster());
            ps.setString(4, movie.getTrailer());

            // Sử dụng java.sql.Date đúng cách
            ps.setDate(5, new java.sql.Date(movie.getReleaseDate().getTime())); // Chuyển đổi
            ps.setString(6, movie.getCountry());
            ps.setString(7, movie.getDirector());
            ps.setInt(8, movie.getAgeRestricted());
            ps.setString(9, movie.getActors());
            ps.setInt(10, movie.getDuration());
            ps.setInt(11, movie.getStatus());

            // Thực thi câu lệnh và kiểm tra số dòng bị ảnh hưởng
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu thêm thành công

        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu xảy ra ngoại lệ
            return false;
        } finally {
            try {
                if (ps != null) ps.close(); // Đóng PreparedStatement
            } catch (SQLException e) {
                e.printStackTrace(); // Xử lý lỗi nếu xảy ra khi đóng PreparedStatement
            }
        }
    }

    public boolean updateMovieByID(int movieId, Movie movie) {
        String sql = "UPDATE Movie SET name = ?, description = ?, poster = ?, trailer = ?, releasedate = ?, country = ?, director = ?, agerestricted = ?, actors = ?, duration = ?, status = ? WHERE movieId = ?";

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, movie.getName());
            ps.setString(2, movie.getDescription());
            ps.setString(3, movie.getPoster());
            ps.setString(4, movie.getTrailer());
            ps.setDate(5, new java.sql.Date(movie.getReleaseDate().getTime())); // Chuyển đổi Date
            ps.setString(6, movie.getCountry());
            ps.setString(7, movie.getDirector());
            ps.setInt(8, movie.getAgeRestricted());
            ps.setString(9, movie.getActors());
            ps.setInt(10, movie.getDuration());
            ps.setInt(11, movie.getStatus());
            ps.setInt(12, movieId); // ID của movie để cập nhật

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công

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
    public boolean deleteMovie(int movieId) {
        return true;
    }
    public ArrayList<Movie> getTop5MostWatchedMovies() {
        
    }
    // Test method
    public static void main(String[] args) {

    }
}

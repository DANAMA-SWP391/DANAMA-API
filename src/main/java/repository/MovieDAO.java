/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import context.DBContext;
import model.Genre;
import model.Movie;

import java.sql.Statement;
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

        // Sử dụng try-with-resources để tự động đóng tài nguyên PreparedStatement và ResultSet
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, movieId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Khởi tạo Movie object và thiết lập các thuộc tính
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

                    // Lấy danh sách thể loại của phim (genres)
                    if (genreDAO != null) {
                        List<Genre> genres = genreDAO.getGenresByMovieId(movie.getMovieId());
                        movie.setGenres(genres);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Bạn có thể thêm logging hoặc ném lại ngoại lệ tùy vào cách bạn muốn xử lý lỗi
        }

        return movie;
    }


    public boolean addMovie(Movie movie) {
        String sql = "INSERT INTO Movie (name, description, poster, trailer, releasedate, country, director, agerestricted, actors, duration, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertGenresSql = "INSERT INTO MovieGenre (movieId, genreId) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Thiết lập các giá trị cho câu lệnh INSERT
            ps.setString(1, movie.getName());
            ps.setString(2, movie.getDescription());
            ps.setString(3, movie.getPoster());
            ps.setString(4, movie.getTrailer());
            ps.setDate(5, new java.sql.Date(movie.getReleaseDate().getTime()));
            ps.setString(6, movie.getCountry());
            ps.setString(7, movie.getDirector());
            ps.setInt(8, movie.getAgeRestricted());
            ps.setString(9, movie.getActors());
            ps.setInt(10, movie.getDuration());
            ps.setInt(11, movie.getStatus());

            // Thực thi câu lệnh INSERT và lấy số lượng hàng bị ảnh hưởng
            int rowsAffected = ps.executeUpdate();

            // Lấy movieId của phim vừa thêm
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int movieId = generatedKeys.getInt(1); // Lấy movieId tự động sinh

                    // Thêm các thể loại liên quan vào bảng MovieGenre
                    try (PreparedStatement insertPs = connection.prepareStatement(insertGenresSql)) {
                        for (Genre genre : movie.getGenres()) {
                            insertPs.setInt(1, movieId);    // Thiết lập movieId
                            insertPs.setInt(2, genre.getGenreId());  // Thiết lập genreId
                            insertPs.addBatch();   // Thêm vào batch
                        }
                        int[] batchResults = insertPs.executeBatch();  // Thực thi batch

                        // Kiểm tra xem tất cả các lệnh trong batch có thực thi thành công không
                        for (int result : batchResults) {
                            if (result == PreparedStatement.EXECUTE_FAILED) {
                                return false;  // Nếu có lỗi, trả về false
                            }
                        }
                    }
                }
            }

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateMovie(Movie movie) {
        String updateSql = "UPDATE Movie SET name = ?, description = ?, poster = ?, trailer = ?, releasedate = ?, country = ?, director = ?, agerestricted = ?, actors = ?, duration = ?, status = ? WHERE movieId = ?";
        String deleteGenresSql = "DELETE FROM MovieGenre WHERE movieId = ?";
        String insertGenresSql = "INSERT INTO MovieGenre (movieId, genreId) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
            // Thiết lập các giá trị cho câu lệnh UPDATE
            ps.setString(1, movie.getName());
            ps.setString(2, movie.getDescription());
            ps.setString(3, movie.getPoster());
            ps.setString(4, movie.getTrailer());
            ps.setDate(5, new java.sql.Date(movie.getReleaseDate().getTime()));
            ps.setString(6, movie.getCountry());
            ps.setString(7, movie.getDirector());
            ps.setInt(8, movie.getAgeRestricted());
            ps.setString(9, movie.getActors());
            ps.setInt(10, movie.getDuration());
            ps.setInt(11, movie.getStatus());
            ps.setInt(12, movie.getMovieId());

            // Thực thi lệnh UPDATE
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Xóa các thể loại cũ
                try (PreparedStatement deletePs = connection.prepareStatement(deleteGenresSql)) {
                    deletePs.setInt(1, movie.getMovieId());
                    deletePs.executeUpdate();
                }

                // Thêm lại các thể loại mới
                try (PreparedStatement insertPs = connection.prepareStatement(insertGenresSql)) {
                    for (Genre genre : movie.getGenres()) {
                        insertPs.setInt(1, movie.getMovieId());
                        insertPs.setInt(2, genre.getGenreId());
                        insertPs.addBatch();
                    }
                    int[] batchResults = insertPs.executeBatch();  // Thực thi batch

                    // Kiểm tra xem tất cả các lệnh trong batch có thực thi thành công không
                    for (int result : batchResults) {
                        if (result == PreparedStatement.EXECUTE_FAILED) {
                            return false;  // Nếu có lỗi, trả về false
                        }
                    }
                }
            }

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public boolean deleteMovie(int movieId) {
//        String sql = "DELETE FROM Movie WHERE movieId = ?";
//
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, movieId);
//
//            int rowsAffected = ps.executeUpdate();
//            return rowsAffected > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
public boolean deleteMovie(int movieId) {
    // Câu lệnh xóa các thể loại liên quan từ bảng MovieGenre trước
    String deleteGenresSql = "DELETE FROM MovieGenre WHERE movieId = ?";
    String deleteMovieSql = "DELETE FROM Movie WHERE movieId = ?";

    try {
        // Xóa các thể loại của phim trước khi xóa phim
        try (PreparedStatement psDeleteGenres = connection.prepareStatement(deleteGenresSql)) {
            psDeleteGenres.setInt(1, movieId);
            psDeleteGenres.executeUpdate();
        }

        // Xóa phim
        try (PreparedStatement psDeleteMovie = connection.prepareStatement(deleteMovieSql)) {
            psDeleteMovie.setInt(1, movieId);
            int rowsAffected = psDeleteMovie.executeUpdate();
            return rowsAffected > 0;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    public ArrayList<Movie> getTop5MostWatchedMovies() {
        ArrayList<Movie> topMovies = new ArrayList<>();
        String sql = "SELECT TOP 5 m.movieId, COUNT(t.ticketId) AS ticketCount " +
                "FROM Movie m " +
                "JOIN Showtime s ON m.movieId = s.movieId " +
                "LEFT JOIN Ticket t ON s.showtimeId = t.showtimeId " +
                "GROUP BY m.movieId " +
                "ORDER BY ticketCount DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int movieId = resultSet.getInt("movieId");
                // Assuming you have a method getMovieById(int movieId) that returns a Movie object
                Movie movie = getMovieById(movieId);
                if (movie != null) {
                    topMovies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topMovies;
    }
    public List<Object[]> getMostWatchedMovies_Admin() {
        List<Object[]> movies = new ArrayList<>();

        String query = """
            WITH MovieStats AS (
                SELECT\s
                    m.name AS movieName,
                    m.poster AS moviePoster,
                    COUNT(t.ticketId) AS totalTicketsSold, \s
                    SUM(t.price) AS totalRevenue    \s
                FROM\s
                    Ticket t
                JOIN\s
                    Showtime s ON t.showtimeId = s.showtimeId
                JOIN\s
                    Movie m ON s.movieId = m.movieId
                GROUP BY\s
                    m.movieId, m.name, m.poster
            )
            SELECT\s
                TOP 5
                ms.movieName,
                ms.moviePoster,
                ms.totalTicketsSold,
                ms.totalRevenue
            FROM\s
                MovieStats ms
            ORDER BY\s
                ms.totalTicketsSold DESC;
        """;

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            // Process the result set
            while (rs.next()) {
                Object[] movieData = new Object[4];
                movieData[0] = rs.getString("movieName");         // Movie name
                movieData[1] = rs.getString("moviePoster");       // Movie poster
                movieData[2] = rs.getInt("totalTicketsSold");     // Total tickets sold
                movieData[3] = rs.getDouble("totalRevenue");      // Total revenue

                // Add movie data to the list
                movies.add(movieData);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle any SQL exceptions
        }

        return movies;
    }
    // Thêm liên kết Movie-Genre vào bảng MovieGenre
    public boolean addMovieGenres(int movieId, List<Genre> genres) {
        String sql = "INSERT INTO MovieGenre (movieId, genreId) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Genre genre : genres) {
                ps.setInt(1, movieId);
                ps.setInt(2, genre.getGenreId());
                ps.addBatch();
            }
            ps.executeBatch(); // Thực thi batch cho nhiều genres
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa liên kết Movie-Genre
    public boolean deleteMovieGenres(int movieId) {
        String sql = "DELETE FROM MovieGenre WHERE movieId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, movieId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Test method
    public static void main(String[] args) {
//        MovieDAO movieDAO = new MovieDAO();
//        // Tạo một danh sách thể loại (genres)
//        List<Genre> genres = new ArrayList<>();
//        genres.add(new Genre(3, null));  // Genre với genreId = 3, name = null
//
//        // Tạo một đối tượng Movie
//        Movie movie = new Movie(
//                12,  // movieId
//                "Minh",  // name
//                "This is a test movie",  // description
//                "test_poster_url",  // poster
//                "test_trailer_url",  // trailer
//                new Date(2024, 9, 2),  // releaseDate (Month in Date is 0-based, so 9 means October)
//                "VN",  // country
//                "Test Director",  // director
//                12,  // ageRestricted
//                "Test Actor 1, Test Actor 2",  // actors
//                120,  // duration
//                1,  // status
//                genres  // genres list
//        );
//        System.out.println(movieDAO.updateMovie(movie));

        // In ra thông tin của đối tượng Movie

    }
        }



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import context.DBContext;
import model.Genre;
import model.Movie;
import model.MovieRequest;

import java.sql.Statement;
import java.text.SimpleDateFormat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    public Map<String, Object> getMovieRequestWithDetails(int requestId) {
        Map<String, Object> result = new HashMap<>();
        MovieRequest movieRequest = null;
        Movie movie = null;

        String requestSql = "SELECT requestId, cinemaId, movieId, status, message, timestamp " +
                "FROM MovieRequest WHERE requestId = ?";
        String movieSql = "SELECT movieId, name, description, poster, trailer, releasedate, country, " +
                "director, agerestricted, actors, duration, status " +
                "FROM Movie WHERE movieId = ?";
        String genresSql = "SELECT g.genreId, g.name FROM Genre g " +
                "JOIN MovieGenre mg ON g.genreId = mg.genreId " +
                "WHERE mg.movieId = ?";

        try {
            // Truy vấn thông tin MovieRequest
            try (PreparedStatement psRequest = connection.prepareStatement(requestSql)) {
                psRequest.setInt(1, requestId);
                try (ResultSet rsRequest = psRequest.executeQuery()) {
                    if (rsRequest.next()) {
                        // Khởi tạo đối tượng MovieRequest
                        movieRequest = new MovieRequest();
                        movieRequest.setRequestId(rsRequest.getInt("requestId"));
                        movieRequest.setCinemaId(rsRequest.getInt("cinemaId"));
                        movieRequest.setMovieId(rsRequest.getInt("movieId"));
                        movieRequest.setStatus(rsRequest.getInt("status"));
                        movieRequest.setMessage(rsRequest.getString("message"));
                        movieRequest.setTimestamp(rsRequest.getDate("timestamp"));
                    }
                }
            }

            // Nếu tìm thấy MovieRequest, truy vấn thông tin Movie tương ứng
            if (movieRequest != null) {
                try (PreparedStatement psMovie = connection.prepareStatement(movieSql)) {
                    psMovie.setInt(1, movieRequest.getMovieId());
                    try (ResultSet rsMovie = psMovie.executeQuery()) {
                        if (rsMovie.next()) {
                            // Khởi tạo đối tượng Movie
                            movie = new Movie();
                            movie.setMovieId(rsMovie.getInt("movieId"));
                            movie.setName(rsMovie.getString("name"));
                            movie.setDescription(rsMovie.getString("description"));
                            movie.setPoster(rsMovie.getString("poster"));
                            movie.setTrailer(rsMovie.getString("trailer"));
                            movie.setReleaseDate(rsMovie.getDate("releasedate"));
                            movie.setCountry(rsMovie.getString("country"));
                            movie.setDirector(rsMovie.getString("director"));
                            movie.setAgeRestricted(rsMovie.getInt("agerestricted"));
                            movie.setActors(rsMovie.getString("actors"));
                            movie.setDuration(rsMovie.getInt("duration"));
                            movie.setStatus(rsMovie.getInt("status"));

                            // Lấy genres của phim
                            List<Genre> genres = new ArrayList<>();
                            try (PreparedStatement psGenres = connection.prepareStatement(genresSql)) {
                                psGenres.setInt(1, movie.getMovieId());
                                try (ResultSet rsGenres = psGenres.executeQuery()) {
                                    while (rsGenres.next()) {
                                        Genre genre = new Genre();
                                        genre.setGenreId(rsGenres.getInt("genreId"));
                                        genre.setName(rsGenres.getString("name"));
                                        genres.add(genre);
                                    }
                                }
                            }
                            movie.setGenres(genres); // Gán genres vào movie
                        }
                    }
                }
            }

            // Thêm cả MovieRequest và Movie vào kết quả trả về
            result.put("movieRequest", movieRequest);
            result.put("movie", movie);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result; // Trả về một map chứa thông tin MovieRequest và Movie
    }



    public boolean addMovieAndMovieRequest(int cinemaId, Movie movie, String message) {
        String sqlMovie = "INSERT INTO Movie (name, description, poster, trailer, releasedate, country, director, agerestricted, actors, duration, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlRequest = "INSERT INTO MovieRequest (cinemaId, movieId, status, message) VALUES (?, ?, ?, ?)";

        try {
            // Thêm movie vào bảng Movie
            try (PreparedStatement psMovie = connection.prepareStatement(sqlMovie, Statement.RETURN_GENERATED_KEYS)) {
                psMovie.setString(1, movie.getName());
                psMovie.setString(2, movie.getDescription());
                psMovie.setString(3, movie.getPoster());
                psMovie.setString(4, movie.getTrailer());
                psMovie.setDate(5, new java.sql.Date(movie.getReleaseDate().getTime()));
                psMovie.setString(6, movie.getCountry());
                psMovie.setString(7, movie.getDirector());
                psMovie.setInt(8, movie.getAgeRestricted());
                psMovie.setString(9, movie.getActors());
                psMovie.setInt(10, movie.getDuration());
                psMovie.setInt(11, 3); // Đặt status là 3

                int rowsAffected = psMovie.executeUpdate();

                // Lấy movieId của phim vừa thêm
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = psMovie.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int movieId = generatedKeys.getInt(1); // Lấy movieId tự động sinh

                            // Thêm thông tin vào bảng MovieRequest
                            try (PreparedStatement psRequest = connection.prepareStatement(sqlRequest)) {
                                psRequest.setInt(1, cinemaId);
                                psRequest.setInt(2, movieId);
                                psRequest.setInt(3, 0); // Đặt status là 0 (Pending)
                                psRequest.setString(4, message);

                                psRequest.executeUpdate();
                            }

                            // Thêm genres vào bảng MovieGenre
                            addMovieGenres(movieId, movie.getGenres()); // Gọi phương thức để thêm genres

                            return true; // Thành công
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Thất bại
    }

    public boolean updateMovieRequest(MovieRequest movieRequest) {
        // Cập nhật thông tin yêu cầu phim
        String updateRequestSql = "UPDATE MovieRequest SET status = ?, message = ? WHERE requestId = ?";

        // Cập nhật thông tin phim
        String updateMovieSql = "UPDATE Movie SET name = ?, description = ?, poster = ?, trailer = ?, releasedate = ?, country = ?, director = ?, agerestricted = ?, actors = ?, duration = ?, status = ? WHERE movieId = ?";

        try {
            // Cập nhật yêu cầu phim
            try (PreparedStatement psRequest = connection.prepareStatement(updateRequestSql)) {
                psRequest.setInt(1, movieRequest.getStatus());
                psRequest.setString(2, movieRequest.getMessage());
                psRequest.setInt(3, movieRequest.getRequestId());

                int rowsAffectedRequest = psRequest.executeUpdate();

                // Cập nhật phim tương ứng
                // Lấy movieId từ movieRequest để cập nhật thông tin phim
                Movie movie = getMovieById(movieRequest.getMovieId());

                if (movie != null) {
                    try (PreparedStatement psMovie = connection.prepareStatement(updateMovieSql)) {
                        psMovie.setString(1, movie.getName());
                        psMovie.setString(2, movie.getDescription());
                        psMovie.setString(3, movie.getPoster());
                        psMovie.setString(4, movie.getTrailer());
                        psMovie.setDate(5, new java.sql.Date(movie.getReleaseDate().getTime()));
                        psMovie.setString(6, movie.getCountry());
                        psMovie.setString(7, movie.getDirector());
                        psMovie.setInt(8, movie.getAgeRestricted());
                        psMovie.setString(9, movie.getActors());
                        psMovie.setInt(10, movie.getDuration());
                        psMovie.setInt(11, movie.getStatus());
                        psMovie.setInt(12, movie.getMovieId());

                        int rowsAffectedMovie = psMovie.executeUpdate();

                        // Cập nhật genres
                        deleteMovieGenres(movie.getMovieId()); // Xóa genres cũ
                        addMovieGenres(movie.getMovieId(), movie.getGenres()); // Thêm genres mới
                    }
                }

                return rowsAffectedRequest > 0; // Trả về true nếu cập nhật yêu cầu thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    public boolean deleteMovieRequest(int requestId) {
        String sql = "DELETE FROM MovieRequest WHERE requestId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    public List<MovieRequest> getAllMovieRequestByCinemaId(int cinemaId) {
        List<MovieRequest> movieRequests = new ArrayList<>();
        String sql = "SELECT requestId, cinemaId, movieId, status, message, timestamp " +
                "FROM MovieRequest " +
                "WHERE cinemaId = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cinemaId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int requestId = resultSet.getInt("requestId");
                    int movieId = resultSet.getInt("movieId");
                    int status = resultSet.getInt("status");
                    String message = resultSet.getString("message");
                    Date timestamp = resultSet.getDate("timestamp");

                    // Tạo đối tượng MovieRequest và thêm vào danh sách
                    MovieRequest movieRequest = new MovieRequest(requestId, cinemaId, movieId, status, message, timestamp);
                    movieRequests.add(movieRequest);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movieRequests;
    }
    public List<Movie> getAllMovieListByCinemaId(int cinemaId) {
        List<Movie> pendingMovies = new ArrayList<>();
        String sql = """
        SELECT m.movieId, m.name, m.description, m.poster, m.trailer, m.releasedate,
               m.country, m.director, m.agerestricted, m.actors, m.duration, m.status
        FROM Movie m
        JOIN PendingMovies pm ON m.movieId = pm.movieId
        WHERE pm.cinemaId = ? AND m.status = 3
    """;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Using the connection from DBContext
            statement = connection.prepareStatement(sql);
            statement.setInt(1, cinemaId);
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

                // Create a new Movie object and add it to the list
                Movie movie = new Movie(movieId, name, description, poster, trailer, releaseDate, country, director, ageRestricted, actors, duration, status, new ArrayList<>());
                pendingMovies.add(movie);
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

        return pendingMovies;
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
    public ArrayList<Movie> getTop5MostWatchedMoviesByCinemaId(int cinemaId) {
        ArrayList<Movie> topMovies = new ArrayList<>();
        String sql = "SELECT TOP 5 m.movieId, COUNT(t.ticketId) AS ticketCount " +
                "FROM Movie m " +
                "JOIN Showtime s ON m.movieId = s.movieId " +
                "JOIN Room r ON s.roomId = r.roomId " +
                "JOIN Cinema c ON r.cinemaId = c.cinemaId " +
                "JOIN Ticket t ON s.showtimeId = t.showtimeId " +  // Change LEFT JOIN to INNER JOIN
                "WHERE c.cinemaId = ? " +
                "GROUP BY m.movieId " +
                "ORDER BY ticketCount DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cinemaId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int movieId = resultSet.getInt("movieId");
                    Movie movie = getMovieById(movieId);
                    if (movie != null) {
                        topMovies.add(movie);
                    }
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

    public ArrayList<Map<String, Object>> getTicketSoldAndTotalCost(int cinemaId) {
        ArrayList<Map<String, Object>> movieRevenues = new ArrayList<>();
        String sql = "SELECT m.movieId, m.name, COUNT(t.ticketId) AS ticketSold, SUM(t.price) AS totalRevenue " +
                "FROM Movie m " +
                "JOIN Showtime s ON m.movieId = s.movieId " +
                "JOIN Room r ON s.roomId = r.roomId " +
                "JOIN Cinema c ON r.cinemaId = c.cinemaId " +
                "JOIN Ticket t ON s.showtimeId = t.showtimeId " +
                "WHERE c.cinemaId = ? " +
                "GROUP BY m.movieId, m.name " +
                "ORDER BY totalRevenue DESC";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cinemaId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> movieData = new HashMap<>();
                    movieData.put("movieId", resultSet.getInt("movieId"));
                    movieData.put("movieName", resultSet.getString("name"));
                    movieData.put("ticketSold", resultSet.getInt("ticketSold"));
                    movieData.put("totalRevenue", resultSet.getDouble("totalRevenue"));

                    movieRevenues.add(movieData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movieRevenues;
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

        MovieDAO movieDAO = new MovieDAO();
//        for(Movie movie : movieDAO.getTop5MostWatchedMoviesByCinemaId(1)) {
//            System.out.println(movie.getName());
//        }
//        ArrayList<Map<String, Object>> result = movieDAO.getTicketSoldAndTotalCost(2);
//        for (Map<String, Object> movieData : result) {
//            System.out.println("Movie ID: " + movieData.get("movieId"));
//            System.out.println("Movie Name: " + movieData.get("movieName"));
//            System.out.println("Tickets Sold: " + movieData.get("ticketSold"));
//            System.out.println("Total Revenue: " + movieData.get("totalRevenue"));
//            System.out.println("----------------------------");
//        }

//        Movie newMovie = new Movie();
//        newMovie.setName("Test Movie");
//        newMovie.setDescription("This is a test movie description.");
//        newMovie.setPoster("http://example.com/poster.jpg");
//        newMovie.setTrailer("http://example.com/trailer.mp4");
//        newMovie.setReleaseDate(new Date()); // Ngày phát hành hiện tại
//        newMovie.setCountry("VN");
//        newMovie.setDirector("Test Director");
//        newMovie.setAgeRestricted(0);
//        newMovie.setActors("Test Actor 1, Test Actor 2");
//        newMovie.setDuration(120); // Thời gian 120 phút
//
//        String message = "This is a new movie request.";
        int cinemaId = 1; // Thay thế bằng cinemaId thực tế bạn muốn kiểm tra
//
//        // Thêm phim và yêu cầu
//        boolean added = movieDAO.addMovieAndMovieRequest(cinemaId, newMovie, message);
//        if (added) {
//            System.out.println("Movie and request added successfully.");
//        } else {
//            System.out.println("Failed to add movie and request.");
//        }

        int requestId = 8; // Thay thế bằng requestId thực tế bạn muốn kiểm tra

        // Gọi hàm lấy thông tin yêu cầu phim cùng thông tin bộ phim
        Map<String, Object> details = movieDAO.getMovieRequestWithDetails(requestId);

        MovieRequest movieRequest = (MovieRequest) details.get("movieRequest");
        Movie movie = (Movie) details.get("movie");

        // Kiểm tra và in ra thông tin
        if (movieRequest != null) {
            System.out.println("Movie Request Details:");
            System.out.println("Request ID: " + movieRequest.getRequestId());
            System.out.println("Cinema ID: " + movieRequest.getCinemaId());
            System.out.println("Movie ID: " + movieRequest.getMovieId());
            System.out.println("Status: " + movieRequest.getStatus());
            System.out.println("Message: " + movieRequest.getMessage());
            System.out.println("Timestamp: " + movieRequest.getTimestamp());
        } else {
            System.out.println("No Movie Request found for ID: " + requestId);
        }

        if (movie != null) {
            System.out.println("\nMovie Details:");
            System.out.println("Movie ID: " + movie.getMovieId());
            System.out.println("Name: " + movie.getName());
            System.out.println("Description: " + movie.getDescription());
            System.out.println("Poster: " + movie.getPoster());
            System.out.println("Trailer: " + movie.getTrailer());
            System.out.println("Release Date: " + movie.getReleaseDate());
            System.out.println("Country: " + movie.getCountry());
            System.out.println("Director: " + movie.getDirector());
            System.out.println("Age Restricted: " + movie.getAgeRestricted());
            System.out.println("Actors: " + movie.getActors());
            System.out.println("Duration: " + movie.getDuration());
            System.out.println("Status: " + movie.getStatus());

            // In ra genres
            System.out.println("Genres: ");
            for (Genre genre : movie.getGenres()) {
                System.out.println("- " + genre.getName());
            }
        } else {
            System.out.println("No Movie found for Movie ID: " + movieRequest.getMovieId());
        }

        // Gọi hàm cập nhật yêu cầu và kiểm tra kết quả

//        boolean deleteResult = movieDAO.deleteMovieRequest(4);


//        // Test getAllMovieRequestByCinemaId
//        List<MovieRequest> requests = movieDAO.getAllMovieRequestByCinemaId(cinemaId);
//        System.out.println("Movie Requests for Cinema ID " + cinemaId + ":");
//        for (MovieRequest request : requests) {
//            System.out.println("Request ID: " + request.getRequestId());
//            System.out.println("Movie ID: " + request.getMovieId());
//            System.out.println("Request Status: " + request.getStatus());
//            System.out.println("Message: " + request.getMessage());
//            System.out.println("Timestamp: " + request.getTimestamp());
//            System.out.println("----------------------------");
//        }
    }
        }



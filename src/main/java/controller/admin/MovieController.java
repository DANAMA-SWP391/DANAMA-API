package controller.admin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cinema;
import model.Genre;
import model.Movie;
import repository.CinemaDAO;
import repository.GenreDAO;
import repository.MovieDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MovieController", value = "/MovieController")
public class MovieController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        MovieDAO movieDao = new MovieDAO();
        GenreDAO genreDao = new GenreDAO();

        // Retrieve all movies including their genres
        List<Movie> movies = movieDao.getAllMovieList();

        // Retrieve all genres
        List<Genre> genres = genreDao.getAllGenres();

        // Prepare JSON response
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("movies", gson.toJsonTree(movies));  // Serialize the list of movies
        jsonObject.add("genres", gson.toJsonTree(genres));  // Serialize the list of genres

        // Write the response
        response.getWriter().write(gson.toJson(jsonObject));
        response.getWriter().flush();
        response.getWriter().close();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
        System.out.println(jsonObject);
        String action = jsonObject.get("action").getAsString();
        MovieDAO dao = new MovieDAO();
        boolean result = false;
        JsonObject jsonResponse = new JsonObject();

        try {
            switch (action) {
                case "add":
                    // Chuyển đổi JSON thành đối tượng Movie
                    Movie movie = gson.fromJson(jsonObject.get("movie"), Movie.class);

                    // Thêm phim và xử lý genres bên trong hàm addMovie
                    result = dao.addMovie(movie);

                    // Trả về kết quả
                    jsonResponse.addProperty("success", result);
                    break;

                case "delete":
                    Movie movieDelete = gson.fromJson(jsonObject.get("movie"), Movie.class);
                    result = dao.deleteMovie(movieDelete.getMovieId());

                    // Xóa liên kết giữa Movie và Genre trong bảng MovieGenre
                    if (result) {
                        dao.deleteMovieGenres(movieDelete.getMovieId());
                    }

                    jsonResponse.addProperty("success", result);
                    break;

                case "update":
                    Movie movieUpdate = gson.fromJson(jsonObject.get("movie"), Movie.class);
                    System.out.println(movieUpdate);

                    // Cập nhật movie
                    result = dao.updateMovie(movieUpdate);

                    // Thêm phản hồi về kết quả
                    jsonResponse.addProperty("success", result);
                    break;
                case "view":
                    Movie movieView = gson.fromJson(jsonObject.get("movie"), Movie.class);  // Chuyển đổi JSON thành đối tượng Movie
                    Movie foundMovie = dao.getMovieById(movieView.getMovieId());  // Lấy movieId từ đối tượng movie và tìm kiếm trong cơ sở dữ liệu
                    if (foundMovie != null) {
                        jsonResponse.add("movie", gson.toJsonTree(foundMovie));  // Nếu tìm thấy phim, thêm movie vào JSON response
                        jsonResponse.addProperty("success", true);  // Báo hiệu thành công
                    } else {
                        jsonResponse.addProperty("success", false);  // Nếu không tìm thấy phim
                    }
                    break;

                default:
                    jsonResponse.addProperty("success", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("error", e.getMessage());
        }

        response.getWriter().write(gson.toJson(jsonResponse));
        response.getWriter().flush();
        response.getWriter().close();
    }

}
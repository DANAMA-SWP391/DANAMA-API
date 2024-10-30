package controller.cManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Genre;
import model.Movie;
import model.MovieRequest;
import repository.GenreDAO;
import repository.MovieDAO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "MovieRequestController", value = "/MovieRequestController")
public class MovieRequestController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        int cinemaId = Integer.parseInt(request.getParameter("cinemaId"));

        MovieDAO movieDAO = new MovieDAO();
        List<MovieRequest> movierequests = movieDAO.getAllMovieRequestByCinemaId(cinemaId);

        GenreDAO genreDAO = new GenreDAO();
        List<Genre> genres = genreDAO.getAllGenres();

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("genres", gson.toJsonTree(genres));
        jsonObject.add("movierequests", gson.toJsonTree(movierequests));
        String json = gson.toJson(jsonObject);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
        System.out.println(jsonObject);
        String action = jsonObject.get("action").getAsString();
        MovieDAO movieDAO = new MovieDAO();
        boolean result = false;

        switch (action) {
            case "add":
                JsonObject movierequest = gson.fromJson(jsonObject.get("movierequest"), JsonObject.class);
                String message = movierequest.get("message").getAsString();
                int cinemaId = movierequest.get("cinemaId").getAsInt();
                Movie movie = gson.fromJson(movierequest.get("movie"), Movie.class);
                result = movieDAO.addMovieAndMovieRequest(cinemaId,movie , message);
                break;
            case "update":
                MovieRequest updateRequest = gson.fromJson(jsonObject.get("movierequest"), MovieRequest.class);
                result = movieDAO.updateMovieRequest(updateRequest);
                break;
            case "delete":
                MovieRequest deleteRequest = gson.fromJson(jsonObject.get("movierequest"), MovieRequest.class);
                result = movieDAO.deleteMovieRequest(deleteRequest.getRequestId());
                break;
            case "view":
                int requestId = jsonObject.get("requestId").getAsInt(); // Lấy requestId từ JSON
                Map<String, Object> details = movieDAO.getMovieRequestWithDetails(requestId); // Gọi hàm lấy thông tin

                // Chuyển đổi kết quả thành JSON
                JsonObject detailJson = new JsonObject();
                detailJson.add("movieRequest", gson.toJsonTree(details.get("movieRequest")));
                detailJson.add("movie", gson.toJsonTree(details.get("movie")));

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(detailJson.toString()); // Gửi kết quả về phía client
                response.getWriter().flush();
                return; // Kết thúc để không gửi thêm thông tin khác
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":" + result + "}");
        response.getWriter().flush();
        response.getWriter().close();
    }

}

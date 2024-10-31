package controller.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;
import model.MovieRequest;
import repository.MovieDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminMovieRequestController", value = "/AdminMovieRequestController")
public class AdminMovieRequestController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        MovieDAO movieDAO = new MovieDAO();

        try {
            // Lấy danh sách các yêu cầu phim có status = 0 (đang chờ duyệt)
            List<Map<String, Object>> pendingRequests = movieDAO.getAllPendingMovieRequestsWithMovies();

            if (pendingRequests.isEmpty()) {
                System.out.println("No pending movie requests with movies found.");
            } else {
                System.out.println("Pending Movie Requests with Movies:");
                for (Map<String, Object> requestWithMovie : pendingRequests) {
                    MovieRequest movieRequest = (MovieRequest) requestWithMovie.get("movieRequest");
                    Movie movie = (Movie) requestWithMovie.get("movie");

                    System.out.println("Request ID: " + movieRequest.getRequestId());
                    System.out.println("Cinema ID: " + movieRequest.getCinemaId());
                    System.out.println("Movie ID: " + movieRequest.getMovieId());
                    System.out.println("Request Status: " + movieRequest.getStatus());
                    System.out.println("Message: " + movieRequest.getMessage());
                    System.out.println("Timestamp: " + movieRequest.getTimestamp());

                    System.out.println("Movie Name: " + movie.getName());
                    System.out.println("Description: " + movie.getDescription());
                    System.out.println("Poster: " + movie.getPoster());
                    System.out.println("Trailer: " + movie.getTrailer());
                    System.out.println("Release Date: " + movie.getReleaseDate());
                    System.out.println("Country: " + movie.getCountry());
                    System.out.println("Director: " + movie.getDirector());
                    System.out.println("Age Restricted: " + movie.getAgeRestricted());
                    System.out.println("Actors: " + movie.getActors());
                    System.out.println("Duration: " + movie.getDuration());
                    System.out.println("Movie Status: " + movie.getStatus());
                    System.out.println("----------------------------");
                }
            }

            // Chuẩn bị phản hồi JSON
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("pendingRequests", pendingRequests);

            // Trả về JSON
            response.getWriter().write(new Gson().toJson(jsonResponse));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Failed to fetch pending movie requests\"}");
            e.printStackTrace();
        } finally {
            response.getWriter().flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
        String action = jsonObject.get("action").getAsString();
        int requestId = jsonObject.get("requestId").getAsInt();
        int movieId = jsonObject.get("movieId").getAsInt();

        MovieDAO movieDAO = new MovieDAO();
        boolean result = false;

        try {
            switch (action) {
                case "accept":
                    // Admin chấp nhận yêu cầu phim, cập nhật trạng thái MovieRequest và Movie
                    result = movieDAO.acceptMovieRequest(requestId, movieId);
                    break;
                case "reject":
                    // Admin từ chối yêu cầu phim, cập nhật trạng thái MovieRequest và Movie
                    result = movieDAO.rejectMovieRequest(requestId, movieId);
                    System.out.println(result);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"Invalid action\"}");
                    return;
            }

            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", result);
            response.getWriter().write(jsonResponse.toString());

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Failed to process movie request\"}");
            e.printStackTrace();
        } finally {
            response.getWriter().flush();
        }
    }
}

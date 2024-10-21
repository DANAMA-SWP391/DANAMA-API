package controller.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;
import repository.CinemaDAO;
import repository.MovieDAO;
import repository.ShowTimeDAO;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "DashBoardController", value = "/adminDashBoard")
public class DashBoardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();

        // Fetch Top 5 Movies
        MovieDAO movieDAO = new MovieDAO();
        ArrayList<Movie> movies = movieDAO.getTop5MostWatchedMovies();
        responseObject.add("movies", gson.toJsonTree(movies));

        // Fetch Most Popular Showtimes
        ShowTimeDAO showTimeDAO = new ShowTimeDAO();
        List<Object[]> popularShowtimes = showTimeDAO.getMostPopularShowtimes_Admin();
        List<JsonObject> showtimesList = new ArrayList<>();
        for (Object[] showtime : popularShowtimes) {
            JsonObject showtimeJson = new JsonObject();
            showtimeJson.addProperty("cinemaName", (String) showtime[0]);
            showtimeJson.addProperty("logo", (String) showtime[1]);
            showtimeJson.addProperty("showDate", ((Date) showtime[2]).toString());
            showtimeJson.addProperty("startTime", ((Time) showtime[3]).toString());
            showtimeJson.addProperty("endTime", ((Time) showtime[4]).toString());
            showtimesList.add(showtimeJson);
        }
        responseObject.add("popularShowtimes", gson.toJsonTree(showtimesList));

        // Fetch Total Revenue for All Cinemas
        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Object[]> revenueList = cinemaDAO.getTotalRevenueForAllCinemas_Admin();
        List<JsonObject> revenueJsonList = new ArrayList<>();
        for (Object[] revenue : revenueList) {
            JsonObject revenueJson = new JsonObject();
            revenueJson.addProperty("cinemaName", (String) revenue[0]);
            revenueJson.addProperty("totalRevenue", (Double) revenue[1]);
            revenueJsonList.add(revenueJson);
        }
        responseObject.add("cinemaRevenues", gson.toJsonTree(revenueJsonList));

        // Send a single JSON response
        String jsonResponse = gson.toJson(responseObject);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Implement POST logic if needed
    }
}
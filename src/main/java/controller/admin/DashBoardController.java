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

        //Top5Movies
        Gson gson = new Gson();
        MovieDAO movieDAO = new MovieDAO();
        ArrayList<Movie> movies = movieDAO.getTop5MostWatchedMovies();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("movies", gson.toJsonTree(movies));
        String json = gson.toJson(jsonObject);
        response.getWriter().println(json);

        //MostPopular_Showtime
        ShowTimeDAO showTimeDAO = new ShowTimeDAO();
        List<Object[]> popularShowtimes = showTimeDAO.getMostPopularShowtimes_Admin();
        JsonObject jsonPopularSlot = new JsonObject();
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
        jsonPopularSlot.add("popularShowtimes", gson.toJsonTree(showtimesList));
        String jsonResponse = gson.toJson(jsonPopularSlot);
        response.getWriter().println(jsonResponse);

        //Total_Revenue
        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Object[]> GetRevenue = cinemaDAO.getTotalRevenueForAllCinemas_Admin();
        JsonObject jsonGetRevenue = new JsonObject();
        List<JsonObject> GetRevenueList = new ArrayList<>();
        for (Object[] revenue : GetRevenue) {
            JsonObject cinemaJson = new JsonObject();
            cinemaJson.addProperty("cinemaName", (String) revenue[0]);
            cinemaJson.addProperty("totalRevenue", (Double) revenue[1]);

            GetRevenueList.add(cinemaJson);
        }
        jsonGetRevenue.add("cinemaRevenues", gson.toJsonTree(GetRevenueList));
        String jsonResponsee = gson.toJson(jsonGetRevenue);
        response.getWriter().println(jsonResponsee);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
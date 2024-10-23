package controller.web;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cinema;
import model.Movie;
import model.Showtime;
import repository.CinemaDAO;
import repository.MovieDAO;
import repository.ShowTimeDAO;
import utils.Utility;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject responseData = new JsonObject();
        Gson gson =  new GsonBuilder()
                .registerTypeAdapter(Time.class, new Utility.TimeSerializer())  // Register the custom Time serializer
                .create();
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> movies= movieDAO.getAllMovieList();
        ShowTimeDAO showTimeDAO = new ShowTimeDAO();
        List<Showtime> showtimes= showTimeDAO.getListShowtimes();
        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas= cinemaDAO.getListCinemas();
        responseData.add("movies", gson.toJsonTree(movies));
        responseData.add("showtimes",gson.toJsonTree(showtimes));
        responseData.add("cinemas",gson.toJsonTree(cinemas));
        String json = gson.toJson(responseData);
        response.getWriter().write(json);
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
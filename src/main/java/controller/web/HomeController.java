package controller.web;
import com.google.gson.Gson;
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

import java.io.IOException;
import java.util.List;

public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject responseData = new JsonObject();
        Gson gson = new Gson();
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> nowPlayingMovies = movieDAO.getNowPlayingMovies();
        List<Movie> comingSoonMovies = movieDAO.getComingSoonMovies();
        ShowTimeDAO showTimeDAO = new ShowTimeDAO();
        List<Showtime> showtimes= showTimeDAO.getListShowtimes();
        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas= cinemaDAO.getListCinemas();
        responseData.add("nowPlayingMovies",gson.toJsonTree(nowPlayingMovies));
        responseData.add("comingSoonMovies",gson.toJsonTree(comingSoonMovies));
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
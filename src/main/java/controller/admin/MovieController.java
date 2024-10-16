package controller.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cinema;
import model.Movie;
import repository.CinemaDAO;
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
        MovieDAO dao = new MovieDAO();
        List<Movie> movies = dao.getAllMovieList();

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("movies", gson.toJsonTree(movies));
        String json = gson.toJson(jsonObject);

        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
        String action = jsonObject.get("action").getAsString();
        System.out.println(jsonObject);
        MovieDAO dao = new MovieDAO();
        boolean result = false;

        switch (action) {
            case "add":
                Movie movie = gson.fromJson(jsonObject.get("movie"), Movie.class);
                result = dao.addMovie(movie);
                System.out.println(movie);
                break;
            case "delete":
                Movie movieDelete = gson.fromJson(jsonObject.get("movie"), Movie.class);
                result = dao.deleteMovie(movieDelete.getMovieId());
                break;
            case "update":
                Movie movieUpdate = gson.fromJson(jsonObject.get("movie"), Movie.class);
                result = dao.updateMovieByID(movieUpdate.getMovieId(), movieUpdate);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":" + result + "}");
        response.getWriter().flush();
        response.getWriter().close();
    }
}
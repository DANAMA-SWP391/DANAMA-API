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

        try {
            switch (action) {
                case "add":
                    Movie movie = gson.fromJson(jsonObject.get("movie"), Movie.class);
                    result = dao.addMovie(movie);
                    System.out.println("Add movie result: " + result);
                    break;

                case "delete":
                    int movieIdDelete = jsonObject.get("movie").getAsJsonObject().get("movieId").getAsInt();
                    result = dao.deleteMovie(movieIdDelete);
                    if (!result) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().write("{\"error\": \"Movie not found\"}");
                        return;
                    }
                    break;

                case "update":
                    Movie movieUpdate = gson.fromJson(jsonObject.get("movie"), Movie.class);
                    result = dao.updateMovieByID(movieUpdate.getMovieId(), movieUpdate);
                    if (!result) {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().write("{\"error\": \"Movie not found or update failed\"}");
                        return;
                    }
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Invalid action\"}");
                    return;
            }

            response.getWriter().write("{\"success\":" + result + "}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
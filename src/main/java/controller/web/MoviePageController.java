package controller.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;
import repository.MovieDAO;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
@WebServlet(name = "MoviePageController", value = "/moviePage")
public class MoviePageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set content type to application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Create an instance of MovieDAO to get the list of movies
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> movies = movieDAO.getAllMovieList();

        // Use Gson to convert the list of movies to a JSON string
        Gson gson = new Gson();
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("movies", movies);
        String moviesJson = gson.toJson(responseData);

        // Send the JSON response
        PrintWriter out = response.getWriter();
        out.write(moviesJson);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
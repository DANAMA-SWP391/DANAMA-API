package controller.web;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Review;
import model.Showtime;
import repository.ReviewDAO;
import repository.ShowTimeDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "DetailMovieController", value = "/detailMovie")
public class DetailMovieController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int movieId= Integer.parseInt(request.getParameter("movieId"));
        ShowTimeDAO showTimeDAO = new ShowTimeDAO();
        ArrayList<Showtime> listShowtime= showTimeDAO.getShowtimeByMovie(movieId);
        ReviewDAO reviewDAO = new ReviewDAO();
        List<Review> listReview= reviewDAO.getReviewByMovie(Integer.toString(movieId));
        HashMap<String,Object> responseData = new HashMap<>();
        responseData.put("showtimes",listShowtime);
        responseData.put("reviews",listReview);
        Gson gson = new Gson();
        String json = gson.toJson(responseData);
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}
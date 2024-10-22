package controller.cManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;
import model.Room;
import model.Showtime;
import repository.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "DashBoardController", value = "/managerDashBoard")
public class DashBoardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        int cinemaId = Integer.parseInt(request.getParameter("cinemaId"));


        ShowTimeDAO showtimeDAO = new ShowTimeDAO();
        TicketDAO ticketDAO = new TicketDAO();
        BookingDAO bookingDAO = new BookingDAO();
        MovieDAO movieDAO = new MovieDAO();

        ArrayList<Showtime> showtimes = showtimeDAO.getTop5PopularShowtimesInCinema(cinemaId);
        ArrayList<Movie> popularmovies = movieDAO.getTop5MostWatchedMoviesByCinemaId(cinemaId);
        int ticketsoldincurrentmonth =  ticketDAO.getTicketSoldInCurrentMonth(cinemaId);
        ArrayList<Integer> ticketsoldpermonth = ticketDAO.getTicketsSoldPerMonth(cinemaId);
        double dailytotalrevenue = bookingDAO.getDailyTotalCostByCinema(cinemaId);
        double monthtotalrevenue = bookingDAO.getTotalCostInCurrentMonthByCinema(cinemaId);
        String populartimeslot = showtimeDAO.getMostPopularTimeSlotInCinema(cinemaId);
        ArrayList<Map<String, Object>> ticketsoldandtotalcosteachmovie= movieDAO.getTicketSoldAndTotalCost(cinemaId);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("dailytotalrevenue", dailytotalrevenue);
        jsonObject.addProperty("monthtotalrevenue", monthtotalrevenue);
        jsonObject.addProperty("populartimeslot", populartimeslot);
        jsonObject.addProperty("ticketsoldincurrentmonth", ticketsoldincurrentmonth);
        jsonObject.add("ticketsoldpermonth", gson.toJsonTree(ticketsoldpermonth));
        jsonObject.add("popularmovies", gson.toJsonTree(popularmovies));
        jsonObject.add("ticketsoldandtotalcosteachmovie",gson.toJsonTree(ticketsoldandtotalcosteachmovie));
        jsonObject.add("showtimes", gson.toJsonTree(showtimes));
        String json = gson.toJson(jsonObject);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
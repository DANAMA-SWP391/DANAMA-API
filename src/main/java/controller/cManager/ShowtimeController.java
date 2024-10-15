package controller.cManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import repository.ShowTimeDAO;
import utils.Utility;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;


@WebServlet(name = "ShowtimeController", value = "/ShowtimeController")

public class ShowtimeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();

        int cinemaId = Integer.parseInt(request.getParameter("cinemaId"));

        ShowTimeDAO showtimeDAO = new ShowTimeDAO();
        ArrayList<Showtime> showtimes = showtimeDAO.getListShowtimesByCinemaID(cinemaId);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("showtimes", gson.toJsonTree(showtimes));

        String json = gson.toJson(jsonObject);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Time.class, new Utility.TimeDeserializer())
                .setDateFormat("HH:mm:ss")
                .create();

        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class) ;
        String action = jsonObject.get("action").getAsString();
        System.out.println(jsonObject);
        ShowTimeDAO showtimeDAO = new ShowTimeDAO();
        boolean result = false;

        switch (action) {
            case "add":
                Showtime showtime = gson.fromJson(jsonObject.get("showtime"), Showtime.class);
                result = showtimeDAO.addShowtime(showtime);
                break;

            case "update":
                Showtime newshowtime = gson.fromJson(jsonObject.get("showtime"), Showtime.class);
                result = showtimeDAO.updateShowtime(newshowtime.getShowtimeId(), newshowtime);
                break;

            case "delete":
                Showtime deleteshowtime = gson.fromJson(jsonObject.get("showtime"), Showtime.class);
                result = showtimeDAO.deleteShowtime(deleteshowtime.getShowtimeId());
                break;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":" + result + "}");
        response.getWriter().flush();
        response.getWriter().close();

    }

}




package controller.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cinema;
import model.Showtime;
import repository.CinemaDAO;
import repository.ShowTimeDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "ShowtimePageController", value = "/showtimePage")
public class ShowtimePageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ShowTimeDAO showTimeDAO= new ShowTimeDAO();
        ArrayList<Showtime> listShowtime= showTimeDAO.getListShowtimes();
        CinemaDAO cinemaDAO = new CinemaDAO();
        ArrayList<Cinema> listCinema= cinemaDAO.getListCinemas();
        HashMap<String,Object> responseData= new HashMap<>();
        responseData.put("cinemas",listCinema);
        responseData.put("showtimes",listShowtime);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        PrintWriter out = response.getWriter();
        out.write(gson.toJson(responseData));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
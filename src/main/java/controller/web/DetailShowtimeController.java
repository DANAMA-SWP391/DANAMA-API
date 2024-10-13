package controller.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Seat;
import model.Showtime;
import model.Ticket;
import repository.SeatDAO;
import repository.ShowTimeDAO;
import repository.TicketDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "DetailShowtimeController", value = "/detailShowtime")
public class DetailShowtimeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject data = gson.fromJson(request.getReader(), JsonObject.class);
        int showtimeId = data.get("showtimeId").getAsInt();
        ShowTimeDAO showTimeDAO= new ShowTimeDAO();
        Showtime showtime = showTimeDAO.getShowtimeById(showtimeId);
        SeatDAO seatDAO = new SeatDAO();
        List<Seat> seats= seatDAO.getListSeatsInRoom(showtime.getRoom().getRoomId());
        TicketDAO ticketDAO = new TicketDAO();
        List<Ticket> tickets = ticketDAO.getTicketListOfShowtime(showtimeId);
        JsonObject responseData = new JsonObject();
        responseData.add("showtime", gson.toJsonTree(showtime));
        responseData.add("seats", gson.toJsonTree(seats));
        responseData.add("tickets", gson.toJsonTree(tickets));
        String json = gson.toJson(responseData);
        response.getWriter().println(json);
        response.getWriter().flush();

    }
}
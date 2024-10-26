package controller.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import utils.Utility;

import java.io.IOException;
import java.sql.Time;
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
        Gson gson =  new GsonBuilder()
                .registerTypeAdapter(Time.class, new Utility.TimeSerializer())  // Register the custom Time serializer
                .create();
        JsonObject data = gson.fromJson(request.getReader(), JsonObject.class);
        int showtimeId = data.get("showtimeId").getAsInt();
        int roomId = data.get("roomId").getAsInt();
        SeatDAO seatDAO = new SeatDAO();
        List<Seat> seats= seatDAO.getListSeatsInRoom(roomId);
        TicketDAO ticketDAO = new TicketDAO();
        List<Ticket> tickets = ticketDAO.getTicketListOfShowtime(showtimeId);
        for(Seat seat : seats) {
            for(Ticket ticket : tickets) {
                if(seat.getSeatId()==ticket.getSeat().getSeatId()) {
                    seat.setType("Booked");
                }
            }
        }
        JsonObject responseData = new JsonObject();
        responseData.add("seats", gson.toJsonTree(seats));
        String json = gson.toJson(responseData);
        response.getWriter().println(json);
        response.getWriter().flush();

    }
}
package controller.user;

import com.google.gson.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Booking;
import model.Ticket;
import repository.BookingDAO;
import repository.TicketDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookingController", value = "/booking")
public class BookingController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        JsonObject data= gson.fromJson(request.getReader(), JsonObject.class);
        System.out.println(data);
        Booking booking = gson.fromJson(data.get("booking"), Booking.class);
        JsonArray ticketsJson = data.get("tickets").getAsJsonArray();

        BookingDAO bookingDAO = new BookingDAO();
        boolean success = true;
        JsonObject responseData = new JsonObject();
        if(!bookingDAO.addBooking(booking)) {
            success = false;
        }
        else {
            booking= bookingDAO.getBookingByUserAndDate(booking.getUser().getUID(),booking.getTimestamp());
            List<Ticket> tickets = new ArrayList<>();
            for(JsonElement ticket : ticketsJson){
                Ticket t = gson.fromJson(ticket, Ticket.class);
                t.setBooking(booking);
                tickets.add(t);
            }
            TicketDAO ticketDAO = new TicketDAO();
            for(Ticket t : tickets){
                if(!ticketDAO.addTicket(t)){
                    success = false;
                }
            }
            tickets= ticketDAO.getTicketByBooking(booking.getBookingId());
            responseData.add("booking", gson.toJsonTree(booking));
            responseData.add("tickets", gson.toJsonTree(tickets));
        }
        responseData.addProperty("success", success);
        String json = gson.toJson(responseData);
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}
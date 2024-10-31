package controller.user;

import com.google.gson.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Booking;
import model.BookingDetail;
import model.Ticket;
import repository.BookingDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "BookingController", value = "/booking")
public class BookingController extends HttpServlet {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        JsonObject jsonResponse = new JsonObject();
        Gson gson = new Gson();
        BookingDAO bookingDAO = new BookingDAO();
        BookingDetail bookingDetail = bookingDAO.getBookingDetailById(bookingId);
        if (bookingDetail == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            jsonResponse.addProperty("error", "Booking not found for ID: " + bookingId);
        } else {
            jsonResponse.add("bookingDetail", gson.toJsonTree(bookingDetail));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        String json = gson.toJson(jsonResponse);
        response.getWriter().write(json);
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        JsonObject data = gson.fromJson(request.getReader(), JsonObject.class);
        System.out.println(data);
        Booking booking = gson.fromJson(data.get("booking"), Booking.class);
        JsonArray ticketsJson = data.get("tickets").getAsJsonArray();

        BookingDAO bookingDAO = new BookingDAO();
        JsonObject responseData = new JsonObject();

        // Deserialize tickets
        List<Ticket> tickets = new ArrayList<>();
        for (JsonElement ticketElement : ticketsJson) {
            Ticket t = gson.fromJson(ticketElement, Ticket.class);
            tickets.add(t);
        }

        // Attempt to add booking with tickets
        boolean success = bookingDAO.addBookingWithTickets(booking, tickets);
        if (success) {
            int bookingId = booking.getBookingId();
            responseData.addProperty("bookingId", bookingId);
            // Schedule task to delete booking if not paid within 5 minutes
            scheduler.schedule(() -> expireBookingIfUnpaid(bookingId), 5, TimeUnit.MINUTES);
        } else {
            responseData.addProperty("error", "Some seats are already booked. Booking failed.");
        }

        responseData.addProperty("success", success);
        String json = gson.toJson(responseData);
        response.getWriter().write(json);
        response.getWriter().flush();
    }
    // Method to delete the booking if it remains unpaid
    private void expireBookingIfUnpaid(int bookingId) {
        BookingDAO bookingDAO = new BookingDAO();
        // Check if booking is still pending and delete if needed
        if (bookingDAO.isBookingPending(bookingId)) {
            bookingDAO.deleteBookingWithTickets(bookingId);
            System.out.println("Booking " + bookingId + " has been deleted due to expiration.");
        }
    }

}
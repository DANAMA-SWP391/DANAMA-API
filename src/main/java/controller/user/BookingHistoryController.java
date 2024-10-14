package controller.user;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Booking;
import model.Ticket;
import repository.BookingDAO;
import repository.TicketDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "BookingHistoryController", value = "/bookingHistory")
public class BookingHistoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
//        HttpSession session = request.getSession();
//        Account account = (Account) session.getAttribute("account");
        int uid = Integer.parseInt(request.getParameter("uid"));
        BookingDAO bookingDAO= new BookingDAO();
        List<Booking> bookings= bookingDAO.getBookingHistory(uid);
        Gson gson = new Gson();
        HashMap<String,Object> responseData = new HashMap<>();
        responseData.put("bookings",bookings);
        String json = gson.toJson(responseData);
        response.getWriter().write(json);
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        Booking booking= gson.fromJson(request.getReader(),Booking.class);
        TicketDAO ticketDAO = new TicketDAO();
        List<Ticket> tickets = ticketDAO.getTicketByBooking(booking.getBookingId());
        HashMap<String,Object> responseData = new HashMap<>();
        responseData.put("booking",booking);
        responseData.put("tickets",tickets);
        String json = gson.toJson(responseData);
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}
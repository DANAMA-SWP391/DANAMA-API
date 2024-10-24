package controller.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Booking;
import repository.BookingDAO;

import java.io.IOException;

@WebServlet(name = "PaymentController", value = "/checkPaymentStatus")
public class CheckPaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int bookingId= Integer.parseInt(request.getParameter("bookingId"));
        BookingDAO bookingDAO = new BookingDAO();
        Booking booking = bookingDAO.getBookingById(bookingId);
        boolean success = (booking.getStatus()==1);
        System.out.println(success);
        response.getWriter().write("{\"success\":" + success + "}");
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
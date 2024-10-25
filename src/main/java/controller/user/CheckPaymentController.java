package controller.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Booking;
import repository.BookingDAO;

import java.io.IOException;
import com.google.gson.JsonObject;

@WebServlet(name = "PaymentController", value = "/checkPaymentStatus")
public class CheckPaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        BookingDAO bookingDAO = new BookingDAO();
        Booking booking = bookingDAO.getBookingById(bookingId);

        JsonObject jsonResponse = new JsonObject();
        if (booking != null) {
            int status = booking.getStatus();
            jsonResponse.addProperty("status", status);  // Only include status in the response
        } else {
            jsonResponse.addProperty("error", "Booking not found");
        }

        response.getWriter().write(jsonResponse.toString());
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

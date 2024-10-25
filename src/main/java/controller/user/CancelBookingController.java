package controller.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.BookingDAO;

import java.io.IOException;

@WebServlet(name = "CancelBookingController", value = "/cancelBooking")
public class CancelBookingController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        JsonObject responseData = new JsonObject();

        try {
            // Parse bookingId from the request
            int bookingId = gson.fromJson(request.getReader(), JsonObject.class).get("bookingId").getAsInt();

            // Initialize BookingDAO and delete the booking
            BookingDAO bookingDAO = new BookingDAO();
            boolean bookingDeleted = bookingDAO.deleteBooking(bookingId);

            // Add success flag to response
            responseData.addProperty("success", bookingDeleted);

        } catch (Exception e) {
            e.printStackTrace();
            responseData.addProperty("success", false);
        }

        // Send the response back to the client
        response.getWriter().write(gson.toJson(responseData));
        response.getWriter().flush();
    }

}
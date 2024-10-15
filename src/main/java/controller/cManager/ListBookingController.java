package controller.cManager;

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
import java.util.ArrayList;

@WebServlet(name = "ListBookingController", value = "/ListBookingController")
public class ListBookingController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        int cinemaId = Integer.parseInt(request.getParameter("cinemaId"));

        BookingDAO bookingDAO = new BookingDAO();
        ArrayList<Booking> bookings = bookingDAO.getListBookingByCinema(cinemaId);
//
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("bookings", gson.toJsonTree(bookings));

        String json = gson.toJson(jsonObject);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
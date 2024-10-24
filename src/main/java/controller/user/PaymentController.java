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

@WebServlet(name = "PaymentController", value = "/payment")
public class PaymentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject data= gson.fromJson(request.getReader(), JsonObject.class);
        System.out.println(data);
        int bookingId= data.get("bookingId").getAsInt();
        BookingDAO bookingDAO = new BookingDAO();
        boolean success = bookingDAO.paymentConfirm(bookingId);
        System.out.println(success);
        response.getWriter().write("{\"success\":" + success + "}");
        response.getWriter().flush();
    }
}
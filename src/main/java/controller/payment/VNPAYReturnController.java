package controller.payment;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.BookingDAO;

import java.io.IOException;

@WebServlet(name = "VNPAYReturnController", value = "/vnpay-return")
public class VNPAYReturnController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get parameters from VNPay
        String bookingId = request.getParameter("vnp_TxnRef");
        String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
        JsonObject jsonObject = new JsonObject();
        // Update the payment status in the session
        if ("00".equals(vnp_TransactionStatus)) {
            // Payment successful
            BookingDAO bookingDAO = new BookingDAO();
            boolean success= bookingDAO.paymentConfirm(Integer.parseInt(bookingId));
            jsonObject.addProperty("success", success);
        } else {
            // Payment failed
            jsonObject.addProperty("success", "false");

        }
        response.getWriter().write(jsonObject.toString());
        response.sendRedirect("index.jsp");
    }
}
package controller.payment;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Get parameters from VNPay
        String bookingIdParam = request.getParameter("vnp_TxnRef");
        String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
        JsonObject jsonObject = new JsonObject();
        BookingDAO bookingDAO = new BookingDAO();

        try {
            int bookingId = Integer.parseInt(bookingIdParam);

            // Update the payment status based on VNPay's response
            if ("00".equals(vnp_TransactionStatus)) {
                // Payment successful
                boolean success = bookingDAO.paymentConfirm(bookingId);
                jsonObject.addProperty("success", success);

                if (success) {
                    // Redirect to sendConfirmEmail if payment is successful
                    response.sendRedirect(request.getContextPath() + "/sendConfirmEmail?bookingId=" + bookingId);
                    return;
                }
            } else {
                // Payment failed
                bookingDAO.paymentFailed(bookingId);
                jsonObject.addProperty("success", false);
                response.sendRedirect(request.getContextPath() + "/paymentFailed.jsp");
            }

        } catch (NumberFormatException e) {
            jsonObject.addProperty("error", "Invalid booking ID format");
            response.sendRedirect(request.getContextPath() + "/paymentFailed.jsp");
        } catch (Exception e) {
            jsonObject.addProperty("error", "An unexpected error occurred");
            response.sendRedirect(request.getContextPath() + "/paymentFailed.jsp");
        }

        // Write the JSON response in case it's needed for logging or additional response
        response.getWriter().write(jsonObject.toString());
    }
}
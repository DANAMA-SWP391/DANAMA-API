package controller.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.AccountDAO;

import java.io.IOException;

@WebServlet(name = "ChangePasswordController", value = "/changePassword")
public class ChangePasswordController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int UID = Integer.parseInt(request.getParameter("UID")); // Get UID from the request

        JsonObject jsonResponse = new JsonObject();
        AccountDAO accountDAO = new AccountDAO();
        boolean hasPassword = accountDAO.hasPassword(UID);
        jsonResponse.addProperty("hasPassword", hasPassword);

        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Parse the JSON request body directly using Gson
        Gson gson = new Gson();
        JsonObject data = gson.fromJson(request.getReader(), JsonObject.class);

        // Extract data from the request
        String action = data.get("action").getAsString();
        String email = data.get("email").getAsString();
        String newPassword = data.get("newPassword").getAsString();

        JsonObject jsonResponse = new JsonObject();
        AccountDAO accountDAO = new AccountDAO();

        if ("change".equals(action)) {
            // Change password action
            String oldPassword = data.get("oldPassword").getAsString();

            // Verify old password first
            if (accountDAO.login(email, oldPassword)) {
                boolean success = accountDAO.updatePassword(email, newPassword);
                jsonResponse.addProperty("success", success);
            } else {
                jsonResponse.addProperty("success", false);
            }
        } else if ("reset".equals(action)) {
            // Reset password action
            boolean success = accountDAO.updatePassword(email, newPassword);
            jsonResponse.addProperty("success", success);
        } else {
            // Invalid action
            jsonResponse.addProperty("success", false);
        }

        // Write the response
        response.getWriter().write(gson.toJson(jsonResponse));
        response.getWriter().flush();
    }
}
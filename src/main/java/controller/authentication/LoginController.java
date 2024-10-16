
package controller.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import repository.AccountDAO;

import java.io.IOException;
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        try {
            JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
            String email = jsonObject.get("email").getAsString();
            String password = jsonObject.get("password").getAsString();
            AccountDAO accountDAO = new AccountDAO();
            boolean loginSuccess = accountDAO.login(email, password);
            jsonResponse.addProperty("success", loginSuccess);
            if (loginSuccess) {
                Account account= accountDAO.getAccountByEmail(email);
                jsonResponse.add("user",gson.toJsonTree(account));
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "An error occurred during login");
        }

        response.getWriter().write(gson.toJson(jsonResponse));
        response.getWriter().flush();
    }
}

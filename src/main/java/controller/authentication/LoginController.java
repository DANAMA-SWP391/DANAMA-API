
package controller.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Cinema;
import repository.AccountDAO;
import repository.CinemaDAO;
import utils.JwtUtil;

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
            // Parse the incoming JSON request body
            JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
            String email = jsonObject.get("email").getAsString();
            String password = jsonObject.get("password").getAsString();

            // Authenticate user with AccountDAO
            AccountDAO accountDAO = new AccountDAO();
            boolean loginSuccess = accountDAO.login(email, password);

            // Prepare the response based on login success
            jsonResponse.addProperty("success", loginSuccess);
            if (loginSuccess) {
                // Get the user account details
                Account account = accountDAO.getAccountByEmail(email);

                // Convert the Account object (without password) to a JSON string
                account.setPassword(null); // Exclude password from being added to the token
                if(account.getRoleId()==2) {
                    Cinema cinema= new CinemaDAO().getCinemaByUId(account.getUID());
                    jsonResponse.add("cinema",gson.toJsonTree(cinema));
                }

                // Generate a JWT token using the existing method (with the account data string)
                String jwtToken = JwtUtil.generateToken(account.getEmail());

                jsonResponse.addProperty("jwtToken", jwtToken);
                JsonObject user = new JsonObject();
                user.addProperty("name", account.getName());
                user.addProperty("avatar", account.getAvatar());
                user.addProperty("roleId", account.getRoleId());

                // Add the user object to the response
                jsonResponse.add("user", user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "An error occurred during login");
        }

        // Send the JSON response
        response.getWriter().write(gson.toJson(jsonResponse));
        response.getWriter().flush();
    }
}

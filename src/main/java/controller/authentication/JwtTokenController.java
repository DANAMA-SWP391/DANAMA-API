package controller.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import repository.AccountDAO;
import utils.JwtUtil;

import java.io.IOException;

@WebServlet(name = "JwtTokenController", value = "/validateJwtToken")
public class JwtTokenController extends HttpServlet {

    // This method handles the token validation
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        try {
            JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
            String token = jsonObject.get("token").getAsString();

            Claims claims = JwtUtil.validateToken(token);
            if (claims != null) {
                if (!JwtUtil.isTokenExpired(claims)) {
                    // Token is valid and not expired
                    String email = claims.getSubject();
                    Account account = new AccountDAO().getAccountByEmail(email);
                    jsonResponse.addProperty("success", true);
                    jsonResponse.add("user", gson.toJsonTree(account));
                } else {
                    // Token is expired, indicate the need for a refresh
                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Session expired");
                    jsonResponse.addProperty("expired", true); // Signal the front-end to ask for refresh
                }
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Invalid token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "An error occurred while validating the token");
        }

        response.getWriter().write(gson.toJson(jsonResponse));
        response.getWriter().flush();
    }

    // Add a new endpoint for refreshing the token
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        try {
            JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
            String token = jsonObject.get("token").getAsString();

            Claims claims = JwtUtil.validateToken(token);
            if (claims != null && JwtUtil.isTokenExpired(claims)) {
                // Refresh token
                String email = claims.getSubject();
                Account account = new AccountDAO().getAccountByEmail(email);
                String refreshedToken = JwtUtil.generateToken(email); // Generate a new token
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("refreshedToken", refreshedToken); // Return new token
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Invalid or active token; refresh is not allowed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "An error occurred while refreshing the token");
        }

        response.getWriter().write(gson.toJson(jsonResponse));
        response.getWriter().flush();
    }
}
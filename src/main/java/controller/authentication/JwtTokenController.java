package controller.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JwtUtil;

import java.io.IOException;

@WebServlet(name = "JwtTokenController", value = "/validateJwtToken")
public class JwtTokenController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        try {
            // Parse the incoming JSON request to get the token
            JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
            String token = jsonObject.get("token").getAsString();
            System.out.println(token);
            // Validate the token using JwtUtil
            Claims claims = JwtUtil.validateToken(token);

            if (claims != null && !JwtUtil.isTokenExpired(claims)) {
                // If the token is valid and not expired
                String accountJson = claims.getSubject(); // 'sub' contains the Account data JSON string

                // Add account data to the response
                jsonResponse.addProperty("success", true);
                jsonResponse.add("user", gson.fromJson(accountJson, JsonObject.class));
            } else {
                // If the token is invalid or expired
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Invalid or expired token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "An error occurred while validating the token");
        }

        // Send the JSON response
        response.getWriter().write(gson.toJson(jsonResponse));
        response.getWriter().flush();
    }
}
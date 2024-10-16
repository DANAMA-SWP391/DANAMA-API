package controller.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
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
import java.security.GeneralSecurityException;
import java.util.Collections;

@WebServlet(name = "LoginGoogleController", value = "/loginGoogle")
public class LoginGoogleController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
        String token = jsonObject.get("idToken").getAsString();
        // Initialize the GoogleIdTokenVerifier
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList("1057976212312-fjk08o0u6klggbc06l4c4j6gulnskamn.apps.googleusercontent.com")) // Replace with your client ID
                .build();

        GoogleIdToken idToken = null;
        JsonObject responseData = new JsonObject();
        try {
            idToken = verifier.verify(token); // Verify the ID token
        } catch (GeneralSecurityException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseData.addProperty("error", "Token verification failed.");
            response.getWriter().write(gson.toJson(responseData));
            return;
        }

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String googleId = payload.getSubject();
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String avatar = (String) payload.get("picture");

            AccountDAO accountDAO = new AccountDAO();
            Account existingAccount = accountDAO.getAccountByGoogleId(googleId);

            if (existingAccount == null) {
                Account newAccount = new Account();
                newAccount.setGoogleId(googleId);
                newAccount.setName(name);
                newAccount.setEmail(email);
                newAccount.setAvatar(avatar);
                newAccount.setPhone(null);
                newAccount.setRoleId(1);
                newAccount.setPassword(null);

                boolean accountAdded = accountDAO.addAccount(newAccount); // Add the new account
                if (!accountAdded) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    responseData.addProperty("error", "Failed to create new account.");
                    response.getWriter().write(gson.toJson(responseData));
                    return;
                }
            }

            Account user = accountDAO.getAccountByGoogleId(googleId);
            responseData.add("user", gson.toJsonTree(user));

            response.getWriter().write(gson.toJson(responseData));
            response.getWriter().flush();
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseData.addProperty("error", "Invalid Id Token");
            response.getWriter().write(gson.toJson(responseData));
            response.getWriter().flush();
        }
    }

}
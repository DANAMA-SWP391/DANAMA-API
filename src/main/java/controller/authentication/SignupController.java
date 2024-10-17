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
import java.util.List;

@WebServlet(name = "SignupController", value = "/signUp")
public class SignupController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        AccountDAO accountDAO = new AccountDAO();
        List<String> listEmails= accountDAO.getListExistEmails();
        JsonObject data = new JsonObject();
        data.add("listEmails", gson.toJsonTree(listEmails));
        response.getWriter().write(gson.toJson(data));
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject data = gson.fromJson(request.getReader(), JsonObject.class);
        Account account = gson.fromJson(data.get("user"), Account.class);
        AccountDAO accountDAO = new AccountDAO();
        boolean success = accountDAO.addAccount(account);
        data = new JsonObject();
        data.addProperty("success", success);
        response.getWriter().write(gson.toJson(data));
        response.getWriter().flush();
    }
}
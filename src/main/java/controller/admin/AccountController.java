package controller.admin;

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
import java.util.ArrayList;

@WebServlet(name = "AccountController", value = "/AccountController")
public class AccountController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        AccountDAO dao = new AccountDAO();
        ArrayList<Account> accounts = dao.getListAccounts();

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("accounts", gson.toJsonTree(accounts));
        String json = gson.toJson(jsonObject);

        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
       Gson gson = new Gson();
       JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
        String action = jsonObject.get("action").getAsString();
        System.out.println(jsonObject);
        AccountDAO dao = new AccountDAO();
        boolean result = false;

        switch (action) {
            case "add":
                Account account = gson.fromJson(jsonObject.get("account"), Account.class);
                result = dao.addAccount(account);
                System.out.println(account);
                break;
            case "view":
                Account viewAccount = gson.fromJson(jsonObject.get("account"), Account.class);
                Account accountDetails = dao.getAccountById(viewAccount.getUID());
                break;
            case "ban":
                Account accountBan = gson.fromJson(jsonObject.get("account"), Account.class);
                result = dao.banAccount(accountBan.getUID());
                break;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":" + result + "}");
        response.getWriter().flush();
        response.getWriter().close();
    }

}
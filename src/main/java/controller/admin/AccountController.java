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
        JsonObject jsonResponse = new JsonObject();

        switch (action) {
            case "add":
                Account account = gson.fromJson(jsonObject.get("account"), Account.class);
                result = dao.addAccount(account);
                System.out.println(account);
                jsonResponse.addProperty("success", result);
                break;

            case "view":
                Account viewAccount = gson.fromJson(jsonObject.get("account"), Account.class);
                System.out.println("Viewing account with UID: " + viewAccount.getUID());
                Account accountDetails = dao.getAccountById(viewAccount.getUID());

                if (accountDetails != null) {
                    jsonResponse.addProperty("success", true);
                    jsonResponse.add("account", gson.toJsonTree(accountDetails)); // Trả về chi tiết tài khoản
                } else {
                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Account not found.");
                }
                response.getWriter().write(gson.toJson(jsonResponse));  // Gửi phản hồi chi tiết tài khoản
                return; // Dừng ở đây sau khi gửi phản hồi cho case "view"

            case "ban":
                Account accountBan = gson.fromJson(jsonObject.get("account"), Account.class);
                result = dao.banAccount(accountBan.getUID());
                jsonResponse.addProperty("success", result);
                break;

            case "update":  // Thêm case "update"
                Account updatedAccount = gson.fromJson(jsonObject.get("account"), Account.class);
                System.out.println("Updating account with UID: " + updatedAccount.getUID());
                result = dao.updateAccountByID(updatedAccount.getUID(), updatedAccount);

                jsonResponse.addProperty("success", result);

                if (result) {
                    // Nếu cập nhật thành công, trả về đối tượng đã được cập nhật
                    Account updatedAccountDetails = dao.getAccountById(updatedAccount.getUID()); // Lấy thông tin chi tiết sau khi cập nhật
                    jsonResponse.add("account", gson.toJsonTree(updatedAccountDetails)); // Trả về tài khoản cập nhật
                } else {
                    jsonResponse.addProperty("message", "Failed to update account.");
                }
                break;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(jsonResponse));  // Gửi phản hồi JSON cho các case khác
        response.getWriter().flush();
        response.getWriter().close();
    }
}
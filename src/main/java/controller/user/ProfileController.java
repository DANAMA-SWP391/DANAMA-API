package controller.user;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import repository.AccountDAO;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "ProfileController", value = "/profile")
public class ProfileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Account user= (Account) session.getAttribute("user");
        Gson gson = new Gson();
        HashMap<String,Object> responseData = new HashMap<>();
        responseData.put("user",user);
        String json = gson.toJson(responseData);
        response.getWriter().write(json);
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        Account user= gson.fromJson(request.getReader(),Account.class);
        AccountDAO accountDAO = new AccountDAO();
        String sucess;
        if(accountDAO.updateProfile(user)){
            sucess = "true";
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
        }
        else sucess = "false";
        HashMap<String,Object> responseData = new HashMap<>();
        responseData.put("sucess",sucess);
        String json = gson.toJson(responseData);
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}
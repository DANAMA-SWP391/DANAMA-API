package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import repository.AccountDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ManagerListController", value = "/list-available-managers")
public class ManagerListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDAO accountDAO = new AccountDAO(); // Assume CinemaDAO is correctly instantiated with a DB connection
        List<Account> managersWithoutCinema = accountDAO.getManagersWithoutCinema();

        // Set the response content type to application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Convert the list of Account objects to JSON
        PrintWriter out = response.getWriter();
        out.print("[");
        for (int i = 0; i < managersWithoutCinema.size(); i++) {
            Account manager = managersWithoutCinema.get(i);
            String json = String.format("{\"UID\": %d, \"email\": \"%s\"}", manager.getUID(), manager.getEmail());
            out.print(json);
            if (i < managersWithoutCinema.size() - 1) {
                out.print(",");
            }
        }
        out.print("]");
        out.flush();
    }
}
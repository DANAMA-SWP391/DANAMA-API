package controller.admin;

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

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "CinemaController", value = "/CinemaController")
public class CinemaController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        CinemaDAO dao = new CinemaDAO();
        ArrayList<Cinema> cinemas = dao.getListCinemas();

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("cinemas", gson.toJsonTree(cinemas));
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
        CinemaDAO dao = new CinemaDAO();
        boolean result = false;

        switch (action) {
            case "add":
                Cinema cinema = gson.fromJson(jsonObject.get("cinema"), Cinema.class);
                result = dao.addCinema(cinema);
                System.out.println("Add result: " + result);
                break;
            case "view":
                Cinema viewCinema = gson.fromJson(jsonObject.get("account"), Cinema.class);
                Cinema cinemaDetails = dao.getCinemaById(viewCinema.getCinemaId());
                break;
            case "delete":
                Cinema cinemaDelete = gson.fromJson(jsonObject.get("cinema"), Cinema.class);
                result = dao.deleteCinema(cinemaDelete.getCinemaId());
                break;
            case "update":
                Cinema cinemaUpdate = gson.fromJson(jsonObject.get("cinema"), Cinema.class);
                result = dao.updateCinemaByID(cinemaUpdate.getCinemaId(), cinemaUpdate);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":" + result + "}");
        response.getWriter().flush();
        response.getWriter().close();
    }
}
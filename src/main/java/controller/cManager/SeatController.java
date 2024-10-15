package controller.cManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Room;
import model.Seat;
import repository.SeatDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "SeatController", value = "/SeatController")
public class SeatController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        int roomId = Integer.parseInt(request.getParameter("roomId"));


        SeatDAO seatDAO = new SeatDAO();
        ArrayList<Seat> seats = seatDAO.getListSeatsInRoom(roomId);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("seats", gson.toJsonTree(seats));
        String json = gson.toJson(jsonObject);

        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class) ;
        String action = jsonObject.get("action").getAsString();
        System.out.println(jsonObject);

        SeatDAO seatDAO = new SeatDAO();
        boolean result = false;

        switch (action) {
            case "add":
                Seat seat = gson.fromJson(jsonObject.get("seat"), Seat.class);
                result = seatDAO.addSeat(seat);
                System.out.println(seat);
                break;
            case "changeSeatType":
                Seat newseat = gson.fromJson(jsonObject.get("seat"),Seat.class);
                result = seatDAO.changeSeatTypeByID(newseat.getSeatId(), newseat.getType());
                break;
            case "delete":
                Seat seatDelete = gson.fromJson(jsonObject.get("seat"), Seat.class);
                result = seatDAO.deleteSeat(seatDelete.getSeatId());
                break;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":" + result + "}");
        response.getWriter().flush();
        response.getWriter().close();

    }










}
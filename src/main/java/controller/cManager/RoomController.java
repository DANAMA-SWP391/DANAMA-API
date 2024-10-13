package controller.cManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cinema;
import model.Room;
import model.Seat;
import repository.CinemaDAO;
import repository.RoomDAO;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "RoomController", value = "/RoomController")
public class RoomController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class) ;
        int cinemaId = jsonObject.get("cinemaId").getAsInt();

        RoomDAO roomDAO = new RoomDAO();
        ArrayList<Room> rooms = roomDAO.getListRoomsByCinemaID(cinemaId);

        jsonObject = new JsonObject();
        jsonObject.add("rooms", gson.toJsonTree(rooms));
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
        RoomDAO roomDAO = new RoomDAO();
        boolean result = false;

        switch (action) {
            case "add":
                Room room = gson.fromJson(jsonObject.get("room"), Room.class);
                result = roomDAO.addNewRoom(room);
                break;
            case "update":
                Room newroom = gson.fromJson(jsonObject.get("room"), Room.class);
                result = roomDAO.updateRoom(newroom.getRoomId(), newroom);
                break;
            case "delete":
                Room deleteRoom = gson.fromJson(jsonObject.get("room"), Room.class);
                result = roomDAO.deleteRoom(deleteRoom.getRoomId());
                break;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":" + result + "}");
        response.getWriter().flush();
        response.getWriter().close();
    }

//    private boolean addRoom(HttpServletRequest request, RoomDAO roomDAO) {
//        String name = request.getParameter("name");
//        int numberOfSeat = Integer.parseInt(request.getParameter("numberOfSeat"));
//        int cinemaId = Integer.parseInt(request.getParameter("cinemaId"));
//
//        CinemaDAO cinemaDAO = new CinemaDAO();
//        Cinema cinema = cinemaDAO.getCinemaById(cinemaId);
//        Room room = new Room(name, numberOfSeat, cinema);
//
//        return roomDAO.addNewRoom(room);
//    }
//
//    private boolean updateRoom(HttpServletRequest request, RoomDAO roomDAO) {
//        int roomId = Integer.parseInt(request.getParameter("roomId"));
//        String name = request.getParameter("name");
//        int numberOfSeat = Integer.parseInt(request.getParameter("numberOfSeat"));
//        int cinemaId = Integer.parseInt(request.getParameter("cinemaId"));
//
//        CinemaDAO cinemaDAO = new CinemaDAO();
//        Cinema cinema = cinemaDAO.getCinemaById(cinemaId);
//        Room room = new Room(roomId, name, numberOfSeat, cinema);
//
//        return roomDAO.updateRoom(roomId, room);
//    }
}
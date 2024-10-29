package controller.cManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Room;
import repository.RoomDAO;
import repository.SeatDAO;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "RoomController", value = "/RoomController")
public class RoomController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        int cinemaId = Integer.parseInt(request.getParameter("cinemaId"));

        RoomDAO roomDAO = new RoomDAO();
        ArrayList<Room> rooms = roomDAO.getListRoomsByCinemaID(cinemaId);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("rooms", gson.toJsonTree(rooms));
        String json = gson.toJson(jsonObject);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
        String action = jsonObject.get("action").getAsString();
        RoomDAO roomDAO = new RoomDAO();
        SeatDAO seatDAO = new SeatDAO(); // To access seat operations
        boolean result = false;
        String message = "";

        switch (action) {
            case "add":
                Room room = gson.fromJson(jsonObject.get("room"), Room.class);
                result = roomDAO.addNewRoom(room);
                message = result ? "Room added successfully." : "Failed to add room.";
                break;

            case "update":
                Room updatedRoom = gson.fromJson(jsonObject.get("room"), Room.class);

                // Check if the new dimensions will place any seats out of bounds
                if (roomDAO.dimensionsChanged(updatedRoom.getRoomId(), updatedRoom) &&
                        !roomDAO.validateSeatDimensions(updatedRoom.getRoomId(), updatedRoom.getNumberOfRows(), updatedRoom.getNumberOfColumns())) {
                    message = "Cannot update room dimensions: Some seats would be out of bounds. Please delete the seats first.";
                } else {
                    result = roomDAO.updateRoom(updatedRoom.getRoomId(), updatedRoom);
                    message = result ? "Room updated successfully." : "Failed to update room.";
                }
                break;

            case "delete":
                int roomId = jsonObject.get("roomId").getAsInt();

                // Check if any seats in the room are booked in an active showtime
                if (roomDAO.hasActiveBookedSeats(roomId)) {
                    message = "Cannot delete room: Some seats are booked in active showtimes.";
                } else {
                    // Delete all seats in the room as per the specified logic
                    roomDAO.deleteAllSeatsInRoom(roomId);
                    result = roomDAO.deleteRoom(roomId);
                    message = result ? "Room and all seats deleted successfully." : "Failed to delete room.";
                }
                break;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("success", result);
        responseJson.addProperty("message", message);
        response.getWriter().write(responseJson.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }

}
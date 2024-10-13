package controller.cManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import repository.ShowTimeDAO;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "ShowtimeController", value = "/ShowtimeController")

public class ShowtimeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class) ;
        int cinemaId = jsonObject.get("cinemaId").getAsInt();

        ShowTimeDAO showtimeDAO = new ShowTimeDAO();
        ArrayList<Showtime> showtimes = showtimeDAO.getListShowtimesByCinemaID(cinemaId);

        jsonObject = new JsonObject();
        jsonObject.add("showtimes", gson.toJsonTree(showtimes));

        String json = gson.toJson(jsonObject);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class) ;
        String action = jsonObject.get("action").getAsString();
        ShowTimeDAO showtimeDAO = new ShowTimeDAO();
        boolean result = false;

        switch (action) {
            case "add":
                Showtime showtime = gson.fromJson(jsonObject.get("showtime"), Showtime.class);
                result = showtimeDAO.addShowtime(showtime);
                break;

            case "update":
                Showtime newshowtime = gson.fromJson(jsonObject.get("showtime"), Showtime.class);
                result = showtimeDAO.updateShowtime(newshowtime.getShowtimeId(), newshowtime);
                break;

            case "delete":
                Showtime deleteshowtime = gson.fromJson(jsonObject.get("showtime"), Showtime.class);
                result = showtimeDAO.deleteShowtime(deleteshowtime.getShowtimeId());
                break;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":" + result + "}");
        response.getWriter().flush();
        response.getWriter().close();

    }

//    private boolean addShowtime(HttpServletRequest request, ShowTimeDAO showtimeDAO) {
//        String movieId = request.getParameter("movieid");
//        String roomId = request.getParameter("roomid");
//        String startTime = request.getParameter("starttime");
//        String endTime = request.getParameter("endtime");
//        String date = request.getParameter("date");
//        String Status = request.getParameter("status");
//        String Baseprice = request.getParameter("baseprice");
//        String SeatAvailable = request.getParameter("seatavailable");
//
//        int seatAvailable = Integer.parseInt(SeatAvailable);
//        double baseprice = Double.parseDouble(Baseprice);
//        int status = Integer.parseInt(Status);
//        int mId = Integer.parseInt(movieId);
//        int rId = Integer.parseInt(roomId);
//
//        RoomDAO roomDAO = new RoomDAO();
//        Room room = roomDAO.getRoomById(rId);
//
//        MovieDAO movieDAO = new MovieDAO();
//        Movie movie = movieDAO.getMovieById(mId);
//
//        Showtime showtime = new Showtime();
//
//        showtime.setMovie(movie);
//        showtime.setRoom(room);
//        showtime.setStatus(status);
//        showtime.setBasePrice(baseprice);
//        showtime.setSeatAvailable(seatAvailable);
//        try {
//            // Parse the start and end times to java.sql.Time
//            Time start = (Time) parseDateTime(startTime, "HH:mm:ss", true);
//            Time end = (Time) parseDateTime(endTime, "HH:mm:ss", true);
//            showtime.setStartTime(start);
//            showtime.setEndTime(end);
//
//            // Parse the show date to java.util.Date
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            Date showDate = dateFormat.parse(date);
//            showtime.setShowDate(showDate);
//
//        } catch (ParseException  e) {
//            e.printStackTrace();
//            return false; // Parsing failed, return false
//        }
//
//        return showtimeDAO.addShowtime(showtime);
//    }
//
//
//    private boolean updateShowtime(HttpServletRequest request, ShowTimeDAO showtimeDAO) {
//        int showtimeId = Integer.parseInt(request.getParameter("showtimeid"));
//        int roomId = Integer.parseInt(request.getParameter("roomid"));
//        int seatAvailable = Integer.parseInt(request.getParameter("seatavailable"));
//        int movieId = Integer.parseInt(request.getParameter("movieid"));
//        int status = Integer.parseInt(request.getParameter("status"));
//        double basePrice = Double.parseDouble(request.getParameter("baseprice"));
//        String startTime = request.getParameter("starttime");
//        String endTime = request.getParameter("endtime");
//        String date = request.getParameter("date");
//
//        RoomDAO roomDAO = new RoomDAO();
//        Room room = roomDAO.getRoomById(roomId);
//
//        MovieDAO movieDAO = new MovieDAO();
//        Movie movie = movieDAO.getMovieById(movieId);
//
//        Showtime showtime = new Showtime();
//
//        try {
//            // Parse the start and end times to java.sql.Time using the parseDateTime method
//            Time start = (Time) parseDateTime(startTime, "HH:mm:ss", true);
//            Time end = (Time) parseDateTime(endTime, "HH:mm:ss", true);
//            showtime.setStartTime(start);
//            showtime.setEndTime(end);
//
//            // Parse the show date to java.util.Date
//            Date showDate = parseDateTime(date, "yyyy-MM-dd", false);
//            showtime.setShowDate(showDate);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return false; // Parsing failed, return false
//        }
//
//
//        showtime.setShowtimeId(showtimeId);
//        showtime.setBasePrice(basePrice);
//        showtime.setSeatAvailable(seatAvailable);
//        showtime.setStatus(status);
//        showtime.setRoom(room);
//        showtime.setMovie(movie);
//
//        return showtimeDAO.updateShowtime(showtimeId, showtime);
//    }
//
//    private Date parseDateTime(String dateTimeStr, String format, boolean isTime) throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        Date parsedDate = dateFormat.parse(dateTimeStr);
//        if (isTime) {
//            // Convert parsed date to java.sql.Time
//            return new Time(parsedDate.getTime());
//        } else {
//            return parsedDate;
//        }
//    }





}




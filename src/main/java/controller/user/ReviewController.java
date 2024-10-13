package controller.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Review;
import repository.ReviewDAO;

import java.io.IOException;

@WebServlet(name = "ReviewController", value = "/review")
public class ReviewController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss") // Set your desired date format
                .create();
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
        System.out.println(jsonObject);
        String action = jsonObject.get("action").getAsString();
        System.out.println(action);
        boolean success;
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            switch (action) {
                case "ADD":
                    Review review = gson.fromJson(jsonObject.get("review"), Review.class);
                    System.out.println(review);
                    success = reviewDAO.addNewReview(review);
                    break;
                case "UPDATE":
                    Review reviewUpdate = gson.fromJson(jsonObject.get("review"), Review.class);
                    success = reviewDAO.updateReview(reviewUpdate) ;
                    break;
                case "DELETE":
                    Review reviewDelete = gson.fromJson(jsonObject.get("review"), Review.class);
                    System.out.println(reviewDelete);
                    success = reviewDAO.deleteReview(reviewDelete.getReviewId()) ;
                    break;
                default:
                    success = false;
                    break;
            }

            // Send the result as JSON
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("success", success);
            response.getWriter().write(gson.toJson(responseJson));
        } catch (Exception e) {
            e.printStackTrace();
            // Return error response as JSON
            JsonObject errorJson = new JsonObject();
            errorJson.addProperty("error", "An error occurred");
            response.getWriter().write(gson.toJson(errorJson));
        }
    }
}
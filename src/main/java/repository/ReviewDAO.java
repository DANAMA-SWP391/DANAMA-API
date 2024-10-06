package repository;

import context.DBContext;
import model.Review;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReviewDAO extends DBContext {
    // Method to add a new review to the database
    public void addNewReview(Review review) {
        String sql = "INSERT INTO Review (rating, comment, [date], [UID], movieId) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = null;

        try {
            // Using the connection from DBContext
            statement = connection.prepareStatement(sql);

            // Set parameters
            statement.setInt(1, review.getRating());
            statement.setString(2, review.getComment());
            statement.setDate(3, new Date(review.getDate().getTime())); // Correct conversion from java.util.Date to java.sql.Date
            statement.setInt(4, review.getUID());
            statement.setInt(5, review.getMovieId());

            // Execute the update
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to get reviews by movieId
    public List<Review> getReviewByMovie(String movieId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Review WHERE movieId = ?";
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            // Sử dụng connection từ DBContext
            statement = connection.prepareStatement(sql);

            // Set tham số movieId cho câu lệnh SQL
            statement.setString(1, movieId);

            // Thực thi câu lệnh và lấy kết quả
            rs = statement.executeQuery();

            // Duyệt qua từng dòng kết quả
            while (rs.next()) {
                int reviewId = rs.getInt("reviewId");
                int rating = rs.getInt("rating");
                String comment = rs.getString("comment");
                Date date = rs.getDate("date");
                int UID = rs.getInt("UID");
                int movie_Id = rs.getInt("movieId");

                // Tạo đối tượng Review từ dữ liệu truy vấn được
                Review review = new Review(reviewId, rating, comment, date, UID, movie_Id);

                // Thêm review vào danh sách
                reviews.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return reviews;
    }

    public static void main(String[] args) {
//        java.util.Date date = new java.util.Date();
//        Review newReview = new Review(3, 5, "Nice!", date, 4, 3);
        ReviewDAO reviewDAO = new ReviewDAO();
//        reviewDAO.addNewReview(newReview);
        List<Review> reviews = reviewDAO.getReviewByMovie("1");

        for (Review review : reviews) {
            System.out.println(review);
        }
    }
}
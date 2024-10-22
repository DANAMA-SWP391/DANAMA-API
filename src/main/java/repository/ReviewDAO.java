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
    public boolean addNewReview(Review review) {
        String sql = "INSERT INTO Review (rating, comment, [date], [UID], movieId) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = null;

        try {
            // Using the connection from DBContext
            statement = connection.prepareStatement(sql);

            // Set parameters
            statement.setInt(1, review.getRating());
            statement.setString(2, review.getComment());
            statement.setDate(3, new Date(review.getDate().getTime())); // Correct conversion from java.util.Date to java.sql.Date
            statement.setInt(4, review.getUid());
            statement.setInt(5, review.getMovieId());

            // Execute the update
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
        String sql = "SELECT r.*, a.name as reviewer, a.avatar as avatar " +
                "FROM Review r " +
                "JOIN Account a ON r.UID = a.UID " +
                "WHERE r.movieId = ?";
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
                Review review = new Review();
                review.setReviewId(rs.getInt("reviewId"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setDate(rs.getDate("date"));
                review.setUid(rs.getInt("UID"));
                review.setMovieId(Integer.parseInt(movieId));
                review.setReviewer(rs.getString("reviewer"));
                review.setAvatar(rs.getString("avatar"));

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

    public boolean updateReview(Review review) {

        String sql = "UPDATE Review SET rating = ?, comment = ?, date = ?, UID = ?, movieId = ? WHERE reviewId = ?";
        PreparedStatement statement = null;

        try {
            // Using the connection from DBContext
            statement = connection.prepareStatement(sql);

            // Set the parameters
            statement.setInt(1, review.getRating());
            statement.setString(2, review.getComment());
            statement.setDate(3, new Date(review.getDate().getTime()));
            statement.setInt(4, review.getUid());
            statement.setInt(5, review.getMovieId());
            statement.setInt(6, review.getReviewId());

            // Execute the update
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean deleteReview(int reviewId) {
        String sql = "DELETE FROM Review WHERE reviewId = ?";
        PreparedStatement statement = null;

        try {
            // Using the connection from DBContext
            statement = connection.prepareStatement(sql);

            // Set parameter
            statement.setInt(1, reviewId);

            // Execute the update
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
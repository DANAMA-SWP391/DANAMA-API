package repository;

import context.DBContext;
import model.Review;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
            statement.setTimestamp(3, review.getDate()); // Use Timestamp to include date and time
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

    public List<Review> getReviewByMovie(String movieId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, a.name as reviewer, a.avatar as avatar " +
                "FROM Review r " +
                "JOIN Account a ON r.UID = a.UID " +
                "WHERE r.movieId = ? " +
                "ORDER BY r.date DESC";  // Sort by date in descending order
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            // Using connection from DBContext
            statement = connection.prepareStatement(sql);

            // Set the movieId parameter for the SQL statement
            statement.setString(1, movieId);

            // Execute the statement and retrieve the result
            rs = statement.executeQuery();

            // Iterate through each result row
            while (rs.next()) {
                Review review = new Review();
                review.setReviewId(rs.getInt("reviewId"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setDate(rs.getTimestamp("date")); // Use getTimestamp to retrieve full datetime
                review.setUid(rs.getInt("UID"));
                review.setMovieId(Integer.parseInt(movieId));
                review.setReviewer(rs.getString("reviewer"));
                review.setAvatar(rs.getString("avatar"));

                // Add the review to the list
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
        String sql = "UPDATE Review SET rating = ?, comment = ?, date = ? WHERE reviewId = ?";
        PreparedStatement statement = null;

        try {
            // Using connection from DBContext
            statement = connection.prepareStatement(sql);

            // Set the parameters
            statement.setInt(1, review.getRating());
            statement.setString(2, review.getComment());
            statement.setTimestamp(3, review.getDate()); // Use Timestamp for date and time
            statement.setInt(4, review.getReviewId());

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
            // Using connection from DBContext
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
        ReviewDAO reviewDAO = new ReviewDAO();
        List<Review> reviews = reviewDAO.getReviewByMovie("1");

        for (Review review : reviews) {
            System.out.println(review);
        }
    }
}

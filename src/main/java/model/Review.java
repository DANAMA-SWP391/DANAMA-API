
package model;

import java.util.Date;

public class Review {
    private int reviewId;
    private int rating;
    private String comment;
    private Date date;
    private int UID;
    private int movieId;

    // Default constructor
    public Review() {
    }

    // Parameterized constructor
    public Review(int reviewId, int rating, String comment, Date date, int UID, int movieId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.UID = UID;
        this.movieId = movieId;
    }

    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "Review{" + "reviewId=" + reviewId + ", rating=" + rating + ", comment=" + comment + ", date=" + date + ", UID=" + UID + ", movieId=" + movieId + '}';
    }
    
}

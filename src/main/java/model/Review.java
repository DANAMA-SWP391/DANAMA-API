
package model;

import java.util.Date;

public class Review {
    private int reviewId;
    private int rating;
    private String comment;
    private Date date;
    private int uid;
    private String reviewer;
    private String avatar;
    private int movieId;

    // Default constructor
    public Review() {
    }

    // Parameterized constructor
    public Review(int reviewId, int rating, String comment, Date date, int uid, int movieId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
        this.uid = uid;
        this.movieId = movieId;
    }

    public Review(String avatar, int uid, int reviewId, String reviewer, int rating, int movieId, Date date, String comment) {
        this.avatar = avatar;
        this.uid = uid;
        this.reviewId = reviewId;
        this.reviewer = reviewer;
        this.rating = rating;
        this.movieId = movieId;
        this.date = date;
        this.comment = comment;
    }
    // Getters and Setters

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

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

    public int getUid() {
        return uid;
    }

    public void setUID(int uid) {
        this.uid = uid;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "avatar='" + avatar + '\'' +
                ", reviewId=" + reviewId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                ", uid=" + uid +
                ", reviewer='" + reviewer + '\'' +
                ", movieId=" + movieId +
                '}';
    }
}

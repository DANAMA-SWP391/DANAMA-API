package model;

import java.util.Date;

public class MovieRequest {
    private int requestId;      // ID của yêu cầu phim
    private int cinemaId;       // ID của rạp phim
    private int movieId;        // ID của phim
    private int status;   // Trạng thái yêu cầu (0: Pending, 1: Approved)
    private String message;      // Tin nhắn của cinema manager
    private Date timestamp;      // Thời gian tạo yêu cầu

    // Constructor

    public MovieRequest() {
    }

    public MovieRequest(int requestId, int cinemaId, int movieId, int status, String message, Date timestamp) {
        this.requestId = requestId;
        this.cinemaId = cinemaId;
        this.movieId = movieId;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp= timestamp;
    }
}

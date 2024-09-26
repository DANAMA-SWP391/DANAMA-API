
package model;

import java.sql.Time;
import java.util.Date;

public class Showtime {
    private int showtimeId;
    private Date showDate;
    private Time startTime;
    private Time endTime;
    private double basePrice;
    private int movieId;
    private int roomId;
    private int seatAvailable;

    // Default constructor
    public Showtime() {
    }

    // Parameterized constructor
    public Showtime(int showtimeId, Date showDate, Time startTime, Time endTime, double basePrice, int movieId, int roomId, int seatAvailable) {
        this.showtimeId = showtimeId;
        this.showDate = showDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.basePrice = basePrice;
        this.movieId = movieId;
        this.roomId = roomId;
        this.seatAvailable = seatAvailable;
    }

    // Getters and Setters
    public int getShowtimeId() {
        return showtimeId;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getSeatAvailable() {
        return seatAvailable;
    }

    public void setSeatAvailable(int seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

    @Override
    public String toString() {
        return "Showtime{" + "showtimeId=" + showtimeId + ", showDate=" + showDate + ", startTime=" + startTime + ", endTime=" + endTime + ", basePrice=" + basePrice + ", movieId=" + movieId + ", roomId=" + roomId + ", seatAvailable=" + seatAvailable + '}';
    }

}

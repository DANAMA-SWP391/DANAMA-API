
package model;

import java.sql.Time;
import java.util.Date;

public class Showtime {
    private int showtimeId;
    private Date showDate;
    private Time startTime;
    private Time endTime;
    private double basePrice;
    private Movie movie;
    private Room room;
    private int seatAvailable;
    private int status;

    // Default constructor
    public Showtime() {
    }

    // Parameterized constructor
    public Showtime(int showtimeId, Date showDate, Time startTime, Time endTime, double basePrice, Movie movie, Room room, int seatAvailable, int status) {
        this.showtimeId = showtimeId;
        this.showDate = showDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.basePrice = basePrice;
        this.movie = movie;
        this.room = room;
        this.seatAvailable = seatAvailable;
        this.status = status;
    }

    // Getters and Setters
    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getSeatAvailable() {
        return seatAvailable;
    }

    public void setSeatAvailable(int seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Showtime{" +
                "basePrice=" + basePrice +
                ", showtimeId=" + showtimeId +
                ", showDate=" + showDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", movie=" + movie +
                ", room=" + room +
                ", seatAvailable=" + seatAvailable +
                ", status=" + status +
                '}';
    }

}

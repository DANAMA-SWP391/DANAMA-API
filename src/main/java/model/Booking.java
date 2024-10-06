
package model;
import java.util.Date;

public class Booking {
    private int bookingId;
    private double totalCost;
    private Date timestamp;
    private Account user;
    private int status;
    // Default constructor
    public Booking() {
    }

    // Parameterized constructor
    public Booking(int bookingId, double totalCost, Date timestamp, Account user, int status) {
        this.bookingId = bookingId;
        this.totalCost = totalCost;
        this.timestamp = timestamp;
        this.user = user;
        this.status = status;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", totalCost=" + totalCost +
                ", timestamp=" + timestamp +
                ", userId=" + user.getUID() +
                ", status=" + status +
                '}';
    }
}

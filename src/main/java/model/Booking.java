
package model;
import java.util.Date;

public class Booking {
    private int bookingId;
    private double totalCost;
    private Date timestamp;
    private int UID;

    // Default constructor
    public Booking() {
    }

    // Parameterized constructor
    public Booking(int bookingId, double totalCost, Date timestamp, int UID) {
        this.bookingId = bookingId;
        this.totalCost = totalCost;
        this.timestamp = timestamp;
        this.UID = UID;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
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

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    @Override
    public String toString() {
        return "Booking{" + "bookingId=" + bookingId + ", totalCost=" + totalCost + ", timestamp=" + timestamp + ", UID=" + UID + '}';
    }
}

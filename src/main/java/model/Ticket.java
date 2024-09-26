
package model;

public class Ticket {
    private int ticketId;
    private double price;
    private String name;
    private String email;
    private String phone;
    private int bookingId;
    private int showtimeId;
    private int seatId;

    // Default constructor
    public Ticket() {
    }

    // Parameterized constructor
    public Ticket(int ticketId, double price, String name, String email, String phone, int bookingId, int showtimeId, int seatId) {
        this.ticketId = ticketId;
        this.price = price;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bookingId = bookingId;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
    }

    // Getters and Setters
    public int getTicketId() {
        return ticketId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    // toString method
    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", bookingId=" + bookingId +
                ", showtimeId=" + showtimeId +
                ", seatId=" + seatId +
                '}';
    }
}

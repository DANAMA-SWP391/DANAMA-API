
package model;

public class Ticket {
    private int ticketId;
    private double price;
    private String name;
    private String email;
    private String phone;
    private Booking booking;
    private Showtime showtime;
    private Seat seat;

    // Default constructor
    public Ticket() {
    }

    // Parameterized constructor
    public Ticket(int ticketId, double price, String name, String email, String phone, Booking booking, Showtime showtime, Seat seat) {
        this.ticketId = ticketId;
        this.price = price;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.booking = booking;
        this.showtime = showtime;
        this.seat = seat;
    }

    // Getters and Setters
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
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

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "booking=" + booking +
                ", ticketId=" + ticketId +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", showtime=" + showtime +
                ", seat=" + seat +
                '}';
    }
}

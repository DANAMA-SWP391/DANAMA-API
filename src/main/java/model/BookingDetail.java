package model;

public class BookingDetail {
    private int bookingId;
    private String bookingDate;
    private String userEmail;
    private double totalCost;
    private String filmName;
    private String moviePoster;
    private String showtimeDate;
    private String showtimeStart;
    private String showtimeEnd;
    private String roomName;
    private String seatNumbers;  // Now a single String for all seat numbers
    private String cinemaName;
    private String cinemaAddress;
    private String cinemaLogo;

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaLogo() {
        return cinemaLogo;
    }

    public void setCinemaLogo(String cinemaLogo) {
        this.cinemaLogo = cinemaLogo;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(String seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public String getShowtimeDate() {
        return showtimeDate;
    }

    public void setShowtimeDate(String showtimeDate) {
        this.showtimeDate = showtimeDate;
    }

    public String getShowtimeEnd() {
        return showtimeEnd;
    }

    public void setShowtimeEnd(String showtimeEnd) {
        this.showtimeEnd = showtimeEnd;
    }

    public String getShowtimeStart() {
        return showtimeStart;
    }

    public void setShowtimeStart(String showtimeStart) {
        this.showtimeStart = showtimeStart;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "BookingDetail{" +
                "bookingDate='" + bookingDate + '\'' +
                ", bookingId=" + bookingId +
                ", userEmail='" + userEmail + '\'' +
                ", totalCost=" + totalCost +
                ", filmName='" + filmName + '\'' +
                ", moviePoster='" + moviePoster + '\'' +
                ", showtimeDate='" + showtimeDate + '\'' +
                ", showtimeStart='" + showtimeStart + '\'' +
                ", showtimeEnd='" + showtimeEnd + '\'' +
                ", roomName='" + roomName + '\'' +
                ", seatNumbers='" + seatNumbers + '\'' +
                ", cinemaName='" + cinemaName + '\'' +
                ", cinemaAddress='" + cinemaAddress + '\'' +
                ", cinemaLogo='" + cinemaLogo + '\'' +
                '}';
    }
// Getters and Setters
    // (Omitted here for brevity, but ensure you have them in your actual class)
}


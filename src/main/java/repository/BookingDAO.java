
package repository;

import context.DBContext;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookingDAO extends DBContext {
    public Booking getBookingById (int bookingId) {
        return null;
    }
    public ArrayList<Booking> getBookingHistory(int userId) {
        return null;
    }
    public ArrayList<Booking> getListBookingByCinema(int cinemaId) {
        return null;
    }
    public boolean addBooking(Booking booking) {
        return true;
    }
    public boolean paymentConfirm(Booking booking) {
        return true;
    }

    public static void main(String[] args) {

    }
}

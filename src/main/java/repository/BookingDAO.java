/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import context.DBContext;
import model.Booking;

import java.util.ArrayList;

public class BookingDAO extends DBContext{
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

}

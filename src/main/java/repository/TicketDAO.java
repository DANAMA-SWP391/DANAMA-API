package repository;

import model.Ticket;

import java.util.ArrayList;

public class TicketDAO {
    public ArrayList<Ticket> getTicketByBooking(int bookingId) {
        return null;
    }
    public boolean addTicket(Ticket ticket) {
        return true;
    }
    public ArrayList<Ticket> getTicketListOfShowtime(int showtimeId) {
        return null;
    }
    public int getTicketSoldInCurrentMonth(int cinemaId) {
        return 0;
    }
    public int getAverageTicketSoldInMonth(int cinemaId) {
        return 0;
    }

}

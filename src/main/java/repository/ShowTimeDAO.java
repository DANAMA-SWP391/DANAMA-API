
package repository;

import context.DBContext;
import model.Showtime;

import java.util.ArrayList;

public class ShowTimeDAO extends DBContext{
    public ArrayList<Showtime> getListShowtimes() {
        ArrayList<Showtime> listShowtimes = new ArrayList<>();
        return  listShowtimes;
    }
    public Showtime getShowtimeByMovie(int movieId) {
        return null;
    }


}

package listener;

import context.DBContext;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class MovieCleanupTask extends DBContext implements Runnable {

    @Override
    public void run() {
        try {
            Date today = new Date(Calendar.getInstance().getTimeInMillis());

            // Update movies to status 1 (Now Playing) if they have any active showtimes, excluding movies with status = 3
            String updateNowPlayingQuery = """
                UPDATE Movie
                SET status = 1
                WHERE movieId IN (
                    SELECT DISTINCT movieId FROM Showtime WHERE status = 1
                ) AND status != 3
            """;
            try (PreparedStatement nowPlayingStatement = connection.prepareStatement(updateNowPlayingQuery)) {
                int nowPlayingRows = nowPlayingStatement.executeUpdate();
                System.out.println("Movies set to 'Now Playing'. Rows affected: " + nowPlayingRows);
            }

            // Update movies to status 2 (Coming Soon) if releaseDate is in the future and they have no active showtimes, excluding status = 3
            String updateComingSoonQuery = """
                UPDATE Movie
                SET status = 2
                WHERE releaseDate > ? AND movieId NOT IN (
                    SELECT DISTINCT movieId FROM Showtime WHERE status = 1
                ) AND status != 3
            """;
            try (PreparedStatement comingSoonStatement = connection.prepareStatement(updateComingSoonQuery)) {
                comingSoonStatement.setDate(1, today);
                int comingSoonRows = comingSoonStatement.executeUpdate();
                System.out.println("Movies set to 'Coming Soon'. Rows affected: " + comingSoonRows);
            }

            // Update movies to status 0 (Inactive) if releaseDate is in the past, have no active showtimes, excluding status = 3
            String updateInactiveQuery = """
                UPDATE Movie
                SET status = 0
                WHERE releaseDate <= ? AND movieId NOT IN (
                    SELECT DISTINCT movieId FROM Showtime WHERE status = 1
                ) AND status != 3
            """;
            try (PreparedStatement inactiveStatement = connection.prepareStatement(updateInactiveQuery)) {
                inactiveStatement.setDate(1, today);
                int inactiveRows = inactiveStatement.executeUpdate();
                System.out.println("Movies set to 'Inactive'. Rows affected: " + inactiveRows);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

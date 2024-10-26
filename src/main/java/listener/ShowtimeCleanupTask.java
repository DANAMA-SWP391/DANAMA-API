package listener;

import context.DBContext;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class ShowtimeCleanupTask extends DBContext implements Runnable {

    @Override
    public void run() {
        try {

            String updateQuery = "UPDATE Showtime SET status = 0 WHERE showdate < ? AND status != 0";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

            Date today = new Date(Calendar.getInstance().getTimeInMillis());
            updateStatement.setDate(1, today);

            int affectedRows = updateStatement.executeUpdate();
            System.out.println("Outdated showtimes updated. Rows affected: " + affectedRows);
            updateStatement.close();

            String deleteQuery = """
                        DELETE FROM Showtime
                        WHERE status = 0
                        AND showtimeId NOT IN (SELECT DISTINCT showtimeId FROM Ticket)
                    """;
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);

            int deletedRows = deleteStatement.executeUpdate();
            System.out.println("Empty showtimes deleted. Rows affected: " + deletedRows);
            deleteStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
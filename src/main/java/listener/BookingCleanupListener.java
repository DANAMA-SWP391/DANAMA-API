package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BookingCleanupListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new BookingCleanupTask(), 0, 1, TimeUnit.MINUTES); // Check every minute
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
}


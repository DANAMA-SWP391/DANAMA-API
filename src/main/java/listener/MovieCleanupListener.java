package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MovieCleanupListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Schedule the MovieCleanupTask to run every 24 hours
        scheduler.scheduleAtFixedRate(new MovieCleanupTask(), 0, 12, TimeUnit.HOURS); // Runs daily
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        if (scheduler != null) {
            scheduler.shutdownNow();
            System.out.println("MovieCleanupListener stopped.");
        }
    }
}


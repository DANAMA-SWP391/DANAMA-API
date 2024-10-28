package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ShowtimeCleanupListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Schedule the ShowtimeCleanupTask to run every 24 hours
        scheduler.scheduleAtFixedRate(new ShowtimeCleanupTask(), 0, 30, TimeUnit.MINUTES); // Daily check
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
        System.out.println("ShowtimeCleanupListener stopped.");
    }
}

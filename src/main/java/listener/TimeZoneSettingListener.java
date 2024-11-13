package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.TimeZone;

@WebListener
public class TimeZoneSettingListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Set default time zone when the application context is initialized
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        System.out.println("Default Time Zone set to Asia/Ho_Chi_Minh");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Optional cleanup logic when the application context is destroyed
    }
}

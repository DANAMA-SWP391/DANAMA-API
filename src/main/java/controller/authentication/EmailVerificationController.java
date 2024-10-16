package controller.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Properties;


@WebServlet(name = "EmailVerificationController", value = "/emailVerify")
public class EmailVerificationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        JsonObject data= gson.fromJson(request.getReader(), JsonObject.class);
        String recipientEmail = data.get("email").getAsString();
        String verificationCode = generateVerificationCode();
        new Thread(() -> {
            try {
                sendEmail(recipientEmail, verificationCode);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
        data.addProperty("code",verificationCode);

        response.getWriter().write(gson.toJson(data));
        response.getWriter().flush();
    }

    private String generateVerificationCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    private void sendEmail(String recipientEmail, String verificationCode)
            throws MessagingException {
        final String SMTP_SERVER = "smtp.gmail.com";
        final String SMTP_PORT = "587";
        final String GMAIL_USERNAME = "danamawebsite@gmail.com";
        final String GMAIL_APP_PASSWORD = "hlhxdsjjkfkzphyc";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_SERVER);
        properties.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(GMAIL_USERNAME, GMAIL_APP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(GMAIL_USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Your Verification Code");
        message.setText("Your verification code is: " + verificationCode);

        Transport.send(message);
    }
}
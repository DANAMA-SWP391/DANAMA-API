package controller.user;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BookingDetail;
import repository.BookingDAO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@WebServlet(name = "BookingConfirmController", value = "/sendConfirmEmail")
public class BookingConfirmController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        // Retrieve BookingDetail using BookingDAO
        BookingDAO bookingDAO = new BookingDAO();
        BookingDetail bookingDetail = bookingDAO.getBookingDetailById(bookingId);

        if (bookingDetail == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("<html><body><script>alert('Booking not found');window.close();</script></body></html>");
            return;
        }

        // Generate email content
        String recipientEmail = bookingDetail.getUserEmail();
        String emailContent = generateEmailContent(bookingDetail);

        // Send email in a new thread to avoid blocking the servlet
        new Thread(() -> {
            try {
                sendEmail(recipientEmail, emailContent);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    // Method to generate HTML content for the email using BookingDetail
    private String generateEmailContent(BookingDetail bookingDetail) {
        // Define the input format (assumes the input format is "HH:mm:ss.SSSSSSS" or similar)
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss.SSSSSSS");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");

        // Format showtimeStart and showtimeEnd to "HH:mm" format
        String formattedShowtimeStart = bookingDetail.getShowtimeStart();
        String formattedShowtimeEnd = bookingDetail.getShowtimeEnd();

        try {
            // Parse the input strings and format them to "HH:mm"
            Date startDate = inputFormat.parse(bookingDetail.getShowtimeStart());
            Date endDate = inputFormat.parse(bookingDetail.getShowtimeEnd());
            formattedShowtimeStart = outputFormat.format(startDate);
            formattedShowtimeEnd = outputFormat.format(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            // If parsing fails, use the original string values
        }

        return "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #333;'>Booking Information</h2>" +
                "<p><strong>Booking details:</strong></p>" +
                "<p><strong>ID:</strong> #" + bookingDetail.getBookingId() + "</p>" +
                "<p><strong>Date:</strong> " + bookingDetail.getBookingDate() + "</p>" +
                "<p><strong>User Email:</strong> <a href='mailto:" + bookingDetail.getUserEmail() + "'>" + bookingDetail.getUserEmail() + "</a></p>" +
                "<p><strong>Total Cost:</strong> " + bookingDetail.getTotalCost() + "VND</p>" +
                "<hr style='border: 0; border-top: 1px solid #ccc;' />" +

                "<table style='width: 100%;'>" +
                "  <tr>" +
                "    <td style='width: 50%; padding-right: 15px;'>" +
                "      <h3 style='margin: 0; color: #555;'>Film Details</h3>" +
                "      <p><strong>Film name:</strong> " + bookingDetail.getFilmName() + "</p>" +
                "      <p><strong>Showtime:</strong> " + formattedShowtimeStart + " ~ " + formattedShowtimeEnd + "</p>" +
                "      <p><strong>Showtime Date:</strong> " + bookingDetail.getShowtimeDate() + "</p>" +
                "      <p><strong>Room:</strong> " + bookingDetail.getRoomName() + "</p>" +
                "      <p><strong>Seat(s):</strong> " + bookingDetail.getSeatNumbers() + "</p>" +
                "      <p><strong>Price:</strong> " + bookingDetail.getTotalCost() + "VND</p>" +
                "    </td>" +
                "    <td style='width: 50%; text-align: center;'>" +
                "      <img src='" + bookingDetail.getMoviePoster() + "' alt='Movie Poster' style='width: 100px; border: 1px solid #ddd; padding: 5px;'>" +
                "    </td>" +
                "  </tr>" +
                "</table>" +

                "<hr style='border: 0; border-top: 1px solid #ccc;' />" +

                "<table style='width: 100%;'>" +
                "  <tr>" +
                "    <td style='width: 50%; padding-right: 15px;'>" +
                "      <h3 style='margin: 0; color: #555;'>Cinema Details</h3>" +
                "      <p><strong>Cinema:</strong> " + bookingDetail.getCinemaName() + "</p>" +
                "      <p><strong>Address:</strong> " + bookingDetail.getCinemaAddress() + "</p>" +
                "    </td>" +
                "    <td style='width: 50%; text-align: center;'>" +
                "      <img src='" + bookingDetail.getCinemaLogo() + "' alt='Cinema Logo' style='width: 100px; border: 1px solid #ddd; padding: 5px;'>" +
                "    </td>" +
                "  </tr>" +
                "</table>" +

                "<hr style='border: 0; border-top: 1px solid #ccc;' />" +

                "<p style='text-align: center; color: #555; font-size: 14px;'>Thank you for booking with <strong>DANAMA</strong>! We hope you enjoy your movie experience.</p>" +

                "</body>" +
                "</html>";
    }


    // Method to send email
    private void sendEmail(String recipientEmail, String emailContent) throws MessagingException {
        // Configure email properties
        String from = "danamawebsite@gmail.com";
        String host = "smtp.gmail.com"; // SMTP server
        final String GMAIL_USERNAME = "danamawebsite@gmail.com";
        final String GMAIL_APP_PASSWORD = "hlhxdsjjkfkzphyc";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Setup the Session and authenticate
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(GMAIL_USERNAME, GMAIL_APP_PASSWORD);
            }
        });

        // Create and configure the message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject("Booking Confirmation");
        message.setContent(emailContent, "text/html; charset=utf-8");

        // Send the email
        Transport.send(message);
    }
}
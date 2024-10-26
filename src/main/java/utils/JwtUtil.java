package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "WCoNLv3MOxJh9tExjnW5hXFlp-OXjj1-ifFqycO1HB8="; // Use a strong secret key

    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Method to generate JWT token
    public static String generateToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 60 * 60 * 1000);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
    }

    // Method to validate and parse JWT token
    public static Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSecretKey()).build()
                    .parseClaimsJws(token)
                    .getBody(); // Extract claims (data) from the token
        } catch (Exception e) {
            return null; // Return null if the token is invalid or expired
        }
    }

    // Method to check if token is within 30 minutes of expiration
    public static boolean isTokenExpired(Claims claims) {
        // Calculate the time 30 minutes before the expiration
        Date thirtyMinutesBeforeExpiry = new Date(claims.getExpiration().getTime() - 30 * 60 * 1000);
        // Check if the current time is after this calculated time
        return new Date().after(thirtyMinutesBeforeExpiry);
    }


    public static void main(String[] args) {
        // Test case: Generating a token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaWFuZ3F0Mms0QGdtYWlsLmNvbSIsImlhdCI6MTcyOTkzNjIwNywiZXhwIjoxNzI5OTM4MTI3fQ.vkOeloe2b7s9dn35bBZjQWND3F1BSduXH1sa2dFpgnE";
        // Test case: Validating the token
        Claims claims = JwtUtil.validateToken(token);
        if (claims != null) {
            System.out.println("Token is valid.");
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issued at: " + claims.getIssuedAt());
            System.out.println("Expiration: " + claims.getExpiration());

            // Test case: Checking if the token is expired
            boolean isExpired = JwtUtil.isTokenExpired(claims);
            System.out.println("Is token expired? " + isExpired);
        } else {
            System.out.println("Token is invalid or expired.");
        }
    }
}

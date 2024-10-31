<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thank You</title>
    <style>
        @font-face {
            font-family: 'Mogra';
            src: url('../assets/Mogra-Regular.ttf') format('truetype');
        }
        /* Reset and universal styles */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-size: 16px;
        }

        /* Centering container */
        html, body {
            width: 100%;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            font-synthesis: none;
            text-rendering: optimizeLegibility;
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;
        }

        .content {
            display: inline-block;
        }

        /* Logo styles */
        .logo {
            font-size: 40px;
            font-family: 'Mogra', sans-serif;
            color: #BA1F1F;
            margin-bottom: 10px;
        }

        /* Message styles */
        .message-line {
            font-size: 1.2em;
            color: #333;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="content">
    <!-- Website Logo -->
    <h1 class="logo">DANAMA</h1>

    <!-- Thank You Message in 3 lines -->
    <div class="message-line">Thank You for Your Booking!</div>
    <div class="message-line">Your booking confirmation email has been sent.</div>
    <div class="message-line">You can now close this tab.</div>
</div>
</body>
</html>

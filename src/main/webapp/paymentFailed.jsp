<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Payment Failed</title>
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
            font-family: 'Albert Sans regular', sans-serif;
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
            color: #D9534F; /* Red color for error */
            margin-top: 10px;
        }

    </style>
</head>
<body>
<div class="content">
    <!-- Website Logo -->
    <h1 class="logo">DANAMA</h1>

    <!-- Payment Failed Message in 3 lines -->
    <div class="message-line">Payment Failed</div>
    <div class="message-line">We’re sorry, but there was an error processing your payment.</div>
    <div class="message-line">Please try again or contact support if the issue persists.</div>
</div>
</body>
</html>

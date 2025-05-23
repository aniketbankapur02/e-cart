<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Internal Server Error - E-Cart</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .error-container {
            max-width: 600px;
            padding: 40px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: center;
        }
        h1 {
            color: #dc3545;
            font-size: 48px;
            margin: 0;
        }
        .error-code {
            font-size: 24px;
            color: #666;
            margin: 20px 0;
        }
        .error-message {
            color: #333;
            margin: 20px 0;
        }
        .error-details {
            margin: 20px 0;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 4px;
            color: #666;
            font-size: 14px;
            text-align: left;
            overflow-wrap: break-word;
        }
        .btn {
            display: inline-block;
            padding: 12px 24px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 20px;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>500</h1>
        <div class="error-code">Internal Server Error</div>
        <div class="error-message">
            Sorry, something went wrong on our end. Please try again later or contact support if the problem persists.
        </div>
        <% if (exception != null && request.getParameter("debug") != null) { %>
            <div class="error-details">
                Error Details: <%= exception.getMessage() %>
            </div>
        <% } %>
        <a href="/" class="btn">Go to Homepage</a>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Cart - Registration Success</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: center;
        }
        h1 {
            color: #28a745;
            margin-bottom: 20px;
        }
        .success-message {
            color: #28a745;
            font-size: 18px;
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #28a745;
            border-radius: 4px;
            background-color: #f8fff9;
        }
        .customer-id {
            font-size: 24px;
            color: #333;
            margin: 20px 0;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 4px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 20px;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .note {
            margin-top: 20px;
            color: #666;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Registration Successful!</h1>
        
        <div class="success-message">
            <%= request.getAttribute("success") %>
        </div>

        <div class="customer-id">
            Your Customer ID: <strong><%= request.getAttribute("userId") %></strong>
        </div>

        <div class="note">
            Please save your Customer ID as it will be required for logging in.
        </div>

        <a href="login" class="btn">Proceed to Login</a>
    </div>
</body>
</html>

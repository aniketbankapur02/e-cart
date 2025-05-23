<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ecart.model.OrderCancellation, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Cart - Order Cancellation Success</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        
        .header {
            background-color: #fff;
            padding: 15px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            max-width: 1200px;
            margin: 0 auto;
        }
        
        .welcome {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .menu {
            display: flex;
            gap: 20px;
        }
        
        .menu a {
            text-decoration: none;
            color: #333;
            padding: 5px 10px;
            border-radius: 4px;
        }
        
        .menu a:hover {
            background-color: #f0f0f0;
        }
        
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        .success-message {
            text-align: center;
            margin-bottom: 30px;
            padding: 20px;
            background-color: #d4edda;
            border-radius: 4px;
            color: #155724;
        }
        
        .success-icon {
            font-size: 48px;
            color: #28a745;
            margin-bottom: 10px;
        }
        
        .cancellation-details {
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 20px;
            margin-bottom: 20px;
        }
        
        .refund-notice {
            background-color: #cce5ff;
            border: 1px solid #b8daff;
            color: #004085;
            padding: 15px;
            border-radius: 4px;
            margin: 20px 0;
            text-align: center;
        }
        
        .btn {
            display: inline-block;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            text-decoration: none;
            background-color: #007bff;
            color: white;
        }
        
        .btn:hover {
            background-color: #0056b3;
        }
        
        .details-row {
            margin-bottom: 10px;
        }
        
        .details-row strong {
            display: inline-block;
            width: 150px;
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="top-bar">
            <div class="welcome">
                <span>Welcome, <%= session.getAttribute("userName") %>!</span>
            </div>
            <div class="menu">
                <a href="home">Home</a>
                <a href="cart">My Cart</a>
                <a href="orders">My Orders</a>
                <a href="logout">Logout</a>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="success-message">
            <div class="success-icon">✓</div>
            <h2>Order Cancelled Successfully</h2>
            <p>Your order has been cancelled and will be processed for refund.</p>
        </div>

        <% 
        OrderCancellation cancellation = (OrderCancellation) request.getAttribute("cancellation");
        if(cancellation != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        %>
            <div class="cancellation-details">
                <h3>Cancellation Details</h3>
                <div class="details-row">
                    <strong>Order ID:</strong> <%= cancellation.getOrderId() %>
                </div>
                <div class="details-row">
                    <strong>Cancellation Date:</strong> <%= sdf.format(cancellation.getCancellationDate()) %>
                </div>
                <div class="details-row">
                    <strong>Refund Amount:</strong> ₹<%= cancellation.getRefundAmount() %>
                </div>
                <div class="details-row">
                    <strong>Reason:</strong> <%= cancellation.getReason() %>
                </div>
            </div>

            <div class="refund-notice">
                <h4>Refund Information</h4>
                <p>The amount of ₹<%= cancellation.getRefundAmount() %> will be refunded to your account within 5 working days.</p>
            </div>
        <% } %>

        <div style="text-align: center; margin-top: 20px;">
            <a href="orders" class="btn">Back to My Orders</a>
        </div>
    </div>
</body>
</html>

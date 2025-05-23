<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ecart.model.Order, com.ecart.model.CartItem, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Cart - Confirm Order Cancellation</title>
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
        
        .order-summary {
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 20px;
            margin-bottom: 20px;
        }
        
        .item-list {
            list-style: none;
            padding: 0;
        }
        
        .item-list li {
            padding: 10px;
            border-bottom: 1px solid #eee;
        }
        
        .item-list li:last-child {
            border-bottom: none;
        }
        
        .confirm-section {
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
            padding: 20px;
            border-radius: 4px;
            margin-bottom: 20px;
            text-align: center;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            resize: vertical;
            min-height: 100px;
            box-sizing: border-box;
        }
        
        .btn {
            display: inline-block;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            text-decoration: none;
        }
        
        .btn-confirm {
            background-color: #dc3545;
            color: white;
            margin-right: 10px;
        }
        
        .btn-confirm:hover {
            background-color: #c82333;
        }
        
        .btn-back {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-back:hover {
            background-color: #545b62;
        }
        
        .error {
            color: #dc3545;
            background-color: #f8d7da;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        
        .total {
            text-align: right;
            font-weight: bold;
            font-size: 18px;
            color: #007bff;
            margin-top: 10px;
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
        <h1>Confirm Order Cancellation</h1>

        <% if(request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>

        <% 
        Order order = (Order) request.getAttribute("orderToCancel");
        if(order != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        %>
            <div class="confirm-section">
                <h3>Are you sure you want to cancel your order?</h3>
                <p>Order ID: <%= order.getOrderId() %></p>
            </div>

            <div class="order-summary">
                <h2>Order Details</h2>
                <p><strong>Order Date:</strong> <%= sdf.format(order.getOrderDate()) %></p>
                
                <h3>Items:</h3>
                <ul class="item-list">
                    <% for(CartItem item : order.getItems()) { %>
                        <li>
                            <%= item.getProductName() %> 
                            (₹<%= item.getProductPrice() %> x <%= item.getQuantity() %> = 
                            ₹<%= item.getSubtotal() %>)
                        </li>
                    <% } %>
                </ul>
                
                <div class="total">
                    Total Amount: ₹<%= order.getTotalAmount() %>
                </div>
            </div>

            <form action="orders/cancel" method="post">
                <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                <input type="hidden" name="confirm" value="yes">
                
                <div class="form-group">
                    <label for="reason">Cancellation Reason <span style="color: red;">*</span></label>
                    <textarea id="reason" name="reason" required 
                            placeholder="Please provide a reason for cancelling the order"></textarea>
                </div>

                <div style="text-align: center;">
                    <button type="submit" class="btn btn-confirm">Confirm Cancellation</button>
                    <a href="orders" class="btn btn-back">Back to Orders</a>
                </div>
            </form>
        <% } else { %>
            <div class="error">
                <p>Order not found. Please try again.</p>
                <p><a href="orders" class="btn btn-back">Back to Orders</a></p>
            </div>
        <% } %>
    </div>
</body>
</html>

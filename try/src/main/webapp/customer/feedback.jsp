<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ecart.model.Order, com.ecart.model.Feedback, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Cart - Order Feedback</title>
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
        
        .order-details {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        
        .form-group {
            margin-bottom: 20px;
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
            box-sizing: border-box;
            min-height: 100px;
            resize: vertical;
        }
        
        .rating-group {
            display: flex;
            flex-direction: row-reverse;
            justify-content: flex-end;
            gap: 10px;
            margin-bottom: 20px;
        }
        
        .rating-group input[type="radio"] {
            display: none;
        }
        
        .rating-group label {
            cursor: pointer;
            font-size: 30px;
            color: #ddd;
        }
        
        .rating-group label:hover,
        .rating-group label:hover ~ label,
        .rating-group input[type="radio"]:checked ~ label {
            color: #ffd700;
        }
        
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-right: 10px;
        }
        
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #0056b3;
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #545b62;
        }
        
        .error {
            color: #dc3545;
            background-color: #f8d7da;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        
        .success {
            color: #155724;
            background-color: #d4edda;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
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
        <h1>Order Feedback</h1>

        <% if(request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>

        <% if(request.getAttribute("success") != null) { %>
            <div class="success"><%= request.getAttribute("success") %></div>
        <% } %>

        <% 
        Order order = (Order) request.getAttribute("order");
        Feedback feedback = (Feedback) request.getAttribute("feedback");
        if(order != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        %>
            <div class="order-details">
                <h3>Order Details</h3>
                <p><strong>Order ID:</strong> <%= order.getOrderId() %></p>
                <p><strong>Order Date:</strong> <%= sdf.format(order.getOrderDate()) %></p>
                <p><strong>Total Amount:</strong> ₹<%= order.getTotalAmount() %></p>
            </div>

            <form action="feedback" method="post">
                <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">

                <div class="form-group">
                    <label>Rating <span style="color: red;">*</span></label>
                    <div class="rating-group">
                        <% for(int i = 5; i >= 1; i--) { %>
                            <input type="radio" name="rating" value="<%= i %>" id="star<%= i %>" 
                                   <%= (feedback != null && feedback.getRating() == i) ? "checked" : "" %> required>
                            <label for="star<%= i %>">★</label>
                        <% } %>
                    </div>
                </div>

                <div class="form-group">
                    <label for="description">Feedback Description <span style="color: red;">*</span></label>
                    <textarea id="description" name="description" required
                            placeholder="Please share your experience with this order"><%= feedback != null ? feedback.getDescription() : "" %></textarea>
                </div>

                <div>
                    <button type="submit" class="btn btn-primary">
                        <%= feedback != null ? "Update Feedback" : "Submit Feedback" %>
                    </button>
                    <a href="orders" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        <% } else { %>
            <div class="error">
                <p>Order not found. Please try again.</p>
                <a href="orders" class="btn btn-secondary">Back to Orders</a>
            </div>
        <% } %>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ecart.model.Order, com.ecart.model.OrderStatus, com.ecart.model.CartItem, java.util.List, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Cart - My Orders</title>
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
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        .filter-section {
            margin-bottom: 20px;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 4px;
        }
        
        .filter-section select {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-right: 10px;
        }
        
        .filter-section button {
            padding: 8px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        
        .filter-section button:hover {
            background-color: #0056b3;
        }
        
        .order-card {
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 20px;
            padding: 15px;
        }
        
        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        
        .order-status {
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: bold;
        }
        
        .status-confirmed {
            background-color: #cce5ff;
            color: #004085;
        }
        
        .status-in-transit {
            background-color: #fff3cd;
            color: #856404;
        }
        
        .status-delivered {
            background-color: #d4edda;
            color: #155724;
        }
        
        .status-cancelled {
            background-color: #f8d7da;
            color: #721c24;
        }
        
        .order-items {
            margin-top: 15px;
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
        
        .order-total {
            text-align: right;
            font-weight: bold;
            margin-top: 15px;
            font-size: 18px;
            color: #007bff;
        }
        
        .cancel-btn {
            padding: 8px 15px;
            background-color: #dc3545;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
        }
        
        .cancel-btn:hover {
            background-color: #c82333;
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
        <h1>My Orders</h1>

        <% if(request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>

        <% if(request.getAttribute("success") != null) { %>
            <div class="success"><%= request.getAttribute("success") %></div>
        <% } %>

        <div class="filter-section">
            <form action="orders" method="get">
                <select name="status">
                    <option value="">All Orders</option>
                    <% 
                    OrderStatus[] statuses = OrderStatus.values();
                    String selectedStatus = (String) request.getAttribute("selectedStatus");
                    for(OrderStatus status : statuses) {
                    %>
                        <option value="<%= status.name() %>" 
                                <%= (status.name().equals(selectedStatus) ? "selected" : "") %>>
                            <%= status.getDisplayName() %>
                        </option>
                    <% } %>
                </select>
                <button type="submit">Filter</button>
            </form>
        </div>

        <% 
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        if(orders != null && !orders.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            for(Order order : orders) {
        %>
            <div class="order-card">
                <div class="order-header">
                    <div>
                        <h3>Order ID: <%= order.getOrderId() %></h3>
                        <p>Date: <%= sdf.format(order.getOrderDate()) %></p>
                    </div>
                    <span class="order-status status-<%= order.getStatus().name().toLowerCase() %>">
                        <%= order.getStatus().getDisplayName() %>
                    </span>
                </div>

                <div class="order-items">
                    <h4>Items:</h4>
                    <ul class="item-list">
                        <% for(CartItem item : order.getItems()) { %>
                            <li>
                                <%= item.getProductName() %> 
                                (₹<%= item.getProductPrice() %> x <%= item.getQuantity() %> = 
                                ₹<%= item.getSubtotal() %>)
                            </li>
                        <% } %>
                    </ul>
                </div>

                <div class="order-total">
                    Total Amount: ₹<%= order.getTotalAmount() %>
                </div>

                <% if(order.getStatus() == OrderStatus.CONFIRMED) { %>
                    <div style="margin-top: 15px;">
                        <form action="orders/cancel" method="post" style="display: inline;">
                            <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                            <button type="submit" class="cancel-btn">Cancel Order</button>
                        </form>
                    </div>
                <% } %>
            </div>
        <% 
            }
        } else {
        %>
            <div style="text-align: center; padding: 20px;">
                <p>No orders found.</p>
            </div>
        <% } %>
    </div>
</body>
</html>

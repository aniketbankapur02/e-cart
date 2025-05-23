<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ecart.model.CartItem, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Cart - My Cart</title>
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
        
        .profile-icon {
            font-size: 24px;
            color: #007bff;
            text-decoration: none;
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
            padding: 0 15px;
        }
        
        .cart-table {
            width: 100%;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        
        .cart-table th, 
        .cart-table td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        
        .cart-table th {
            background-color: #f8f9fa;
            font-weight: bold;
        }
        
        .quantity-input {
            width: 60px;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        
        .btn {
            padding: 8px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        
        .update-btn {
            background-color: #28a745;
            color: white;
        }
        
        .update-btn:hover {
            background-color: #218838;
        }
        
        .delete-btn {
            background-color: #ffc107;
            color: #000;
        }
        
        .delete-btn:hover {
            background-color: #e0a800;
        }
        
        .remove-btn {
            background-color: #dc3545;
            color: white;
        }
        
        .remove-btn:hover {
            background-color: #c82333;
        }
        
        .cart-summary {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: right;
        }
        
        .total {
            font-size: 24px;
            color: #007bff;
            margin-bottom: 20px;
        }
        
        .checkout-btn {
            background-color: #007bff;
            color: white;
            padding: 12px 24px;
            font-size: 16px;
            text-decoration: none;
            display: inline-block;
        }
        
        .checkout-btn:hover {
            background-color: #0056b3;
        }
        
        .empty-cart {
            text-align: center;
            padding: 40px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        .error {
            color: #dc3545;
            background-color: #f8d7da;
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
                <a href="profile" class="profile-icon">👤</a>
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
        <% if(request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>

        <% 
        List<CartItem> cartItems = (List<CartItem>)request.getAttribute("cartItems");
        if(cartItems == null || cartItems.isEmpty()) { 
        %>
            <div class="empty-cart">
                <h2>Your Cart is Empty</h2>
                <p>Cart is empty, please add products to check out</p>
                <a href="home" class="btn checkout-btn">Continue Shopping</a>
            </div>
        <% } else { %>
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(CartItem item : cartItems) { %>
                        <tr>
                            <td><%= item.getProductId() %></td>
                            <td><%= item.getProductName() %></td>
                            <td>₹<%= item.getProductPrice() %></td>
                            <td>
                                <form action="cart/update" method="post" style="display: inline;">
                                    <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
                                    <input type="number" name="quantity" value="<%= item.getQuantity() %>" 
                                           min="1" class="quantity-input">
                                    <button type="submit" class="btn update-btn">Update</button>
                                </form>
                            </td>
                            <td>₹<%= item.getSubtotal() %></td>
                            <td>
                                <form action="cart/delete" method="post" style="display: inline;">
                                    <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
                                    <button type="submit" class="btn delete-btn">Delete</button>
                                </form>
                                <form action="cart/remove" method="post" style="display: inline;">
                                    <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
                                    <button type="submit" class="btn remove-btn">Remove</button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <div class="cart-summary">
                <div class="total">Total: ₹<%= request.getAttribute("cartTotal") %></div>
                <a href="checkout" class="btn checkout-btn">Proceed to Buy</a>
            </div>
        <% } %>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ecart.model.Invoice, com.ecart.model.CartItem, java.util.List, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Cart - Payment Success</title>
    <style>
        /* Keep existing styles */
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
        
        .invoice {
            border: 1px solid #ddd;
            padding: 20px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        
        .invoice-header {
            border-bottom: 2px solid #007bff;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }
        
        .invoice-details {
            margin-bottom: 20px;
        }
        
        .invoice-items {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        
        .invoice-items th,
        .invoice-items td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }
        
        .invoice-items th {
            background-color: #f8f9fa;
        }
        
        .total {
            text-align: right;
            font-size: 18px;
            font-weight: bold;
            margin-top: 20px;
            color: #007bff;
        }
        
        .btn {
            display: inline-block;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-right: 10px;
            text-decoration: none;
        }
        
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #0056b3;
        }
        
        .btn-download {
            background-color: #28a745;
            color: white;
        }
        
        .btn-download:hover {
            background-color: #218838;
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
            <h2>Payment Successful!</h2>
            <p>Your order has been confirmed and will be processed shortly.</p>
        </div>

        <% 
        Invoice invoice = (Invoice) request.getAttribute("invoice");
        if(invoice != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        %>
        <div class="invoice">
            <div class="invoice-header">
                <h2>Invoice</h2>
            </div>

            <div class="invoice-details">
                <p><strong>Invoice ID:</strong> <%= invoice.getInvoiceId() %></p>
                <p><strong>Order ID:</strong> <%= invoice.getOrderId() %></p>
                <p><strong>Transaction ID:</strong> <%= invoice.getTransactionId() %></p>
                <p><strong>Date:</strong> <%= sdf.format(invoice.getTimestamp()) %></p>
                <p><strong>Payment Mode:</strong> <%= invoice.getPaymentMode() %></p>
            </div>

            <div class="customer-details">
                <h3>Customer Details</h3>
                <p><strong>Name:</strong> <%= invoice.getCustomerName() %></p>
                <p><strong>Email:</strong> <%= invoice.getCustomerEmail() %></p>
                <p><strong>Phone:</strong> <%= invoice.getCustomerPhone() %></p>
                <p><strong>Address:</strong> <%= invoice.getCustomerAddress() %></p>
            </div>

            <h3>Items</h3>
            <table class="invoice-items">
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(CartItem item : invoice.getItems()) { %>
                        <tr>
                            <td><%= item.getProductName() %></td>
                            <td>₹<%= item.getProductPrice() %></td>
                            <td><%= item.getQuantity() %></td>
                            <td>₹<%= item.getSubtotal() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <div class="total">
                Total Amount: ₹<%= invoice.getTotalAmount() %>
            </div>
        </div>

        <div style="text-align: center; margin-top: 20px;">
            <a href="downloadInvoice?invoiceId=<%= invoice.getInvoiceId() %>" class="btn btn-download">Download Invoice</a>
            <a href="home" class="btn btn-primary">Continue Shopping</a>
        </div>
        <% } %>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Cart - Payment</title>
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
        
        .payment-options {
            margin-bottom: 30px;
        }
        
        .payment-method {
            margin-bottom: 10px;
        }
        
        .payment-form {
            display: none;
            margin-top: 20px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
        
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        
        .order-summary {
            margin-top: 30px;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 4px;
        }
        
        .error {
            color: #dc3545;
            background-color: #f8d7da;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        
        .btn {
            display: inline-block;
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
        
        .btn-back {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-back:hover {
            background-color: #545b62;
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
        <h1>Payment Details</h1>
        
        <% if(request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>

        <div class="order-summary">
            <h2>Order Summary</h2>
            <p><strong>Total Amount:</strong> ₹<%= request.getAttribute("totalAmount") %></p>
        </div>

        <form action="checkout" method="post" onsubmit="return validateForm()">
            <div class="payment-options">
                <h3>Select Payment Method</h3>
                
                <div class="payment-method">
                    <input type="radio" id="creditCard" name="paymentMode" value="CREDIT_CARD" onchange="showPaymentForm(this.value)">
                    <label for="creditCard">Credit Card</label>
                </div>
                
                <div class="payment-method">
                    <input type="radio" id="upi" name="paymentMode" value="UPI" onchange="showPaymentForm(this.value)">
                    <label for="upi">UPI</label>
                </div>
            </div>

            <div id="creditCardForm" class="payment-form">
                <div class="form-group">
                    <label for="cardNumber">Card Number</label>
                    <input type="text" id="cardNumber" name="cardNumber" placeholder="1234 5678 9012 3456">
                </div>
                
                <div class="form-group">
                    <label for="cardHolderName">Card Holder Name</label>
                    <input type="text" id="cardHolderName" name="cardHolderName" placeholder="Name on card">
                </div>
                
                <div class="form-group">
                    <label for="expiryDate">Expiry Date</label>
                    <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY">
                </div>
                
                <div class="form-group">
                    <label for="cvvCode">CVV Code</label>
                    <input type="text" id="cvvCode" name="cvvCode" placeholder="4-digit CVV">
                </div>
            </div>

            <div id="upiForm" class="payment-form">
                <div class="form-group">
                    <label for="upiId">UPI ID</label>
                    <input type="text" id="upiId" name="upiId" placeholder="yourname@bank">
                </div>
            </div>

            <div style="margin-top: 20px;">
                <button type="submit" class="btn btn-primary">Make Payment</button>
                <a href="cart" class="btn btn-back">Back to Cart</a>
            </div>
        </form>
    </div>

    <script>
        function showPaymentForm(paymentMode) {
            document.getElementById('creditCardForm').style.display = 'none';
            document.getElementById('upiForm').style.display = 'none';
            
            if (paymentMode === 'CREDIT_CARD') {
                document.getElementById('creditCardForm').style.display = 'block';
            } else if (paymentMode === 'UPI') {
                document.getElementById('upiForm').style.display = 'block';
            }
        }

        function validateForm() {
            const paymentMode = document.querySelector('input[name="paymentMode"]:checked');
            if (!paymentMode) {
                alert('Please select a payment method');
                return false;
            }

            if (paymentMode.value === 'CREDIT_CARD') {
                const cardNumber = document.getElementById('cardNumber').value;
                const cardHolderName = document.getElementById('cardHolderName').value;
                const expiryDate = document.getElementById('expiryDate').value;
                const cvvCode = document.getElementById('cvvCode').value;

                if (!/^\d{16}$/.test(cardNumber)) {
                    alert('Card number must be 16 digits');
                    return false;
                }
                if (cardHolderName.length < 10) {
                    alert('Card holder name must be at least 10 characters');
                    return false;
                }
                if (!/^(0[1-9]|1[0-2])\/([0-9]{2})$/.test(expiryDate)) {
                    alert('Invalid expiry date format (MM/YY)');
                    return false;
                }
                if (!/^\d{4}$/.test(cvvCode)) {
                    alert('CVV must be 4 digits');
                    return false;
                }
            } else if (paymentMode.value === 'UPI') {
                const upiId = document.getElementById('upiId').value;
                if (!/^[\w.-]+@[\w.-]+$/.test(upiId)) {
                    alert('Invalid UPI ID format');
                    return false;
                }
            }

            return true;
        }
    </script>
</body>
</html>

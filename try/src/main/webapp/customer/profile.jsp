<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ecart.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Cart - My Profile</title>
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
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        
        .form-group input[type="text"],
        .form-group input[type="email"],
        .form-group input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        
        .password-section {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 4px;
            margin-top: 30px;
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
        <h1>My Profile</h1>

        <% if(request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>

        <% if(request.getAttribute("success") != null) { %>
            <div class="success"><%= request.getAttribute("success") %></div>
        <% } %>

        <% 
        User user = (User) request.getAttribute("user");
        if(user != null) {
        %>
            <form action="profile" method="post">
                <div class="form-group">
                    <label for="name">Name <span style="color: red;">*</span></label>
                    <input type="text" id="name" name="name" value="<%= user.getName() %>" required>
                </div>

                <div class="form-group">
                    <label for="email">Email <span style="color: red;">*</span></label>
                    <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone <span style="color: red;">*</span></label>
                    <input type="text" id="phone" name="phone" value="<%= user.getPhone() %>" required>
                </div>

                <div class="form-group">
                    <label for="address1">Address Line 1 <span style="color: red;">*</span></label>
                    <input type="text" id="address1" name="address1" value="<%= user.getAddress1() %>" required>
                </div>

                <div class="form-group">
                    <label for="address2">Address Line 2</label>
                    <input type="text" id="address2" name="address2" value="<%= user.getAddress2() != null ? user.getAddress2() : "" %>">
                </div>

                <div class="password-section">
                    <h3>Change Password</h3>
                    <p>Leave blank if you don't want to change your password</p>

                    <div class="form-group">
                        <label for="currentPassword">Current Password</label>
                        <input type="password" id="currentPassword" name="currentPassword">
                    </div>

                    <div class="form-group">
                        <label for="newPassword">New Password</label>
                        <input type="password" id="newPassword" name="newPassword">
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword">Confirm New Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword">
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <button type="submit" class="btn btn-primary">Update Profile</button>
                    <a href="home" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        <% } else { %>
            <div class="error">
                <p>Failed to load user profile. Please try again later.</p>
                <a href="home" class="btn btn-secondary">Back to Home</a>
            </div>
        <% } %>
    </div>

    <script>
        document.querySelector('form').addEventListener('submit', function(event) {
            // Get the password fields
            const currentPassword = document.getElementById('currentPassword').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            // Check if any password field is filled
            if (currentPassword || newPassword || confirmPassword) {
                // If any password field is filled, all must be filled
                if (!currentPassword || !newPassword || !confirmPassword) {
                    event.preventDefault();
                    alert('All password fields must be filled to change password');
                    return;
                }

                // Check if passwords match
                if (newPassword !== confirmPassword) {
                    event.preventDefault();
                    alert('New password and confirm password do not match');
                    return;
                }
            }
        });
    </script>
</body>
</html>

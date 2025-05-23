<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Cart - Customer Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"],
        select,
        textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            height: 80px;
            resize: vertical;
        }
        select {
            height: 35px;
        }
        .error {
            color: #dc3545;
            margin-top: 10px;
            padding: 10px;
            border: 1px solid #dc3545;
            border-radius: 4px;
            background-color: #fff;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .btn-reset {
            background-color: #6c757d;
            margin-left: 10px;
        }
        .btn-reset:hover {
            background-color: #545b62;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Customer Registration</h1>
        
        <% if(request.getAttribute("error") != null) { %>
            <div class="error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="register" method="post" onsubmit="return validateForm()">
            <div class="form-group">
                <label for="name">Name <span style="color: red;">*</span></label>
                <input type="text" id="name" name="name" required maxlength="50">
            </div>

            <div class="form-group">
                <label for="country">Country <span style="color: red;">*</span></label>
                <select id="country" name="country" required onchange="populateStates()">
                    <option value="">Select Country</option>
                    <option value="India">India</option>
                    <!-- Add more countries as needed -->
                </select>
            </div>

            <div class="form-group">
                <label for="state">State <span style="color: red;">*</span></label>
                <select id="state" name="state" required>
                    <option value="">Select State</option>
                </select>
            </div>

            <div class="form-group">
                <label for="city">City <span style="color: red;">*</span></label>
                <input type="text" id="city" name="city" required>
            </div>

            <div class="form-group">
                <label for="address1">Address Line 1 <span style="color: red;">*</span></label>
                <textarea id="address1" name="address1" required></textarea>
            </div>

            <div class="form-group">
                <label for="address2">Address Line 2</label>
                <textarea id="address2" name="address2"></textarea>
            </div>

            <div class="form-group">
                <label for="zipcode">Zip Code <span style="color: red;">*</span></label>
                <input type="text" id="zipcode" name="zipcode" required pattern="[0-9]{6}">
            </div>

            <div class="form-group">
                <label for="phone">Phone Number <span style="color: red;">*</span></label>
                <input type="text" id="phone" name="phone" required>
            </div>

            <div class="form-group">
                <label for="email">Email <span style="color: red;">*</span></label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password <span style="color: red;">*</span></label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm Password <span style="color: red;">*</span></label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>

            <button type="submit" class="btn">Register</button>
            <button type="reset" class="btn btn-reset">Reset</button>
        </form>
    </div>

    <script>
        function populateStates() {
            const country = document.getElementById('country').value;
            const stateSelect = document.getElementById('state');
            stateSelect.innerHTML = '<option value="">Select State</option>';
            
            if (country === 'India') {
                const states = [
                    'Andhra Pradesh', 'Arunachal Pradesh', 'Assam', 'Bihar', 
                    'Chhattisgarh', 'Goa', 'Gujarat', 'Haryana', 'Himachal Pradesh',
                    'Jharkhand', 'Karnataka', 'Kerala', 'Madhya Pradesh', 'Maharashtra',
                    'Manipur', 'Meghalaya', 'Mizoram', 'Nagaland', 'Odisha', 'Punjab',
                    'Rajasthan', 'Sikkim', 'Tamil Nadu', 'Telangana', 'Tripura',
                    'Uttar Pradesh', 'Uttarakhand', 'West Bengal'
                ];
                
                states.forEach(state => {
                    const option = new Option(state, state);
                    stateSelect.add(option);
                });
            }
        }

        function validateForm() {
            const name = document.getElementById('name').value;
            const phone = document.getElementById('phone').value;
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (name.length > 50) {
                alert('Name should not exceed 50 characters');
                return false;
            }

            if (phone.startsWith('0')) {
                alert('Phone number should not start with 0');
                return false;
            }

            if (password.length < 10) {
                alert('Password must be at least 10 characters long');
                return false;
            }

            if (!password.match(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{10,}$/)) {
                alert('Password must contain at least one number, one uppercase letter, one lowercase letter, and one special character');
                return false;
            }

            if (password !== confirmPassword) {
                alert('Passwords do not match');
                return false;
            }

            return true;
        }
    </script>
</body>
</html>

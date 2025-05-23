<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>E-Cart - Home</title>
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
        
        .search-bar {
            max-width: 1200px;
            margin: 20px auto;
            padding: 0 15px;
            display: flex;
            gap: 10px;
        }
        
        .search-bar input[type="text"] {
            flex: 1;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        
        .search-bar select {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            min-width: 150px;
        }
        
        .search-bar button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        
        .search-bar button:hover {
            background-color: #0056b3;
        }
        
        .products {
            max-width: 1200px;
            margin: 20px auto;
            padding: 0 15px;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }
        
        .product-card {
            background-color: white;
            border-radius: 8px;
            padding: 15px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        .product-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 4px;
            margin-bottom: 10px;
        }
        
        .product-name {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        
        .product-price {
            color: #007bff;
            font-size: 20px;
            margin-bottom: 10px;
        }
        
        .product-description {
            color: #666;
            margin-bottom: 15px;
            font-size: 14px;
        }
        
        .add-to-cart {
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        
        .add-to-cart:hover {
            background-color: #218838;
        }
        
        .out-of-stock {
            width: 100%;
            padding: 10px;
            background-color: #dc3545;
            color: white;
            border: none;
            border-radius: 4px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="top-bar">
            <div class="welcome">
                <span>Welcome, ${sessionScope.userName}!</span>
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

    <div class="search-bar">
        <form action="home" method="get">
            <input type="text" name="search" placeholder="Search products..." value="${searchKeyword}">
            <select name="category">
                <option value="">All Categories</option>
                <c:forEach items="${categories}" var="cat">
                    <option value="${cat}" ${cat eq selectedCategory ? 'selected' : ''}>${cat}</option>
                </c:forEach>
            </select>
            <button type="submit">Search</button>
        </form>
    </div>

    <div class="products">
        <c:forEach items="${products}" var="product">
            <div class="product-card">
                <c:if test="${not empty product.imageUrl}">
                    <img src="${product.imageUrl}" alt="${product.name}" class="product-image">
                </c:if>
                <div class="product-name">${product.name}</div>
                <div class="product-price">₹${product.price}</div>
                <div class="product-description">${product.description}</div>
                
                <c:choose>
                    <c:when test="${product.quantity > 0}">
                        <form action="home" method="post">
                            <input type="hidden" name="productId" value="${product.productId}">
                            <button type="submit" class="add-to-cart">Add to Cart</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div class="out-of-stock">Product not available</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </div>
</body>
</html>

package com.ecart.dao;

import com.ecart.model.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private Connection connection;
    
    public CartDAO() {
        this.connection = DatabaseConnection.getConnection();
        createCartTableIfNotExists();
    }
    
    private void createCartTableIfNotExists() {
        try {
            connection.createStatement().execute(
                "CREATE TABLE Cart (" +
                "cart_item_id VARCHAR(20) PRIMARY KEY, " +
                "user_id VARCHAR(10) NOT NULL, " +
                "product_id VARCHAR(10) NOT NULL, " +
                "product_name VARCHAR(100) NOT NULL, " +
                "product_price DECIMAL(10,2) NOT NULL, " +
                "quantity INT NOT NULL, " +
                "FOREIGN KEY (user_id) REFERENCES Users(user_id), " +
                "FOREIGN KEY (product_id) REFERENCES Products(product_id))"
            );
        } catch (SQLException e) {
            // Table likely exists already
        }
    }

    public List<CartItem> getCartItems(String userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT * FROM Cart WHERE user_id = ?";
        
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, userId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getString("cart_item_id"));
                item.setUserId(rs.getString("user_id"));
                item.setProductId(rs.getString("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setProductPrice(rs.getDouble("product_price"));
                item.setQuantity(rs.getInt("quantity"));
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cartItems;
    }
    
    public String generateCartItemId(String userId) {
        String query = "SELECT COUNT(*) FROM Cart WHERE user_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                return "CART" + userId + "_" + String.format("%03d", count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "CART" + userId + "_001";
    }
    
    public boolean addToCart(CartItem item) {
        // Check if product already exists in cart
        String checkQuery = "SELECT cart_item_id, quantity FROM Cart WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(checkQuery)) {
            pst.setString(1, item.getUserId());
            pst.setString(2, item.getProductId());
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                // Update existing cart item
                String updateQuery = "UPDATE Cart SET quantity = quantity + ? WHERE cart_item_id = ?";
                try (PreparedStatement updatePst = connection.prepareStatement(updateQuery)) {
                    updatePst.setInt(1, item.getQuantity());
                    updatePst.setString(2, rs.getString("cart_item_id"));
                    return updatePst.executeUpdate() > 0;
                }
            } else {
                // Insert new cart item
                String insertQuery = "INSERT INTO Cart (cart_item_id, user_id, product_id, product_name, product_price, quantity) " +
                                   "VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertPst = connection.prepareStatement(insertQuery)) {
                    insertPst.setString(1, item.getCartItemId());
                    insertPst.setString(2, item.getUserId());
                    insertPst.setString(3, item.getProductId());
                    insertPst.setString(4, item.getProductName());
                    insertPst.setDouble(5, item.getProductPrice());
                    insertPst.setInt(6, item.getQuantity());
                    return insertPst.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateQuantity(String cartItemId, int quantity) {
        String query = "UPDATE Cart SET quantity = ? WHERE cart_item_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, quantity);
            pst.setString(2, cartItemId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean removeFromCart(String cartItemId) {
        String query = "DELETE FROM Cart WHERE cart_item_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, cartItemId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean clearCart(String userId) {
        String query = "DELETE FROM Cart WHERE user_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, userId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public double getCartTotal(String userId) {
        String query = "SELECT SUM(product_price * quantity) as total FROM Cart WHERE user_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}

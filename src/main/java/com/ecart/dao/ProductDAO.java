package com.ecart.dao;

import com.ecart.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;
    
    public ProductDAO() {
        this.connection = DatabaseConnection.getConnection();
        createProductTableIfNotExists();
    }
    
    private void createProductTableIfNotExists() {
        try {
            connection.createStatement().execute(
                "CREATE TABLE Products (" +
                "product_id VARCHAR(10) PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "price DECIMAL(10,2) NOT NULL, " +
                "category VARCHAR(50) NOT NULL, " +
                "description VARCHAR(500), " +
                "quantity INT NOT NULL, " +
                "status VARCHAR(20) NOT NULL, " +
                "image_url VARCHAR(200))"
            );
        } catch (SQLException e) {
            // Table likely exists already
        }
    }
    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE status = 'Active'";
        
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setQuantity(rs.getInt("quantity"));
                product.setStatus(rs.getString("status"));
                product.setImageUrl(rs.getString("image_url"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }
    
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE category = ? AND status = 'Active'";
        
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, category);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setQuantity(rs.getInt("quantity"));
                product.setStatus(rs.getString("status"));
                product.setImageUrl(rs.getString("image_url"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }
    
    public Product getProductById(String productId) {
        String query = "SELECT * FROM Products WHERE product_id = ? AND status = 'Active'";
        
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, productId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setQuantity(rs.getInt("quantity"));
                product.setStatus(rs.getString("status"));
                product.setImageUrl(rs.getString("image_url"));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE (LOWER(name) LIKE ? OR product_id = ?) AND status = 'Active'";
        
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, "%" + keyword.toLowerCase() + "%");
            pst.setString(2, keyword);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setQuantity(rs.getInt("quantity"));
                product.setStatus(rs.getString("status"));
                product.setImageUrl(rs.getString("image_url"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }
    
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT category FROM Products WHERE status = 'Active' ORDER BY category";
        
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categories;
    }
}

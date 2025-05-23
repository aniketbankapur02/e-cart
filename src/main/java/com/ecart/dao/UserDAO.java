package com.ecart.dao;

import com.ecart.model.User;
import com.ecart.util.ValidationUtil;
import java.sql.*;

public class UserDAO {
    private Connection connection;
    
    public UserDAO() {
        this.connection = DatabaseConnection.getConnection();
    }
    
    public boolean isEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String generateUserId() {
        String query = "SELECT COUNT(*) FROM Users";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                return "CUST" + String.format("%04d", count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "CUST0001";
    }
    
    public boolean registerUser(User user) {
        String query = "INSERT INTO Users (user_id, name, country, state, city, " +
                      "address1, address2, zipcode, phone, email, password, role) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                      
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, user.getUserId());
            pst.setString(2, user.getName());
            pst.setString(3, user.getCountry());
            pst.setString(4, user.getState());
            pst.setString(5, user.getCity());
            pst.setString(6, user.getAddress1());
            pst.setString(7, user.getAddress2());
            pst.setString(8, user.getZipcode());
            pst.setString(9, user.getPhone());
            pst.setString(10, user.getEmail());
            pst.setString(11, user.getPassword());
            pst.setString(12, user.getRole());
            
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setName(rs.getString("name"));
                user.setCountry(rs.getString("country"));
                user.setState(rs.getString("state"));
                user.setCity(rs.getString("city"));
                user.setAddress1(rs.getString("address1"));
                user.setAddress2(rs.getString("address2"));
                user.setZipcode(rs.getString("zipcode"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(String userId) {
        String query = "SELECT * FROM Users WHERE user_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, userId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setName(rs.getString("name"));
                user.setCountry(rs.getString("country"));
                user.setState(rs.getString("state"));
                user.setCity(rs.getString("city"));
                user.setAddress1(rs.getString("address1"));
                user.setAddress2(rs.getString("address2"));
                user.setZipcode(rs.getString("zipcode"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validateLogin(String userId, String password) {
        String query = "SELECT password FROM Users WHERE user_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, userId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String hashedPassword = ValidationUtil.encryptPassword(password);
                return storedPassword.equals(hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

package com.ecart.dao;

import com.ecart.model.User;
import java.sql.*;

public class UserDAO {
    private Connection connection;
    
    public UserDAO() {
        this.connection = DatabaseConnection.getConnection();
        createUserTableIfNotExist();
    }
    
    private void createUserTableIfNotExist() {
        try {
            connection.createStatement().execute(
                "CREATE TABLE Users (" +
                "user_id VARCHAR(10) PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, " +
                "email VARCHAR(100) NOT NULL UNIQUE, " +
                "password VARCHAR(64) NOT NULL, " +
                "phone VARCHAR(10) NOT NULL, " +
                "address1 VARCHAR(200) NOT NULL, " +
                "address2 VARCHAR(200), " +
                "role VARCHAR(10) DEFAULT 'CUSTOMER')"
            );
        } catch (SQLException e) {
            // Table likely exists already
        }
    }

    public boolean addUser(User user) {
        String query = "INSERT INTO Users (user_id, name, email, password, phone, address1, address2) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, generateUserId());
            pst.setString(2, user.getName());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPassword());
            pst.setString(5, user.getPhone());
            pst.setString(6, user.getAddress1());
            pst.setString(7, user.getAddress2());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User authenticate(String email, String password) {
        String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
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
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(User user) {
        String query = "UPDATE Users SET name = ?, email = ?, phone = ?, address1 = ?, address2 = ?" +
                      (user.getPassword() != null ? ", password = ?" : "") +
                      " WHERE user_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPhone());
            pst.setString(4, user.getAddress1());
            pst.setString(5, user.getAddress2());
            
            int paramIndex = 6;
            if (user.getPassword() != null) {
                pst.setString(paramIndex++, user.getPassword());
            }
            pst.setString(paramIndex, user.getUserId());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getString("user_id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhone(rs.getString("phone"));
        user.setAddress1(rs.getString("address1"));
        user.setAddress2(rs.getString("address2"));
        user.setRole(rs.getString("role"));
        return user;
    }

    private String generateUserId() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT COUNT(*) as count FROM Users");
            if (rs.next()) {
                int count = rs.getInt("count");
                return "U" + String.format("%09d", count + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "U000000001";
    }
}

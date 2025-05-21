package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    private static final String DB_URL = "jdbc:derby:derby/feedbackDB;create=true";

    static {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            initializeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Create orders table
            stmt.executeUpdate("CREATE TABLE orders (id INT PRIMARY KEY, customer VARCHAR(255), status VARCHAR(50), feedbackDescription VARCHAR(1000), feedbackRating INT)");
        } catch (SQLException e) {
            // Table might already exist
        }
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Create feedback table
            stmt.executeUpdate("CREATE TABLE feedback (orderId INT PRIMARY KEY, description VARCHAR(1000), rating INT)");
        } catch (SQLException e) {
            // Table might already exist
        }
    }
} 
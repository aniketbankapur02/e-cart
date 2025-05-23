package com.ecart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:derby:eCartDB;create=true";
    private static Connection connection = null;
    
    private DatabaseConnection() {}
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                connection = DriverManager.getConnection(URL);
                createTablesIfNotExist();
            } catch (ClassNotFoundException e) {
                System.err.println("Derby driver not found: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Database connection error: " + e.getMessage());
            }
        }
        return connection;
    }
    
    private static void createTablesIfNotExist() {
        try {
            connection.createStatement().execute(
                "CREATE TABLE Users (" +
                "user_id VARCHAR(10) PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "country VARCHAR(50), " +
                "state VARCHAR(50), " +
                "city VARCHAR(50), " +
                "address1 VARCHAR(200), " +
                "address2 VARCHAR(200), " +
                "zipcode VARCHAR(10), " +
                "phone VARCHAR(15), " +
                "email VARCHAR(50) UNIQUE, " +
                "password VARCHAR(100), " +
                "role VARCHAR(10))"
            );
        } catch (SQLException e) {
            // Table likely exists already
        }
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}

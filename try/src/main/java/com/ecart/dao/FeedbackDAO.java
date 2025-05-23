package com.ecart.dao;

import com.ecart.model.Feedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {
    private Connection connection;
    
    public FeedbackDAO() {
        this.connection = DatabaseConnection.getConnection();
        createFeedbackTableIfNotExist();
    }
    
    private void createFeedbackTableIfNotExist() {
        try {
            connection.createStatement().execute(
                "CREATE TABLE Feedback (" +
                "feedback_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                "order_id VARCHAR(20) NOT NULL, " +
                "user_id VARCHAR(10) NOT NULL, " +
                "description VARCHAR(500) NOT NULL, " +
                "rating INTEGER NOT NULL, " +
                "feedback_date TIMESTAMP NOT NULL, " +
                "PRIMARY KEY (feedback_id), " +
                "FOREIGN KEY (order_id) REFERENCES Orders(order_id), " +
                "FOREIGN KEY (user_id) REFERENCES Users(user_id), " +
                "CHECK (rating BETWEEN 1 AND 5))"
            );
        } catch (SQLException e) {
            // Table likely exists already
        }
    }

    public boolean addFeedback(Feedback feedback) {
        String query = "INSERT INTO Feedback (order_id, user_id, description, rating, feedback_date) " +
                      "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, feedback.getOrderId());
            pst.setString(2, feedback.getUserId());
            pst.setString(3, feedback.getDescription());
            pst.setInt(4, feedback.getRating());
            pst.setTimestamp(5, feedback.getFeedbackDate());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Feedback getFeedbackByOrderId(String orderId) {
        String query = "SELECT * FROM Feedback WHERE order_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, orderId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToFeedback(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Feedback> getFeedbacksByUserId(String userId) {
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT * FROM Feedback WHERE user_id = ? ORDER BY feedback_date DESC";
        
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, userId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                feedbacks.add(mapResultSetToFeedback(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;
    }

    public boolean updateFeedback(Feedback feedback) {
        String query = "UPDATE Feedback SET description = ?, rating = ? WHERE order_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, feedback.getDescription());
            pst.setInt(2, feedback.getRating());
            pst.setString(3, feedback.getOrderId());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFeedback(String orderId) {
        String query = "DELETE FROM Feedback WHERE order_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, orderId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Feedback mapResultSetToFeedback(ResultSet rs) throws SQLException {
        return new Feedback(
            rs.getInt("feedback_id"),
            rs.getString("order_id"),
            rs.getString("user_id"),
            rs.getString("description"),
            rs.getInt("rating"),
            rs.getTimestamp("feedback_date")
        );
    }

    public boolean hasFeedback(String orderId) {
        String query = "SELECT COUNT(*) FROM Feedback WHERE order_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, orderId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

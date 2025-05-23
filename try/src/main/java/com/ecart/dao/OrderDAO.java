package com.ecart.dao;

import com.ecart.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDAO {
    private Connection connection;
    
    public OrderDAO() {
        this.connection = DatabaseConnection.getConnection();
        createOrderTablesIfNotExist();
    }
    
    private void createOrderTablesIfNotExist() {
        try {
            // Create Orders table
            connection.createStatement().execute(
                "CREATE TABLE Orders (" +
                "order_id VARCHAR(20) PRIMARY KEY, " +
                "customer_id VARCHAR(10) NOT NULL, " +
                "total_amount DECIMAL(10,2) NOT NULL, " +
                "status VARCHAR(20) NOT NULL, " +
                "order_date TIMESTAMP NOT NULL, " +
                "payment_mode VARCHAR(20), " +
                "transaction_id VARCHAR(20), " +
                "FOREIGN KEY (customer_id) REFERENCES Users(user_id))"
            );

            // Create OrderItems table
            connection.createStatement().execute(
                "CREATE TABLE OrderItems (" +
                "order_id VARCHAR(20) NOT NULL, " +
                "product_id VARCHAR(10) NOT NULL, " +
                "quantity INT NOT NULL, " +
                "price DECIMAL(10,2) NOT NULL, " +
                "PRIMARY KEY (order_id, product_id), " +
                "FOREIGN KEY (order_id) REFERENCES Orders(order_id), " +
                "FOREIGN KEY (product_id) REFERENCES Products(product_id))"
            );

            // Create Payments table
            connection.createStatement().execute(
                "CREATE TABLE Payments (" +
                "transaction_id VARCHAR(20) PRIMARY KEY, " +
                "order_id VARCHAR(20) NOT NULL, " +
                "customer_id VARCHAR(10) NOT NULL, " +
                "amount DECIMAL(10,2) NOT NULL, " +
                "payment_mode VARCHAR(20) NOT NULL, " +
                "status VARCHAR(20) NOT NULL, " +
                "payment_date TIMESTAMP NOT NULL, " +
                "FOREIGN KEY (order_id) REFERENCES Orders(order_id), " +
                "FOREIGN KEY (customer_id) REFERENCES Users(user_id))"
            );

            // Create OrderCancellations table
            connection.createStatement().execute(
                "CREATE TABLE OrderCancellations (" +
                "order_id VARCHAR(20) PRIMARY KEY, " +
                "reason TEXT NOT NULL, " +
                "cancellation_date TIMESTAMP NOT NULL, " +
                "refund_amount DECIMAL(10,2) NOT NULL, " +
                "FOREIGN KEY (order_id) REFERENCES Orders(order_id))"
            );
        } catch (SQLException e) {
            // Tables likely exist already
        }
    }

    // ... [Previous methods remain unchanged] ...

    public List<Order> getOrdersByCustomerId(String customerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE customer_id = ? ORDER BY order_date DESC";
        
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, customerId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getString("order_id"));
                order.setCustomerId(customerId);
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setPaymentMode(rs.getString("payment_mode"));
                order.setTransactionId(rs.getString("transaction_id"));
                
                // Get order items
                order.setItems(getOrderItems(order.getOrderId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private List<CartItem> getOrderItems(String orderId) {
        List<CartItem> items = new ArrayList<>();
        String query = "SELECT oi.*, p.name, p.description, p.category FROM OrderItems oi " +
                      "JOIN Products p ON oi.product_id = p.product_id " +
                      "WHERE oi.order_id = ?";
        
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, orderId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setProductId(rs.getString("product_id"));
                item.setProductName(rs.getString("name"));
                item.setProductPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Order> getOrdersByStatus(String customerId, String status) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE customer_id = ? AND status = ? ORDER BY order_date DESC";
        
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, customerId);
            pst.setString(2, status);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getString("order_id"));
                order.setCustomerId(customerId);
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setPaymentMode(rs.getString("payment_mode"));
                order.setTransactionId(rs.getString("transaction_id"));
                order.setItems(getOrderItems(order.getOrderId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order getOrderById(String orderId) {
        String query = "SELECT * FROM Orders WHERE order_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, orderId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                Order order = new Order();
                order.setOrderId(orderId);
                order.setCustomerId(rs.getString("customer_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setPaymentMode(rs.getString("payment_mode"));
                order.setTransactionId(rs.getString("transaction_id"));
                order.setItems(getOrderItems(orderId));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateOrderStatus(String orderId, OrderStatus status) {
        String query = "UPDATE Orders SET status = ? WHERE order_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, status.name());
            pst.setString(2, orderId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cancelOrder(OrderCancellation cancellation) {
        connection.setAutoCommit(false);
        try {
            // Update order status
            String updateOrder = "UPDATE Orders SET status = ? WHERE order_id = ? AND status = ?";
            try (PreparedStatement pst = connection.prepareStatement(updateOrder)) {
                pst.setString(1, OrderStatus.CANCELLED.name());
                pst.setString(2, cancellation.getOrderId());
                pst.setString(3, OrderStatus.CONFIRMED.name());
                
                if (pst.executeUpdate() > 0) {
                    // Record cancellation details
                    String insertCancellation = "INSERT INTO OrderCancellations " +
                        "(order_id, reason, cancellation_date, refund_amount) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement cancelPst = connection.prepareStatement(insertCancellation)) {
                        cancelPst.setString(1, cancellation.getOrderId());
                        cancelPst.setString(2, cancellation.getReason());
                        cancelPst.setTimestamp(3, cancellation.getCancellationDate());
                        cancelPst.setDouble(4, cancellation.getRefundAmount());
                        
                        if (cancelPst.executeUpdate() > 0) {
                            connection.commit();
                            return true;
                        }
                    }
                }
            }
            connection.rollback();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException re) {
                re.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public OrderCancellation getOrderCancellation(String orderId) {
        String query = "SELECT * FROM OrderCancellations WHERE order_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, orderId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return new OrderCancellation(
                    rs.getString("order_id"),
                    rs.getString("reason"),
                    rs.getTimestamp("cancellation_date"),
                    rs.getDouble("refund_amount")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

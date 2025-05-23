package com.ecart.model;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private String orderId;
    private String customerId;
    private List<CartItem> items;
    private double totalAmount;
    private OrderStatus status;
    private Timestamp orderDate;
    private String paymentMode;
    private String transactionId;

    public Order() {}

    public Order(String orderId, String customerId, List<CartItem> items, double totalAmount,
                OrderStatus status, Timestamp orderDate, String paymentMode, String transactionId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.paymentMode = paymentMode;
        this.transactionId = transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", orderDate=" + orderDate +
                ", paymentMode='" + paymentMode + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}

package com.example.model;

public class Feedback {
    private int orderId;
    private String description;
    private int rating;

    public Feedback(int orderId, String description, int rating) {
        this.orderId = orderId;
        this.description = description;
        this.rating = rating;
    }

    // Getters and setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
} 
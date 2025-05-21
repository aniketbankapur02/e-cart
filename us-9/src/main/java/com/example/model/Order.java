package com.example.model;

public class Order {
    private int id;
    private String customer;
    private String status; // e.g., 'delivered', 'pending', etc.
    private String feedbackDescription;
    private Integer feedbackRating;

    public Order(int id, String customer, String status, String feedbackDescription, Integer feedbackRating) {
        this.id = id;
        this.customer = customer;
        this.status = status;
        this.feedbackDescription = feedbackDescription;
        this.feedbackRating = feedbackRating;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFeedbackDescription() { return feedbackDescription; }
    public void setFeedbackDescription(String feedbackDescription) { this.feedbackDescription = feedbackDescription; }
    public Integer getFeedbackRating() { return feedbackRating; }
    public void setFeedbackRating(Integer feedbackRating) { this.feedbackRating = feedbackRating; }
} 
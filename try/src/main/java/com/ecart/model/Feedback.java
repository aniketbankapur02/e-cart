package com.ecart.model;

import java.sql.Timestamp;

public class Feedback {
    private int feedbackId;
    private String orderId;
    private String userId;
    private String description;
    private int rating;
    private Timestamp feedbackDate;

    public Feedback() {}

    public Feedback(int feedbackId, String orderId, String userId, String description, int rating, Timestamp feedbackDate) {
        this.feedbackId = feedbackId;
        this.orderId = orderId;
        this.userId = userId;
        this.description = description;
        this.rating = rating;
        this.feedbackDate = feedbackDate;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", feedbackDate=" + feedbackDate +
                '}';
    }
}

package com.ecart.model;

import java.sql.Timestamp;

public class Payment {
    private String transactionId;
    private String orderId;
    private String customerId;
    private double amount;
    private String paymentMode;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvvCode;
    private String upiId;
    private String status;
    private Timestamp paymentDate;

    public Payment() {}

    // Constructor for Credit Card payment
    public Payment(String orderId, String customerId, double amount, 
                  String cardNumber, String cardHolderName, 
                  String expiryDate, String cvvCode) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentMode = "CREDIT_CARD";
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvvCode = cvvCode;
        this.status = "PENDING";
        this.paymentDate = new Timestamp(System.currentTimeMillis());
    }

    // Constructor for UPI payment
    public Payment(String orderId, String customerId, double amount, String upiId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.paymentMode = "UPI";
        this.upiId = upiId;
        this.status = "PENDING";
        this.paymentDate = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    // Helper methods for validation
    public boolean isValidCardNumber() {
        return cardNumber != null && cardNumber.matches("\\d{16}");
    }

    public boolean isValidCardHolderName() {
        return cardHolderName != null && cardHolderName.length() >= 10;
    }

    public boolean isValidExpiryDate() {
        return expiryDate != null && expiryDate.matches("^(0[1-9]|1[0-2])/([0-9]{2})$");
    }

    public boolean isValidCvvCode() {
        return cvvCode != null && cvvCode.matches("\\d{4}");
    }

    public boolean isValidUpiId() {
        return upiId != null && upiId.matches("^[\\w.-]+@[\\w.-]+$");
    }

    @Override
    public String toString() {
        return "Payment{" +
                "transactionId='" + transactionId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", amount=" + amount +
                ", paymentMode='" + paymentMode + '\'' +
                ", status='" + status + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
}

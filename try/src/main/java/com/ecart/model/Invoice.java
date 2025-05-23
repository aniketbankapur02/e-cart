package com.ecart.model;

import java.sql.Timestamp;
import java.util.List;

public class Invoice {
    private String invoiceId;
    private String orderId;
    private String transactionId;
    private String customerId;
    private List<CartItem> items;
    private double totalAmount;
    private String paymentMode;
    private Timestamp timestamp;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private String customerPhone;

    public Invoice() {}

    public Invoice(String invoiceId, String orderId, String transactionId, String customerId,
                  List<CartItem> items, double totalAmount, String paymentMode, Timestamp timestamp,
                  String customerName, String customerEmail, String customerAddress, String customerPhone) {
        this.invoiceId = invoiceId;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.paymentMode = paymentMode;
        this.timestamp = timestamp;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
    }

    // Getters and Setters
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    // Helper method to generate PDF content
    public String generatePdfContent() {
        StringBuilder content = new StringBuilder();
        content.append("E-CART INVOICE\n\n");
        content.append("Invoice ID: ").append(invoiceId).append("\n");
        content.append("Order ID: ").append(orderId).append("\n");
        content.append("Transaction ID: ").append(transactionId).append("\n");
        content.append("Date: ").append(timestamp).append("\n\n");
        
        content.append("Customer Details:\n");
        content.append("Name: ").append(customerName).append("\n");
        content.append("ID: ").append(customerId).append("\n");
        content.append("Email: ").append(customerEmail).append("\n");
        content.append("Phone: ").append(customerPhone).append("\n");
        content.append("Address: ").append(customerAddress).append("\n\n");
        
        content.append("Items:\n");
        content.append(String.format("%-10s %-30s %-10s %-10s %-10s\n", 
                "ID", "Name", "Price", "Qty", "Total"));
        content.append("-".repeat(70)).append("\n");
        
        for (CartItem item : items) {
            content.append(String.format("%-10s %-30s ₹%-9.2f %-10d ₹%-9.2f\n",
                    item.getProductId(), item.getProductName(), item.getProductPrice(),
                    item.getQuantity(), item.getSubtotal()));
        }
        
        content.append("-".repeat(70)).append("\n");
        content.append(String.format("%52s ₹%.2f\n", "Total Amount:", totalAmount));
        content.append("\n");
        content.append("Payment Method: ").append(paymentMode).append("\n");
        content.append("Payment Status: Successful\n\n");
        
        content.append("Thank you for shopping with E-Cart!\n");
        return content.toString();
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId='" + invoiceId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", totalAmount=" + totalAmount +
                ", paymentMode='" + paymentMode + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

package com.ecart.model;

import java.sql.Timestamp;

public class OrderCancellation {
    private String orderId;
    private String reason;
    private Timestamp cancellationDate;
    private double refundAmount;

    public OrderCancellation() {}

    public OrderCancellation(String orderId, String reason, Timestamp cancellationDate, double refundAmount) {
        this.orderId = orderId;
        this.reason = reason;
        this.cancellationDate = cancellationDate;
        this.refundAmount = refundAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Timestamp cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    @Override
    public String toString() {
        return "OrderCancellation{" +
                "orderId='" + orderId + '\'' +
                ", reason='" + reason + '\'' +
                ", cancellationDate=" + cancellationDate +
                ", refundAmount=" + refundAmount +
                '}';
    }
}

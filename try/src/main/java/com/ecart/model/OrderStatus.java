package com.ecart.model;

public enum OrderStatus {
    CONFIRMED("Confirmed"),
    IN_TRANSIT("In Transit"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static OrderStatus fromDisplayName(String displayName) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.displayName.equals(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No status found for display name: " + displayName);
    }
}

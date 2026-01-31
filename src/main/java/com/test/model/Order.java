package com.test.model;

import java.util.List;

public class Order {
    private final String id;
    private final String customerId;
    private final double amount;
    private final String status;
    private final List<String> items;

    public Order(String id, String customerId, double amount, String status, List<String> items) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Order{id='" + id + "', customer='" + customerId + "', amount=" + amount + ", status='" + status + "'}";
    }
}

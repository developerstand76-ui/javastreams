package com.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Order input data")
public class OrderInput {
    
    @Schema(description = "Order ID", example = "O1")
    private String id;
    
    @Schema(description = "Customer ID", example = "C1")
    private String customerId;
    
    @Schema(description = "Order amount", example = "150.50")
    private double amount;
    
    @Schema(description = "Order status", example = "COMPLETED", allowableValues = {"COMPLETED", "PENDING", "CANCELLED"})
    private String status;
    
    @Schema(description = "List of items", example = "[\"Item1\", \"Item2\"]")
    private List<String> items;

    public OrderInput() {
    }

    public OrderInput(String id, String customerId, double amount, String status, List<String> items) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}

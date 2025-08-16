package org.example.marico_bangladesh.INVENTORY;

import java.io.Serializable;

public class InventoryManager implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private String category;
    private String location;
    private String lastUpdated;
    private int threshold = 10;

    public InventoryManager() {
    }

    public InventoryManager(String productId, String productName, int quantity, double price, String category, String location) {
        this(productId, productName, quantity, price, category, location, 10);
    }
    
    public InventoryManager(String productId, String productName, int quantity, double price, String category, String location, int threshold) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.location = location;
        this.threshold = threshold;
        this.lastUpdated = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }

    public int getThreshold() { return threshold; }
    public void setThreshold(int threshold) { this.threshold = threshold; }
}

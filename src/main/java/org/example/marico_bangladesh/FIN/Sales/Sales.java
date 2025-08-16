package org.example.marico_bangladesh.FIN.Sales;

import java.time.LocalDate;
import java.util.Objects;

public class Sales {
    private String salesId;
    private String customerId;
    private String customerName;
    private String productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private String region;
    private String salesPerson;
    private String status;
    private String paymentMethod;
    private double discount;
    private double taxAmount;
    private double finalAmount;
    
    // Default constructor
    public Sales() {
        this.salesId = generateSalesId();
        this.orderDate = LocalDate.now();
        this.status = "Pending";
    }
    
    // Parameterized constructor
    public Sales(String customerId, String customerName, String productId, String productName, 
                 int quantity, double unitPrice, String region, String salesPerson) {
        this();
        this.customerId = customerId;
        this.customerName = customerName;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.region = region;
        this.salesPerson = salesPerson;
        calculateAmounts();
    }
    
    // Generate unique sales ID
    private String generateSalesId() {
        return "SALES-" + System.currentTimeMillis();
    }
    
    // Calculate all amounts
    public void calculateAmounts() {
        this.totalAmount = quantity * unitPrice;
        this.taxAmount = totalAmount * 0.15; // 15% tax
        this.finalAmount = totalAmount + taxAmount - discount;
    }
    
    // Getters and Setters
    public String getSalesId() {
        return salesId;
    }
    
    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateAmounts();
    }
    
    public double getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        calculateAmounts();
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public LocalDate getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
    
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getSalesPerson() {
        return salesPerson;
    }
    
    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public double getDiscount() {
        return discount;
    }
    
    public void setDiscount(double discount) {
        this.discount = discount;
        calculateAmounts();
    }
    
    public double getTaxAmount() {
        return taxAmount;
    }
    
    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }
    
    public double getFinalAmount() {
        return finalAmount;
    }
    
    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }
    
    // Business methods
    public boolean isDelivered() {
        return "Delivered".equalsIgnoreCase(status);
    }
    
    public boolean isPending() {
        return "Pending".equalsIgnoreCase(status);
    }
    
    public boolean isCancelled() {
        return "Cancelled".equalsIgnoreCase(status);
    }
    
    public void markAsDelivered() {
        this.status = "Delivered";
        this.deliveryDate = LocalDate.now();
    }
    
    public void markAsCancelled() {
        this.status = "Cancelled";
    }
    
    public double getProfitMargin(double costPrice) {
        return (unitPrice - costPrice) * quantity;
    }
    
    public double getProfitPercentage(double costPrice) {
        if (costPrice > 0) {
            return ((unitPrice - costPrice) / costPrice) * 100;
        }
        return 0;
    }
    
    // Override methods
    @Override
    public String toString() {
        return "Sales{" +
                "salesId='" + salesId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sales sales = (Sales) o;
        return Objects.equals(salesId, sales.salesId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(salesId);
    }
}

package org.example.marico_bangladesh.FIN.Business_analytics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class BusinessAnalytics {
    private String analyticsId;
    private String reportType;
    private String reportName;
    private LocalDateTime generatedDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String dataSource;
    private String analystName;
    private String department;
    private String region;
    private String productCategory;
    private double revenue;
    private double cost;
    private double profit;
    private double profitMargin;
    private int totalOrders;
    private int totalCustomers;
    private double averageOrderValue;
    private double marketShare;
    private double growthRate;
    private String trend;
    private String insights;
    private String recommendations;
    private String status;
    private String priority;
    private boolean isAutomated;
    private String exportFormat;
    
    // Default constructor
    public BusinessAnalytics() {
        this.analyticsId = generateAnalyticsId();
        this.generatedDate = LocalDateTime.now();
        this.status = "Draft";
        this.priority = "Medium";
        this.isAutomated = false;
    }
    
    // Parameterized constructor
    public BusinessAnalytics(String reportType, String reportName, LocalDate startDate, 
                           LocalDate endDate, String dataSource, String analystName) {
        this();
        this.reportType = reportType;
        this.reportName = reportName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dataSource = dataSource;
        this.analystName = analystName;
    }
    
    // Generate unique analytics ID
    private String generateAnalyticsId() {
        return "ANALYTICS-" + System.currentTimeMillis();
    }
    
    // Calculate key metrics
    public void calculateMetrics() {
        if (revenue > 0 && cost > 0) {
            this.profit = revenue - cost;
            this.profitMargin = (profit / revenue) * 100;
        }
        
        if (totalOrders > 0 && revenue > 0) {
            this.averageOrderValue = revenue / totalOrders;
        }
    }
    
    // Getters and Setters
    public String getAnalyticsId() {
        return analyticsId;
    }
    
    public void setAnalyticsId(String analyticsId) {
        this.analyticsId = analyticsId;
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public String getReportName() {
        return reportName;
    }
    
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    
    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }
    
    public void setGeneratedDate(LocalDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getDataSource() {
        return dataSource;
    }
    
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
    
    public String getAnalystName() {
        return analystName;
    }
    
    public void setAnalystName(String analystName) {
        this.analystName = analystName;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getProductCategory() {
        return productCategory;
    }
    
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
    
    public double getRevenue() {
        return revenue;
    }
    
    public void setRevenue(double revenue) {
        this.revenue = revenue;
        calculateMetrics();
    }
    
    public double getCost() {
        return cost;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
        calculateMetrics();
    }
    
    public double getProfit() {
        return profit;
    }
    
    public void setProfit(double profit) {
        this.profit = profit;
    }
    
    public double getProfitMargin() {
        return profitMargin;
    }
    
    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }
    
    public int getTotalOrders() {
        return totalOrders;
    }
    
    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
        calculateMetrics();
    }
    
    public int getTotalCustomers() {
        return totalCustomers;
    }
    
    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }
    
    public double getAverageOrderValue() {
        return averageOrderValue;
    }
    
    public void setAverageOrderValue(double averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }
    
    public double getMarketShare() {
        return marketShare;
    }
    
    public void setMarketShare(double marketShare) {
        this.marketShare = marketShare;
    }
    
    public double getGrowthRate() {
        return growthRate;
    }
    
    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
    }
    
    public String getTrend() {
        return trend;
    }
    
    public void setTrend(String trend) {
        this.trend = trend;
    }
    
    public String getInsights() {
        return insights;
    }
    
    public void setInsights(String insights) {
        this.insights = insights;
    }
    
    public String getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public boolean isAutomated() {
        return isAutomated;
    }
    
    public void setAutomated(boolean automated) {
        isAutomated = automated;
    }
    
    public String getExportFormat() {
        return exportFormat;
    }
    
    public void setExportFormat(String exportFormat) {
        this.exportFormat = exportFormat;
    }
    
    // Business methods
    public boolean isCompleted() {
        return "Completed".equalsIgnoreCase(status);
    }
    
    public boolean isDraft() {
        return "Draft".equalsIgnoreCase(status);
    }
    
    public boolean isHighPriority() {
        return "High".equalsIgnoreCase(priority);
    }
    
    public void markAsCompleted() {
        this.status = "Completed";
    }
    
    public void markAsInProgress() {
        this.status = "In Progress";
    }
    
    public void setHighPriority() {
        this.priority = "High";
    }
    
    public void setLowPriority() {
        this.priority = "Low";
    }
    
    public double getROI(double investment) {
        if (investment > 0) {
            return (profit / investment) * 100;
        }
        return 0;
    }
    
    public String getPerformanceCategory() {
        if (profitMargin >= 20) {
            return "Excellent";
        } else if (profitMargin >= 10) {
            return "Good";
        } else if (profitMargin >= 5) {
            return "Average";
        } else {
            return "Poor";
        }
    }
    
    public boolean isPositiveGrowth() {
        return growthRate > 0;
    }
    
    public String getTrendDirection() {
        if (growthRate > 5) {
            return "Strong Upward";
        } else if (growthRate > 0) {
            return "Upward";
        } else if (growthRate > -5) {
            return "Downward";
        } else {
            return "Strong Downward";
        }
    }
    
    // Override methods
    @Override
    public String toString() {
        return "BusinessAnalytics{" +
                "analyticsId='" + analyticsId + '\'' +
                ", reportName='" + reportName + '\'' +
                ", reportType='" + reportType + '\'' +
                ", revenue=" + revenue +
                ", profit=" + profit +
                ", profitMargin=" + profitMargin +
                ", status='" + status + '\'' +
                ", generatedDate=" + generatedDate +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessAnalytics that = (BusinessAnalytics) o;
        return Objects.equals(analyticsId, that.analyticsId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(analyticsId);
    }
}

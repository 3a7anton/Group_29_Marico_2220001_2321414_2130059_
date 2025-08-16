package org.example.marico_bangladesh.FIN.Business_analytics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

public class SalesPerformanceController {
    
    @FXML
    private DatePicker startDatePicker;
    
    @FXML
    private DatePicker endDatePicker;
    
    @FXML
    private ComboBox<String> regionComboBox;
    
    @FXML
    private ComboBox<String> productComboBox;
    
    @FXML
    private TableView<Object> salesTableView;
    
    @FXML
    private TableColumn<Object, String> dateColumn;
    
    @FXML
    private TableColumn<Object, String> regionColumn;
    
    @FXML
    private TableColumn<Object, String> productColumn;
    
    @FXML
    private TableColumn<Object, String> salesColumn;
    
    @FXML
    private TableColumn<Object, String> quantityColumn;
    
    @FXML
    private TableColumn<Object, String> performanceColumn;
    
    @FXML
    private Button generateReportButton;
    
    @FXML
    private Button backToDashboardButton;
    
    @FXML
    private Button clearFiltersButton;
    
    @FXML
    public void initialize() {
        setupEventHandlers();
        loadData();
    }
    
    private void setupEventHandlers() {
        generateReportButton.setOnAction(e -> generateReport());
        clearFiltersButton.setOnAction(e -> clearFilters());
    }
    
    private void loadData() {
        // Load regions and products into combo boxes
        regionComboBox.getItems().addAll("North", "South", "East", "West", "Central");
        productComboBox.getItems().addAll("Product A", "Product B", "Product C", "Product D");
        
        // Set default dates
        startDatePicker.setValue(java.time.LocalDate.now().minusDays(30));
        endDatePicker.setValue(java.time.LocalDate.now());
    }
    
    private void generateReport() {
        if (validateFilters()) {
            // Generate sales performance report
            loadSalesData();
        }
    }
    
    @FXML
    private void backToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/marico_bangladesh/FIN/Business_analytics/BusinessAnalyticsDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading BusinessAnalyticsDashboard.fxml: " + e.getMessage());
        }
    }
    
    private void clearFilters() {
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        regionComboBox.setValue(null);
        productComboBox.setValue(null);
        salesTableView.getItems().clear();
    }
    
    private boolean validateFilters() {
        if (startDatePicker.getValue() == null) {
            showAlert("Please select start date");
            return false;
        }
        if (endDatePicker.getValue() == null) {
            showAlert("Please select end date");
            return false;
        }
        if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            showAlert("Start date cannot be after end date");
            return false;
        }
        return true;
    }
    
    private void loadSalesData() {
        // Load sales data into table
        // This would typically involve database queries
        salesTableView.getItems().clear();
        // Add sample data or actual data loading logic here
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sales Performance Analysis");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

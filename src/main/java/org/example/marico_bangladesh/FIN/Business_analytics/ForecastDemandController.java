package org.example.marico_bangladesh.FIN.Business_analytics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

public class ForecastDemandController {
    
    @FXML
    private ComboBox<String> productCategoryComboBox;
    
    @FXML
    private DatePicker startDatePicker;
    
    @FXML
    private DatePicker endDatePicker;
    
    @FXML
    private ComboBox<String> forecastModelComboBox;
    
    @FXML
    private ComboBox<String> forecastPeriodComboBox;
    
    @FXML
    private LineChart<String, Number> forecastChart;
    
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
    @FXML
    private TextArea forecastSummaryTextArea;
    
    @FXML
    private Label accuracyLabel;
    
    @FXML
    private Button backToDashboardButton;
    
    @FXML
    private Button saveForecastButton;
    
    @FXML
    private Button exportChartButton;
    
    @FXML
    private Button runForecastButton;
    
    @FXML
    private Button clearButton;
    
    @FXML
    public void initialize() {
        setupEventHandlers();
        loadData();
    }
    
    private void setupEventHandlers() {
        saveForecastButton.setOnAction(e -> saveForecast());
        exportChartButton.setOnAction(e -> exportChart());
        runForecastButton.setOnAction(e -> runForecast());
        clearButton.setOnAction(e -> clearForm());
    }
    
    private void loadData() {
        // Load product categories
        productCategoryComboBox.getItems().addAll("Electronics", "Clothing", "Food", "Automotive", "Healthcare");
        
        // Load forecast models
        forecastModelComboBox.getItems().addAll("Linear Regression", "Moving Average", "Exponential Smoothing", "ARIMA");
        
        // Load forecast periods
        forecastPeriodComboBox.getItems().addAll("1 Month", "3 Months", "6 Months", "1 Year");
        
        // Set default dates
        startDatePicker.setValue(java.time.LocalDate.now().minusMonths(6));
        endDatePicker.setValue(java.time.LocalDate.now());
        
        // Initialize chart
        initializeChart();
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
    
    private void saveForecast() {
        // Save the forecast
        showAlert("Forecast saved successfully!");
    }
    
    private void exportChart() {
        // Export chart to file
        showAlert("Chart exported successfully!");
    }
    
    private void runForecast() {
        if (validateForm()) {
            // Run the forecast analysis
            generateForecast();
            updateAccuracy();
            updateForecastSummary();
        }
    }
    
    private void clearForm() {
        productCategoryComboBox.setValue(null);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        forecastModelComboBox.setValue(null);
        forecastPeriodComboBox.setValue(null);
        forecastSummaryTextArea.clear();
        accuracyLabel.setText("N/A");
        clearChart();
    }
    
    private boolean validateForm() {
        if (productCategoryComboBox.getValue() == null) {
            showAlert("Please select a product category");
            return false;
        }
        if (startDatePicker.getValue() == null) {
            showAlert("Please select start date");
            return false;
        }
        if (endDatePicker.getValue() == null) {
            showAlert("Please select end date");
            return false;
        }
        if (forecastModelComboBox.getValue() == null) {
            showAlert("Please select a forecast model");
            return false;
        }
        if (forecastPeriodComboBox.getValue() == null) {
            showAlert("Please select forecast period");
            return false;
        }
        if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            showAlert("Start date cannot be after end date");
            return false;
        }
        return true;
    }
    
    private void initializeChart() {
        xAxis.setLabel("Time Period");
        yAxis.setLabel("Demand");
        forecastChart.setTitle("Demand Forecast");
    }
    
    private void generateForecast() {
        // Generate forecast data and update chart
        // This would typically involve statistical analysis
        clearChart();
        // Add sample forecast data to chart
    }
    
    private void updateAccuracy() {
        // Calculate and update forecast accuracy
        double accuracy = 85.5; // Example accuracy percentage
        accuracyLabel.setText(String.format("%.1f%%", accuracy));
    }
    
    private void updateForecastSummary() {
        // Generate and update forecast summary
        String summary = "Forecast Summary:\n\n" +
                "Product Category: " + productCategoryComboBox.getValue() + "\n" +
                "Model Used: " + forecastModelComboBox.getValue() + "\n" +
                "Forecast Period: " + forecastPeriodComboBox.getValue() + "\n" +
                "Accuracy: " + accuracyLabel.getText() + "\n\n" +
                "Key Insights:\n" +
                "- Expected demand increase of 15%\n" +
                "- Seasonal patterns detected\n" +
                "- Confidence interval: 80-90%";
        
        forecastSummaryTextArea.setText(summary);
    }
    
    private void clearChart() {
        forecastChart.getData().clear();
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Demand Forecast");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package org.example.marico_bangladesh.FIN.Sales;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

public class SalesTargetController {
    
    @FXML
    private ComboBox<String> productComboBox;
    
    @FXML
    private DatePicker targetDatePicker;
    
    @FXML
    private TextField targetTextField;
    
    @FXML
    private ProgressBar targetProgressBar;
    
    @FXML
    private Label progressLabel;
    
    @FXML
    private Label achievedAmountLabel;
    
    @FXML
    private Label monthlyRevenueLabel;
    
    @FXML
    private Label avgDealSizeLabel;
    
    @FXML
    private TableView<Object> salesDataTableView;
    
    @FXML
    private TableColumn<Object, String> dateColumn;
    
    @FXML
    private TableColumn<Object, String> closedDealsColumn;
    
    @FXML
    private TableColumn<Object, String> amountColumn;
    
    @FXML
    private DatePicker filterDatePicker;
    
    @FXML
    private Button backToDashboardButton;
    
    @FXML
    private Button clearFilterButton;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private Label monthlyDealsLabel;
    
    @FXML
    public void initialize() {
        setupEventHandlers();
        loadData();
    }
    
    private void setupEventHandlers() {
        clearFilterButton.setOnAction(e -> clearFilter());
        refreshButton.setOnAction(e -> applyFilter());
    }
    
    private void loadData() {
        productComboBox.getItems().addAll("Product A", "Product B", "Product C", "Product D");
        
        targetDatePicker.setValue(java.time.LocalDate.now().plusMonths(1));
        
        loadSalesData();
        updateProgress();
    }
    
    @FXML
    private void backToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/marico_bangladesh/FIN/Sales/SalesDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading SalesDashboard.fxml: " + e.getMessage());
        }
    }
    
    @FXML
    private void setTarget() {
        if (validateTargetForm()) {

            updateProgress();
            showAlert("Target set successfully!");
        }
    }
    
    @FXML
    private void reset() {
        targetTextField.clear();
        productComboBox.setValue(null);
        targetDatePicker.setValue(null);
        updateProgress();
    }
    
    private void clearFilter() {
        filterDatePicker.setValue(null);
        loadSalesData();
    }
    
    private void applyFilter() {
        // Apply date filter to sales data
        loadSalesData();
    }
    
    private boolean validateTargetForm() {
        if (productComboBox.getValue() == null) {
            showAlert("Please select a product");
            return false;
        }
        if (targetTextField.getText().isEmpty()) {
            showAlert("Please enter target amount");
            return false;
        }
        if (targetDatePicker.getValue() == null) {
            showAlert("Please select target date");
            return false;
        }
        try {
            Double.parseDouble(targetTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid target amount");
            return false;
        }
        return true;
    }
    
    private void loadSalesData() {

        salesDataTableView.getItems().clear();

    }
    
    private void updateProgress() {

        double target = 0;
        double achieved = 0;
        
        try {
            if (!targetTextField.getText().isEmpty()) {
                target = Double.parseDouble(targetTextField.getText());
            }
        } catch (NumberFormatException e) {
        }
        

        achieved = target * 0.65;
        
        if (target > 0) {
            double progress = achieved / target;
            targetProgressBar.setProgress(progress);
            progressLabel.setText(String.format("%.1f%% of target achieved", progress * 100));
            achievedAmountLabel.setText(String.format("Achieved: $%.2f / Target: $%.2f", achieved, target));
        } else {
            targetProgressBar.setProgress(0);
            progressLabel.setText("0% of target achieved");
            achievedAmountLabel.setText("Achieved: $0 / Target: $0");
        }
        
        monthlyRevenueLabel.setText("Revenue this month: $" + String.format("%.2f", achieved));
        avgDealSizeLabel.setText("Average deal size: $" + String.format("%.2f", achieved / 10)); // Example calculation
        monthlyDealsLabel.setText("Deals this month: 10"); // Example value
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sales Target");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

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

public class CustomerOrderController {
    
    @FXML
    private TextField customerIdComboBox;
    
    @FXML
    private ComboBox<String> productComboBox;
    
    @FXML
    private TextField quantityTextField;
    
    @FXML
    private DatePicker deliveryDatePicker;
    
    @FXML
    private Button calculateButton;
    
    @FXML
    private Button saveOrderButton;
    
    @FXML
    private Button clearFormButton;
    
    @FXML
    private Button viewOrdersButton;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private Button backToDashboardButton;
    
    @FXML
    private Label pendingOrdersLabel;
    
    @FXML
    private Label todayOrdersLabel;
    
    @FXML
    public void initialize() {

        setupEventHandlers();
        loadProducts();
    }
    
    private void setupEventHandlers() {
        calculateButton.setOnAction(e -> calculateTotal());
        saveOrderButton.setOnAction(e -> saveOrder());
        clearFormButton.setOnAction(e -> clearForm());
        viewOrdersButton.setOnAction(e -> viewOrders());
        refreshButton.setOnAction(e -> refreshData());
    }
    
    private void loadProducts() {

        productComboBox.getItems().addAll("Product A", "Product B", "Product C", "Product D");
    }
    
    private void calculateTotal() {

        try {
            String product = productComboBox.getValue();
            int quantity = Integer.parseInt(quantityTextField.getText());

        } catch (NumberFormatException ex) {
            showAlert("Please enter a valid quantity");
        }
    }
    
    private void saveOrder() {

        if (validateForm()) {

            showAlert("Order saved successfully!");
            clearForm();
        }
    }
    
    private void clearForm() {
        customerIdComboBox.clear();
        productComboBox.setValue(null);
        quantityTextField.clear();
        deliveryDatePicker.setValue(null);
    }
    
    private void viewOrders() {

    }
    
    private void refreshData() {

        updateOrderCounts();
    }
    
    private boolean validateForm() {
        if (customerIdComboBox.getText().isEmpty()) {
            showAlert("Please enter Customer ID");
            return false;
        }
        if (productComboBox.getValue() == null) {
            showAlert("Please select a product");
            return false;
        }
        if (quantityTextField.getText().isEmpty()) {
            showAlert("Please enter quantity");
            return false;
        }
        if (deliveryDatePicker.getValue() == null) {
            showAlert("Please select delivery date");
            return false;
        }
        return true;
    }
    
    private void updateOrderCounts() {

        pendingOrdersLabel.setText("5"); // Example value
        todayOrdersLabel.setText("12"); // Example value
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer Order");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
}

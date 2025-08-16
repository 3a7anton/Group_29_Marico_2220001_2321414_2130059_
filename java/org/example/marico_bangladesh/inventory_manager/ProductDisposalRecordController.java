package org.example.marico_bangladesh.inventory_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class ProductDisposalRecordController extends BaseController {
    @FXML
    private TextField productIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextArea reasonField;
    @FXML
    private DatePicker disposalDate;
    @FXML
    private Label messageLabel;
    @FXML
    private Label currentStockLabel;

    @FXML
    public void initialize() {
        disposalDate.setValue(LocalDate.now());
    }

    @FXML
    private void handleSearch() {
        String productId = productIdField.getText();
        InventoryManager product = FileStorageUtil.findProduct(productId);
        if (product != null) {
            currentStockLabel.setText("Current Stock: " + product.getQuantity());
            messageLabel.setText("");
        } else {
            currentStockLabel.setText("");
            messageLabel.setText("Product not found");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleRecord(ActionEvent event) {
        try {
            if (productIdField.getText().isEmpty() || 
                quantityField.getText().isEmpty() || 
                reasonField.getText().isEmpty()) {
                messageLabel.setText("Please fill in all fields");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            String productId = productIdField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            InventoryManager product = FileStorageUtil.findProduct(productId);
            
            if (product == null) {
                messageLabel.setText("Product not found");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            if (quantity > product.getQuantity()) {
                messageLabel.setText("Disposal quantity cannot be greater than current stock");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }
            
            FileStorageUtil.updateStock(productId, quantity, "Remove Stock", "Disposal: " + reasonField.getText());
            messageLabel.setText("Disposal record added successfully");
            messageLabel.setStyle("-fx-text-fill: green;");
            
            clearFields();
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter a valid quantity");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        goBack(event);
    }

    @FXML
    private void handleClear() {
        clearFields();
    }

    private void clearFields() {
        productIdField.clear();
        quantityField.clear();
        reasonField.clear();
        disposalDate.setValue(LocalDate.now());
        messageLabel.setText("");
        currentStockLabel.setText("");
    }
}

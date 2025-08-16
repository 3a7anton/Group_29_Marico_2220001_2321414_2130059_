package org.example.marico_bangladesh.INVENTORY;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class UpdateStockQuantityController extends BaseController {
    @FXML
    private TextField productIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<String> updateTypeComboBox;
    @FXML
    private Label currentStockLabel;
    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        updateTypeComboBox.getItems().addAll("Add Stock", "Remove Stock");
        updateTypeComboBox.setValue("Add Stock");
    }

    @FXML
    private void handleSearch() {
        String productId = productIdField.getText();
        if (productId.isEmpty()) {
            messageLabel.setText("Please enter a product ID");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

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
    private void handleUpdate(ActionEvent event) {
        try {
            String productId = productIdField.getText();
            if (productId.isEmpty()) {
                messageLabel.setText("Please enter a product ID");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) {
                messageLabel.setText("Quantity must be greater than 0");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            String updateType = updateTypeComboBox.getValue();
            String reason = "Manual stock " + (updateType.equals("Add Stock") ? "addition" : "removal");
            
            FileStorageUtil.updateStock(productId, quantity, updateType, reason);
            handleSearch(); // Refresh the current stock display
            messageLabel.setText("Stock updated successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter a valid quantity!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        goBack(event);
    }

    @FXML
    private void handleClear() {
        productIdField.clear();
        quantityField.clear();
        currentStockLabel.setText("");
        messageLabel.setText("");
        updateTypeComboBox.setValue("Add Stock");
    }
}

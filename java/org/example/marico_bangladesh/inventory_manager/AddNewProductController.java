package org.example.marico_bangladesh.inventory_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class AddNewProductController extends BaseController {
    @FXML
    private TextField productIdField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField locationField;
    @FXML
    private Label messageLabel;

    @FXML
    private void handleSave(ActionEvent event) {
        try {
            // Validate input fields
            if (productIdField.getText().isEmpty() || 
                productNameField.getText().isEmpty() || 
                quantityField.getText().isEmpty() || 
                priceField.getText().isEmpty() ||
                categoryField.getText().isEmpty() ||
                locationField.getText().isEmpty()) {
                messageLabel.setText("Please fill in all fields");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            String productId = productIdField.getText();
            String productName = productNameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            String category = categoryField.getText();
            String location = locationField.getText();

            if (quantity < 0) {
                messageLabel.setText("Quantity cannot be negative");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            if (price < 0) {
                messageLabel.setText("Price cannot be negative");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            InventoryManager newProduct = new InventoryManager(productId, productName, quantity, price, category, location);
            FileStorageUtil.addProduct(newProduct);
            messageLabel.setText("Product added successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
            
            // Initial stock is logged automatically in FileStorageUtil.addProduct
            
            goBack(event);
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter valid numbers for quantity and price");
            messageLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            messageLabel.setText("Error adding product: " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        goBack(event);
    }
}

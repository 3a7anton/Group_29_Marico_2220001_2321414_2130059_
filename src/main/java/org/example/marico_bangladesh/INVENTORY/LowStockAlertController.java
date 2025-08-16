package org.example.marico_bangladesh.INVENTORY;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.util.List;
import javafx.scene.control.cell.PropertyValueFactory;

public class LowStockAlertController extends BaseController {
    @FXML
    private TableView<InventoryManager> alertTable;
    @FXML
    private TableColumn<InventoryManager, String> productIdColumn;
    @FXML
    private TableColumn<InventoryManager, String> productNameColumn;
    @FXML
    private TableColumn<InventoryManager, Integer> currentStockColumn;
    @FXML
    private TableColumn<InventoryManager, Integer> thresholdColumn;
    @FXML
    private TextField thresholdField;
    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        setupTable();
        loadLowStockItems();
    }

    private void setupTable() {
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        currentStockColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        // Threshold property exists in InventoryManager class
        thresholdColumn.setCellValueFactory(new PropertyValueFactory<>("threshold"));
    }

    private void loadLowStockItems() {
        // Clear existing items
        alertTable.getItems().clear();
        
        // Get low stock items
        List<InventoryManager> lowStockItems = FileStorageUtil.getLowStockItems();
            
        // Add to table
        alertTable.getItems().addAll(lowStockItems);
        
        // Update message
        if (lowStockItems.isEmpty()) {
            messageLabel.setText("No items are below threshold");
            messageLabel.setStyle("-fx-text-fill: green;");
        } else {
            messageLabel.setText(lowStockItems.size() + " items are below threshold");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleUpdateThreshold(ActionEvent event) {
        try {
            int threshold = Integer.parseInt(thresholdField.getText());
            // Update table with new threshold
            alertTable.getItems().clear();
            alertTable.getItems().addAll(FileStorageUtil.getLowStockItems(threshold));
            messageLabel.setText("Threshold updated successfully");
            messageLabel.setStyle("-fx-text-fill: green;");
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter a valid number");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        goBack(event);
    }

    @FXML
    private void handleRefresh() {
        loadLowStockItems();
        messageLabel.setText("Alert list refreshed");
        messageLabel.setStyle("-fx-text-fill: green;");
    }
}

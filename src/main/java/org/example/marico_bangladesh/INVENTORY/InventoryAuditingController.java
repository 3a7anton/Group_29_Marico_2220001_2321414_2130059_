package org.example.marico_bangladesh.INVENTORY;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventoryAuditingController extends BaseController {
    @FXML
    private TableView<InventoryManager> auditTable;
    @FXML
    private TableColumn<InventoryManager, String> productIdColumn;
    @FXML
    private TableColumn<InventoryManager, String> productNameColumn;
    @FXML
    private TableColumn<InventoryManager, Integer> quantityColumn;
    @FXML
    private TableColumn<InventoryManager, String> lastUpdatedColumn;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        setupTable();
    }

    private void setupTable() {
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        lastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
    }

    @FXML
    private void handleGenerateReport(ActionEvent event) {
        if (startDate.getValue() == null || endDate.getValue() == null) {
            messageLabel.setText("Please select both start and end dates");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Clear existing table data
        auditTable.getItems().clear();
        
        // Get all products and their current state
        List<InventoryManager> products = FileStorageUtil.getAllProducts();
        auditTable.getItems().addAll(products);
        messageLabel.setText("Report generated successfully");
        messageLabel.setStyle("-fx-text-fill: green;");
    }

    @FXML
    private void handleBack(ActionEvent event) {
        goBack(event);
    }

    @FXML
    private void handleExport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Inventory Report");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        
        File file = fileChooser.showSaveDialog(
            ((Node) event.getSource()).getScene().getWindow()
        );
        
        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                // Write header
                writer.println("Product ID,Product Name,Quantity,Price,Category,Location,Last Updated");
                
                // Write data
                for (InventoryManager product : auditTable.getItems()) {
                    writer.println(String.format("%s,%s,%d,%.2f,%s,%s,%s",
                        product.getProductId(),
                        product.getProductName(),
                        product.getQuantity(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getLocation(),
                        product.getLastUpdated()
                    ));
                }
                
                messageLabel.setText("Report exported successfully to " + file.getName());
                messageLabel.setStyle("-fx-text-fill: green;");
            } catch (IOException e) {
                messageLabel.setText("Error exporting report: " + e.getMessage());
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }
}

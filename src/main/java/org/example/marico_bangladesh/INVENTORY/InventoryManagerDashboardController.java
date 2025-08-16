package org.example.marico_bangladesh.INVENTORY;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InventoryManagerDashboardController extends BaseController {
    
    @FXML
    private void handleAddNewProduct(ActionEvent event) {
        loadScene("INVENTORY/add_new_product.fxml", event);
    }

    @FXML
    private void handleUpdateStock(ActionEvent event) {
        loadScene("INVENTORY/update_stock_quantity.fxml", event);
    }

    @FXML
    private void handleViewInventory(ActionEvent event) {
        loadScene("INVENTORY/view_inventory_report.fxml", event);
    }

    @FXML
    private void handleStockMovement(ActionEvent event) {
        loadScene("INVENTORY/stock_movement_log.fxml", event);
    }

    @FXML
    private void handleLowStockAlert(ActionEvent event) {
        loadScene("INVENTORY/low_stock_alert_monitoring.fxml", event);
    }

    @FXML
    private void handleReorderSuggestion(ActionEvent event) {
        loadScene("INVENTORY/reorder_product_suggestion.fxml", event);
    }

    @FXML
    private void handleInventoryAudit(ActionEvent event) {
        loadScene("INVENTORY/inventory_auditing.fxml", event);
    }

    @FXML
    private void handleProductDisposal(ActionEvent event) {
        loadScene("INVENTORY/product_disposal_record.fxml", event);
    }
    
    @FXML
    private void handleLogout(ActionEvent event) {
        logout(event);
    }
}

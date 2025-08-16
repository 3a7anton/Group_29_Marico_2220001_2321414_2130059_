package org.example.marico_bangladesh.inventory_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InventoryManagerDashboardController extends BaseController {
    
    @FXML
    private void handleAddNewProduct(ActionEvent event) {
        loadScene("HR/inventory_manager/add_new_product.fxml", event);
    }

    @FXML
    private void handleUpdateStock(ActionEvent event) {
        loadScene("HR/inventory_manager/update_stock_quantity.fxml", event);
    }

    @FXML
    private void handleViewInventory(ActionEvent event) {
        loadScene("HR/inventory_manager/view_inventory_report.fxml", event);
    }

    @FXML
    private void handleStockMovement(ActionEvent event) {
        loadScene("HR/inventory_manager/stock_movement_log.fxml", event);
    }

    @FXML
    private void handleLowStockAlert(ActionEvent event) {
        loadScene("HR/inventory_manager/low_stock_alert_monitoring.fxml", event);
    }

    @FXML
    private void handleReorderSuggestion(ActionEvent event) {
        loadScene("HR/inventory_manager/reorder_product_suggestion.fxml", event);
    }

    @FXML
    private void handleInventoryAudit(ActionEvent event) {
        loadScene("HR/inventory_manager/inventory_auditing.fxml", event);
    }

    @FXML
    private void handleProductDisposal(ActionEvent event) {
        loadScene("HR/inventory_manager/product_disposal_record.fxml", event);
    }
}

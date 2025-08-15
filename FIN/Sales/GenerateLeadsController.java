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

public class GenerateLeadsController {
    
    @FXML
    private TextField nameTextField;
    
    @FXML
    private TextField phoneTextField;
    
    @FXML
    private ComboBox<String> productComboBox;
    
    @FXML
    private TableView<Object> leadsTableView;
    
    @FXML
    private TableColumn<Object, String> nameColumn;
    
    @FXML
    private TableColumn<Object, String> contactColumn;
    
    @FXML
    private TableColumn<Object, String> productColumn;
    
    @FXML
    private Button backToDashboardButton;
    
    @FXML
    private Button exportLeadsButton;
    
    @FXML
    private Label totalLeadsLabel;
    
    @FXML
    private Label todayLeadsLabel;
    
    @FXML
    public void initialize() {
        setupEventHandlers();
        loadData();
    }
    
    private void setupEventHandlers() {
        exportLeadsButton.setOnAction(e -> exportLeads());
    }
    
    private void loadData() {

        productComboBox.getItems().addAll("Product A", "Product B", "Product C", "Product D");
        

        loadLeadsData();
        updateLeadCounts();
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
    
    private void exportLeads() {

        showAlert("Leads exported successfully!");
    }
    
    @FXML
    private void saveLead() {
        if (validateForm()) {

            showAlert("Lead saved successfully!");
            clearForm();
            loadLeadsData();
            updateLeadCounts();
        }
    }
    
    @FXML
    private void clearForm() {
        nameTextField.clear();
        phoneTextField.clear();
        productComboBox.setValue(null);
    }
    
    private boolean validateForm() {
        if (nameTextField.getText().isEmpty()) {
            showAlert("Please enter customer name");
            return false;
        }
        if (phoneTextField.getText().isEmpty()) {
            showAlert("Please enter contact number");
            return false;
        }
        if (productComboBox.getValue() == null) {
            showAlert("Please select product interest");
            return false;
        }
        return true;
    }
    
    private void loadLeadsData() {

        leadsTableView.getItems().clear();

    }
    
    private void updateLeadCounts() {

        totalLeadsLabel.setText("Total Leads: 25"); // Example value
        todayLeadsLabel.setText("Today's Leads: 3"); // Example value
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Generate Leads");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

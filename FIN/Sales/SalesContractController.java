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

public class SalesContractController {
    
    @FXML
    private TextField clientIdTextField;
    
    @FXML
    private TextField clientNameTextField;
    
    @FXML
    private TextField contractValueTextField;
    
    @FXML
    private TextArea contractTermsTextArea;
    
    @FXML
    private DatePicker startDatePicker;
    
    @FXML
    private DatePicker endDatePicker;
    
    @FXML
    private ComboBox<String> contractTypeComboBox;
    
    @FXML
    private Button backToDashboardButton;
    
    @FXML
    private Button validateButton;
    
    @FXML
    private Button previewButton;
    
    @FXML
    private Button saveContractButton;
    
    @FXML
    private Button generatePdfButton;
    
    @FXML
    private Button clearFormButton;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    private Label contractIdLabel;
    
    @FXML
    private Label createdDateLabel;
    
    @FXML
    private Label lastModifiedLabel;
    
    @FXML
    private Label contractStatusLabel;
    
    @FXML
    public void initialize() {
        setupEventHandlers();
        loadData();
    }
    
    private void setupEventHandlers() {
        validateButton.setOnAction(e -> validateContract());
        previewButton.setOnAction(e -> previewContract());
        saveContractButton.setOnAction(e -> saveContract());
        generatePdfButton.setOnAction(e -> generatePdf());
        clearFormButton.setOnAction(e -> clearForm());
    }
    
    private void loadData() {

        contractTypeComboBox.getItems().addAll("Service Contract", "Product Contract", "Maintenance Contract", "Consulting Contract");
        
        // Set default dates
        startDatePicker.setValue(java.time.LocalDate.now());
        endDatePicker.setValue(java.time.LocalDate.now().plusMonths(12));
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
    
    private void validateContract() {
        if (validateForm()) {
            statusLabel.setText("Contract validated successfully");
            showAlert("Contract validation passed!");
        }
    }
    
    private void previewContract() {
        if (validateForm()) {
            // Generate contract preview
            showAlert("Contract preview generated!");
        }
    }
    
    private void saveContract() {
        if (validateForm()) {
            // Save the contract
            statusLabel.setText("Contract saved successfully");
            updateContractInfo();
            showAlert("Contract saved successfully!");
        }
    }
    
    private void generatePdf() {
        if (validateForm()) {

            showAlert("PDF contract generated successfully!");
        }
    }
    
    private void clearForm() {
        clientIdTextField.clear();
        clientNameTextField.clear();
        contractValueTextField.clear();
        contractTermsTextArea.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        contractTypeComboBox.setValue(null);
        statusLabel.setText("Ready to create new contract");
        resetContractInfo();
    }
    
    private boolean validateForm() {
        if (clientIdTextField.getText().isEmpty()) {
            showAlert("Please enter Client ID");
            return false;
        }
        if (clientNameTextField.getText().isEmpty()) {
            showAlert("Please enter Client Name");
            return false;
        }
        if (contractValueTextField.getText().isEmpty()) {
            showAlert("Please enter Contract Value");
            return false;
        }
        if (startDatePicker.getValue() == null) {
            showAlert("Please select Start Date");
            return false;
        }
        if (endDatePicker.getValue() == null) {
            showAlert("Please select End Date");
            return false;
        }
        if (contractTypeComboBox.getValue() == null) {
            showAlert("Please select Contract Type");
            return false;
        }
        if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            showAlert("Start date cannot be after end date");
            return false;
        }
        return true;
    }
    
    private void updateContractInfo() {
        contractIdLabel.setText("Contract ID: " + generateContractId());
        createdDateLabel.setText("Created: " + java.time.LocalDate.now());
        lastModifiedLabel.setText("Modified: " + java.time.LocalDate.now());
        contractStatusLabel.setText("Status: Active");
    }
    
    private void resetContractInfo() {
        contractIdLabel.setText("Contract Info:");
        createdDateLabel.setText("Created: --");
        lastModifiedLabel.setText("Modified: --");
        contractStatusLabel.setText("Status: Draft");
    }
    
    private String generateContractId() {
        // Generate a unique contract ID
        return "CON-" + System.currentTimeMillis();
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sales Contract");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

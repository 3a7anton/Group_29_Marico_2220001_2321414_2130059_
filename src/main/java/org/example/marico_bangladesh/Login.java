package org.example.marico_bangladesh;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class Login {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    public void initialize() {
        // Populate the department ComboBox
        departmentComboBox.getItems().addAll("HR Officer", "HR Assistant", "Sales", "Business Analytics", "Inventory Manager");
    }

    @FXML
    public void onClickLogin(ActionEvent actionEvent) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String department = departmentComboBox.getValue();
        
        // Validate input fields
        if (username.isEmpty() || password.isEmpty() || department == null) {
            showAlert("Error", "Please fill in all fields", Alert.AlertType.ERROR);
            return;
        }
        
        // Authentication logic
        if (authenticateUser(username, password, department)) {
            try {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Parent root;
                String title;
                
                if ("HR Officer".equals(department)) {
                    root = FXMLLoader.load(getClass().getResource("/org/example/marico_bangladesh/HR/hr_officer/hr_officer_dashboard.fxml"));
                    title = "HR Officer Dashboard - Welcome " + username;
                } else if ("HR Assistant".equals(department)) {
                    root = FXMLLoader.load(getClass().getResource("/org/example/marico_bangladesh/HR/hr_assistant/hr_assistant_dashboard.fxml"));
                    title = "HR Assistant Dashboard - Welcome " + username;
                } else if ("Sales".equals(department)) {
                    root = FXMLLoader.load(getClass().getResource("/org/example/marico_bangladesh/FIN/Sales/SalesDashboard.fxml"));
                    title = "Sales Dashboard - Welcome " + username;
                } else if ("Business Analytics".equals(department)) {
                    root = FXMLLoader.load(getClass().getResource("/org/example/marico_bangladesh/FIN/Business_analytics/BusinessAnalyticsDashboard.fxml"));
                    title = "Business Analytics Dashboard - Welcome " + username;
                } else if ("Inventory Manager".equals(department)) {
                    root = FXMLLoader.load(getClass().getResource("/org/example/marico_bangladesh/INVENTORY/inventory_manager_dashboard.fxml"));
                    title = "Inventory Manager Dashboard - Welcome " + username;
                } else {
                    showAlert("Error", "Invalid department selection", Alert.AlertType.ERROR);
                    return;
                }
                
                Scene scene = new Scene(root);
                stage.setTitle(title);
                stage.setScene(scene);
                stage.show();
                
            } catch (IOException e) {
                showAlert("Error", "Could not load dashboard: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            showAlert("Login Failed", "Invalid username or password", Alert.AlertType.WARNING);
        }
    }
    
    private boolean authenticateUser(String username, String password, String department) {
        // HR Department users
        if ("HR Officer".equals(department) || "HR Assistant".equals(department)) {
            return "anton".equals(username) && "anton7".equals(password);
        }
        
        // Sales Department users
        if ("Sales".equals(department)) {
            return "ishmam".equals(username) && "ishmam9".equals(password);
        }
        
        // Business Analytics Department users
        if ("Business Analytics".equals(department)) {
            return "ishmam".equals(username) && "ishmam9".equals(password);
        }
        
        // Inventory Manager Department users
        if ("Inventory Manager".equals(department)) {
            return "sajib".equals(username) && "sajib1".equals(password);
        }
        
        return false;
    }
    
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package org.example.marico_bangladesh.FIN.Business_analytics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

public class BusinessAnalyticsDashboardController {
    
    @FXML
    private Button salesPerformanceButton;
    
    @FXML
    private Button demandForecastButton;
    
    @FXML
    public void initialize() {

    }
    
    @FXML
    private void salesPerformanceButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/marico_bangladesh/FIN/Business_analytics/SalesPerformance.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading SalesPerformance.fxml: " + e.getMessage());
        }
    }
    
    @FXML
    private void demandForecastButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/marico_bangladesh/FIN/Business_analytics/ForecastDemand.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading ForecastDemand.fxml: " + e.getMessage());
        }
    }
}

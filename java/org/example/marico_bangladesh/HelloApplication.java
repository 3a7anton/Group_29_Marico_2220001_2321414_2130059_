package org.example.marico_bangladesh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.marico_bangladesh.inventory_manager.FileStorageUtil;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Initialize file storage
            FileStorageUtil.loadData();
            
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("HR/inventory_manager/inventory_manager_dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
            stage.setTitle("Marico Bangladesh - Inventory Management System");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Show error dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Failed to initialize database");
            alert.setContentText("Please make sure MySQL is running and the connection settings are correct.\n\nError: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
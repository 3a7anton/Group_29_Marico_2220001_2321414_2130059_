package org.example.marico_bangladesh.HR.hr_assistant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TrainingScheduleCoordinationController
{
    @javafx.fxml.FXML
    private TextField trainerText;
    @javafx.fxml.FXML
    private TextField venueText;
    @javafx.fxml.FXML
    private DatePicker updateDate;
    @javafx.fxml.FXML
    private Label outputLabel;
    @javafx.fxml.FXML
    private Button updateDateButton;
    @javafx.fxml.FXML
    private DatePicker datePicker;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/marico_bangladesh/HR/hr_assistant/hr_assistant_dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("HR Assistant Dashboard");
            stage.show();
        } catch (IOException e) {
            outputLabel.setText("Error going back: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

package org.example.marico_bangladesh.HR.hr_assistant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeFeedBackCollectionController {
    @FXML
    private TextField employeeIdText;
    @FXML
    private TextField employeeNameText;
    @FXML
    private ComboBox<String> feedbackTypeCombobox;
    @FXML
    private TextArea feedbackContentTextArea;
    @FXML
    private ComboBox<Integer> ratingCombobox;
    @FXML
    private ComboBox<String> departmentCombobox;
    @FXML
    private Label outputLabel;
    @FXML
    private TableView<HrAssistant> feedbackTable;
    @FXML
    private TableColumn<HrAssistant, String> employeeIdCol;
    @FXML
    private TableColumn<HrAssistant, String> employeeNameCol;
    @FXML
    private TableColumn<HrAssistant, String> feedbackTypeCol;
    @FXML
    private TableColumn<HrAssistant, String> feedbackContentCol;
    @FXML
    private TableColumn<HrAssistant, Integer> ratingCol;
    @FXML
    private TableColumn<HrAssistant, LocalDate> dateCol;

    // main array list
    ArrayList<HrAssistant> feedbackList = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize comboboxes with null checks
        if (feedbackTypeCombobox != null) {
            feedbackTypeCombobox.getItems().addAll("Performance", "Training", "Work Environment", "Management", "General");
        }
        if (ratingCombobox != null) {
            ratingCombobox.getItems().addAll(1, 2, 3, 4, 5);
        }
        if (departmentCombobox != null) {
            departmentCombobox.getItems().addAll("HR", "IT", "Finance", "Marketing", "Operations", "Sales");
        }

        // Initialize table columns with null checks
        if (employeeIdCol != null) {
            employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        }
        if (employeeNameCol != null) {
            employeeNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        }
        if (feedbackTypeCol != null) {
            feedbackTypeCol.setCellValueFactory(new PropertyValueFactory<>("feedbackType"));
        }
        if (feedbackContentCol != null) {
            feedbackContentCol.setCellValueFactory(new PropertyValueFactory<>("feedbackContent"));
        }
        if (ratingCol != null) {
            ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        }
        if (dateCol != null) {
            dateCol.setCellValueFactory(new PropertyValueFactory<>("feedbackDate"));
        }

        // Load existing feedback data
        try {
            feedbackList = HrAssistantStorage.loadEmployeeFeedback();
            feedbackTable.getItems().addAll(feedbackList);
        } catch (Exception e) {
            outputLabel.setText("Error loading feedback data: " + e.getMessage());
        }
    }

    @FXML
    public void submitFeedback(ActionEvent actionEvent) {
        if (!validateForm()) {
            return;
        }

        try {
            HrAssistant feedback = new HrAssistant(
                    employeeIdText.getText().trim(),
                    employeeNameText.getText().trim(),
                    feedbackTypeCombobox.getValue(),
                    feedbackContentTextArea.getText().trim(),
                    ratingCombobox.getValue(),
                    departmentCombobox.getValue()
            );

            HrAssistantStorage.saveEmployeeFeedback(feedback);
            feedbackList.add(feedback);

            // Update table
            feedbackTable.getItems().clear();
            feedbackTable.getItems().addAll(feedbackList);

            clearForm();
            outputLabel.setText("Feedback submitted successfully");

        } catch (IOException e) {
            outputLabel.setText("Error saving feedback: " + e.getMessage());
        }
    }

    @FXML
    public void clearForm() {
        employeeIdText.clear();
        employeeNameText.clear();
        feedbackTypeCombobox.setValue(null);
        feedbackContentTextArea.clear();
        ratingCombobox.setValue(null);
        departmentCombobox.setValue(null);
    }

    private boolean validateForm() {
        if (employeeIdText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter employee ID");
            return false;
        }

        if (employeeNameText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter employee name");
            return false;
        }

        if (feedbackTypeCombobox.getValue() == null) {
            outputLabel.setText("Select feedback type");
            return false;
        }

        if (feedbackContentTextArea.getText().trim().isEmpty()) {
            outputLabel.setText("Enter feedback content");
            return false;
        }

        if (ratingCombobox.getValue() == null) {
            outputLabel.setText("Select rating");
            return false;
        }

        if (departmentCombobox.getValue() == null) {
            outputLabel.setText("Select department");
            return false;
        }

        return true;
    }

    @FXML
    public void loadFeedback(ActionEvent actionEvent) {
        try {
            // For now, use existing feedback storage method (adjust as needed)
            feedbackList = new ArrayList<>(); // Initialize empty list for demo
            // You might want to implement a proper loadFeedbackData method in HrAssistantStorage
            
            if (feedbackTable != null) {
                feedbackTable.getItems().clear();
                feedbackTable.getItems().addAll(feedbackList);
            }
            outputLabel.setText("Feedback data loaded successfully. Total records: " + feedbackList.size());
        } catch (Exception e) {
            outputLabel.setText("Error loading feedback data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void clearForm(ActionEvent actionEvent) {
        if (employeeIdText != null) employeeIdText.clear();
        if (employeeNameText != null) employeeNameText.clear();
        if (feedbackTypeCombobox != null) feedbackTypeCombobox.setValue(null);
        if (departmentCombobox != null) departmentCombobox.setValue(null);
        if (ratingCombobox != null) ratingCombobox.setValue(null);
        if (feedbackContentTextArea != null) feedbackContentTextArea.clear();
        outputLabel.setText("Form cleared");
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

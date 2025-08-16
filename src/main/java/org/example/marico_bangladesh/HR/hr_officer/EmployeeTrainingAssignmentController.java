package org.example.marico_bangladesh.HR.hr_officer;

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

public class EmployeeTrainingAssignmentController
{
    @FXML
    private Button backButton;
    @FXML
    private TextField employeeIdTextField;
    @FXML
    private TextField employeeNameTextField;
    @FXML
    private Button searchEmployeeButton;
    @FXML
    private ComboBox<String> trainingProgramComboBox;
    @FXML
    private TextField trainerTextField;
    @FXML
    private TextField venueTextField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField durationTextField;
    @FXML
    private ComboBox<String> priorityComboBox;
    @FXML
    private TextArea trainingDetailsTextArea;
    @FXML
    private Button assignTrainingButton;
    @FXML
    private Label outputLabel;
    
    @FXML
    private TableView<hrOfficer> trainingAssignmentsTableView;
    @FXML
    private TableColumn<hrOfficer, String> assignmentIdCol;
    @FXML
    private TableColumn<hrOfficer, String> empIdCol;
    @FXML
    private TableColumn<hrOfficer, String> empNameCol;
    @FXML
    private TableColumn<hrOfficer, String> trainingProgramCol;
    @FXML
    private TableColumn<hrOfficer, String> trainerCol;
    @FXML
    private TableColumn<hrOfficer, String> venueCol;
    @FXML
    private TableColumn<hrOfficer, LocalDate> startDateCol;
    @FXML
    private TableColumn<hrOfficer, LocalDate> endDateCol;

    // main array lists
    ArrayList<hrOfficer> employeeList = new ArrayList<>();
    ArrayList<hrOfficer> trainingList = new ArrayList<>();
    
    @FXML
    public void initialize() {
        // Initialize ComboBoxes
        if (trainingProgramComboBox != null) {
            trainingProgramComboBox.getItems().addAll(
                "Leadership Development", "Technical Skills", "Safety Training", 
                "Customer Service", "Project Management", "Communication Skills",
                "Software Training", "Compliance Training", "Sales Training"
            );
        }
        
        if (priorityComboBox != null) {
            priorityComboBox.getItems().addAll("High", "Medium", "Low");
        }

        // Initialize table columns
        if (assignmentIdCol != null) assignmentIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        if (empIdCol != null) empIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        if (empNameCol != null) empNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        if (trainingProgramCol != null) trainingProgramCol.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        if (trainerCol != null) trainerCol.setCellValueFactory(new PropertyValueFactory<>("comments"));
        if (venueCol != null) venueCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        if (startDateCol != null) startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        if (endDateCol != null) endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        // Load employee data using HrOfficerStorage
        try {
            ArrayList<hrOfficer> loadedEmployees = (ArrayList<hrOfficer>) HrOfficerStorage.loadAllEmployees();
            employeeList.addAll(loadedEmployees);
            
            // Load training data using HrOfficerStorage
            ArrayList<hrOfficer> loadedTraining = (ArrayList<hrOfficer>) HrOfficerStorage.loadAllTraining();
            trainingList.addAll(loadedTraining);
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        if (trainingAssignmentsTableView != null) {
            trainingAssignmentsTableView.getItems().addAll(trainingList);
        }
    }

    @FXML
    public void searchEmployee(ActionEvent actionEvent) {
        if (employeeIdTextField == null) return;

        if (employeeIdTextField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter employee ID to search");
            return;
        }

        String searchId = employeeIdTextField.getText().trim();
        boolean found = false;

        for (hrOfficer emp : employeeList) {
            if (emp.getEmployeeId() != null && emp.getEmployeeId().equals(searchId)) {
                if (employeeNameTextField != null) {
                    employeeNameTextField.setText(emp.getEmployeeName() != null ? emp.getEmployeeName() : "");
                }
                found = true;
                break;
            }
        }

        if (found) {
            if (outputLabel != null) outputLabel.setText("Employee found");
        } else {
            if (outputLabel != null) outputLabel.setText("Employee not found");
            if (employeeNameTextField != null) employeeNameTextField.clear();
        }
    }

    @FXML
    public void assignTraining(ActionEvent actionEvent) {
        if (employeeIdTextField == null || employeeNameTextField == null || 
            trainingProgramComboBox == null || trainerTextField == null || 
            startDatePicker == null || endDatePicker == null || 
            priorityComboBox == null) {
            return;
        }

        if (employeeIdTextField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter employee ID");
            return;
        }

        if (employeeNameTextField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Search for employee first");
            return;
        }

        if (trainingProgramComboBox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select training program");
            return;
        }

        if (trainerTextField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter trainer name");
            return;
        }

        if (startDatePicker.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select training start date");
            return;
        }

        if (endDatePicker.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select training end date");
            return;
        }

        if (priorityComboBox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select training priority");
            return;
        }

        // Check if training is already assigned to this employee
        for (hrOfficer training : trainingList) {
            if (training.getEmployeeId() != null && training.getEmployeeId().equals(employeeIdTextField.getText()) &&
                training.getJobTitle() != null && training.getJobTitle().equals(trainingProgramComboBox.getValue())) {
                if (outputLabel != null) outputLabel.setText("Training already assigned to this employee");
                return;
            }
        }

        // Create training assignment record
        hrOfficer trainingAssignment = new hrOfficer(
                employeeIdTextField.getText(),
                employeeNameTextField.getText(),
                venueTextField.getText(), // Using venue as department field
                "Training Assignment", // Position
                0.0, // Salary not applicable
                priorityComboBox.getValue(), // Status
                startDatePicker.getValue() // Start date
        );
        
        // Set training-specific fields
        trainingAssignment.setJobTitle(trainingProgramComboBox.getValue());
        trainingAssignment.setEndDate(endDatePicker.getValue());
        trainingAssignment.setComments(trainerTextField.getText() + " | " + 
                                     (trainingDetailsTextArea != null ? trainingDetailsTextArea.getText() : ""));

        // Save using HrOfficerStorage
        boolean saved = HrOfficerStorage.appendTraining(trainingAssignment);
        if (!saved) {
            if (outputLabel != null) outputLabel.setText("Failed to save training assignment");
            return;
        }

        trainingList.add(trainingAssignment);

        if (outputLabel != null) outputLabel.setText("Training assigned successfully");

        // clear and refresh table
        if (trainingAssignmentsTableView != null) {
            trainingAssignmentsTableView.getItems().clear();
            trainingAssignmentsTableView.getItems().addAll(trainingList);
        }

        // clear input fields
        clearFieldsInternal();
    }

    @FXML
    public void notifyEmployee(ActionEvent actionEvent) {
        if (employeeIdTextField == null || employeeNameTextField == null) {
            if (outputLabel != null) outputLabel.setText("Employee information required");
            return;
        }

        if (employeeIdTextField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter employee ID");
            return;
        }

        if (trainingProgramComboBox == null || trainingProgramComboBox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select training program");
            return;
        }

        String employeeId = employeeIdTextField.getText().trim();
        String employeeName = employeeNameTextField.getText().trim();
        String trainingProgram = trainingProgramComboBox.getValue();
        String trainer = trainerTextField != null ? trainerTextField.getText() : "";
        String venue = venueTextField != null ? venueTextField.getText() : "";
        
        // Create notification message
        String notification = String.format(
            "Training Notification for %s (ID: %s)\n" +
            "Program: %s\n" +
            "Trainer: %s\n" +
            "Venue: %s\n" +
            "Start Date: %s\n" +
            "Please confirm your attendance.",
            employeeName, employeeId, trainingProgram, trainer, venue,
            startDatePicker != null ? startDatePicker.getValue() : "TBD"
        );

        // In a real application, this would send an email/notification
        System.out.println("=== TRAINING NOTIFICATION ===");
        System.out.println(notification);

        if (outputLabel != null) outputLabel.setText("Training notification sent to " + employeeName);
    }

    @FXML
    public void saveAssignment(ActionEvent actionEvent) {
        if (trainingList.isEmpty()) {
            if (outputLabel != null) outputLabel.setText("No training assignments to save");
            return;
        }

        // Save all training assignments
        boolean allSaved = true;
        for (hrOfficer training : trainingList) {
            if (!HrOfficerStorage.appendTraining(training)) {
                allSaved = false;
                break;
            }
        }

        if (allSaved) {
            if (outputLabel != null) outputLabel.setText("All training assignments saved successfully");
        } else {
            if (outputLabel != null) outputLabel.setText("Some assignments failed to save");
        }
    }

    @FXML
    public void clearFields(ActionEvent actionEvent) {
        if (employeeIdTextField != null) employeeIdTextField.clear();
        if (employeeNameTextField != null) employeeNameTextField.clear();
        if (trainingProgramComboBox != null) trainingProgramComboBox.getSelectionModel().clearSelection();
        if (trainerTextField != null) trainerTextField.clear();
        if (venueTextField != null) venueTextField.clear();
        if (startDatePicker != null) startDatePicker.setValue(null);
        if (endDatePicker != null) endDatePicker.setValue(null);
        if (durationTextField != null) durationTextField.clear();
        if (priorityComboBox != null) priorityComboBox.getSelectionModel().clearSelection();
        if (trainingDetailsTextArea != null) trainingDetailsTextArea.clear();
        
        if (outputLabel != null) outputLabel.setText("Fields cleared");
    }

    private void clearFieldsInternal() {
        if (employeeIdTextField != null) employeeIdTextField.clear();
        if (employeeNameTextField != null) employeeNameTextField.clear();
        if (trainingProgramComboBox != null) trainingProgramComboBox.getSelectionModel().clearSelection();
        if (trainerTextField != null) trainerTextField.clear();
        if (venueTextField != null) venueTextField.clear();
        if (startDatePicker != null) startDatePicker.setValue(null);
        if (endDatePicker != null) endDatePicker.setValue(null);
        if (durationTextField != null) durationTextField.clear();
        if (priorityComboBox != null) priorityComboBox.getSelectionModel().clearSelection();
        if (trainingDetailsTextArea != null) trainingDetailsTextArea.clear();
    }
    
    @FXML
    public void backButton(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/marico_bangladesh/HR/hr_officer/hr_officer_dashboard.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("HR Officer Dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

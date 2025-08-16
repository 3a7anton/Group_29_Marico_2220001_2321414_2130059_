package org.example.marico_bangladesh.HR.hr_assistant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HrAssistantDashboardController
{
    @FXML
    private Button employeeDataEntryButton;
    @FXML
    private Button leaveRequestButton;
    @FXML
    private Button hrReportButton;
    @FXML
    private Button trainingScheduleButton;
    @FXML
    private Button attendanceTrackingButton;
    @FXML
    private Button employeeQueryButton;
    @FXML
    private Button employeeFeedbackButton;
    @FXML
    private Button documentScanningButton;
    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        System.out.println("HR Assistant Dashboard initialized");
    }

    @FXML
    public void goToEmployeeDataEntry(ActionEvent actionEvent) {
        navigateToScreen("/org/example/marico_bangladesh/HR/hr_assistant/EmployeeDataEntry.fxml", "Employee Data Entry", actionEvent);
    }

    @FXML
    public void goToAttendanceTracking(ActionEvent actionEvent) {
        navigateToScreen("/org/example/marico_bangladesh/HR/hr_assistant/AttendanceTracking.fxml", "Attendance Tracking", actionEvent);
    }

    @FXML
    public void goToTrainingSchedule(ActionEvent actionEvent) {
        navigateToScreen("/org/example/marico_bangladesh/HR/hr_assistant/Training ScheduleCoordination.fxml", "Training Schedule", actionEvent);
    }

    @FXML
    public void goToDocumentScanning(ActionEvent actionEvent) {
        navigateToScreen("/org/example/marico_bangladesh/HR/hr_assistant/DocumentScanning.fxml", "Document Scanning", actionEvent);
    }

    @FXML
    public void goToEmployeeQuery(ActionEvent actionEvent) {
        navigateToScreen("/org/example/marico_bangladesh/HR/hr_assistant/EmployeeQueryHandling.fxml", "Employee Query", actionEvent);
    }

    @FXML
    public void goToLeaveRequest(ActionEvent actionEvent) {
        navigateToScreen("/org/example/marico_bangladesh/HR/hr_assistant/LeaveRequestSubmission.fxml", "Leave Request", actionEvent);
    }

    @FXML
    public void goToHRReport(ActionEvent actionEvent) {
        navigateToScreen("/org/example/marico_bangladesh/HR/hr_assistant/HR_ReportGeneration.fxml", "HR Report", actionEvent);
    }

    @FXML
    public void goToEmployeeFeedback(ActionEvent actionEvent) {
        navigateToScreen("/org/example/marico_bangladesh/HR/hr_assistant/EmployeeFeedBackCollection.fxml", "Employee Feedback", actionEvent);
    }

    @FXML
    public void logoutButton(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/marico_bangladesh/login.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Marico Bangladesh - Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading login: " + e.getMessage());
        }
    }

    private void navigateToScreen(String fxmlPath, String title, ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading " + title + ": " + e.getMessage());
        }
    }
}

package org.example.marico_bangladesh.HR.hr_officer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HrOfficerDashboardController
{
    @FXML
    private Button jobPostingButton;
    
    @FXML
    private Button applicationsButton;
    
    @FXML
    private Button leaveManagementButton;
    
    @FXML
    private Button performanceReviewButton;
    
    @FXML
    private Button salaryStructureButton;
    
    @FXML
    private Button terminationProcessButton;
    
    @FXML
    private Button complianceAuditButton;
    
    @FXML
    private Button trainingAssignmentButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    public void initialize() {
    }
    
    @FXML
    public void jobPostingButton(ActionEvent actionEvent) {
        navigateToScreen(actionEvent, "/org/example/marico_bangladesh/HR/hr_officer/ManageJobPosting.fxml", "Manage Job Posting");
    }
    
    @FXML
    public void applicationsButton(ActionEvent actionEvent) {
        navigateToScreen(actionEvent, "/org/example/marico_bangladesh/HR/hr_officer/ReviewApplications.fxml", "Review Applications");
    }
    
    @FXML
    public void leaveManagementButton(ActionEvent actionEvent) {
        navigateToScreen(actionEvent, "/org/example/marico_bangladesh/HR/hr_officer/LeaveApproval.fxml", "Leave Management");
    }
    
    @FXML
    public void performanceReviewButton(ActionEvent actionEvent) {
        navigateToScreen(actionEvent, "/org/example/marico_bangladesh/HR/hr_officer/PerformanceReview.fxml", "Performance Review");
    }
    
    @FXML
    public void salaryStructureButton(ActionEvent actionEvent) {
        navigateToScreen(actionEvent, "/org/example/marico_bangladesh/HR/hr_officer/SalaryStructureUpdate.fxml", "Salary Structure Update");
    }
    
    @FXML
    public void terminationProcessButton(ActionEvent actionEvent) {
        navigateToScreen(actionEvent, "/org/example/marico_bangladesh/HR/hr_officer/EmployeeTermination.fxml", "Employee Termination");
    }
    
    @FXML
    public void complianceAuditButton(ActionEvent actionEvent) {
        navigateToScreen(actionEvent, "/org/example/marico_bangladesh/HR/hr_officer/ComplianceAudit.fxml", "Compliance Audit");
    }
    
    @FXML
    public void trainingAssignmentButton(ActionEvent actionEvent) {
        navigateToScreen(actionEvent, "/org/example/marico_bangladesh/HR/hr_officer/EmployeeTrainingAssignment.fxml", "Employee Training Assignment");
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
        }
    }
    
    private void navigateToScreen(ActionEvent actionEvent, String fxmlPath, String title) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

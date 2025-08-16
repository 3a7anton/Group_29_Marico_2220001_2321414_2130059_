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
import org.example.marico_bangladesh.HR.hr_assistant.HrAssistant;
import org.example.marico_bangladesh.HR.hr_assistant.HrAssistantStorage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class LeaveApprovalController
{
    @FXML
    private Button updateButton;
    @FXML
    private Button backButton;
    @FXML
    private Button approveButton;
    @FXML
    private Button rejectButton;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<hrOfficer> tableViewLeaveApproval;
    @FXML
    private TableColumn<hrOfficer, String> employeeIdCol;
    @FXML
    private TableColumn<hrOfficer, String> employeeNameCol;
    @FXML
    private TableColumn<hrOfficer, String> leaveTypeCol;
    @FXML
    private TableColumn<hrOfficer, LocalDate> startDateCol;
    @FXML
    private TableColumn<hrOfficer, LocalDate> endDateCol;
    @FXML
    private TableColumn<hrOfficer, String> reasonCol;
    @FXML
    private TableColumn<hrOfficer, String> statusCol;
    @FXML
    private Label outputLabel;

    // main array lists
    ArrayList<hrOfficer> leaveList = new ArrayList<>();
    ArrayList<HrAssistant> hrAssistantLeaveRequests = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize table columns with null checks
        if (employeeIdCol != null) {
            employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        }
        if (employeeNameCol != null) {
            employeeNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        }
        if (leaveTypeCol != null) {
            leaveTypeCol.setCellValueFactory(new PropertyValueFactory<>("leaveType"));
        }
        if (startDateCol != null) {
            startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        }
        if (endDateCol != null) {
            endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        }
        if (reasonCol != null) {
            reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
        }
        if (statusCol != null) {
            statusCol.setCellValueFactory(new PropertyValueFactory<>("leaveStatus"));
        }

        // Load leave requests from HR Assistant's leave_request.bin file
        try {
            hrAssistantLeaveRequests = HrAssistantStorage.loadLeaveRequests();
            
            // Convert HrAssistant leave requests to hrOfficer format for display
            for (HrAssistant assistantLeave : hrAssistantLeaveRequests) {
                hrOfficer officerLeave = new hrOfficer(
                    assistantLeave.getEmployeeId(),
                    assistantLeave.getEmployeeName(),
                    assistantLeave.getLeaveType(),
                    assistantLeave.getStartDate(),
                    assistantLeave.getEndDate(),
                    assistantLeave.getLeaveStatus() != null ? assistantLeave.getLeaveStatus() : "Pending"
                );
                // Set the reason field separately since it's not in the constructor
                officerLeave.setReason(assistantLeave.getReason());
                leaveList.add(officerLeave);
            }
            
            if (outputLabel != null) {
                outputLabel.setText("Loaded " + hrAssistantLeaveRequests.size() + " leave requests from leave_request.bin");
            }
        } catch (Exception e) {
            System.out.println("Error loading leave requests from HR Assistant: " + e.getMessage());
            if (outputLabel != null) {
                outputLabel.setText("Error loading leave requests: " + e.getMessage());
            }
        }

        if (tableViewLeaveApproval != null) {
            tableViewLeaveApproval.getItems().addAll(leaveList);
        }
    }

    @FXML
    public void approveLeave(ActionEvent actionEvent) {
        hrOfficer selectedLeave = tableViewLeaveApproval.getSelectionModel().getSelectedItem();
        if (selectedLeave == null) {
            if (outputLabel != null) outputLabel.setText("Please select a leave request to approve");
            return;
        }

        selectedLeave.setLeaveStatus("Approved");
        if (tableViewLeaveApproval != null) {
            tableViewLeaveApproval.refresh();
        }
        if (outputLabel != null) outputLabel.setText("Leave request approved successfully");
    }

    @FXML
    public void rejectLeave(ActionEvent actionEvent) {
        hrOfficer selectedLeave = tableViewLeaveApproval.getSelectionModel().getSelectedItem();
        if (selectedLeave == null) {
            if (outputLabel != null) outputLabel.setText("Please select a leave request to reject");
            return;
        }

        selectedLeave.setLeaveStatus("Rejected");
        if (tableViewLeaveApproval != null) {
            tableViewLeaveApproval.refresh();
        }
        if (outputLabel != null) outputLabel.setText("Leave request rejected successfully");
    }

    @FXML
    public void updateDatabase(ActionEvent actionEvent) {
        try {
            // Update the status of corresponding HrAssistant records and save back to leave_request.bin
            for (int i = 0; i < leaveList.size() && i < hrAssistantLeaveRequests.size(); i++) {
                hrOfficer officerLeave = leaveList.get(i);
                HrAssistant assistantLeave = hrAssistantLeaveRequests.get(i);
                
                // Update the HrAssistant record with the new status
                assistantLeave.setLeaveStatus(officerLeave.getLeaveStatus());
            }
            
            // Save all updated leave requests back to leave_request.bin
            // Note: This overwrites the file with updated records
            java.io.File file = new java.io.File("leave_request.bin");
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(file, false);
                 java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos)) {
                for (HrAssistant leave : hrAssistantLeaveRequests) {
                    oos.writeObject(leave);
                }
            }
            
            if (outputLabel != null) outputLabel.setText("Database updated successfully");
        } catch (Exception e) {
            if (outputLabel != null) outputLabel.setText("Error updating database: " + e.getMessage());
        }
    }

    @FXML
    public void refreshTable(ActionEvent actionEvent) {
        try {
            leaveList.clear();
            hrAssistantLeaveRequests.clear();
            
            // Reload from HR Assistant's leave_request.bin file
            hrAssistantLeaveRequests = HrAssistantStorage.loadLeaveRequests();
            
            // Convert HrAssistant leave requests to hrOfficer format for display
            for (HrAssistant assistantLeave : hrAssistantLeaveRequests) {
                hrOfficer officerLeave = new hrOfficer(
                    assistantLeave.getEmployeeId(),
                    assistantLeave.getEmployeeName(),
                    assistantLeave.getLeaveType(),
                    assistantLeave.getStartDate(),
                    assistantLeave.getEndDate(),
                    assistantLeave.getLeaveStatus() != null ? assistantLeave.getLeaveStatus() : "Pending"
                );
                // Set the reason field separately since it's not in the constructor
                officerLeave.setReason(assistantLeave.getReason());
                leaveList.add(officerLeave);
            }
            
            if (tableViewLeaveApproval != null) {
                tableViewLeaveApproval.getItems().clear();
                tableViewLeaveApproval.getItems().addAll(leaveList);
            }
            
            if (outputLabel != null) outputLabel.setText("Table refreshed successfully");
        } catch (Exception e) {
            if (outputLabel != null) outputLabel.setText("Error refreshing table: " + e.getMessage());
        }
    }

    @FXML
    public void searchLeave(ActionEvent actionEvent) {
        if (searchField == null || searchField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter search term");
            return;
        }

        String searchTerm = searchField.getText().trim().toLowerCase();
        ArrayList<hrOfficer> filteredList = new ArrayList<>();

        for (hrOfficer leave : leaveList) {
            if ((leave.getEmployeeId() != null && leave.getEmployeeId().toLowerCase().contains(searchTerm)) ||
                (leave.getEmployeeName() != null && leave.getEmployeeName().toLowerCase().contains(searchTerm)) ||
                (leave.getLeaveType() != null && leave.getLeaveType().toLowerCase().contains(searchTerm)) ||
                (leave.getLeaveStatus() != null && leave.getLeaveStatus().toLowerCase().contains(searchTerm))) {
                filteredList.add(leave);
            }
        }

        if (tableViewLeaveApproval != null) {
            tableViewLeaveApproval.getItems().clear();
            tableViewLeaveApproval.getItems().addAll(filteredList);
        }

        if (outputLabel != null) {
            outputLabel.setText("Search completed. Found " + filteredList.size() + " leave requests.");
        }
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

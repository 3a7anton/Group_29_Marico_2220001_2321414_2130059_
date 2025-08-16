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

public class LeaveRequestSubmissionController {
    @FXML
    private TextField employeeIdText;
    @FXML
    private TextField employeeNameText;
    @FXML
    private ComboBox<String> leaveTypeCombobox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextArea reasonTextArea;
    @FXML
    private TextField searchText;
    @FXML
    private Label outputLabel;
    @FXML
    private TableView<HrAssistant> leaveRequestTable;
    @FXML
    private TableColumn<HrAssistant, String> employeeIdCol;
    @FXML
    private TableColumn<HrAssistant, String> employeeNameCol;
    @FXML
    private TableColumn<HrAssistant, String> leaveTypeCol;
    @FXML
    private TableColumn<HrAssistant, LocalDate> startDateCol;
    @FXML
    private TableColumn<HrAssistant, LocalDate> endDateCol;
    @FXML
    private TableColumn<HrAssistant, String> reasonCol;
    @FXML
    private TableColumn<HrAssistant, String> statusCol;
    @FXML
    private TableColumn<HrAssistant, LocalDate> requestDateCol;

    // main array list
    ArrayList<HrAssistant> leaveRequestList = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize leave type combobox with null check
        if (leaveTypeCombobox != null) {
            leaveTypeCombobox.getItems().addAll("Annual Leave", "Sick Leave", "Maternity Leave", 
                                              "Paternity Leave", "Emergency Leave", "Casual Leave");
        }

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
        if (requestDateCol != null) {
            requestDateCol.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        }

        // Load existing leave request data
        try {
            leaveRequestList = HrAssistantStorage.loadLeaveRequests();
            if (leaveRequestTable != null) {
                leaveRequestTable.getItems().addAll(leaveRequestList);
            }
        } catch (Exception e) {
            if (outputLabel != null) {
                outputLabel.setText("Error loading leave request data: " + e.getMessage());
            }
        }

        // Add table selection listener with null check
        if (leaveRequestTable != null) {
            leaveRequestTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    populateForm(newSelection);
                }
            });
        }
    }

    @FXML
    public void submitLeaveRequest(ActionEvent actionEvent) {
        if (!validateForm()) {
            return;
        }

        // Check if dates are valid
        if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            outputLabel.setText("Start date cannot be after end date");
            return;
        }

        if (startDatePicker.getValue().isBefore(LocalDate.now())) {
            outputLabel.setText("Start date cannot be in the past");
            return;
        }

        try {
            HrAssistant leaveRequest = new HrAssistant(
                    employeeIdText.getText().trim(),
                    employeeNameText.getText().trim(),
                    leaveTypeCombobox.getValue(),
                    startDatePicker.getValue(),
                    endDatePicker.getValue(),
                    reasonTextArea.getText().trim()
            );

            HrAssistantStorage.saveLeaveRequest(leaveRequest);
            leaveRequestList.add(leaveRequest);

            // Update table
            leaveRequestTable.getItems().clear();
            leaveRequestTable.getItems().addAll(leaveRequestList);

            clearForm(null);
            outputLabel.setText("Leave request submitted successfully");

        } catch (IOException e) {
            outputLabel.setText("Error saving leave request: " + e.getMessage());
        }
    }

    @FXML
    public void searchLeaveRequest(ActionEvent actionEvent) {
        String searchTerm = searchText.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            outputLabel.setText("Please enter search term");
            return;
        }

        ArrayList<HrAssistant> filteredList = new ArrayList<>();
        for (HrAssistant leave : leaveRequestList) {
            if ((leave.getEmployeeName() != null && leave.getEmployeeName().toLowerCase().contains(searchTerm)) || 
                (leave.getEmployeeId() != null && leave.getEmployeeId().toLowerCase().contains(searchTerm)) ||
                (leave.getLeaveType() != null && leave.getLeaveType().toLowerCase().contains(searchTerm))) {
                filteredList.add(leave);
            }
        }

        leaveRequestTable.getItems().clear();
        leaveRequestTable.getItems().addAll(filteredList);
        outputLabel.setText("Found " + filteredList.size() + " leave requests");
    }

    @FXML
    public void resetFilter(ActionEvent actionEvent) {
        leaveRequestTable.getItems().clear();
        leaveRequestTable.getItems().addAll(leaveRequestList);
        searchText.clear();
        outputLabel.setText("Filter reset");
    }

    @FXML
    public void clearForm(ActionEvent actionEvent) {
        employeeIdText.clear();
        employeeNameText.clear();
        leaveTypeCombobox.setValue(null);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        reasonTextArea.clear();
        leaveRequestTable.getSelectionModel().clearSelection();
    }

    private void populateForm(HrAssistant leaveRequest) {
        if (leaveRequest == null) return;
        
        if (employeeIdText != null && leaveRequest.getEmployeeId() != null) {
            employeeIdText.setText(leaveRequest.getEmployeeId());
        }
        if (employeeNameText != null && leaveRequest.getEmployeeName() != null) {
            employeeNameText.setText(leaveRequest.getEmployeeName());
        }
        if (leaveTypeCombobox != null && leaveRequest.getLeaveType() != null) {
            leaveTypeCombobox.setValue(leaveRequest.getLeaveType());
        }
        if (startDatePicker != null) {
            startDatePicker.setValue(leaveRequest.getStartDate());
        }
        if (endDatePicker != null) {
            endDatePicker.setValue(leaveRequest.getEndDate());
        }
        if (reasonTextArea != null && leaveRequest.getReason() != null) {
            reasonTextArea.setText(leaveRequest.getReason());
        }
    }

    private boolean validateForm() {
        if (employeeIdText == null || employeeIdText.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter employee ID");
            return false;
        }

        if (employeeNameText == null || employeeNameText.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter employee name");
            return false;
        }

        if (leaveTypeCombobox == null || leaveTypeCombobox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select leave type");
            return false;
        }

        if (startDatePicker == null || startDatePicker.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select start date");
            return false;
        }

        if (endDatePicker == null || endDatePicker.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select end date");
            return false;
        }

        if (reasonTextArea == null || reasonTextArea.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter reason for leave");
            return false;
        }

        return true;
    }

    @FXML
    public void checkEligibleForLeave(ActionEvent actionEvent) {
        try {
            String employeeId = employeeIdText.getText().trim();
            if (employeeId.isEmpty()) {
                outputLabel.setText("Please enter Employee ID");
                return;
            }

            // Load existing leave requests to check eligibility
            ArrayList<HrAssistant> existingRequests = HrAssistantStorage.loadLeaveRequests();
            
            // Check if employee has any pending requests
            long pendingRequests = existingRequests.stream()
                    .filter(request -> request.getEmployeeId().equals(employeeId) && 
                                     "Pending".equals(request.getLeaveStatus()))
                    .count();
            
            if (pendingRequests > 0) {
                outputLabel.setText("Employee has pending leave requests. Please wait for approval.");
                return;
            }
            
            // Check leave balance (assuming 30 days annual leave)
            long usedLeaveDays = existingRequests.stream()
                    .filter(request -> request.getEmployeeId().equals(employeeId) && 
                                     "Approved".equals(request.getLeaveStatus()) &&
                                     request.getRequestDate() != null &&
                                     request.getRequestDate().getYear() == LocalDate.now().getYear())
                    .mapToLong(request -> {
                        // Calculate days between start and end date
                        if (request.getStartDate() != null && request.getEndDate() != null) {
                            return request.getStartDate().until(request.getEndDate()).getDays() + 1;
                        }
                        return 1; // Default to 1 day if dates not properly set
                    })
                    .sum();
            
            long remainingDays = 30 - usedLeaveDays;
            
            if (remainingDays <= 0) {
                outputLabel.setText("Employee has exhausted annual leave quota (30 days)");
            } else {
                outputLabel.setText("Employee is eligible for leave. Remaining days: " + remainingDays);
            }
            
        } catch (Exception e) {
            outputLabel.setText("Error checking eligibility: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void submitRequestToHrOfficer(ActionEvent actionEvent) {
        try {
            // Validate input first
            if (!validateForm()) {
                return;
            }

            // Create new leave request
            HrAssistant leaveRequest = new HrAssistant();
            leaveRequest.setEmployeeId(employeeIdText.getText().trim());
            leaveRequest.setEmployeeName(employeeNameText.getText().trim());
            leaveRequest.setLeaveType(leaveTypeCombobox.getValue());
            leaveRequest.setStartDate(startDatePicker.getValue());
            leaveRequest.setEndDate(endDatePicker.getValue());
            leaveRequest.setReason(reasonTextArea.getText().trim());
            leaveRequest.setRequestDate(LocalDate.now());
            leaveRequest.setLeaveStatus("Submitted to HR Officer");

            // Save the leave request
            HrAssistantStorage.saveLeaveRequest(leaveRequest);
            leaveRequestList.add(leaveRequest);

            // Update table
            if (leaveRequestTable != null) {
                leaveRequestTable.getItems().add(leaveRequest);
            }

            outputLabel.setText("Leave request submitted to HR Officer successfully!");
            clearForm(null);

        } catch (Exception e) {
            outputLabel.setText("Error submitting request: " + e.getMessage());
            e.printStackTrace();
        }
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

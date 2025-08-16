package org.example.marico_bangladesh.HR.hr_assistant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HRReportGenerationController {
    @FXML
    private ComboBox<String> reportTypeCombobox;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private ComboBox<String> departmentFilterCombobox;
    @FXML
    private Label outputLabel;
    @FXML
    private Label totalRecordsLabel;
    @FXML
    private Label departmentCountLabel;
    @FXML
    private Label avgAttendanceLabel;
    @FXML
    private Label pendingRequestsLabel;
    @FXML
    private TableView<Object> reportTable;
    @FXML
    private TableColumn<Object, String> idCol;
    @FXML
    private TableColumn<Object, String> nameCol;
    @FXML
    private TableColumn<Object, String> departmentCol;
    @FXML
    private TableColumn<Object, String> typeCol;
    @FXML
    private TableColumn<Object, String> valueCol;
    @FXML
    private TableColumn<Object, String> dateCol;
    @FXML
    private TableColumn<Object, String> statusCol;

    // Data lists - fetch from various bin files as per your instructions
    ArrayList<HrAssistant> employeeList = new ArrayList<>();
    ArrayList<HrAssistant> attendanceList = new ArrayList<>();
    ArrayList<HrAssistant> trainingList = new ArrayList<>();
    ArrayList<HrAssistant> leaveRequestList = new ArrayList<>();
    
    // Current report data
    ObservableList<Object> currentReportData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize report type combobox with null check
        if (reportTypeCombobox != null) {
            reportTypeCombobox.getItems().addAll(
                "Employee Report", 
                "Attendance Report", 
                "Training Report", 
                "Leave Report",
                "Comprehensive Report"
            );
        }

        // Initialize department filter combobox with null check
        if (departmentFilterCombobox != null) {
            departmentFilterCombobox.getItems().addAll("All", "HR", "IT", "Finance", "Marketing", "Operations", "Sales");
            departmentFilterCombobox.setValue("All");
        }

        // Set default date range (last 30 days) with null checks
        if (toDatePicker != null) {
            toDatePicker.setValue(LocalDate.now());
        }
        if (fromDatePicker != null) {
            fromDatePicker.setValue(LocalDate.now().minusDays(30));
        }

        // Load all data from various sources as per your instructions
        loadAllData();

        // Add report type change listener with null check
        if (reportTypeCombobox != null) {
            reportTypeCombobox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    setupTableColumns(newVal);
                }
            });
        }
    }

    private void loadAllData() {
        try {
            // Fetch data from 'employee data entry' bin for various reports
            employeeList = HrAssistantStorage.loadEmployees();
            
            // Fetch data from 'attendance track' bin for hr report generation  
            attendanceList = HrAssistantStorage.loadAttendanceRecords();
            
            // Fetch data from 'train schedule' bin for hr report generation
            trainingList = HrAssistantStorage.loadTrainingSchedules();
            
            // Fetch data from 'leave request' bin
            leaveRequestList = HrAssistantStorage.loadLeaveRequests();
            
            outputLabel.setText("Data loaded successfully from all sources");
        } catch (Exception e) {
            outputLabel.setText("Error loading data: " + e.getMessage());
        }
    }

    @FXML
    public void generateReport(ActionEvent actionEvent) {
        String reportType = reportTypeCombobox.getValue();
        if (reportType == null) {
            outputLabel.setText("Please select a report type");
            return;
        }

        if (fromDatePicker.getValue() == null || toDatePicker.getValue() == null) {
            outputLabel.setText("Please select date range");
            return;
        }

        if (fromDatePicker.getValue().isAfter(toDatePicker.getValue())) {
            outputLabel.setText("From date cannot be after to date");
            return;
        }

        try {
            currentReportData.clear();
            
            switch (reportType) {
                case "Employee Report":
                    generateEmployeeReport();
                    break;
                case "Attendance Report":
                    generateAttendanceReport();
                    break;
                case "Training Report":
                    generateTrainingReport();
                    break;
                case "Leave Report":
                    generateLeaveReport();
                    break;
                case "Comprehensive Report":
                    generateComprehensiveReport();
                    break;
            }

            reportTable.setItems(currentReportData);
            updateReportSummary();
            outputLabel.setText("Report generated successfully with " + currentReportData.size() + " records");

        } catch (Exception e) {
            outputLabel.setText("Error generating report: " + e.getMessage());
        }
    }

    private void generateEmployeeReport() {
        setupTableColumns("Employee Report");
        String selectedDept = departmentFilterCombobox.getValue();
        
        for (HrAssistant employee : employeeList) {
            if (selectedDept.equals("All") || employee.getDepartment().equals(selectedDept)) {
                if (isWithinDateRange(employee.getHireDate())) {
                    currentReportData.add(employee);
                }
            }
        }
    }

    private void generateAttendanceReport() {
        setupTableColumns("Attendance Report");
        String selectedDept = departmentFilterCombobox.getValue();
        
        for (HrAssistant attendance : attendanceList) {
            if (isWithinDateRange(attendance.getAttendanceDate())) {
                if (selectedDept.equals("All") || isDepartmentMatch(attendance.getEmployeeId(), selectedDept)) {
                    currentReportData.add(attendance);
                }
            }
        }
    }

    private void generateTrainingReport() {
        setupTableColumns("Training Report");
        String selectedDept = departmentFilterCombobox.getValue();
        
        for (HrAssistant training : trainingList) {
            if (isWithinDateRange(training.getTrainingDate())) {
                if (selectedDept.equals("All") || training.getDepartment().equals(selectedDept)) {
                    currentReportData.add(training);
                }
            }
        }
    }

    private void generateLeaveReport() {
        setupTableColumns("Leave Report");
        String selectedDept = departmentFilterCombobox.getValue();
        
        for (HrAssistant leave : leaveRequestList) {
            if (isWithinDateRange(leave.getRequestDate())) {
                if (selectedDept.equals("All") || isDepartmentMatch(leave.getEmployeeId(), selectedDept)) {
                    currentReportData.add(leave);
                }
            }
        }
    }

    private void generateComprehensiveReport() {
        // This combines data from all sources as per your instructions:
        // 'salary structure','train schedule', 'attendance track' and 'employee data entry'
        setupTableColumns("Comprehensive Report");
        
        generateEmployeeReport();
        generateAttendanceReport();
        generateTrainingReport();
        generateLeaveReport();
    }

    private void setupTableColumns(String reportType) {
        switch (reportType) {
            case "Employee Report":
                idCol.setText("Employee ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
                nameCol.setText("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
                departmentCol.setText("Department");
                departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
                typeCol.setText("Position");
                typeCol.setCellValueFactory(new PropertyValueFactory<>("position"));
                valueCol.setText("Salary");
                valueCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
                statusCol.setText("Status");
                statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
                break;
                
            case "Attendance Report":
                idCol.setText("Employee ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
                nameCol.setText("Employee Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
                departmentCol.setText("Department");
                departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
                typeCol.setText("Check-in");
                typeCol.setCellValueFactory(new PropertyValueFactory<>("checkInTime"));
                valueCol.setText("Check-out");
                valueCol.setCellValueFactory(new PropertyValueFactory<>("checkOutTime"));
                statusCol.setText("Status");
                statusCol.setCellValueFactory(new PropertyValueFactory<>("attendanceStatus"));
                break;
                
            case "Training Report":
                idCol.setText("Training ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("trainingId"));
                nameCol.setText("Title");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("trainingTitle"));
                departmentCol.setText("Department");
                departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
                typeCol.setText("Trainer");
                typeCol.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
                valueCol.setText("Duration");
                valueCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
                statusCol.setText("Status");
                statusCol.setCellValueFactory(new PropertyValueFactory<>("trainingStatus"));
                break;
                
            case "Leave Report":
                idCol.setText("Employee ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
                nameCol.setText("Employee Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
                departmentCol.setText("Department");
                departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
                typeCol.setText("Leave Type");
                typeCol.setCellValueFactory(new PropertyValueFactory<>("leaveType"));
                valueCol.setText("Duration");
                valueCol.setCellValueFactory(new PropertyValueFactory<>("leaveDuration"));
                statusCol.setText("Status");
                statusCol.setCellValueFactory(new PropertyValueFactory<>("leaveStatus"));
                break;
                
            case "Comprehensive Report":
                idCol.setText("Type");
                nameCol.setText("ID/Name");
                departmentCol.setText("Details");
                typeCol.setText("Category");
                valueCol.setText("Value");
                statusCol.setText("Status");
                break;
        }
    }

    private void updateReportSummary() {
        totalRecordsLabel.setText(String.valueOf(currentReportData.size()));
        
        // Count unique departments
        Set<String> departments = new HashSet<>();
        for (HrAssistant emp : employeeList) {
            departments.add(emp.getDepartment());
        }
        departmentCountLabel.setText(String.valueOf(departments.size()));
        
        // Calculate average attendance
        long totalAttendance = attendanceList.stream()
                .filter(att -> "Present".equals(att.getAttendanceStatus()))
                .count();
        double avgAttendance = attendanceList.isEmpty() ? 0 : (totalAttendance * 100.0) / attendanceList.size();
        avgAttendanceLabel.setText(String.format("%.1f%%", avgAttendance));
        
        // Count pending requests
        long pendingRequests = leaveRequestList.stream()
                .filter(leave -> "Pending".equals(leave.getLeaveStatus()))
                .count();
        pendingRequestsLabel.setText(String.valueOf(pendingRequests));
    }

    private boolean isWithinDateRange(LocalDate date) {
        return !date.isBefore(fromDatePicker.getValue()) && !date.isAfter(toDatePicker.getValue());
    }

    private boolean isDepartmentMatch(String employeeId, String department) {
        return employeeList.stream()
                .anyMatch(emp -> emp.getEmployeeId().equals(employeeId) && emp.getDepartment().equals(department));
    }

    @FXML
    public void exportReport(ActionEvent actionEvent) {
        if (currentReportData.isEmpty()) {
            outputLabel.setText("No data to export. Please generate a report first.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("hr_report_" + LocalDate.now() + ".csv");

        Stage stage = (Stage) reportTable.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Write CSV header
                writer.write(idCol.getText() + "," + nameCol.getText() + "," + departmentCol.getText() + "," + 
                           typeCol.getText() + "," + valueCol.getText() + "," + dateCol.getText() + "," + statusCol.getText() + "\n");

                // Write data - this is a simplified export, you may need to customize based on data type
                for (Object item : currentReportData) {
                    writer.write(item.toString() + "\n");
                }

                outputLabel.setText("Report exported successfully to: " + file.getAbsolutePath());
            } catch (IOException e) {
                outputLabel.setText("Error exporting report: " + e.getMessage());
            }
        }
    }

    @FXML
    public void clearReport(ActionEvent actionEvent) {
        currentReportData.clear();
        reportTable.getItems().clear();
        totalRecordsLabel.setText("0");
        departmentCountLabel.setText("0");
        avgAttendanceLabel.setText("0%");
        pendingRequestsLabel.setText("0");
        outputLabel.setText("Report cleared");
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

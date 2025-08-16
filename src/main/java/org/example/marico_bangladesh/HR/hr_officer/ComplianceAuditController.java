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

public class ComplianceAuditController
{
    @FXML
    private Button backButton;
    @FXML
    private TextField employeeSearchTextField;
    @FXML
    private Button fetchRecordsButton;
    @FXML
    private ComboBox<String> complianceTypeComboBox;
    @FXML
    private Button verifyComplianceButton;
    @FXML
    private Button flagNonCompliantButton;
    @FXML
    private Button generateReportButton;
    @FXML
    private Label outputLabel;
    @FXML
    private Label totalRecordsLabel;
    @FXML
    private Label compliantRecordsLabel;
    @FXML
    private Label nonCompliantRecordsLabel;
    
    // Employee Records Table
    @FXML
    private TableView<hrOfficer> employeeRecordsTableView;
    @FXML
    private TableColumn<hrOfficer, String> empIdCol;
    @FXML
    private TableColumn<hrOfficer, String> empNameCol;
    @FXML
    private TableColumn<hrOfficer, String> deptCol;
    @FXML
    private TableColumn<hrOfficer, String> positionCol;
    @FXML
    private TableColumn<hrOfficer, Double> salaryCol;
    @FXML
    private TableColumn<hrOfficer, LocalDate> joinDateCol;
    @FXML
    private TableColumn<hrOfficer, String> complianceStatusCol;
    
    // Audit Report Table
    @FXML
    private TableView<hrOfficer> auditReportTableView;
    @FXML
    private TableColumn<hrOfficer, String> auditEmpIdCol;
    @FXML
    private TableColumn<hrOfficer, String> auditEmpNameCol;
    @FXML
    private TableColumn<hrOfficer, String> complianceTypeCol;
    @FXML
    private TableColumn<hrOfficer, String> issueCol;
    @FXML
    private TableColumn<hrOfficer, String> severityCol;
    @FXML
    private TableColumn<hrOfficer, String> actionRequiredCol;

    // main array lists
    ArrayList<hrOfficer> employeeList = new ArrayList<>();
    ArrayList<hrOfficer> auditList = new ArrayList<>();
    
    @FXML
    public void initialize() {
        // Initialize ComboBoxes
        if (complianceTypeComboBox != null) {
            complianceTypeComboBox.getItems().addAll(
                "Employee Documentation", "Salary Compliance", "Working Hours", 
                "Safety Training", "Legal Requirements", "Contract Compliance", 
                "Performance Reviews", "Leave Records"
            );
        }

        // Initialize Employee Records table columns
        if (empIdCol != null) empIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        if (empNameCol != null) empNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        if (deptCol != null) deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        if (positionCol != null) positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        if (salaryCol != null) salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        if (joinDateCol != null) joinDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        if (complianceStatusCol != null) complianceStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Initialize Audit Report table columns
        if (auditEmpIdCol != null) auditEmpIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        if (auditEmpNameCol != null) auditEmpNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        if (complianceTypeCol != null) complianceTypeCol.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        if (issueCol != null) issueCol.setCellValueFactory(new PropertyValueFactory<>("comments"));
        if (severityCol != null) severityCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        if (actionRequiredCol != null) actionRequiredCol.setCellValueFactory(new PropertyValueFactory<>("department"));

        // Load employee data using HrOfficerStorage
        try {
            ArrayList<hrOfficer> loadedEmployees = (ArrayList<hrOfficer>) HrOfficerStorage.loadAllEmployees();
            employeeList.addAll(loadedEmployees);
        } catch (Exception e) {
            System.out.println("Error loading employee data: " + e.getMessage());
        }

        // Load existing audit data
        try {
            ArrayList<hrOfficer> loadedAudits = (ArrayList<hrOfficer>) HrOfficerStorage.loadAllAudits();
            auditList.addAll(loadedAudits);
        } catch (Exception e) {
            System.out.println("Error loading audit data: " + e.getMessage());
        }

        if (employeeRecordsTableView != null) {
            employeeRecordsTableView.getItems().addAll(employeeList);
        }
        
        if (auditReportTableView != null) {
            auditReportTableView.getItems().addAll(auditList);
        }
        
        updateSummaryLabels();
    }

    @FXML
    public void fetchEmployeeRecords(ActionEvent actionEvent) {
        if (employeeRecordsTableView == null) return;

        // Clear and reload all employee records
        employeeRecordsTableView.getItems().clear();
        
        if (employeeSearchTextField != null && !employeeSearchTextField.getText().trim().isEmpty()) {
            // Filter by specific employee ID
            String searchId = employeeSearchTextField.getText().trim();
            ArrayList<hrOfficer> filteredList = new ArrayList<>();
            
            for (hrOfficer emp : employeeList) {
                if (emp.getEmployeeId() != null && emp.getEmployeeId().equals(searchId)) {
                    filteredList.add(emp);
                }
            }
            
            employeeRecordsTableView.getItems().addAll(filteredList);
            if (outputLabel != null) {
                outputLabel.setText("Found " + filteredList.size() + " employee record(s)");
            }
        } else {
            // Show all employee records
            employeeRecordsTableView.getItems().addAll(employeeList);
            if (outputLabel != null) {
                outputLabel.setText("Fetched all employee records");
            }
        }
        
        updateSummaryLabels();
    }

    @FXML
    public void verifyCompliance(ActionEvent actionEvent) {
        if (complianceTypeComboBox == null || complianceTypeComboBox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Please select a compliance type");
            return;
        }

        String complianceType = complianceTypeComboBox.getValue();
        int compliantCount = 0;
        int nonCompliantCount = 0;

        for (hrOfficer emp : employeeList) {
            boolean isCompliant = checkCompliance(emp, complianceType);
            if (isCompliant) {
                emp.setStatus("Compliant");
                compliantCount++;
            } else {
                emp.setStatus("Non-Compliant");
                nonCompliantCount++;
            }
        }

        // Refresh employee records table
        if (employeeRecordsTableView != null) {
            employeeRecordsTableView.getItems().clear();
            employeeRecordsTableView.getItems().addAll(employeeList);
        }

        if (outputLabel != null) {
            outputLabel.setText("Compliance verified: " + compliantCount + " compliant, " + nonCompliantCount + " non-compliant");
        }
        
        updateSummaryLabels();
    }

    @FXML
    public void flagNonCompliantRecords(ActionEvent actionEvent) {
        if (complianceTypeComboBox == null || complianceTypeComboBox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Please select a compliance type");
            return;
        }

        String complianceType = complianceTypeComboBox.getValue();
        ArrayList<hrOfficer> nonCompliantRecords = new ArrayList<>();

        for (hrOfficer emp : employeeList) {
            if (!checkCompliance(emp, complianceType)) {
                // Create audit record for non-compliant employee
                hrOfficer auditRecord = new hrOfficer(
                        emp.getEmployeeId(),
                        emp.getEmployeeName(),
                        emp.getDepartment(),
                        emp.getPosition(),
                        emp.getSalary(),
                        "High", // Severity
                        LocalDate.now()
                );
                
                auditRecord.setJobTitle(complianceType); // Compliance type
                auditRecord.setComments(getComplianceIssue(emp, complianceType));
                auditRecord.setDepartment("Required"); // Action required
                
                nonCompliantRecords.add(auditRecord);
                auditList.add(auditRecord);
            }
        }

        // Save flagged records
        for (hrOfficer audit : nonCompliantRecords) {
            HrOfficerStorage.appendAudit(audit);
        }

        // Refresh audit report table
        if (auditReportTableView != null) {
            auditReportTableView.getItems().clear();
            auditReportTableView.getItems().addAll(auditList);
        }

        if (outputLabel != null) {
            outputLabel.setText("Flagged " + nonCompliantRecords.size() + " non-compliant records");
        }
    }

    @FXML
    public void generateAuditReport(ActionEvent actionEvent) {
        if (auditList.isEmpty()) {
            if (outputLabel != null) outputLabel.setText("No audit records to report");
            return;
        }

        int totalAudits = auditList.size();
        int highSeverity = 0;
        int mediumSeverity = 0;
        int lowSeverity = 0;

        for (hrOfficer audit : auditList) {
            String severity = audit.getStatus();
            if ("High".equals(severity)) {
                highSeverity++;
            } else if ("Medium".equals(severity)) {
                mediumSeverity++;
            } else {
                lowSeverity++;
            }
        }

        String report = String.format(
            "Audit Report Generated - Total Issues: %d | High: %d | Medium: %d | Low: %d",
            totalAudits, highSeverity, mediumSeverity, lowSeverity
        );

        if (outputLabel != null) outputLabel.setText(report);
        
        // For a real application, you would export this to a file
        System.out.println("Compliance Audit Report:");
        System.out.println(report);
    }

    private boolean checkCompliance(hrOfficer employee, String complianceType) {
        // Simulate compliance checking based on different criteria
        switch (complianceType) {
            case "Employee Documentation":
                return employee.getEmployeeId() != null && employee.getEmployeeName() != null;
            case "Salary Compliance":
                return employee.getSalary() > 0;
            case "Working Hours":
                return true; // Assume compliant for demo
            case "Safety Training":
                return employee.getStartDate() != null && employee.getStartDate().isBefore(LocalDate.now().minusMonths(6));
            case "Legal Requirements":
                return employee.getStatus() != null && !employee.getStatus().equals("Terminated");
            case "Contract Compliance":
                return employee.getPosition() != null && !employee.getPosition().trim().isEmpty();
            case "Performance Reviews":
                return employee.getStartDate() != null && employee.getStartDate().isBefore(LocalDate.now().minusYears(1));
            case "Leave Records":
                return true; // Assume compliant for demo
            default:
                return false;
        }
    }

    private String getComplianceIssue(hrOfficer employee, String complianceType) {
        switch (complianceType) {
            case "Employee Documentation":
                return "Missing employee documentation";
            case "Salary Compliance":
                return "Salary not properly configured";
            case "Working Hours":
                return "Working hours violation";
            case "Safety Training":
                return "Safety training overdue";
            case "Legal Requirements":
                return "Legal compliance issue";
            case "Contract Compliance":
                return "Contract terms not met";
            case "Performance Reviews":
                return "Performance review overdue";
            case "Leave Records":
                return "Leave records incomplete";
            default:
                return "Compliance issue detected";
        }
    }

    private void updateSummaryLabels() {
        int totalRecords = employeeList.size();
        int compliantRecords = 0;
        int nonCompliantRecords = 0;

        for (hrOfficer emp : employeeList) {
            if ("Compliant".equals(emp.getStatus())) {
                compliantRecords++;
            } else if ("Non-Compliant".equals(emp.getStatus())) {
                nonCompliantRecords++;
            }
        }

        if (totalRecordsLabel != null) {
            totalRecordsLabel.setText("Total Records: " + totalRecords);
        }
        if (compliantRecordsLabel != null) {
            compliantRecordsLabel.setText("Compliant: " + compliantRecords);
        }
        if (nonCompliantRecordsLabel != null) {
            nonCompliantRecordsLabel.setText("Non-Compliant: " + nonCompliantRecords);
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

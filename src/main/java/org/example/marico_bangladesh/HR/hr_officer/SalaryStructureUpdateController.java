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

public class SalaryStructureUpdateController
{
    @FXML
    private Button backButton;
    @FXML
    private TextField employeeIdField;
    @FXML
    private TextField employeeNameField;
    @FXML
    private ComboBox<String> departmentCombobox;
    @FXML
    private TextField positionField;
    @FXML
    private TextField salaryField;
    @FXML
    private ComboBox<String> statusCombobox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Button updateSalaryButton;
    @FXML
    private TableView<hrOfficer> employeeTable;
    @FXML
    private TableColumn<hrOfficer, String> employeeIdCol;
    @FXML
    private TableColumn<hrOfficer, String> employeeNameCol;
    @FXML
    private TableColumn<hrOfficer, String> departmentCol;
    @FXML
    private TableColumn<hrOfficer, String> positionCol;
    @FXML
    private TableColumn<hrOfficer, Double> salaryCol;
    @FXML
    private TableColumn<hrOfficer, String> statusCol;
    @FXML
    private TableColumn<hrOfficer, LocalDate> startDateCol;
    @FXML
    private TextField searchField;
    @FXML
    private Label outputLabel;

    ArrayList<hrOfficer> employeeList = new ArrayList<>();
    
    @FXML
    public void initialize() {
        if (departmentCombobox != null) {
            departmentCombobox.getItems().addAll("HR", "Product", "Sales", "IT", "Marketing", "Finance");
        }
        if (statusCombobox != null) {
            statusCombobox.getItems().addAll("Active", "Inactive", "On Leave", "Terminated");
        }

        // Initialize table columns
        if (employeeIdCol != null) employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        if (employeeNameCol != null) employeeNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        if (departmentCol != null) departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        if (positionCol != null) positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        if (salaryCol != null) salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        if (statusCol != null) statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        if (startDateCol != null) startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        // Load employee data using HrOfficerStorage
        try {
            ArrayList<hrOfficer> loadedList = (ArrayList<hrOfficer>) HrOfficerStorage.loadAllEmployees();
            employeeList.addAll(loadedList);
        } catch (Exception e) {
            System.out.println("Error loading employee data: " + e.getMessage());
        }

        if (employeeTable != null) {
            employeeTable.getItems().addAll(employeeList);
        }
    }

    @FXML
    public void addEmployeeButton(ActionEvent actionEvent) {
        if (employeeIdField == null || employeeNameField == null || 
            departmentCombobox == null || positionField == null || 
            salaryField == null || statusCombobox == null || startDatePicker == null) {
            return;
        }

        if (employeeIdField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter employee ID");
            return;
        }

        if (employeeNameField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter employee name");
            return;
        }

        if (departmentCombobox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select department");
            return;
        }

        if (positionField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter position");
            return;
        }

        if (salaryField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter salary");
            return;
        }

        if (statusCombobox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select status");
            return;
        }

        if (startDatePicker.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select start date");
            return;
        }

        try {
            double salary = Double.parseDouble(salaryField.getText().trim());

            // Check if employee already exists
            for (hrOfficer emp : employeeList) {
                if (emp.getEmployeeId() != null && emp.getEmployeeId().equals(employeeIdField.getText())) {
                    if (outputLabel != null) outputLabel.setText("Employee ID already exists");
                    return;
                }
            }

            hrOfficer employee = new hrOfficer(
                    employeeIdField.getText(),
                    employeeNameField.getText(),
                    departmentCombobox.getValue(),
                    positionField.getText(),
                    salary,
                    statusCombobox.getValue(),
                    startDatePicker.getValue()
            );

            // Save using HrOfficerStorage
            boolean saved = HrOfficerStorage.appendEmployee(employee);
            if (!saved) {
                if (outputLabel != null) outputLabel.setText("Failed to save employee data");
                return;
            }

            employeeList.add(employee);

            if (outputLabel != null) outputLabel.setText("Employee added successfully");

            // clear and refresh table
            if (employeeTable != null) {
                employeeTable.getItems().clear();
                employeeTable.getItems().addAll(employeeList);
            }

            // clear input fields
            clearFields();

        } catch (NumberFormatException e) {
            if (outputLabel != null) outputLabel.setText("Invalid salary format");
        }
    }

    @FXML
    public void updateSalaryButton(ActionEvent actionEvent) {
        if (employeeIdField == null || salaryField == null) return;

        if (employeeIdField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter employee ID");
            return;
        }

        if (salaryField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter new salary");
            return;
        }

        try {
            double newSalary = Double.parseDouble(salaryField.getText().trim());
            String employeeId = employeeIdField.getText().trim();
            boolean found = false;

            for (hrOfficer emp : employeeList) {
                if (emp.getEmployeeId() != null && emp.getEmployeeId().equals(employeeId)) {
                    emp.setSalary(newSalary);
                    found = true;
                    break;
                }
            }

            if (found) {
                if (outputLabel != null) outputLabel.setText("Salary updated successfully");
                // Refresh table
                if (employeeTable != null) {
                    employeeTable.getItems().clear();
                    employeeTable.getItems().addAll(employeeList);
                }
            } else {
                if (outputLabel != null) outputLabel.setText("Employee not found");
            }

        } catch (NumberFormatException e) {
            if (outputLabel != null) outputLabel.setText("Invalid salary format");
        }
    }

    private void clearFields() {
        if (employeeIdField != null) employeeIdField.clear();
        if (employeeNameField != null) employeeNameField.clear();
        if (departmentCombobox != null) departmentCombobox.getSelectionModel().clearSelection();
        if (positionField != null) positionField.clear();
        if (salaryField != null) salaryField.clear();
        if (statusCombobox != null) statusCombobox.getSelectionModel().clearSelection();
        if (startDatePicker != null) startDatePicker.setValue(null);
    }

    @FXML
    public void clearFields(ActionEvent actionEvent) {
        clearFields();
        if (outputLabel != null) outputLabel.setText("Form cleared");
    }

    @FXML
    public void refreshTable(ActionEvent actionEvent) {
        try {
            employeeList.clear();
            ArrayList<hrOfficer> loadedList = (ArrayList<hrOfficer>) HrOfficerStorage.loadAllEmployees();
            employeeList.addAll(loadedList);
            
            if (employeeTable != null) {
                employeeTable.getItems().clear();
                employeeTable.getItems().addAll(employeeList);
            }
            
            if (outputLabel != null) outputLabel.setText("Table refreshed successfully");
        } catch (Exception e) {
            if (outputLabel != null) outputLabel.setText("Error refreshing table: " + e.getMessage());
        }
    }

    @FXML
    public void searchEmployee(ActionEvent actionEvent) {
        if (searchField == null || searchField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter search term");
            return;
        }

        String searchTerm = searchField.getText().trim().toLowerCase();
        ArrayList<hrOfficer> filteredList = new ArrayList<>();

        for (hrOfficer emp : employeeList) {
            if ((emp.getEmployeeId() != null && emp.getEmployeeId().toLowerCase().contains(searchTerm)) ||
                (emp.getEmployeeName() != null && emp.getEmployeeName().toLowerCase().contains(searchTerm)) ||
                (emp.getDepartment() != null && emp.getDepartment().toLowerCase().contains(searchTerm)) ||
                (emp.getPosition() != null && emp.getPosition().toLowerCase().contains(searchTerm))) {
                filteredList.add(emp);
            }
        }

        if (employeeTable != null) {
            employeeTable.getItems().clear();
            employeeTable.getItems().addAll(filteredList);
        }

        if (outputLabel != null) {
            outputLabel.setText("Search completed. Found " + filteredList.size() + " employees.");
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

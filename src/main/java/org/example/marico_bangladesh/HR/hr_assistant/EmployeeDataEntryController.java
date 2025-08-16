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

public class EmployeeDataEntryController {
    @FXML
    private TextField employeeIdText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField contactText;
    @FXML
    private TextField addressText;
    @FXML
    private ComboBox<String> departmentCombobox;
    @FXML
    private TextField positionText;
    @FXML
    private TextField salaryText;
    @FXML
    private TextField searchText;
    @FXML
    private Label outputLabel;
    @FXML
    private TableView<HrAssistant> tableViewEmployee;
    @FXML
    private TableColumn<HrAssistant, String> employeeIdCol;
    @FXML
    private TableColumn<HrAssistant, String> nameCol;
    @FXML
    private TableColumn<HrAssistant, String> emailCol;
    @FXML
    private TableColumn<HrAssistant, String> contactCol;
    @FXML
    private TableColumn<HrAssistant, String> addressCol;
    @FXML
    private TableColumn<HrAssistant, String> departmentCol;
    @FXML
    private TableColumn<HrAssistant, String> positionCol;
    @FXML
    private TableColumn<HrAssistant, Double> salaryCol;
    @FXML
    private TableColumn<HrAssistant, LocalDate> joiningDateCol;
    @FXML
    private TableColumn<HrAssistant, String> statusCol;

    // main array list
    ArrayList<HrAssistant> employeeList = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize department combobox with null check
        if (departmentCombobox != null) {
            departmentCombobox.getItems().addAll("HR", "IT", "Finance", "Marketing", "Operations", "Sales");
        }

        // Initialize table columns with null checks
        if (employeeIdCol != null) {
            employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        }
        if (nameCol != null) {
            nameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        }
        if (emailCol != null) {
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        }
        if (contactCol != null) {
            contactCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        }
        if (addressCol != null) {
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        }
        if (departmentCol != null) {
            departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        }
        if (positionCol != null) {
            positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        }
        if (salaryCol != null) {
            salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        }
        if (joiningDateCol != null) {
            joiningDateCol.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        }
        if (statusCol != null) {
            statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        }

        // Load existing employee data
        try {
            employeeList = HrAssistantStorage.loadEmployees();
            tableViewEmployee.getItems().addAll(employeeList);
        } catch (Exception e) {
            outputLabel.setText("Error loading employee data: " + e.getMessage());
        }

        // Add table selection listener
        tableViewEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
    }

    @FXML
    public void addEmployeeInfo(ActionEvent actionEvent) {
        if (!validateForm()) {
            return;
        }

        // Check for duplicate employee ID
        for (HrAssistant emp : employeeList) {
            if (emp.getEmployeeId() != null && emp.getEmployeeId().equals(employeeIdText.getText().trim())) {
                outputLabel.setText("Employee ID already exists");
                return;
            }
        }

        try {
            HrAssistant employee = new HrAssistant(
                    employeeIdText.getText().trim(),
                    nameText.getText().trim(),
                    departmentCombobox.getValue(),
                    positionText.getText().trim(),
                    Double.parseDouble(salaryText.getText().trim()),
                    emailText.getText().trim(),
                    contactText.getText().trim(),
                    null, // dateOfBirth - can be set later
                    LocalDate.now(), // hireDate
                    addressText.getText().trim(),
                    "" // emergencyContact - can be set later
            );

            HrAssistantStorage.saveEmployee(employee);
            employeeList.add(employee);

            // Update table
            tableViewEmployee.getItems().clear();
            tableViewEmployee.getItems().addAll(employeeList);

            clearForm(null);
            outputLabel.setText("Employee added successfully");

        } catch (NumberFormatException e) {
            outputLabel.setText("Please enter valid salary amount");
        } catch (IOException e) {
            outputLabel.setText("Error saving employee: " + e.getMessage());
        }
    }

    @FXML
    public void updateEmployeeInfo(ActionEvent actionEvent) {
        HrAssistant selectedEmployee = tableViewEmployee.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            outputLabel.setText("Please select an employee to update");
            return;
        }

        if (!validateForm()) {
            return;
        }

        try {
            // Update selected employee
            selectedEmployee.setEmployeeId(employeeIdText.getText().trim());
            selectedEmployee.setEmployeeName(nameText.getText().trim());
            selectedEmployee.setEmail(emailText.getText().trim());
            selectedEmployee.setPhoneNumber(contactText.getText().trim());
            selectedEmployee.setAddress(addressText.getText().trim());
            selectedEmployee.setDepartment(departmentCombobox.getValue());
            selectedEmployee.setPosition(positionText.getText().trim());
            selectedEmployee.setSalary(Double.parseDouble(salaryText.getText().trim()));

            // Refresh table
            tableViewEmployee.refresh();

            clearForm(null);
            outputLabel.setText("Employee updated successfully");

        } catch (NumberFormatException e) {
            outputLabel.setText("Please enter valid salary amount");
        }
    }

    @FXML
    public void deleteEmployeeInfo(ActionEvent actionEvent) {
        HrAssistant selectedEmployee = tableViewEmployee.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            outputLabel.setText("Please select an employee to delete");
            return;
        }

        // Confirm deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Delete Employee");
        alert.setContentText("Are you sure you want to delete " + selectedEmployee.getEmployeeName() + "?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            employeeList.remove(selectedEmployee);
            tableViewEmployee.getItems().remove(selectedEmployee);
            clearForm(null);
            outputLabel.setText("Employee deleted successfully");
        }
    }

    @FXML
    public void searchEmployee(ActionEvent actionEvent) {
        String searchTerm = searchText.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            outputLabel.setText("Please enter search term");
            return;
        }

        ArrayList<HrAssistant> filteredList = new ArrayList<>();
        for (HrAssistant emp : employeeList) {
            if ((emp.getEmployeeName() != null && emp.getEmployeeName().toLowerCase().contains(searchTerm)) || 
                (emp.getEmployeeId() != null && emp.getEmployeeId().toLowerCase().contains(searchTerm)) ||
                (emp.getDepartment() != null && emp.getDepartment().toLowerCase().contains(searchTerm))) {
                filteredList.add(emp);
            }
        }

        tableViewEmployee.getItems().clear();
        tableViewEmployee.getItems().addAll(filteredList);
        outputLabel.setText("Found " + filteredList.size() + " employees");
    }

    @FXML
    public void resetFilter(ActionEvent actionEvent) {
        tableViewEmployee.getItems().clear();
        tableViewEmployee.getItems().addAll(employeeList);
        searchText.clear();
        outputLabel.setText("Filter reset");
    }

    @FXML
    public void clearForm(ActionEvent actionEvent) {
        employeeIdText.clear();
        nameText.clear();
        emailText.clear();
        contactText.clear();
        addressText.clear();
        departmentCombobox.setValue(null);
        positionText.clear();
        salaryText.clear();
        tableViewEmployee.getSelectionModel().clearSelection();
    }

    private void populateForm(HrAssistant employee) {
        employeeIdText.setText(employee.getEmployeeId());
        nameText.setText(employee.getEmployeeName());
        emailText.setText(employee.getEmail());
        contactText.setText(employee.getPhoneNumber());
        addressText.setText(employee.getAddress());
        departmentCombobox.setValue(employee.getDepartment());
        positionText.setText(employee.getPosition());
        salaryText.setText(String.valueOf(employee.getSalary()));
    }

    private boolean validateForm() {
        if (employeeIdText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter employee ID");
            return false;
        }

        if (nameText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter employee name");
            return false;
        }

        if (emailText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter email address");
            return false;
        }

        if (contactText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter contact number");
            return false;
        }

        if (addressText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter address");
            return false;
        }

        if (departmentCombobox.getValue() == null) {
            outputLabel.setText("Select department");
            return false;
        }

        if (positionText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter position");
            return false;
        }

        if (salaryText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter salary");
            return false;
        }

        try {
            Double.parseDouble(salaryText.getText().trim());
        } catch (NumberFormatException e) {
            outputLabel.setText("Enter valid salary amount");
            return false;
        }

        return true;
    }

    @FXML
    public void updateEmployee(ActionEvent actionEvent) {
        try {
            HrAssistant selectedEmployee = tableViewEmployee.getSelectionModel().getSelectedItem();
            if (selectedEmployee == null) {
                outputLabel.setText("Please select an employee to update");
                return;
            }

            // Validate basic fields
            if (nameText.getText().trim().isEmpty() || 
                addressText.getText().trim().isEmpty() || 
                contactText.getText().trim().isEmpty() ||
                departmentCombobox.getValue() == null) {
                outputLabel.setText("Please fill all required fields");
                return;
            }

            // Update selected employee with new values
            selectedEmployee.setEmployeeName(nameText.getText().trim());
            selectedEmployee.setAddress(addressText.getText().trim());
            selectedEmployee.setPhoneNumber(contactText.getText().trim());
            selectedEmployee.setDepartment(departmentCombobox.getValue());
            if (positionText != null && !positionText.getText().trim().isEmpty()) {
                selectedEmployee.setPosition(positionText.getText().trim());
            }
            if (salaryText != null && !salaryText.getText().trim().isEmpty()) {
                try {
                    selectedEmployee.setSalary(Double.parseDouble(salaryText.getText().trim()));
                } catch (NumberFormatException e) {
                    outputLabel.setText("Invalid salary format");
                    return;
                }
            }

            // Refresh table
            tableViewEmployee.refresh();
            outputLabel.setText("Employee updated successfully");
            
        } catch (Exception e) {
            outputLabel.setText("Error updating employee: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteEmployee(ActionEvent actionEvent) {
        try {
            HrAssistant selectedEmployee = tableViewEmployee.getSelectionModel().getSelectedItem();
            if (selectedEmployee == null) {
                outputLabel.setText("Please select an employee to delete");
                return;
            }

            employeeList.remove(selectedEmployee);
            tableViewEmployee.getItems().remove(selectedEmployee);
            outputLabel.setText("Employee deleted successfully");
            
        } catch (Exception e) {
            outputLabel.setText("Error deleting employee: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void loadEmployees(ActionEvent actionEvent) {
        try {
            employeeList = HrAssistantStorage.loadEmployees();
            if (tableViewEmployee != null) {
                tableViewEmployee.getItems().clear();
                tableViewEmployee.getItems().addAll(employeeList);
            }
            outputLabel.setText("Employee data loaded successfully. Total records: " + employeeList.size());
        } catch (Exception e) {
            outputLabel.setText("Error loading employee data: " + e.getMessage());
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

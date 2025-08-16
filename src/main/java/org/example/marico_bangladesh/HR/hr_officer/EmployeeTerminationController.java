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

public class EmployeeTerminationController
{
    @FXML
    private Button backButton;
    @FXML
    private TextField employeeIdTextField;
    @FXML
    private TextField employeeNameTextField;
    @FXML
    private Button fetchRecordsButton;
    @FXML
    private Button searchButton;
    @FXML
    private DatePicker terminationDatePicker;
    @FXML
    private ComboBox<String> terminationReasonComboBox;
    @FXML
    private TextArea terminationNotesTextArea;
    @FXML
    private ComboBox<String> terminationStatusComboBox;
    @FXML
    private Button updateTerminationButton;
    @FXML
    private Button clearFormButton;
    @FXML
    private Label outputLabel;
    
    @FXML
    private TableView<hrOfficer> terminationTableView;
    @FXML
    private TableColumn<hrOfficer, String> empIdCol;
    @FXML
    private TableColumn<hrOfficer, String> empNameCol;
    @FXML
    private TableColumn<hrOfficer, LocalDate> terminationDateCol;
    @FXML
    private TableColumn<hrOfficer, String> reasonCol;
    @FXML
    private TableColumn<hrOfficer, String> notesCol;
    @FXML
    private TableColumn<hrOfficer, String> statusCol;

    // main array lists
    ArrayList<hrOfficer> employeeList = new ArrayList<>();
    ArrayList<hrOfficer> terminationList = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize termination reason combobox
        if (terminationReasonComboBox != null) {
            terminationReasonComboBox.getItems().addAll(
                "Resignation", "Retirement", "Contract Expiry", "Performance Issues", 
                "Misconduct", "Layoff", "Company Restructuring", "Other"
            );
        }
        
        // Initialize termination status combobox
        if (terminationStatusComboBox != null) {
            terminationStatusComboBox.getItems().addAll(
                "Pending", "Approved", "Completed", "Cancelled"
            );
        }

        // Initialize table columns
        if (empIdCol != null) empIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        if (empNameCol != null) empNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        if (terminationDateCol != null) terminationDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        if (reasonCol != null) reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
        if (notesCol != null) notesCol.setCellValueFactory(new PropertyValueFactory<>("comments"));
        if (statusCol != null) statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load employee data using HrOfficerStorage
        try {
            ArrayList<hrOfficer> loadedEmployees = (ArrayList<hrOfficer>) HrOfficerStorage.loadAllEmployees();
            employeeList.addAll(loadedEmployees);
            
            // Load existing termination records
            ArrayList<hrOfficer> loadedTerminations = (ArrayList<hrOfficer>) HrOfficerStorage.loadTerminationRecords();
            terminationList.addAll(loadedTerminations);
            
            if (outputLabel != null) {
                outputLabel.setText("Loaded " + employeeList.size() + " employees, " + terminationList.size() + " termination records");
            }
        } catch (Exception e) {
            System.out.println("Error loading employee data: " + e.getMessage());
            if (outputLabel != null) {
                outputLabel.setText("Error loading data: " + e.getMessage());
            }
        }

        if (terminationTableView != null) {
            terminationTableView.getItems().addAll(terminationList);
            
            // Add table selection listener to populate form fields
            terminationTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    populateFormFields(newValue);
                }
            });
        }
    }

    @FXML
    public void fetchEmployeeRecords(ActionEvent actionEvent) {
        try {
            // Clear existing lists
            employeeList.clear();
            terminationList.clear();
            
            // Load all employee data from storage
            ArrayList<hrOfficer> loadedEmployees = (ArrayList<hrOfficer>) HrOfficerStorage.loadAllEmployees();
            employeeList.addAll(loadedEmployees);
            
            // Load existing termination records
            ArrayList<hrOfficer> loadedTerminations = (ArrayList<hrOfficer>) HrOfficerStorage.loadTerminationRecords();
            terminationList.addAll(loadedTerminations);
            
            // Clear and refresh table with all employee records
            if (terminationTableView != null) {
                terminationTableView.getItems().clear();
                terminationTableView.getItems().addAll(employeeList);
            }
            
            if (outputLabel != null) {
                outputLabel.setText("Loaded " + employeeList.size() + " employee records");
            }
        } catch (Exception e) {
            System.out.println("Error loading employee records: " + e.getMessage());
            if (outputLabel != null) {
                outputLabel.setText("Error loading employee records: " + e.getMessage());
            }
        }
    }

    @FXML
    public void searchEmployee(ActionEvent actionEvent) {
        String searchId = "";
        String searchName = "";
        
        if (employeeIdTextField != null) {
            searchId = employeeIdTextField.getText().trim().toLowerCase();
        }
        
        if (employeeNameTextField != null) {
            searchName = employeeNameTextField.getText().trim().toLowerCase();
        }
        
        if (searchId.isEmpty() && searchName.isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter employee ID or name to search");
            return;
        }
        
        ArrayList<hrOfficer> filteredList = new ArrayList<>();
        
        // Search in both employee list and termination list
        for (hrOfficer emp : employeeList) {
            boolean matches = false;
            
            if (!searchId.isEmpty() && emp.getEmployeeId() != null && 
                emp.getEmployeeId().toLowerCase().contains(searchId)) {
                matches = true;
            }
            
            if (!searchName.isEmpty() && emp.getEmployeeName() != null && 
                emp.getEmployeeName().toLowerCase().contains(searchName)) {
                matches = true;
            }
            
            if (matches) {
                filteredList.add(emp);
            }
        }
        
        // Also search in termination records
        for (hrOfficer term : terminationList) {
            boolean matches = false;
            boolean alreadyInList = false;
            
            // Check if already added from employee list
            for (hrOfficer existing : filteredList) {
                if (existing.getEmployeeId() != null && term.getEmployeeId() != null &&
                    existing.getEmployeeId().equals(term.getEmployeeId())) {
                    alreadyInList = true;
                    break;
                }
            }
            
            if (!alreadyInList) {
                if (!searchId.isEmpty() && term.getEmployeeId() != null && 
                    term.getEmployeeId().toLowerCase().contains(searchId)) {
                    matches = true;
                }
                
                if (!searchName.isEmpty() && term.getEmployeeName() != null && 
                    term.getEmployeeName().toLowerCase().contains(searchName)) {
                    matches = true;
                }
                
                if (matches) {
                    filteredList.add(term);
                }
            }
        }
        
        // Update table with filtered results
        if (terminationTableView != null) {
            terminationTableView.getItems().clear();
            terminationTableView.getItems().addAll(filteredList);
        }
        
        if (outputLabel != null) {
            outputLabel.setText("Found " + filteredList.size() + " matching records");
        }
    }

    @FXML
    public void updateTerminationStatus(ActionEvent actionEvent) {
        // Get selected employee from table
        hrOfficer selectedEmployee = null;
        if (terminationTableView != null) {
            selectedEmployee = terminationTableView.getSelectionModel().getSelectedItem();
        }
        
        if (selectedEmployee == null) {
            if (outputLabel != null) outputLabel.setText("Please select an employee from the table");
            return;
        }

        if (terminationReasonComboBox == null || terminationDatePicker == null || terminationStatusComboBox == null) {
            if (outputLabel != null) outputLabel.setText("Required fields not found");
            return;
        }

        if (terminationReasonComboBox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select termination reason");
            return;
        }

        if (terminationDatePicker.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select termination date");
            return;
        }

        if (terminationStatusComboBox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select termination status");
            return;
        }

        // Update selected employee's termination details
        selectedEmployee.setEndDate(terminationDatePicker.getValue());
        selectedEmployee.setReason(terminationReasonComboBox.getValue());
        selectedEmployee.setComments(terminationNotesTextArea != null ? terminationNotesTextArea.getText() : "");
        selectedEmployee.setStatus(terminationStatusComboBox.getValue());

        // Check if this employee already exists in termination list
        boolean existsInTerminationList = false;
        for (int i = 0; i < terminationList.size(); i++) {
            hrOfficer term = terminationList.get(i);
            if (term.getEmployeeId() != null && term.getEmployeeId().equals(selectedEmployee.getEmployeeId())) {
                // Update existing termination record
                terminationList.set(i, selectedEmployee);
                existsInTerminationList = true;
                break;
            }
        }

        // If not in termination list, add it
        if (!existsInTerminationList) {
            terminationList.add(selectedEmployee);
        }

        // Save updated termination records
        try {
            boolean saved = HrOfficerStorage.saveTerminationRecords(terminationList);
            if (!saved) {
                if (outputLabel != null) outputLabel.setText("Failed to save termination record");
                return;
            }

            if (outputLabel != null) outputLabel.setText("Termination status updated successfully for " + selectedEmployee.getEmployeeName());

            // Refresh table to show updated data
            if (terminationTableView != null) {
                terminationTableView.refresh();
            }

            // Clear input fields
            clearFields();
        } catch (Exception e) {
            if (outputLabel != null) outputLabel.setText("Error saving termination record: " + e.getMessage());
        }
    }

    private void populateFormFields(hrOfficer employee) {
        if (employee == null) return;
        
        if (employeeIdTextField != null && employee.getEmployeeId() != null) {
            employeeIdTextField.setText(employee.getEmployeeId());
        }
        
        if (employeeNameTextField != null && employee.getEmployeeName() != null) {
            employeeNameTextField.setText(employee.getEmployeeName());
        }
        
        if (terminationDatePicker != null && employee.getEndDate() != null) {
            terminationDatePicker.setValue(employee.getEndDate());
        }
        
        if (terminationReasonComboBox != null && employee.getReason() != null) {
            terminationReasonComboBox.setValue(employee.getReason());
        }
        
        if (terminationStatusComboBox != null && employee.getStatus() != null) {
            terminationStatusComboBox.setValue(employee.getStatus());
        }
        
        if (terminationNotesTextArea != null && employee.getComments() != null) {
            terminationNotesTextArea.setText(employee.getComments());
        }
    }

    @FXML
    public void clearForm(ActionEvent actionEvent) {
        clearFields();
        if (outputLabel != null) outputLabel.setText("Form cleared");
    }

    private void clearFields() {
        if (employeeIdTextField != null) employeeIdTextField.clear();
        if (employeeNameTextField != null) employeeNameTextField.clear();
        if (terminationReasonComboBox != null) terminationReasonComboBox.getSelectionModel().clearSelection();
        if (terminationDatePicker != null) terminationDatePicker.setValue(null);
        if (terminationStatusComboBox != null) terminationStatusComboBox.getSelectionModel().clearSelection();
        if (terminationNotesTextArea != null) terminationNotesTextArea.clear();
        
        // Clear table selection
        if (terminationTableView != null) {
            terminationTableView.getSelectionModel().clearSelection();
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

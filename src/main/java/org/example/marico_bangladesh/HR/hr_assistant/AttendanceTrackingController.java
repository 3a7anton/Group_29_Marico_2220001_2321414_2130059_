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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class AttendanceTrackingController {
    @FXML
    private TextField employeeIdText;
    @FXML
    private TextField employeeNameText;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField checkInTimeText;
    @FXML
    private TextField checkOutTimeText;
    @FXML
    private ComboBox<String> statusCombobox;
    @FXML
    private TextField searchText;
    @FXML
    private DatePicker filterDatePicker;
    @FXML
    private Label outputLabel;
    @FXML
    private TableView<HrAssistant> attendanceTable;
    @FXML
    private TableColumn<HrAssistant, String> employeeIdCol;
    @FXML
    private TableColumn<HrAssistant, String> employeeNameCol;
    @FXML
    private TableColumn<HrAssistant, LocalDate> dateCol;
    @FXML
    private TableColumn<HrAssistant, LocalTime> checkInTimeCol;
    @FXML
    private TableColumn<HrAssistant, LocalTime> checkOutTimeCol;
    @FXML
    private TableColumn<HrAssistant, Double> hoursWorkedCol;
    @FXML
    private TableColumn<HrAssistant, String> statusCol;

    // main array list
    ArrayList<HrAssistant> attendanceList = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize status combobox with null check
        if (statusCombobox != null) {
            statusCombobox.getItems().addAll("Present", "Absent", "Late", "Half Day", "Holiday");
        }

        // Initialize table columns with null checks
        if (employeeIdCol != null) {
            employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        }
        if (employeeNameCol != null) {
            employeeNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        }
        if (dateCol != null) {
            dateCol.setCellValueFactory(new PropertyValueFactory<>("attendanceDate"));
        }
        if (checkInTimeCol != null) {
            checkInTimeCol.setCellValueFactory(new PropertyValueFactory<>("checkInTime"));
        }
        if (checkOutTimeCol != null) {
            checkOutTimeCol.setCellValueFactory(new PropertyValueFactory<>("checkOutTime"));
        }
        if (hoursWorkedCol != null) {
            hoursWorkedCol.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));
        }
        if (statusCol != null) {
            statusCol.setCellValueFactory(new PropertyValueFactory<>("attendanceStatus"));
        }

        // Set default date to today with null check
        if (datePicker != null) {
            datePicker.setValue(LocalDate.now());
        }

        // Load existing attendance data
        try {
            attendanceList = HrAssistantStorage.loadAttendanceRecords();
            attendanceTable.getItems().addAll(attendanceList);
        } catch (Exception e) {
            outputLabel.setText("Error loading attendance data: " + e.getMessage());
        }

        // Add table selection listener
        attendanceTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
    }

    @FXML
    public void markAttendance(ActionEvent actionEvent) {
        if (!validateForm()) {
            return;
        }

        try {
            LocalTime checkInTime = parseTime(checkInTimeText.getText().trim());
            LocalTime checkOutTime = null;
            
            if (!checkOutTimeText.getText().trim().isEmpty()) {
                checkOutTime = parseTime(checkOutTimeText.getText().trim());
                
                if (checkOutTime.isBefore(checkInTime)) {
                    outputLabel.setText("Check-out time cannot be before check-in time");
                    return;
                }
            }

            HrAssistant attendance = new HrAssistant(
                    employeeIdText.getText().trim(),
                    employeeNameText.getText().trim(),
                    datePicker.getValue(),
                    checkInTime,
                    checkOutTime,
                    statusCombobox.getValue()
            );

            HrAssistantStorage.saveAttendanceRecord(attendance);
            attendanceList.add(attendance);

            // Update table
            attendanceTable.getItems().clear();
            attendanceTable.getItems().addAll(attendanceList);

            clearForm(null);
            outputLabel.setText("Attendance marked successfully");

        } catch (DateTimeParseException e) {
            outputLabel.setText("Please enter valid time format (HH:MM)");
        } catch (IOException e) {
            outputLabel.setText("Error saving attendance: " + e.getMessage());
        }
    }

    @FXML
    public void updateAttendance(ActionEvent actionEvent) {
        HrAssistant selectedAttendance = attendanceTable.getSelectionModel().getSelectedItem();
        if (selectedAttendance == null) {
            outputLabel.setText("Please select an attendance record to update");
            return;
        }

        if (!validateForm()) {
            return;
        }

        try {
            LocalTime checkInTime = parseTime(checkInTimeText.getText().trim());
            LocalTime checkOutTime = null;
            
            if (!checkOutTimeText.getText().trim().isEmpty()) {
                checkOutTime = parseTime(checkOutTimeText.getText().trim());
                
                if (checkOutTime.isBefore(checkInTime)) {
                    outputLabel.setText("Check-out time cannot be before check-in time");
                    return;
                }
            }

            // Update selected attendance
            selectedAttendance.setEmployeeId(employeeIdText.getText().trim());
            selectedAttendance.setEmployeeName(employeeNameText.getText().trim());
            selectedAttendance.setAttendanceDate(datePicker.getValue());
            selectedAttendance.setCheckInTime(checkInTime);
            selectedAttendance.setCheckOutTime(checkOutTime);
            selectedAttendance.setAttendanceStatus(statusCombobox.getValue());

            // Refresh table
            attendanceTable.refresh();

            clearForm(null);
            outputLabel.setText("Attendance updated successfully");

        } catch (DateTimeParseException e) {
            outputLabel.setText("Please enter valid time format (HH:MM)");
        }
    }

    @FXML
    public void searchAttendance(ActionEvent actionEvent) {
        String searchTerm = searchText.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            outputLabel.setText("Please enter search term");
            return;
        }

        ArrayList<HrAssistant> filteredList = new ArrayList<>();
        for (HrAssistant attendance : attendanceList) {
            if (attendance.getEmployeeName().toLowerCase().contains(searchTerm) || 
                attendance.getEmployeeId().toLowerCase().contains(searchTerm) ||
                attendance.getAttendanceStatus().toLowerCase().contains(searchTerm)) {
                filteredList.add(attendance);
            }
        }

        attendanceTable.getItems().clear();
        attendanceTable.getItems().addAll(filteredList);
        outputLabel.setText("Found " + filteredList.size() + " attendance records");
    }

    @FXML
    public void filterByDate(ActionEvent actionEvent) {
        LocalDate filterDate = filterDatePicker.getValue();
        if (filterDate == null) {
            outputLabel.setText("Please select a date to filter");
            return;
        }

        ArrayList<HrAssistant> filteredList = new ArrayList<>();
        for (HrAssistant attendance : attendanceList) {
            if (attendance.getAttendanceDate().equals(filterDate)) {
                filteredList.add(attendance);
            }
        }

        attendanceTable.getItems().clear();
        attendanceTable.getItems().addAll(filteredList);
        outputLabel.setText("Found " + filteredList.size() + " attendance records for " + filterDate);
    }

    @FXML
    public void resetFilter(ActionEvent actionEvent) {
        attendanceTable.getItems().clear();
        attendanceTable.getItems().addAll(attendanceList);
        searchText.clear();
        filterDatePicker.setValue(null);
        outputLabel.setText("Filter reset");
    }

    @FXML
    public void clearForm(ActionEvent actionEvent) {
        employeeIdText.clear();
        employeeNameText.clear();
        datePicker.setValue(LocalDate.now());
        checkInTimeText.clear();
        checkOutTimeText.clear();
        statusCombobox.setValue(null);
        attendanceTable.getSelectionModel().clearSelection();
    }

    private void populateForm(HrAssistant attendance) {
        employeeIdText.setText(attendance.getEmployeeId());
        employeeNameText.setText(attendance.getEmployeeName());
        datePicker.setValue(attendance.getAttendanceDate());
        if (attendance.getCheckInTime() != null) {
            checkInTimeText.setText(attendance.getCheckInTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        if (attendance.getCheckOutTime() != null) {
            checkOutTimeText.setText(attendance.getCheckOutTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        statusCombobox.setValue(attendance.getAttendanceStatus());
    }

    private LocalTime parseTime(String timeString) throws DateTimeParseException {
        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
    }

    private boolean validateForm() {
        if (employeeIdText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter employee ID");
            return false;
        }

        if (employeeNameText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter employee name");
            return false;
        }

        if (datePicker.getValue() == null) {
            outputLabel.setText("Select date");
            return false;
        }

        if (checkInTimeText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter check-in time");
            return false;
        }

        if (statusCombobox.getValue() == null) {
            outputLabel.setText("Select status");
            return false;
        }

        return true;
    }

    @FXML
    public void addAttendanceRecord(ActionEvent actionEvent) {
        try {
            if (!validateForm()) {
                return;
            }

            LocalTime checkInTime = LocalTime.parse(checkInTimeText.getText().trim());
            LocalTime checkOutTime = null;
            if (!checkOutTimeText.getText().trim().isEmpty()) {
                checkOutTime = LocalTime.parse(checkOutTimeText.getText().trim());
            }

            HrAssistant attendance = new HrAssistant();
            attendance.setEmployeeId(employeeIdText.getText().trim());
            attendance.setEmployeeName(employeeNameText.getText().trim());
            attendance.setAttendanceDate(datePicker.getValue());
            attendance.setCheckInTime(checkInTime);
            attendance.setCheckOutTime(checkOutTime);
            attendance.setAttendanceStatus(statusCombobox.getValue());

            HrAssistantStorage.saveAttendanceRecord(attendance);
            attendanceList.add(attendance);
            attendanceTable.getItems().add(attendance);

            clearForm(null);
            outputLabel.setText("Attendance record added successfully");

        } catch (Exception e) {
            outputLabel.setText("Error adding attendance: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void updateAttendanceRecord(ActionEvent actionEvent) {
        updateAttendance(actionEvent);
    }

    @FXML
    public void deleteAttendanceRecord(ActionEvent actionEvent) {
        try {
            HrAssistant selectedAttendance = attendanceTable.getSelectionModel().getSelectedItem();
            if (selectedAttendance == null) {
                outputLabel.setText("Please select an attendance record to delete");
                return;
            }

            attendanceList.remove(selectedAttendance);
            attendanceTable.getItems().remove(selectedAttendance);
            outputLabel.setText("Attendance record deleted successfully");
            clearForm(null);

        } catch (Exception e) {
            outputLabel.setText("Error deleting attendance: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void loadAttendanceRecords(ActionEvent actionEvent) {
        try {
            attendanceList = HrAssistantStorage.loadAttendanceRecords();
            attendanceTable.getItems().clear();
            attendanceTable.getItems().addAll(attendanceList);
            outputLabel.setText("Attendance records loaded successfully. Total records: " + attendanceList.size());
        } catch (Exception e) {
            outputLabel.setText("Error loading attendance records: " + e.getMessage());
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

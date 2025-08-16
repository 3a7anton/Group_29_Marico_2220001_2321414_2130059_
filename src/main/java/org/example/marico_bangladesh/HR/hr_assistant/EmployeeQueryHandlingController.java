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

public class EmployeeQueryHandlingController {
    @FXML
    private TextField queryIdText;
    @FXML
    private TextField employeeIdText;
    @FXML
    private TextField employeeNameText;
    @FXML
    private ComboBox<String> queryTypeCombobox;
    @FXML
    private TextField querySubjectText;
    @FXML
    private TextArea queryDescriptionTextArea;
    @FXML
    private ComboBox<String> priorityCombobox;
    @FXML
    private TextArea responseTextArea;
    @FXML
    private ComboBox<String> statusCombobox;
    @FXML
    private Label outputLabel;
    @FXML
    private TableView<HrAssistant> queryTable;
    @FXML
    private TableColumn<HrAssistant, String> queryIdCol;
    @FXML
    private TableColumn<HrAssistant, String> employeeIdCol;
    @FXML
    private TableColumn<HrAssistant, String> employeeNameCol;
    @FXML
    private TableColumn<HrAssistant, String> queryTypeCol;
    @FXML
    private TableColumn<HrAssistant, String> subjectCol;
    @FXML
    private TableColumn<HrAssistant, String> priorityCol;
    @FXML
    private TableColumn<HrAssistant, String> statusCol;
    @FXML
    private TableColumn<HrAssistant, LocalDate> submissionDateCol;

    // main array list
    ArrayList<HrAssistant> queryList = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize comboboxes
        queryTypeCombobox.getItems().addAll("IT Support", "HR Policy", "Leave", "Payroll", "Training", "General");
        priorityCombobox.getItems().addAll("High", "Medium", "Low");
        statusCombobox.getItems().addAll("Pending", "In Progress", "Resolved", "Closed");

        // Initialize table columns
        queryIdCol.setCellValueFactory(new PropertyValueFactory<>("queryId"));
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employeeNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        queryTypeCol.setCellValueFactory(new PropertyValueFactory<>("queryType"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("querySubject"));
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("queryStatus"));
        submissionDateCol.setCellValueFactory(new PropertyValueFactory<>("submissionDate"));

        // Load existing query data
        try {
            queryList = HrAssistantStorage.loadEmployeeQueries();
            queryTable.getItems().addAll(queryList);
        } catch (Exception e) {
            outputLabel.setText("Error loading query data: " + e.getMessage());
        }

        // Add table selection listener
        queryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
    }

    @FXML
    public void submitQuery(ActionEvent actionEvent) {
        if (!validateForm()) {
            return;
        }

        try {
            // Generate query ID if not provided
            String queryId = queryIdText.getText().trim();
            if (queryId.isEmpty()) {
                queryId = "Q" + System.currentTimeMillis();
            }

            HrAssistant query = new HrAssistant(
                    queryId,
                    employeeIdText.getText().trim(),
                    employeeNameText.getText().trim(),
                    queryTypeCombobox.getValue(),
                    querySubjectText.getText().trim(),
                    queryDescriptionTextArea.getText().trim(),
                    priorityCombobox.getValue()
            );

            HrAssistantStorage.saveEmployeeQuery(query);
            queryList.add(query);

            // Update table
            queryTable.getItems().clear();
            queryTable.getItems().addAll(queryList);

            clearForm();
            outputLabel.setText("Query submitted successfully with ID: " + queryId);

        } catch (IOException e) {
            outputLabel.setText("Error saving query: " + e.getMessage());
        }
    }

    @FXML
    public void updateQueryStatus(ActionEvent actionEvent) {
        HrAssistant selectedQuery = queryTable.getSelectionModel().getSelectedItem();
        if (selectedQuery == null) {
            outputLabel.setText("Please select a query to update");
            return;
        }

        if (statusCombobox.getValue() == null) {
            outputLabel.setText("Please select status");
            return;
        }

        selectedQuery.setQueryStatus(statusCombobox.getValue());
        if (responseTextArea.getText() != null && !responseTextArea.getText().trim().isEmpty()) {
            selectedQuery.setResponse(responseTextArea.getText().trim());
        }

        queryTable.refresh();
        outputLabel.setText("Query status updated successfully");
    }

    @FXML
    public void clearForm() {
        queryIdText.clear();
        employeeIdText.clear();
        employeeNameText.clear();
        queryTypeCombobox.setValue(null);
        querySubjectText.clear();
        queryDescriptionTextArea.clear();
        priorityCombobox.setValue(null);
        responseTextArea.clear();
        statusCombobox.setValue(null);
        queryTable.getSelectionModel().clearSelection();
    }

    private void populateForm(HrAssistant query) {
        queryIdText.setText(query.getQueryId());
        employeeIdText.setText(query.getEmployeeId());
        employeeNameText.setText(query.getEmployeeName());
        queryTypeCombobox.setValue(query.getQueryType());
        querySubjectText.setText(query.getQuerySubject());
        queryDescriptionTextArea.setText(query.getQueryDescription());
        priorityCombobox.setValue(query.getPriority());
        responseTextArea.setText(query.getResponse());
        statusCombobox.setValue(query.getQueryStatus());
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

        if (queryTypeCombobox.getValue() == null) {
            outputLabel.setText("Select query type");
            return false;
        }

        if (querySubjectText.getText().trim().isEmpty()) {
            outputLabel.setText("Enter query subject");
            return false;
        }

        if (queryDescriptionTextArea.getText().trim().isEmpty()) {
            outputLabel.setText("Enter query description");
            return false;
        }

        if (priorityCombobox.getValue() == null) {
            outputLabel.setText("Select priority");
            return false;
        }

        return true;
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

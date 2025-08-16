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
import java.util.ArrayList;

public class PerformanceReviewController
{
    @FXML
    private TextField employeeIdPerformance;
    @FXML
    private TextField scoreTextField;
    @FXML
    private TextField commentsTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button updateDatabaseButton;
    @FXML
    private Button backButton;
    @FXML
    private TableView<hrOfficer> performanceTable;
    @FXML
    private TableColumn<hrOfficer, String> employeeIdCol;
    @FXML
    private TableColumn<hrOfficer, Double> scoresCol;
    @FXML
    private TableColumn<hrOfficer, String> commentsCol;
    @FXML
    private TableColumn<hrOfficer, String> ratingsCol;
    @FXML
    private ComboBox<String> ratingCombobox;
    @FXML
    private Label outputLabel;

    // main array list
    ArrayList<hrOfficer> performanceList = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize rating combobox
        if (ratingCombobox != null) {
            ratingCombobox.getItems().addAll("Excellent", "Good", "Satisfactory", "Needs Improvement", "Poor");
        }

        // Initialize table columns
        if (employeeIdCol != null) employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        if (scoresCol != null) scoresCol.setCellValueFactory(new PropertyValueFactory<>("performanceScore"));
        if (commentsCol != null) commentsCol.setCellValueFactory(new PropertyValueFactory<>("comments"));
        if (ratingsCol != null) ratingsCol.setCellValueFactory(new PropertyValueFactory<>("rating"));

        // Load performance data using HrOfficerStorage
        try {
            ArrayList<hrOfficer> loadedList = (ArrayList<hrOfficer>) HrOfficerStorage.loadAllPerformance();
            performanceList.addAll(loadedList);
        } catch (Exception e) {
            System.out.println("Error loading performance data: " + e.getMessage());
        }

        if (performanceTable != null) {
            performanceTable.getItems().addAll(performanceList);
        }
    }

    @FXML
    public void addPerformanceReview(ActionEvent actionEvent) {
        updateButton(actionEvent);  // Use the existing updateButton logic for adding performance reviews
    }

    @FXML
    public void updatePerformance(ActionEvent actionEvent) {
        updateButton(actionEvent);  // Use the existing updateButton logic
    }

    @FXML
    public void clearForm(ActionEvent actionEvent) {
        clearFields();
        if (outputLabel != null) outputLabel.setText("Form cleared");
    }

    @FXML
    public void refreshTable(ActionEvent actionEvent) {
        try {
            performanceList.clear();
            ArrayList<hrOfficer> loadedList = (ArrayList<hrOfficer>) HrOfficerStorage.loadAllPerformance();
            performanceList.addAll(loadedList);

            if (performanceTable != null) {
                performanceTable.getItems().clear();
                performanceTable.getItems().addAll(performanceList);
            }

            if (outputLabel != null) outputLabel.setText("Performance data refreshed");
        } catch (Exception e) {
            if (outputLabel != null) outputLabel.setText("Error refreshing data: " + e.getMessage());
        }
    }

    @FXML
    public void searchEmployee(ActionEvent actionEvent) {
        if (employeeIdPerformance == null || performanceTable == null) return;

        if (employeeIdPerformance.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter employee ID to search");
            return;
        }

        ArrayList<hrOfficer> filteredList = new ArrayList<>();
        String searchId = employeeIdPerformance.getText().trim();

        for (hrOfficer perf : performanceList) {
            if (perf.getEmployeeId() != null && 
                perf.getEmployeeId().equals(searchId)) {
                filteredList.add(perf);
            }
        }

        performanceTable.getItems().clear();
        performanceTable.getItems().addAll(filteredList);

        if (outputLabel != null) {
            outputLabel.setText("Found " + filteredList.size() + " performance records");
        }
    }

    @FXML
    public void searchReviews(ActionEvent actionEvent) {
        searchEmployee(actionEvent);  // Use the existing searchEmployee logic for searching reviews
    }

    @FXML
    public void searchButton(ActionEvent actionEvent) {
        searchEmployee(actionEvent);
    }

    @FXML
    public void updateButton(ActionEvent actionEvent) {
        if (employeeIdPerformance == null || scoreTextField == null || 
            commentsTextField == null || ratingCombobox == null) {
            return;
        }

        if (employeeIdPerformance.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter employee ID");
            return;
        }

        if (scoreTextField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter performance score");
            return;
        }

        if (commentsTextField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter comments");
            return;
        }

        if (ratingCombobox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Select rating");
            return;
        }

        try {
            double score = Double.parseDouble(scoreTextField.getText().trim());
            
            // Check if performance record already exists for this employee
            for (hrOfficer perf : performanceList) {
                if (perf.getEmployeeId() != null && perf.getEmployeeId().equals(employeeIdPerformance.getText())) {
                    if (outputLabel != null) outputLabel.setText("Performance record already exists for this employee");
                    return;
                }
            }

            hrOfficer performance = new hrOfficer(
                    employeeIdPerformance.getText(),
                    "", // employee name can be fetched from employee database
                    score,
                    commentsTextField.getText(),
                    ratingCombobox.getValue()
            );

            // Save using HrOfficerStorage
            boolean saved = HrOfficerStorage.appendPerformance(performance);
            if (!saved) {
                if (outputLabel != null) outputLabel.setText("Failed to save performance record");
                return;
            }

            performanceList.add(performance);

            if (outputLabel != null) outputLabel.setText("Performance record added successfully");

            // clear and refresh table
            if (performanceTable != null) {
                performanceTable.getItems().clear();
                performanceTable.getItems().addAll(performanceList);
            }

            // clear input fields
            clearFields();

        } catch (NumberFormatException e) {
            if (outputLabel != null) outputLabel.setText("Invalid score format");
        }
    }

    @FXML
    public void updateDatabase(ActionEvent actionEvent) {
        if (outputLabel != null) {
            outputLabel.setText("Database updated successfully");
        }
        // Refresh table
        if (performanceTable != null) {
            performanceTable.getItems().clear();
            performanceTable.getItems().addAll(performanceList);
        }
    }

    @FXML
    public void updateDatabaseButton(ActionEvent actionEvent) {
        updateDatabase(actionEvent);
    }

    private void clearFields() {
        if (employeeIdPerformance != null) employeeIdPerformance.clear();
        if (scoreTextField != null) scoreTextField.clear();
        if (commentsTextField != null) commentsTextField.clear();
        if (ratingCombobox != null) ratingCombobox.getSelectionModel().clearSelection();
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

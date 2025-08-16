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

public class ReviewApplicationsController
{
    @FXML
    private Button backButton;
    @FXML
    private TextField jobTitleSearchField;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<hrOfficer> applicationsTable;
    @FXML
    private TableColumn<hrOfficer, String> jobTitleCol;
    @FXML
    private TableColumn<hrOfficer, String> applicantNameCol;
    @FXML
    private TableColumn<hrOfficer, String> statusCol;
    @FXML
    private Label outputLabel;

    // main array list
    ArrayList<hrOfficer> applicationsList = new ArrayList<>();

    @FXML
    public void initialize() {
        // Initialize table columns
        if (jobTitleCol != null) jobTitleCol.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        if (applicantNameCol != null) applicantNameCol.setCellValueFactory(new PropertyValueFactory<>("applicantName"));
        if (statusCol != null) statusCol.setCellValueFactory(new PropertyValueFactory<>("applicationStatus"));

        // Load job postings data from job_postings.bin file
        try {
            ArrayList<hrOfficer> loadedJobPostings = (ArrayList<hrOfficer>) HrOfficerStorage.loadAll();
            applicationsList.addAll(loadedJobPostings);
            if (outputLabel != null) {
                outputLabel.setText("Loaded " + loadedJobPostings.size() + " job postings from job_postings.bin");
            }
        } catch (Exception e) {
            System.out.println("Error loading job postings data: " + e.getMessage());
            if (outputLabel != null) {
                outputLabel.setText("Error loading job postings: " + e.getMessage());
            }
        }

        if (applicationsTable != null) {
            applicationsTable.getItems().addAll(applicationsList);
        }
    }

    @FXML
    public void searchButton(ActionEvent actionEvent) {
        if (jobTitleSearchField == null || applicationsTable == null) return;

        if (jobTitleSearchField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter job title to search");
            return;
        }

        ArrayList<hrOfficer> filteredList = new ArrayList<>();
        String searchTitle = jobTitleSearchField.getText().trim().toLowerCase();

        for (hrOfficer app : applicationsList) {
            if (app.getJobTitle() != null && 
                app.getJobTitle().toLowerCase().contains(searchTitle)) {
                filteredList.add(app);
            }
        }

        applicationsTable.getItems().clear();
        applicationsTable.getItems().addAll(filteredList);

        if (outputLabel != null) {
            outputLabel.setText("Found " + filteredList.size() + " applications");
        }
    }

    @FXML
    public void resetSearch(ActionEvent actionEvent) {
        if (applicationsTable != null) {
            applicationsTable.getItems().clear();
            applicationsTable.getItems().addAll(applicationsList);
        }
        if (jobTitleSearchField != null) {
            jobTitleSearchField.clear();
        }
        if (outputLabel != null) {
            outputLabel.setText("Showing all applications");
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

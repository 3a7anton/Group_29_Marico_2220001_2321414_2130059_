package org.example.marico_bangladesh.HR.hr_officer;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ManageJobPostingController
{
    @javafx.fxml.FXML
    private TextField jobTitleTextField;
    @javafx.fxml.FXML
    private TextField jobDescriptionTextField;
    @javafx.fxml.FXML
    private ComboBox<String> deptCombobox;
    @javafx.fxml.FXML
    private DatePicker deadlineDatePicker;
    @javafx.fxml.FXML
    private Button backButton;
    @javafx.fxml.FXML
    private Label outputLabel;
    
    @javafx.fxml.FXML
    private TableView<hrOfficer> manageJobPostTable;
    @javafx.fxml.FXML
    private TableColumn<hrOfficer, String> jobTitleCol;
    @javafx.fxml.FXML
    private TableColumn<hrOfficer, String> deptCol;
    @javafx.fxml.FXML
    private TableColumn<hrOfficer, LocalDate> deadlineCol;
    @javafx.fxml.FXML
    private TableColumn<hrOfficer, String> descriptionCol;

    // main array list
    ArrayList<hrOfficer> jobPostingList = new ArrayList<>();

    @javafx.fxml.FXML
    public void initialize() throws IOException {
        // Initialize department combobox with null check
        if (deptCombobox != null) {
            deptCombobox.getItems().addAll("HR", "Product", "Sales", "IT", "Marketing", "Finance");
        }

        // Initialize table columns with null checks
        if (jobTitleCol != null) {
            jobTitleCol.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        }
        if (deptCol != null) {
            deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        }
        if (deadlineCol != null) {
            deadlineCol.setCellValueFactory(new PropertyValueFactory<>("jobDeadline"));
        }
        if (descriptionCol != null) {
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("jobDescription"));
        }

        // read bin file using HrOfficerStorage
        try {
            ArrayList<hrOfficer> loadedList = (ArrayList<hrOfficer>) HrOfficerStorage.loadAll();
            jobPostingList.addAll(loadedList);
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        if (manageJobPostTable != null) {
            manageJobPostTable.getItems().addAll(jobPostingList);
        }
    }

    // .trim avoids unnecessary stuffs
    @javafx.fxml.FXML
    public void submitButton(ActionEvent actionEvent) throws IOException {
        if (jobTitleTextField == null || jobTitleTextField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter job title");
            return;
        }

        if (jobDescriptionTextField == null || jobDescriptionTextField.getText().trim().isEmpty()) {
            if (outputLabel != null) outputLabel.setText("Enter job description");
            return;
        }

        if (deptCombobox == null || deptCombobox.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Please select department");
            return;
        }

        if (deadlineDatePicker == null || deadlineDatePicker.getValue() == null) {
            if (outputLabel != null) outputLabel.setText("Enter deadline date");
            return;
        }

        for (hrOfficer jp : jobPostingList) {
            if (jp.getJobTitle() != null && jp.getDeptCombobox() != null &&
                jp.getJobTitle().equals(jobTitleTextField.getText()) && 
                jp.getDeptCombobox().equals(deptCombobox.getValue())) {
                if (outputLabel != null) outputLabel.setText("Job posting already exists in this department");
                return;
            }
        }

        hrOfficer jp = new hrOfficer(
                jobTitleTextField.getText(),
                deadlineDatePicker.getValue(),
                deptCombobox.getValue(),
                jobDescriptionTextField.getText()
        );

        // Save using HrOfficerStorage
        boolean saved = HrOfficerStorage.append(jp);
        if (!saved) {
            if (outputLabel != null) outputLabel.setText("Failed to save job posting");
            return;
        }

        jobPostingList.add(jp);

        if (outputLabel != null) outputLabel.setText("Job posting added successfully");

        // clear and refresh table
        if (manageJobPostTable != null) {
            manageJobPostTable.getItems().clear();
            manageJobPostTable.getItems().addAll(jobPostingList);
        }
        
        // clear input fields
        if (jobTitleTextField != null) jobTitleTextField.clear();
        if (jobDescriptionTextField != null) jobDescriptionTextField.clear();
        if (deptCombobox != null) deptCombobox.getSelectionModel().clearSelection();
        if (deadlineDatePicker != null) deadlineDatePicker.setValue(null);
    }

    @javafx.fxml.FXML
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

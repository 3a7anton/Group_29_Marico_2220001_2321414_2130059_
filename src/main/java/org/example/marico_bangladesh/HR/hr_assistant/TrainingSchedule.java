package org.example.marico_bangladesh.HR.hr_assistant;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingSchedule implements Serializable {
    private String trainingId;
    private String trainingTitle;
    private String trainerId;
    private String trainerName;
    private LocalDate trainingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String venue;
    private String description;
    private int maxParticipants;
    private String department;
    private String status;

    public TrainingSchedule(String trainingId, String trainingTitle, String trainerId, String trainerName, 
                           LocalDate trainingDate, LocalTime startTime, LocalTime endTime, String venue, 
                           String description, int maxParticipants, String department) {
        this.trainingId = trainingId;
        this.trainingTitle = trainingTitle;
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.trainingDate = trainingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.department = department;
        this.status = "Scheduled";
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public String getTrainingTitle() {
        return trainingTitle;
    }

    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

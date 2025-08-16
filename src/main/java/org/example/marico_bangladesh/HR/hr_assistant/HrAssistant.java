package org.example.marico_bangladesh.HR.hr_assistant;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class HrAssistant implements Serializable {
    
    // Employee Data fields
    private String employeeId;
    private String employeeName;
    private String department;
    private String position;
    private double salary;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private LocalDate hireDate;
    private String status;
    private String address;
    private String emergencyContact;
    
    // Employee Feedback fields
    private String feedbackType;
    private String feedbackContent;
    private int rating;
    private LocalDate feedbackDate;
    
    // Employee Query fields
    private String queryId;
    private String queryType;
    private String querySubject;
    private String queryDescription;
    private String priority;
    private LocalDate submissionDate;
    private String queryStatus;
    private String response;
    
    // Leave Request fields
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String leaveStatus;
    private LocalDate requestDate;
    
    // Training Schedule fields
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
    private String trainingStatus;
    
    // Attendance Record fields
    private LocalDate attendanceDate;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String attendanceStatus;
    private String attendanceComments;
    
    // Document Record fields
    private String documentId;
    private String documentName;
    private String documentType;
    private String documentPath;
    private LocalDate uploadDate;
    private String documentStatus;
    
    // Activity Record fields
    private String activityType;
    private String activityDescription;
    private LocalDate activityDate;
    
    // Default constructor
    public HrAssistant() {
    }
    
    // Constructor for Employee Data
    public HrAssistant(String employeeId, String employeeName, String department, String position, 
                      double salary, String email, String phoneNumber, LocalDate dateOfBirth, 
                      LocalDate hireDate, String address, String emergencyContact) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.hireDate = hireDate;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.status = "Active";
    }
    
    // Constructor for Employee Feedback
    public HrAssistant(String employeeId, String employeeName, String feedbackType, 
                      String feedbackContent, int rating, String department) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.feedbackType = feedbackType;
        this.feedbackContent = feedbackContent;
        this.rating = rating;
        this.department = department;
        this.feedbackDate = LocalDate.now();
    }
    
    // Constructor for Employee Query
    public HrAssistant(String queryId, String employeeId, String employeeName, String queryType,
                      String querySubject, String queryDescription, String priority) {
        this.queryId = queryId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.queryType = queryType;
        this.querySubject = querySubject;
        this.queryDescription = queryDescription;
        this.priority = priority;
        this.submissionDate = LocalDate.now();
        this.queryStatus = "Pending";
        this.response = "";
    }
    
    // Constructor for Leave Request
    public HrAssistant(String employeeId, String employeeName, String leaveType, 
                      LocalDate startDate, LocalDate endDate, String reason) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.leaveStatus = "Pending";
        this.requestDate = LocalDate.now();
    }
    
    // Constructor for Training Schedule
    public HrAssistant(String trainingId, String trainingTitle, String trainerId, String trainerName,
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
        this.trainingStatus = "Scheduled";
    }
    
    // Constructor for Attendance Record
    public HrAssistant(String employeeId, String employeeName, LocalDate attendanceDate,
                      LocalTime checkInTime, LocalTime checkOutTime, String attendanceStatus) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.attendanceDate = attendanceDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.attendanceStatus = attendanceStatus;
    }
    
    // Constructor for Document Record
    public HrAssistant(String documentId, String employeeId, String documentName, String documentType,
                      String documentPath) {
        this.documentId = documentId;
        this.employeeId = employeeId;
        this.documentName = documentName;
        this.documentType = documentType;
        this.documentPath = documentPath;
        this.uploadDate = LocalDate.now();
        this.documentStatus = "Uploaded";
    }
    
    // Constructor for Activity Record
    public HrAssistant(String activityType, String activityDescription, LocalDate activityDate) {
        this.activityType = activityType;
        this.activityDescription = activityDescription;
        this.activityDate = activityDate;
    }
    
    // Getters and Setters for Employee Data fields
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public LocalDate getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    // Getters and Setters for Employee Feedback fields
    public String getFeedbackType() {
        return feedbackType;
    }
    
    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }
    
    public String getFeedbackContent() {
        return feedbackContent;
    }
    
    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public LocalDate getFeedbackDate() {
        return feedbackDate;
    }
    
    public void setFeedbackDate(LocalDate feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
    
    // Getters and Setters for Employee Query fields
    public String getQueryId() {
        return queryId;
    }
    
    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }
    
    public String getQueryType() {
        return queryType;
    }
    
    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
    
    public String getQuerySubject() {
        return querySubject;
    }
    
    public void setQuerySubject(String querySubject) {
        this.querySubject = querySubject;
    }
    
    public String getQueryDescription() {
        return queryDescription;
    }
    
    public void setQueryDescription(String queryDescription) {
        this.queryDescription = queryDescription;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public LocalDate getSubmissionDate() {
        return submissionDate;
    }
    
    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }
    
    public String getQueryStatus() {
        return queryStatus;
    }
    
    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }
    
    public String getResponse() {
        return response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    
    // Getters and Setters for Leave Request fields
    public String getLeaveType() {
        return leaveType;
    }
    
    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getLeaveStatus() {
        return leaveStatus;
    }
    
    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
    
    public LocalDate getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
    
    // Getters and Setters for Training Schedule fields
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
    
    public String getTrainingStatus() {
        return trainingStatus;
    }
    
    public void setTrainingStatus(String trainingStatus) {
        this.trainingStatus = trainingStatus;
    }
    
    // Getters and Setters for Attendance Record fields
    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }
    
    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
    
    public LocalTime getCheckInTime() {
        return checkInTime;
    }
    
    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }
    
    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }
    
    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
    
    public String getAttendanceStatus() {
        return attendanceStatus;
    }
    
    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
    
    public String getAttendanceComments() {
        return attendanceComments;
    }
    
    public void setAttendanceComments(String attendanceComments) {
        this.attendanceComments = attendanceComments;
    }
    
    // Getters and Setters for Document Record fields
    public String getDocumentId() {
        return documentId;
    }
    
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    
    public String getDocumentName() {
        return documentName;
    }
    
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    
    public String getDocumentType() {
        return documentType;
    }
    
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    
    public String getDocumentPath() {
        return documentPath;
    }
    
    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
    
    public LocalDate getUploadDate() {
        return uploadDate;
    }
    
    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }
    
    public String getDocumentStatus() {
        return documentStatus;
    }
    
    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }
    
    // Getters and Setters for Activity Record fields
    public String getActivityType() {
        return activityType;
    }
    
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
    
    public String getActivityDescription() {
        return activityDescription;
    }
    
    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }
    
    public LocalDate getActivityDate() {
        return activityDate;
    }
    
    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }
}

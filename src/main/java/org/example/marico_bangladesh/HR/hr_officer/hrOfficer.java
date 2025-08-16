package org.example.marico_bangladesh.HR.hr_officer;

import java.io.Serializable;
import java.time.LocalDate;

public class hrOfficer implements Serializable {
    // Job Posting fields
    private String jobTitle;
    private LocalDate jobDeadline;
    private String deptCombobox;
    private String jobDescription;
    
    // Employee fields
    private String employeeId;
    private String employeeName;
    private String department;
    private String position;
    private double salary;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    
    // Performance Review fields
    private double performanceScore;
    private String comments;
    private String rating;
    
    // Leave fields
    private String leaveType;
    private LocalDate leaveStartDate;
    private LocalDate leaveEndDate;
    private String leaveStatus;
    private String reason;
    
    // Application fields
    private String applicantName;
    private String applicationStatus;
    
    // Training fields
    private String trainingName;
    private String trainingType;
    
    // Default constructor for job postings (maintaining compatibility)
    public hrOfficer(String jobTitle, LocalDate jobDeadline, String deptCombobox, String jobDescription) {
        this.jobTitle = jobTitle;
        this.jobDeadline = jobDeadline;
        this.deptCombobox = deptCombobox;
        this.jobDescription = jobDescription;
    }
    
    // Constructor for employee data
    public hrOfficer(String employeeId, String employeeName, String department, String position, double salary, String status, LocalDate startDate) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.status = status;
        this.startDate = startDate;
    }
    
    // Constructor for performance review
    public hrOfficer(String employeeId, String employeeName, double performanceScore, String comments, String rating) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.performanceScore = performanceScore;
        this.comments = comments;
        this.rating = rating;
    }
    
    // Constructor for leave management
    public hrOfficer(String employeeId, String employeeName, String leaveType, LocalDate leaveStartDate, LocalDate leaveEndDate, String leaveStatus) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.leaveType = leaveType;
        this.leaveStartDate = leaveStartDate;
        this.leaveEndDate = leaveEndDate;
        this.leaveStatus = leaveStatus;
    }
    
    // Constructor for applications
    public hrOfficer(String jobTitle, String applicantName, String applicationStatus) {
        this.jobTitle = jobTitle;
        this.applicantName = applicantName;
        this.applicationStatus = applicationStatus;
    }

    // Getters and Setters for Job Posting fields
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LocalDate getJobDeadline() {
        return jobDeadline;
    }

    public void setJobDeadline(LocalDate jobDeadline) {
        this.jobDeadline = jobDeadline;
    }

    public String getDeptCombobox() {
        return deptCombobox;
    }

    public void setDeptCombobox(String deptCombobox) {
        this.deptCombobox = deptCombobox;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
    
    // Getters and Setters for Employee fields
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    
    // Getters and Setters for Performance Review fields
    public double getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(double performanceScore) {
        this.performanceScore = performanceScore;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    
    // Getters and Setters for Leave fields
    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getLeaveStartDate() {
        return leaveStartDate;
    }

    public void setLeaveStartDate(LocalDate leaveStartDate) {
        this.leaveStartDate = leaveStartDate;
    }

    public LocalDate getLeaveEndDate() {
        return leaveEndDate;
    }

    public void setLeaveEndDate(LocalDate leaveEndDate) {
        this.leaveEndDate = leaveEndDate;
    }

    public String getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
    
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    // Getters and Setters for Application fields
    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
    
    // Getters and Setters for Training fields
    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }
}

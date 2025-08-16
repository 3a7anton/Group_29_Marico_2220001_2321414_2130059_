package org.example.marico_bangladesh.HR.hr_assistant;

import java.io.*;
import java.util.ArrayList;

public class HrAssistantStorage {
    
    // Save Employee Data
    public static void saveEmployee(HrAssistant employee) throws IOException {
        File file = new File("employee_data.bin");
        FileOutputStream fos;
        ObjectOutputStream oos;

        boolean append = file.exists();
        fos = new FileOutputStream(file, append);
        
        if (append) {
            oos = new AppendableObjectOutputStream(fos);
        } else {
            oos = new ObjectOutputStream(fos);
        }

        oos.writeObject(employee);
        oos.close();
        fos.close();
    }

    // Load Employee Data
    public static ArrayList<HrAssistant> loadEmployees() {
        ArrayList<HrAssistant> employees = new ArrayList<>();
        File file = new File("employee_data.bin");

        if (!file.exists()) {
            return employees;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            while (true) {
                HrAssistant employee = (HrAssistant) ois.readObject();
                employees.add(employee);
            }
        } catch (EOFException eof) {
            System.out.println("End of employee data file reached");
        } catch (ClassNotFoundException cnf) {
            System.out.println("Class not found: " + cnf.getMessage());
        } catch (StreamCorruptedException sce) {
            System.out.println("Employee data file corrupted, deleting: " + sce.getMessage());
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e) {
            System.out.println("Error reading employee data: " + e.getMessage());
            if (file.exists()) {
                file.delete();
            }
        }

        return employees;
    }

    // Save Leave Request
    public static void saveLeaveRequest(HrAssistant leaveRequest) throws IOException {
        File file = new File("leave_request.bin");
        FileOutputStream fos;
        ObjectOutputStream oos;

        boolean append = file.exists();
        fos = new FileOutputStream(file, append);
        
        if (append) {
            oos = new AppendableObjectOutputStream(fos);
        } else {
            oos = new ObjectOutputStream(fos);
        }

        oos.writeObject(leaveRequest);
        oos.close();
        fos.close();
    }

    // Load Leave Requests
    public static ArrayList<HrAssistant> loadLeaveRequests() {
        ArrayList<HrAssistant> leaveRequests = new ArrayList<>();
        File file = new File("leave_request.bin");

        if (!file.exists()) {
            return leaveRequests;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            while (true) {
                HrAssistant leaveRequest = (HrAssistant) ois.readObject();
                leaveRequests.add(leaveRequest);
            }
        } catch (EOFException eof) {
            System.out.println("End of leave request file reached");
        } catch (ClassNotFoundException cnf) {
            System.out.println("Class not found: " + cnf.getMessage());
        } catch (StreamCorruptedException sce) {
            System.out.println("Leave request file corrupted, deleting: " + sce.getMessage());
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e) {
            System.out.println("Error reading leave requests: " + e.getMessage());
            if (file.exists()) {
                file.delete();
            }
        }

        return leaveRequests;
    }

    // Save Attendance Record
    public static void saveAttendanceRecord(HrAssistant attendanceRecord) throws IOException {
        File file = new File("attendance_track.bin");
        FileOutputStream fos;
        ObjectOutputStream oos;

        boolean append = file.exists();
        fos = new FileOutputStream(file, append);
        
        if (append) {
            oos = new AppendableObjectOutputStream(fos);
        } else {
            oos = new ObjectOutputStream(fos);
        }

        oos.writeObject(attendanceRecord);
        oos.close();
        fos.close();
    }

    // Load Attendance Records
    public static ArrayList<HrAssistant> loadAttendanceRecords() {
        ArrayList<HrAssistant> attendanceRecords = new ArrayList<>();
        File file = new File("attendance_track.bin");

        if (!file.exists()) {
            return attendanceRecords;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            while (true) {
                HrAssistant attendanceRecord = (HrAssistant) ois.readObject();
                attendanceRecords.add(attendanceRecord);
            }
        } catch (EOFException eof) {
            System.out.println("End of attendance file reached");
        } catch (ClassNotFoundException cnf) {
            System.out.println("Class not found: " + cnf.getMessage());
        } catch (StreamCorruptedException sce) {
            System.out.println("Attendance file corrupted, deleting: " + sce.getMessage());
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e) {
            System.out.println("Error reading attendance records: " + e.getMessage());
            if (file.exists()) {
                file.delete();
            }
        }

        return attendanceRecords;
    }

    // Save Training Schedule
    public static void saveTrainingSchedule(HrAssistant trainingSchedule) throws IOException {
        File file = new File("train_schedule.bin");
        FileOutputStream fos;
        ObjectOutputStream oos;

        boolean append = file.exists();
        fos = new FileOutputStream(file, append);
        
        if (append) {
            oos = new AppendableObjectOutputStream(fos);
        } else {
            oos = new ObjectOutputStream(fos);
        }

        oos.writeObject(trainingSchedule);
        oos.close();
        fos.close();
    }

    // Load Training Schedules
    public static ArrayList<HrAssistant> loadTrainingSchedules() {
        ArrayList<HrAssistant> trainingSchedules = new ArrayList<>();
        File file = new File("train_schedule.bin");

        if (!file.exists()) {
            return trainingSchedules;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            while (true) {
                HrAssistant trainingSchedule = (HrAssistant) ois.readObject();
                trainingSchedules.add(trainingSchedule);
            }
        } catch (EOFException eof) {
            System.out.println("End of training schedule file reached");
        } catch (ClassNotFoundException cnf) {
            System.out.println("Class not found: " + cnf.getMessage());
        } catch (StreamCorruptedException sce) {
            System.out.println("Training schedule file corrupted, deleting: " + sce.getMessage());
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e) {
            System.out.println("Error reading training schedules: " + e.getMessage());
            if (file.exists()) {
                file.delete();
            }
        }

        return trainingSchedules;
    }

    // Save Employee Feedback
    public static void saveEmployeeFeedback(HrAssistant employeeFeedback) throws IOException {
        File file = new File("employee_feedback.bin");
        FileOutputStream fos;
        ObjectOutputStream oos;

        boolean append = file.exists();
        fos = new FileOutputStream(file, append);
        
        if (append) {
            oos = new AppendableObjectOutputStream(fos);
        } else {
            oos = new ObjectOutputStream(fos);
        }

        oos.writeObject(employeeFeedback);
        oos.close();
        fos.close();
    }

    // Load Employee Feedback
    public static ArrayList<HrAssistant> loadEmployeeFeedback() {
        ArrayList<HrAssistant> feedbackList = new ArrayList<>();
        File file = new File("employee_feedback.bin");

        if (!file.exists()) {
            return feedbackList;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            while (true) {
                HrAssistant feedback = (HrAssistant) ois.readObject();
                feedbackList.add(feedback);
            }
        } catch (EOFException eof) {
            System.out.println("End of feedback file reached");
        } catch (ClassNotFoundException cnf) {
            System.out.println("Class not found: " + cnf.getMessage());
        } catch (StreamCorruptedException sce) {
            System.out.println("Feedback file corrupted, deleting: " + sce.getMessage());
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e) {
            System.out.println("Error reading feedback: " + e.getMessage());
            if (file.exists()) {
                file.delete();
            }
        }

        return feedbackList;
    }

    // Save Employee Query
    public static void saveEmployeeQuery(HrAssistant employeeQuery) throws IOException {
        File file = new File("employee_query.bin");
        FileOutputStream fos;
        ObjectOutputStream oos;

        boolean append = file.exists();
        fos = new FileOutputStream(file, append);
        
        if (append) {
            oos = new AppendableObjectOutputStream(fos);
        } else {
            oos = new ObjectOutputStream(fos);
        }

        oos.writeObject(employeeQuery);
        oos.close();
        fos.close();
    }

    // Load Employee Queries
    public static ArrayList<HrAssistant> loadEmployeeQueries() {
        ArrayList<HrAssistant> queries = new ArrayList<>();
        File file = new File("employee_query.bin");

        if (!file.exists()) {
            return queries;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            while (true) {
                HrAssistant query = (HrAssistant) ois.readObject();
                queries.add(query);
            }
        } catch (EOFException eof) {
            System.out.println("End of query file reached");
        } catch (ClassNotFoundException cnf) {
            System.out.println("Class not found: " + cnf.getMessage());
        } catch (StreamCorruptedException sce) {
            System.out.println("Query file corrupted, deleting: " + sce.getMessage());
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e) {
            System.out.println("Error reading queries: " + e.getMessage());
            if (file.exists()) {
                file.delete();
            }
        }

        return queries;
    }

    // Save Document Record
    public static void saveDocumentRecord(HrAssistant documentRecord) throws IOException {
        File file = new File("document_record.bin");
        FileOutputStream fos;
        ObjectOutputStream oos;

        boolean append = file.exists();
        fos = new FileOutputStream(file, append);
        
        if (append) {
            oos = new AppendableObjectOutputStream(fos);
        } else {
            oos = new ObjectOutputStream(fos);
        }

        oos.writeObject(documentRecord);
        oos.close();
        fos.close();
    }

    // Load Document Records
    public static ArrayList<HrAssistant> loadDocumentRecords() {
        ArrayList<HrAssistant> documents = new ArrayList<>();
        File file = new File("document_record.bin");

        if (!file.exists()) {
            return documents;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            while (true) {
                HrAssistant document = (HrAssistant) ois.readObject();
                documents.add(document);
            }
        } catch (EOFException eof) {
            System.out.println("End of document file reached");
        } catch (ClassNotFoundException cnf) {
            System.out.println("Class not found: " + cnf.getMessage());
        } catch (StreamCorruptedException sce) {
            System.out.println("Document file corrupted, deleting: " + sce.getMessage());
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e) {
            System.out.println("Error reading documents: " + e.getMessage());
            if (file.exists()) {
                file.delete();
            }
        }

        return documents;
    }
}

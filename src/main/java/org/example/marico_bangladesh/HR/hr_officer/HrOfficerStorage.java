package org.example.marico_bangladesh.HR.hr_officer;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HrOfficerStorage {

	private static final String JOB_POSTINGS_FILE = "job_postings.bin";
	private static final String EMPLOYEES_FILE = "employees.bin";
	private static final String PERFORMANCE_FILE = "performance.bin";
	private static final String LEAVE_FILE = "leave.bin";
	private static final String APPLICATIONS_FILE = "applications.bin";
	private static final String TRAINING_FILE = "training.bin";

	// Job Postings methods (maintaining existing functionality)
	static List<hrOfficer> loadAll() {
		return loadFromFile(JOB_POSTINGS_FILE);
	}

	static boolean append(hrOfficer posting) {
		return appendToFile(JOB_POSTINGS_FILE, posting);
	}

	// Employee data methods
	static List<hrOfficer> loadAllEmployees() {
		return loadFromFile(EMPLOYEES_FILE);
	}

	static boolean appendEmployee(hrOfficer employee) {
		return appendToFile(EMPLOYEES_FILE, employee);
	}

	// Performance data methods
	static List<hrOfficer> loadAllPerformance() {
		return loadFromFile(PERFORMANCE_FILE);
	}

	static boolean appendPerformance(hrOfficer performance) {
		return appendToFile(PERFORMANCE_FILE, performance);
	}

	// Leave data methods
	static List<hrOfficer> loadAllLeave() {
		return loadFromFile(LEAVE_FILE);
	}

	static boolean appendLeave(hrOfficer leave) {
		return appendToFile(LEAVE_FILE, leave);
	}

	// Applications data methods
	static List<hrOfficer> loadAllApplications() {
		return loadFromFile(APPLICATIONS_FILE);
	}

	static boolean appendApplication(hrOfficer application) {
		return appendToFile(APPLICATIONS_FILE, application);
	}

	// Training data methods
	static List<hrOfficer> loadAllTraining() {
		return loadFromFile(TRAINING_FILE);
	}

	static boolean appendTraining(hrOfficer training) {
		return appendToFile(TRAINING_FILE, training);
	}

	static List<hrOfficer> loadTrainingAssignments() {
		return loadAllTraining();
	}

	static boolean saveTrainingAssignments(List<hrOfficer> assignments) {
		// Save all assignments (overwrite existing file)
		File file = new File(TRAINING_FILE);
		try (FileOutputStream fos = new FileOutputStream(file, false);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			for (hrOfficer assignment : assignments) {
				oos.writeObject(assignment);
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	// Termination data methods
	private static final String TERMINATION_FILE = "termination.bin";

	static List<hrOfficer> loadTerminationRecords() {
		return loadFromFile(TERMINATION_FILE);
	}

	static boolean saveTerminationRecords(List<hrOfficer> records) {
		File file = new File(TERMINATION_FILE);
		try (FileOutputStream fos = new FileOutputStream(file, false);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			for (hrOfficer record : records) {
				oos.writeObject(record);
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	// Audit data methods
	private static final String AUDIT_FILE = "audit.bin";

	static List<hrOfficer> loadAllAudits() {
		return loadFromFile(AUDIT_FILE);
	}

	static boolean appendAudit(hrOfficer audit) {
		return appendToFile(AUDIT_FILE, audit);
	}

	static boolean saveAuditReports(List<hrOfficer> reports) {
		File file = new File(AUDIT_FILE);
		try (FileOutputStream fos = new FileOutputStream(file, false);
			 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			for (hrOfficer report : reports) {
				oos.writeObject(report);
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	// Generic methods for file operations
	private static List<hrOfficer> loadFromFile(String filename) {
		File file = new File(filename);
		List<hrOfficer> result = new ArrayList<>();
		if (!file.exists()) {
			return result;
		}

		try (FileInputStream fis = new FileInputStream(file);
			 ObjectInputStream ois = new ObjectInputStream(fis)) {
			while (true) {
				Object obj = ois.readObject();
				if (obj instanceof hrOfficer) {
					result.add((hrOfficer) obj);
				} else if (obj instanceof JobPostingRecord record) {
					result.add(convertToHrOfficer(record));
				}
			}
		} catch (EOFException eof) {
			// finished reading
		} catch (StreamCorruptedException sce) {
			// delete corrupted file and start fresh
			file.delete();
			result.clear();
		} catch (IOException | ClassNotFoundException e) {
			// best effort: delete and return empty
			file.delete();
			result.clear();
		}
		return result;
	}

	private static boolean appendToFile(String filename, hrOfficer data) {
		File file = new File(filename);
		boolean append = file.exists();
		try (FileOutputStream fos = new FileOutputStream(file, append);
			 ObjectOutputStream oos = append ? new AppendableObjectOutputStream(fos) : new ObjectOutputStream(fos)) {
			oos.writeObject(data);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private static hrOfficer convertToHrOfficer(JobPostingRecord record) {
		return new hrOfficer(record.title, record.deadline, record.department, record.description);
	}

	private static class JobPostingRecord implements Serializable {
		private static final long serialVersionUID = 1L;
		final String title;
		final String department;
		final String description;
		final LocalDate deadline;

		// Constructor used for deserialization
		@SuppressWarnings("unused")
		JobPostingRecord(String title, String department, String description, LocalDate deadline) {
			this.title = title;
			this.department = department;
			this.description = description;
			this.deadline = deadline;
		}
	}

	private static class AppendableObjectOutputStream extends ObjectOutputStream {
		AppendableObjectOutputStream(OutputStream out) throws IOException {
			super(out);
		}

		@Override
		protected void writeStreamHeader() throws IOException {
			// do nothing
		}
	}
}



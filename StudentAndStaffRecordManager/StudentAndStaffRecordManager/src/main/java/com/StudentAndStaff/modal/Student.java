package com.StudentAndStaff.modal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public class Student {


	@NotEmpty(message = "studentId should not be empty")
	private String studentId;

	@NotEmpty(message = "studentName should not be empty")
	private String studentName;

	@NotEmpty(message = "studentEmail should not be empty")
	@Email(message = "Invalid email format")
	private String studentEmail;

	@NotEmpty(message = "studentBranch should not be empty")
	private String studentBranch;

	@NotNull(message = "studentJoinDate should not be null")
	private LocalDate studentJoinDate;

	@NotEmpty(message = "currentYear should not be empty")
	private String currentYear;

	@NotEmpty(message = "studentYearlyAttendance should not be empty")
	@Size(min = 1, message = "studentYearlyAttendance should have at least one entry")
	private Map<String, Set<StudentAttendance>> studentYearlyAttendance;

	@NotEmpty(message = "yearWiseStudentSubjectsAndMarksAndResult should not be empty")
	@Size(min = 1, message = "yearWiseStudentSubjectsAndMarksAndResult should have at least one entry")
	private Map<String, Set<StudentSubjectsAndMarksAndResult>> yearWiseStudentSubjectsAndMarksAndResult;

	public Student() {
		super();
	}

	public Student(String studentId, String studentName, String studentEmail, String studentBranch,
			LocalDate studentJoinDate, String currentYear, Map<String, Set<StudentAttendance>> studentYearlyAttendance,
			Map<String, Set<StudentSubjectsAndMarksAndResult>> yearWiseStudentSubjectsAndMarksAndResult) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentEmail = studentEmail;
		this.studentBranch = studentBranch;
		this.studentJoinDate = studentJoinDate;
		this.currentYear = currentYear;
		this.studentYearlyAttendance = studentYearlyAttendance;
		this.yearWiseStudentSubjectsAndMarksAndResult = yearWiseStudentSubjectsAndMarksAndResult;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	public String getStudentBranch() {
		return studentBranch;
	}

	public void setStudentBranch(String studentBranch) {
		this.studentBranch = studentBranch;
	}

	public LocalDate getStudentJoinDate() {
		return studentJoinDate;
	}

	public void setStudentJoinDate(LocalDate studentJoinDate) {
		this.studentJoinDate = studentJoinDate;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public Map<String, Set<StudentAttendance>> getStudentYearlyAttendance() {
		return studentYearlyAttendance;
	}

	public void setStudentYearlyAttendance(Map<String, Set<StudentAttendance>> studentYearlyAttendance) {
		this.studentYearlyAttendance = studentYearlyAttendance;
	}

	public Map<String, Set<StudentSubjectsAndMarksAndResult>> getYearWiseStudentSubjectsAndMarksAndResult() {
		return yearWiseStudentSubjectsAndMarksAndResult;
	}

	public void setYearWiseStudentSubjectsAndMarksAndResult(
			Map<String, Set<StudentSubjectsAndMarksAndResult>> yearWiseStudentSubjectsAndMarksAndResult) {
		this.yearWiseStudentSubjectsAndMarksAndResult = yearWiseStudentSubjectsAndMarksAndResult;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", studentEmail=" + studentEmail
				+ ", studentBranch=" + studentBranch + ", studentJoinDate=" + studentJoinDate + ", currentYear="
				+ currentYear + ", studentYearlyAttendance=" + studentYearlyAttendance
				+ ", yearWiseStudentSubjectsAndMarksAndResult=" + yearWiseStudentSubjectsAndMarksAndResult + "]";
	}
   }

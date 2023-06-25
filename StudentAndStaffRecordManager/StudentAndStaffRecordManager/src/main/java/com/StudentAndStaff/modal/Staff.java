package com.StudentAndStaff.modal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


public class Staff {

	private String staffId;

	@NotEmpty(message = "staffName should not be empty")
	private String staffName;

	@NotEmpty(message = "staffEmail should not be empty")
	@Email(regexp = "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
	private String staffEmail;

	private Boolean isTeaching;

	@NotNull(message = "staffJoinDate should not be null")
	private LocalDate staffJoinDate;

	@NotEmpty(message = "teachingYearsOrWorkingYears should not be empty")
	private List<String> teachingYearsOrWorkingYears;

	@NotEmpty(message = "staffAttendanceAndDetails should not be empty")
	private List<StaffAttendance> staffAttendanceAndDetails;

	@NotEmpty(message = "teachingsubjects should not be empty")
	private List<Subjects> teachingsubjects;

	@NotEmpty(message = "teachingOrWorkingBranches should not be empty")
	private List<String> teachingOrWorkingBranches;

    
    public Staff() {
    }


	public Staff(String staffId, String staffName, String staffEmail, Boolean isTeaching, LocalDate staffJoinDate,
			List<String> teachingYearsOrWorkingYears, List<StaffAttendance> staffAttendanceAndDetails,
			List<Subjects> teachingsubjects, List<String> teachingOrWorkingBranches) {
		super();
		this.staffId = staffId;
		this.staffName = staffName;
		this.staffEmail = staffEmail;
		this.isTeaching = isTeaching;
		this.staffJoinDate = staffJoinDate;
		this.teachingYearsOrWorkingYears = teachingYearsOrWorkingYears;
		this.staffAttendanceAndDetails = staffAttendanceAndDetails;
		this.teachingsubjects = teachingsubjects;
		this.teachingOrWorkingBranches = teachingOrWorkingBranches;
	}


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	public String getStaffName() {
		return staffName;
	}


	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}


	public String getStaffEmail() {
		return staffEmail;
	}


	public void setStaffEmail(String staffEmail) {
		this.staffEmail = staffEmail;
	}


	public Boolean getIsTeaching() {
		return isTeaching;
	}


	public void setIsTeaching(Boolean isTeaching) {
		this.isTeaching = isTeaching;
	}


	public LocalDate getStaffJoinDate() {
		return staffJoinDate;
	}


	public void setStaffJoinDate(LocalDate staffJoinDate) {
		this.staffJoinDate = staffJoinDate;
	}


	public List<String> getTeachingYearsOrWorkingYears() {
		return teachingYearsOrWorkingYears;
	}


	public void setTeachingYearsOrWorkingYears(List<String> teachingYearsOrWorkingYears) {
		this.teachingYearsOrWorkingYears = teachingYearsOrWorkingYears;
	}


	public List<StaffAttendance> getStaffAttendanceAndDetails() {
		return staffAttendanceAndDetails;
	}


	public void setStaffAttendanceAndDetails(List<StaffAttendance> staffAttendanceAndDetails) {
		this.staffAttendanceAndDetails = staffAttendanceAndDetails;
	}


	public List<Subjects> getTeachingsubjects() {
		return teachingsubjects;
	}


	public void setTeachingsubjects(List<Subjects> teachingsubjects) {
		this.teachingsubjects = teachingsubjects;
	}


	public List<String> getTeachingOrWorkingBranches() {
		return teachingOrWorkingBranches;
	}


	public void setTeachingOrWorkingBranches(List<String> teachingOrWorkingBranches) {
		this.teachingOrWorkingBranches = teachingOrWorkingBranches;
	}


	@Override
	public String toString() {
		return "Staff [staffId=" + staffId + ", staffName=" + staffName + ", staffEmail=" + staffEmail + ", isTeaching="
				+ isTeaching + ", staffJoinDate=" + staffJoinDate + ", teachingYearsOrWorkingYears="
				+ teachingYearsOrWorkingYears + ", staffAttendanceAndDetails=" + staffAttendanceAndDetails
				+ ", teachingsubjects=" + teachingsubjects + ", teachingOrWorkingBranches=" + teachingOrWorkingBranches
				+ "]";
	}
    

   }

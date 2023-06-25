package com.StudentAndStaff.modal;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

public class StaffAttendance {

    LocalDate staffAttendanceDate;
    String staffAttendancestatus;
    

	public StaffAttendance() {
		super();
	}

	public StaffAttendance(LocalDate staffAttendanceDate, String staffAttendancestatus) {
		super();
		this.staffAttendanceDate = staffAttendanceDate;
		this.staffAttendancestatus = staffAttendancestatus;
	}

	public LocalDate getStaffAttendanceDate() {
		return staffAttendanceDate;
	}

	public void setStaffAttendanceDate(LocalDate staffAttendanceDate) {
		this.staffAttendanceDate = staffAttendanceDate;
	}

	public String getStaffAttendancestatus() {
		return staffAttendancestatus;
	}

	public void setStaffAttendancestatus(String staffAttendancestatus) {
		this.staffAttendancestatus = staffAttendancestatus;
	}

	@Override
	public String toString() {
		return "StaffAttendance [staffAttendanceDate=" + staffAttendanceDate + ", staffAttendancestatus="
				+ staffAttendancestatus + "]";
	}
	   
}
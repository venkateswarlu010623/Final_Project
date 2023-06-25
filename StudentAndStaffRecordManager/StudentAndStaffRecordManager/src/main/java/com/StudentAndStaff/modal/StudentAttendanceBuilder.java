package com.StudentAndStaff.modal;

import java.time.LocalDate;

public class StudentAttendanceBuilder {
    private LocalDate studentAttendanceDate;
    private String studentAttendancestatus;
    

	public StudentAttendanceBuilder() {
		super();
	}

	public StudentAttendanceBuilder(LocalDate studentAttendanceDate, String studentAttendancestatus) {
		super();
		this.studentAttendanceDate = studentAttendanceDate;
		this.studentAttendancestatus = studentAttendancestatus;
	}

	public LocalDate getStudentAttendanceDate() {
		return studentAttendanceDate;
	}

	public void setStudentAttendanceDate(LocalDate studentAttendanceDate) {
		this.studentAttendanceDate = studentAttendanceDate;
	}

	public String getStudentAttendancestatus() {
		return studentAttendancestatus;
	}

	public void setStudentAttendancestatus(String studentAttendancestatus) {
		this.studentAttendancestatus = studentAttendancestatus;
	}

	@Override
	public String toString() {
		return "StudentAttendanceBuilder [studentAttendanceDate=" + studentAttendanceDate + ", studentAttendancestatus="
				+ studentAttendancestatus + "]";
	}
    
   }
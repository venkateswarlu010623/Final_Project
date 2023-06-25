package com.StudentAndStaff.modal;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentAttendance {
	
    LocalDate studentAttendanceDate;
    String studentAttendanceStatus;

    public StudentAttendance() {
    }

	public StudentAttendance(LocalDate studentAttendanceDate, String studentAttendanceStatus) {
		super();
		this.studentAttendanceDate = studentAttendanceDate;
		this.studentAttendanceStatus = studentAttendanceStatus;
	}

	public LocalDate getStudentAttendanceDate() {
		return studentAttendanceDate;
	}

	public void setStudentAttendanceDate(LocalDate studentAttendanceDate) {
		this.studentAttendanceDate = studentAttendanceDate;
	}

	public String getStudentAttendanceStatus() {
		return studentAttendanceStatus;
	}

	public void setStudentAttendanceStatus(String studentAttendanceStatus) {
		this.studentAttendanceStatus = studentAttendanceStatus;
	}

	@Override
	public String toString() {
		return "StudentAttendance [studentAttendanceDate=" + studentAttendanceDate + ", studentAttendanceStatus="
				+ studentAttendanceStatus + "]";
	}
    }

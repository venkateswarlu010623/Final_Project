package com.StudentAndStaff.modal;

public class Subjects {
    int subjectId;
    String subjectName;
    String subjectYear;
    String branch;

    public Subjects() {
    }

	public Subjects(int subjectId, String subjectName, String subjectYear, String branch) {
		super();
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.subjectYear = subjectYear;
		this.branch = branch;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectYear() {
		return subjectYear;
	}

	public void setSubjectYear(String subjectYear) {
		this.subjectYear = subjectYear;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Override
	public String toString() {
		return "Subjects [subjectId=" + subjectId + ", subjectName=" + subjectName + ", subjectYear=" + subjectYear
				+ ", branch=" + branch + "]";
	}

  
}



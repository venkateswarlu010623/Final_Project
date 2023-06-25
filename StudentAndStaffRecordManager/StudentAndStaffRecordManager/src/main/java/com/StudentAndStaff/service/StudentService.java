package com.StudentAndStaff.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.StudentAndStaff.ExceptionHandling.StaffNotFoundException;
import com.StudentAndStaff.ExceptionHandling.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.StudentAndStaff.modal.Student;

@Service
public class StudentService {
	
	List<Student> student1 = new LinkedList<>();

    public ResponseEntity<List<Student>> saveStudents(Student  student)
    {
    	student1.add(student);
		return new ResponseEntity<>(student1, HttpStatus.CREATED);

	}

    //1) Retrieve Student details in year 1 
    public List<Student> getStudentsOfYear1(String year) throws StudentNotFoundException
    {
		List<Student> student2 = student1.stream().filter(s -> s.getCurrentYear().equalsIgnoreCase(year))
                				.collect(Collectors.toList());

		if(student2.size()>0)
		{
			return student2;
		}
		else
		{
			throw new StaffNotFoundException("Student not found details in "+year);
		}
    }

    
	//2) Retrieve Students who is present today in year 1 and branch CSE
    public List<Student> getStudentsOfYear1WhoPresentTodayInCSEBranch(String year, String branch,String attendanceStatus)throws StudentNotFoundException
    {
    	LocalDate today = LocalDate.now();
    	List<Student> student3 =student1.stream().
    							filter(st->st.getCurrentYear().equals(year) &&
    										st.getStudentBranch().equalsIgnoreCase(branch) && 
    										st.getStudentYearlyAttendance().get(year).stream().
    							anyMatch(m->m.getStudentAttendanceDate().isEqual(today) &&
    									m.getStudentAttendanceStatus().equalsIgnoreCase(attendanceStatus))).
    							collect(Collectors.toList());
		if(student3.size()>0)
		{
			return student3;
		}
		else
		{
			throw new StaffNotFoundException("Students not found who is present today in "+year+" and branch" +branch);
		}
    			
    }
    
    //3) Retrieve students who are not passed in year 1
    public List<Student> getStudentsWhoAreNotPassesdInYear1(String result, String year)throws StudentNotFoundException
    {
    	
    	List<Student> student4 = student1.stream()
    							.filter(fs->!fs.getCurrentYear().equalsIgnoreCase(year) &&
    							fs.getYearWiseStudentSubjectsAndMarksAndResult().get(year)
    							.stream().anyMatch(a->a.getResult().equalsIgnoreCase(result)))
    							.collect(Collectors.toList());
		if(student4.size()>0)
		{
			return student4;
		}
		else
		{
			throw new StaffNotFoundException("Students not found who are not passed in"+ year);
		}
    }

}
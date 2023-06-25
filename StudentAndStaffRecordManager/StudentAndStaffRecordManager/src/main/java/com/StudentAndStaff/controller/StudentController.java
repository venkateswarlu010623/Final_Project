package com.StudentAndStaff.controller;

import java.util.List;

import com.StudentAndStaff.modal.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.StudentAndStaff.modal.Student;
import com.StudentAndStaff.service.StudentService;

import javax.validation.Valid;

@RestController
public class StudentController {
	
	@Autowired
	StudentService studentService;



    @PostMapping ("/createStudents")
    public ResponseEntity<List<Student>> saveAllStudents(@Valid @RequestBody Student  student)
    {
           return studentService.saveStudents(student);
    }
    
    
    @GetMapping ("students-in-{year}")
    public ResponseEntity<List<Student>> getStudentListByYear( @PathVariable String  year)
    {
        List<Student> student1 =  studentService.getStudentsOfYear1(year);
        return new ResponseEntity<>(student1, HttpStatus.FOUND);
    }
    
    
    @GetMapping("/students/{attendanceStatus}/in/{year}/{branch}")
    public ResponseEntity<List<Student>> getStudentListByYearAndBranchAndPresentToday(@PathVariable String  year,@PathVariable String  branch,@PathVariable String  attendanceStatus)
    {
        List<Student> student1 =  studentService.getStudentsOfYear1WhoPresentTodayInCSEBranch(year,branch,attendanceStatus);
        return new ResponseEntity<>(student1, HttpStatus.FOUND);
    }
    
    
    @GetMapping ("student/{result}-in-{year}")
    public ResponseEntity<List<Student>> getStudentList3(@PathVariable String  result,@PathVariable String  year)
    {
        List<Student> student1 =  studentService.getStudentsWhoAreNotPassesdInYear1(result,year);
        return new ResponseEntity<>(student1, HttpStatus.FOUND);
    }
    
}

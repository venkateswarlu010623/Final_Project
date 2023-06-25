package com.StudentAndStaff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.StudentAndStaff.modal.Staff;
import com.StudentAndStaff.service.StaffService;

import javax.validation.Valid;

@RestController
public class StaffController {
	
	@Autowired
	StaffService staffService;


    @PostMapping ("/createstaffmembers")
    public ResponseEntity<List<Staff>> saveAllStaff(@Valid @RequestBody Staff  staff )
    {
        List<Staff> staff1 = staffService.saveAllStaff(staff);
        return new ResponseEntity<>(staff1, HttpStatus.CREATED);
    }

    
    
    @GetMapping ("staff-teaching/{year}")
    public ResponseEntity<List<Staff>> getStaffByYearsTeaching(@PathVariable String year)
    {
        List<Staff> staff1 =  staffService.getStaffWhoTeachYear1(year);
        return new ResponseEntity<>(staff1, HttpStatus.CREATED);

    }
    
    
    @GetMapping("staff/present-and-teaching/{subject}")
    public ResponseEntity<List<Staff>> getStaffList(@PathVariable String subject)
    {
        List<Staff> staff1 =  staffService.getStaffWhoPresentTodayAndTeachSubject1(subject);
        return new ResponseEntity<>(staff1, HttpStatus.CREATED);
    }
    
    
    @GetMapping("staff/teaching-more-than-{number}-subjects-in-{year}")
    public ResponseEntity<List<Staff>> getStaffList3(@PathVariable int number,@PathVariable String year)
    {
        List<Staff> staff1 =  staffService.getStaffWhoTeachesMoreThanTwoSubjectsInYear2(number,year);
        return new ResponseEntity<>(staff1, HttpStatus.CREATED);
    }

}

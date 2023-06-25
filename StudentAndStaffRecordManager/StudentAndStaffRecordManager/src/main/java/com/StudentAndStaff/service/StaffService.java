package com.StudentAndStaff.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.StudentAndStaff.ExceptionHandling.StaffNotFoundException;
import org.apache.el.stream.Optional;
import org.springframework.stereotype.Service;

import com.StudentAndStaff.modal.Staff;
import com.StudentAndStaff.modal.Student;

@Service
public class StaffService {

	List<Staff> staff1 = new LinkedList<>();
	
	public List<Staff> saveAllStaff(Staff staff) {
		
		staff1.add(staff);
	    return staff1;
	}



//  1) Retrieve staff details who teach year 1
    public List<Staff> getStaffWhoTeachYear1(String year) throws StaffNotFoundException
    {
    	List<Staff> staff2 = staff1.stream().filter(s->s.getTeachingYearsOrWorkingYears()
    									.contains(year) && s.getIsTeaching())
    									.collect(Collectors.toList());
		if(staff2.size()>0)
        {
            return staff2;
        }
        else
        {
            throw new StaffNotFoundException("Staff list not found who teach "+year);
        }
    }
    
//    2) Retrieve staff who is present today and who teaches Subject1
    public List<Staff> getStaffWhoPresentTodayAndTeachSubject1(String subject)throws StaffNotFoundException
    {
    	LocalDate today = LocalDate.now();
    	List<Staff> staff2 = staff1.stream().filter(s->s.getIsTeaching() != false)
    					.filter(s->s.getTeachingsubjects().stream()
    					.anyMatch(sub->sub.getSubjectName().equalsIgnoreCase(subject)) &&
    					s.getStaffAttendanceAndDetails().stream()
    					.anyMatch(a->a.getStaffAttendanceDate().isEqual(today)))
    					.collect(Collectors.toList());

		if(staff2.size()>0)
		{
			return staff2;
		}
		else
		{
			throw new StaffNotFoundException("Staff list not found who is present today and who teaches Subject1 "+subject);
		}
    }
    
    //3) Retrieve staff who teaches more than 2 subjects in year 2 (edited)

    public List<Staff> getStaffWhoTeachesMoreThanTwoSubjectsInYear2(int number, String year)throws StaffNotFoundException
    {
    	
    	List<Staff> staff2 = staff1.stream().filter(s->s.getIsTeaching() == true &&
    												s.getTeachingsubjects().stream()
    												.count()>number && s.getTeachingsubjects().stream()
    												.anyMatch(a->a.getSubjectYear().equalsIgnoreCase(year)))
    												.collect(Collectors.toList());
		if(staff2.size()>0)
		{
			return staff2;
		}
		else
		{
			throw new StaffNotFoundException("Staff list not found who teaches more than" +number+" subjects in "+year);
		}
    }

}


//public List<Staff> saveAllStaff(List<Staff> staff) {
//	for (Staff staff2 : staff) {
//		Staff st = new Staff();
//		st.setStaffId(staff2.getStaffId());
//		st.setStaffName(staff2.getStaffName());
//		st.setStaffEmail(staff2.getStaffEmail());
//		st.setIsTeaching(staff2.getIsTeaching());
//		st.setStaffJoinDate(staff2.getStaffJoinDate());
//		st.setStaffAttendanceAndDetails(staff2.getStaffAttendanceAndDetails());
//		st.setTeachingOrWorkingBranches(staff2.getTeachingOrWorkingBranches());
//		st.setTeachingsubjects(staff2.getTeachingsubjects());
//		st.setTeachingYearsOrWorkingYears(staff2.getTeachingYearsOrWorkingYears());
//
//		staff1.add(st);
//	}
//    return staff1;
//}


package com.Employee.service;

import com.Employee.Exception_Handling.EmployeesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Employee.modal.Employee;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

	List<Employee> employee1 = new LinkedList<>();

	public List<Employee> saveEmployee(Employee employee)
	{
		employee1.add(employee);
		return employee1;
	}
	

//	1.Retrieve all employees in India

	public List<Employee> getAllEmployeeInIndia(String country) throws EmployeesNotFoundException {
		List<Employee> emp = employee1.stream()
				.filter(e -> e.getLocation().getLocationCountry().equalsIgnoreCase(country))
				.collect(Collectors.toList());

		if(emp.size()>0)
		{
			return emp;
		}
		else
		{
			throw new EmployeesNotFoundException("Employees list not found with country"+country);
		}
	}

//	2.Retrieve employees who are in Chennai or Hyderabad

	public List<Employee> getAllEmployeesByCities(String city1, String city2) throws EmployeesNotFoundException
	{
		List<Employee> emp = employee1.stream()
				.filter(e -> e.getLocation().getLocationName().equalsIgnoreCase(city1) ||
						e.getLocation().getLocationName().equalsIgnoreCase(city2))
				.collect(Collectors.toList());

		if(emp.size()>0)
		{
			return emp;
		}
		else
		{
			throw new EmployeesNotFoundException("Employees list not found with Cities"+city1+","+city2);
		}
	}

	
//	3. Retrieve employees who have Benefits and display benefits details

		public List<String> getAllEmployeesWithBenefits() throws EmployeesNotFoundException {
			List<String> emp =  employee1.stream()
							.filter(e->e.getBenefits() != null)
							.map(emp1->"EmployeeName ="+emp1.getEmployeeName()+" "+"EmployeeBenefits ="+emp1.getBenefits())
							.collect(Collectors.toList());

			if(emp.size()>0)
			{
				return emp;
			}
			else
			{
				throw new EmployeesNotFoundException("Employees list not found with benefits");
			}
		}
		
//	4.Retrieve employee details -  employeeId, employeeName, Salary, deptName, locationName, locationCountry

		public List<String> getAllEmployeeDetails()
		{
			List<String> emp = employee1.stream()
							.map(e->
								"employeeId="+e.getEmployeeId()+
								" employeeName= "+e.getEmployeeName() +
								" Salary= "+e.getSalary()+
								" departmentName= "+e.getDepartment().getDepartmentName()+
								" locationName="+e.getLocation().getLocationName()+
								" locationCountry"+e.getLocation().getLocationCountry())
							.collect(Collectors.toList());

			if(emp.size()>0)
			{
				return emp;
			}
			else
			{
				throw new EmployeesNotFoundException("Employees Details list not found");
			}
		}

//	5.Retrieve employees who are in Chennai and India
		
		public List<Employee> getAllEmployeesInCityAndCountry(String city, String country)
		{
			List<Employee> emp =  employee1.stream()
							.filter(e->e.getLocation().getLocationCountry().equalsIgnoreCase(country)&&
								e.getLocation().getLocationName().equalsIgnoreCase(city))
							.collect(Collectors.toList());

			if(emp.size()>0)
			{
				return emp;
			}
			else
			{
				throw new EmployeesNotFoundException("Employees list not found with country"+country+"city"+city);
			}

		}



		public List<Employee> getOneEmployeeById(int id)
		{

			return employee1.stream().filter(emp->emp.getEmployeeId() == id)
									.collect(Collectors.toList());

		}

		public void deleteOneEmployee(int id)
		{

			List<Employee> emp2 = employee1.stream()
											.filter(emp->!(emp.getEmployeeId() == id))
											.collect(Collectors.toList());

			employee1.clear();
			employee1.addAll(emp2);

		}




}

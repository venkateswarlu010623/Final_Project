package com.Employee.modal;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
public class Employee {
	@NotNull(message = "employeeId should not be null")
	private Integer employeeId;

	@NotEmpty(message = "employeeName should not be empty")
	private String employeeName;

	@NotNull(message = "salary should not be null")
	private Integer salary;

	@NotNull(message = "locationId should not be null")
	private Location location;

	@NotNull(message = "department should not be null")
	private Department department;

	@NotEmpty(message = "benefitsId should not be empty")
	private List<Benefits> benefits;


	public Employee() {
		super();
	}


	public Employee(Integer employeeId, String employeeName, Integer salary, Location location, Department department, List<Benefits> benefits) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.salary = salary;
		this.location = location;
		this.department = department;
		this.benefits = benefits;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Benefits> getBenefits() {
		return benefits;
	}

	public void setBenefits(List<Benefits> benefits) {
		this.benefits = benefits;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"employeeId=" + employeeId +
				", employeeName='" + employeeName + '\'' +
				", salary=" + salary +
				", location=" + location +
				", department=" + department +
				", benefits=" + benefits +
				'}';
	}
}
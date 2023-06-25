package com.Employee.controller;

import java.util.List;
import java.util.Optional;

import com.Employee.Exception_Handling.EmployeesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Employee.modal.Employee;
import com.Employee.service.EmployeeService;

import javax.validation.Valid;


@RestController
public class EmployeeController {

    @Autowired
  private  EmployeeService employeeService;

    @PostMapping("/create/employee")
    public ResponseEntity<List<Employee>> saveAllStaff(@Valid @RequestBody Employee employee)
    {
        List<Employee> employee1 =  employeeService.saveEmployee(employee);
        return new ResponseEntity<>(employee1, HttpStatus.CREATED);
    }

    @GetMapping("/getEmployeesInCountry/{country}")
    public ResponseEntity<List<Employee>> getEmployeeListByCountry(@PathVariable String country)
    {
        List<Employee> emp = employeeService.getAllEmployeeInIndia(country);
        return new ResponseEntity<>(emp, HttpStatus.FOUND);
    }

    @GetMapping("/employees/by-cities/{city1}-{city2}")
    public ResponseEntity<List<Employee>> getEmployeesByCities(@PathVariable String city1, @PathVariable String city2)
    {
        List<Employee> emp = employeeService.getAllEmployeesByCities(city1, city2);
        return new ResponseEntity<>(emp, HttpStatus.FOUND);
    }

    @GetMapping("/employees/with-benefits")
    public ResponseEntity<List<String>> getEmployeesWithBenefits()
    {
        List<String> emp = employeeService.getAllEmployeesWithBenefits();
        return new ResponseEntity<>(emp, HttpStatus.FOUND);
    }

    @GetMapping("/employees/all-details")
    public ResponseEntity<List<String>> getEmployeeDetails()
    {
        List<String> emp = employeeService.getAllEmployeeDetails();
        return new ResponseEntity<>(emp, HttpStatus.FOUND);
    }

    @GetMapping("/employees/by-country-city/{country}/{city}")
    public ResponseEntity<List<Employee>> getEmployeesByCountryAndCity(@PathVariable String city, @PathVariable String country)
    {
        List<Employee> emp = employeeService.getAllEmployeesInCityAndCountry(city, country);
        return new ResponseEntity<>(emp, HttpStatus.FOUND);
    }

    @GetMapping("/employees/by-id/{id}")
    public ResponseEntity<List<Employee>> getEmployeeById(@PathVariable int id)
    {
        List<Employee> emp = employeeService.getOneEmployeeById(id);
        return new ResponseEntity<>(emp, HttpStatus.FOUND);
    }

    @DeleteMapping("/employees/by-id/{id}")
    public void deleteEmployeeById(@PathVariable int id)
    {
        employeeService.deleteOneEmployee(id);
    }

}
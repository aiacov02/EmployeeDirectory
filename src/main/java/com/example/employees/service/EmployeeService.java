package com.example.employees.service;

import com.example.employees.entities.Employee;
import com.example.employees.request.EmployeeRequest;

import java.util.List;

public interface EmployeeService {

    public List<Employee> getAllEmployees();

    public Employee getEmployeeById(Long id);
    public Employee saveEmployee(EmployeeRequest employeeRequest);
    public Employee updateEmployee(long id, EmployeeRequest employeeRequest);
    public void deleteEmployee(Long id);
}

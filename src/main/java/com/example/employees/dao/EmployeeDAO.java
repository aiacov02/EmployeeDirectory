package com.example.employees.dao;

import com.example.employees.entities.Employee;

import java.util.List;

public interface EmployeeDAO {

    public List<Employee> getAllEmployees();

    public Employee getEmployeeById(Long id);

    public Employee saveEmployee(long id, Employee employee);

    public void deleteEmployee(Long id);

}

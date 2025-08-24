package com.example.employees.dao;

import com.example.employees.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {

    public List<Employee> getAllEmployees();

    public Optional<Employee> getEmployeeById(Long id);

    public Employee saveEmployee(long id, Employee employee);

    public boolean deleteEmployee(Long id);

}

package com.example.employees.service.Impl;

import com.example.employees.dao.EmployeeDAO;
import com.example.employees.entities.Employee;
import com.example.employees.request.EmployeeRequest;
import com.example.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    private Employee convertToEmployee(long id, EmployeeRequest employeeRequest) {
        Employee employee = new Employee(employeeRequest.getFirstName(), employeeRequest.getLastName(), employeeRequest.getEmail());
        employee.setId(id);
        return employee;
    }

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(Long id) {

        return employeeDAO.getEmployeeById(id);
    }

    @Transactional
    @Override
    public Employee saveEmployee(EmployeeRequest employeeRequest) {

        Employee employee = convertToEmployee(0, employeeRequest);
        return employeeDAO.saveEmployee(0, employee);
    }

    @Transactional
    @Override
    public Employee updateEmployee(long id, EmployeeRequest employeeRequest) {
        Employee employee = convertToEmployee(id, employeeRequest);
        return employeeDAO.saveEmployee(id, employee);
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteEmployee(Long id) {
        employeeDAO.deleteEmployee(id);
    }
}

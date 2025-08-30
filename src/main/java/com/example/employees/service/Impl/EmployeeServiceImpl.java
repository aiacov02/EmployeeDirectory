package com.example.employees.service.Impl;

import com.example.employees.dao.EmployeeRepository;
import com.example.employees.entities.Employee;
import com.example.employees.exception.EmployeeNotFoundException;
import com.example.employees.request.EmployeeRequest;
import com.example.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    private Employee convertToEmployee(long id, EmployeeRequest employeeRequest) {
        Employee employee = new Employee(employeeRequest.getFirstName(), employeeRequest.getLastName(), employeeRequest.getEmail());
        employee.setId(id);
        return employee;
    }

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found"));
    }

    @Transactional
    @Override
    public Employee saveEmployee(EmployeeRequest employeeRequest) {

        Employee employee = convertToEmployee(0, employeeRequest);
        return repository.save(employee);
    }

    @Transactional
    @Override
    public Employee updateEmployee(long id, EmployeeRequest employeeRequest) {
        Employee employee = convertToEmployee(id, employeeRequest);
        return repository.save(employee);
    }

    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        repository.deleteById(id);
    }
}

package com.example.employees.controller;

import com.example.employees.entities.Employee;
import com.example.employees.request.EmployeeRequest;
import com.example.employees.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employees")
@Tag(name="Employee API Rest Endpoints", description = "Operations related to employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get all employees")
     @GetMapping
     public List<Employee> getAllEmployees() {
         return employeeService.getAllEmployees();
     }

    @Operation(summary = "Get employee by id")
    @GetMapping("/{id}")
    public Employee getEmployeeById(@Min(value=1) Long id) {
        return employeeService.getEmployeeById(id);
    }

    // Additional methods for saving, updating, and deleting employees can be added here
    @Operation(summary = "Save a new employee")
    @PostMapping()
    public Employee saveEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.saveEmployee(employeeRequest);
    }

    @Operation(summary = "Update an existing employee")
    @PutMapping("/{id}")
    public Employee updateEmployee(@Parameter(description = "id of employee to update") @PathVariable @Min(value=1) long id, @Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.updateEmployee(id, employeeRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an employee by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@Parameter(description = "id of employee to delete") @PathVariable @Min(value=1) Long id) {
        employeeService.deleteEmployee(id);
    }
}

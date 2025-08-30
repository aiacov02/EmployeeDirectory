package com.example.employees.dao;

import com.example.employees.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//    No need to write code
}

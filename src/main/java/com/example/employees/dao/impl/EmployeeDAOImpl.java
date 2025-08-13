package com.example.employees.dao.impl;

import com.example.employees.dao.EmployeeDAO;
import com.example.employees.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private final EntityManager entityManager;

    @Autowired
    public EmployeeDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> getAllEmployees() {

        TypedQuery<Employee> query = entityManager.createQuery("FROM Employee e", Employee.class);
        return query.getResultList();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public Employee saveEmployee(long id, Employee employee) {
        return entityManager.merge(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employeeToRemove = entityManager.find(Employee.class, id);
        entityManager.remove(employeeToRemove);
    }
}

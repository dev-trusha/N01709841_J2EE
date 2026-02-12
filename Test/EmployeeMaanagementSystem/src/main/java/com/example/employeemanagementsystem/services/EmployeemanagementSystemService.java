package com.example.employeemanagementsystem.services;

import com.example.employeemanagementsystem.models.Employee;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class EmployeemanagementSystemService {

    private static Map<Integer, Employee> employeeMap = new HashMap<>();
    private static int currentId = 1;

    public List<Employee> getAll() {
        return new ArrayList<>(employeeMap.values());
    }

    public Employee getById(int id) {
        return employeeMap.get(id);
    }

    public Employee create(Employee employee) {
        employee.setId(currentId++);
        employeeMap.put(employee.getId(), employee);
        return employee;
    }

    public Employee update(int id, Employee employee) {
        if (!employeeMap.containsKey(id)) return null;
        employee.setId(id);
        employeeMap.put(id, employee);
        return employee;
    }

    public boolean delete(int id) {
        return employeeMap.remove(id) != null;
    }
}

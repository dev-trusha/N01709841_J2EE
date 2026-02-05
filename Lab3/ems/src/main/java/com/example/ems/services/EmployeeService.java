package com.example.ems.services;

import com.example.ems.models.Employee;
import jakarta.ejb.Stateless;
import java.util.*;

@Stateless
public class EmployeeService {

    private static final Map<Integer, Employee> employees = new HashMap<>();
    private static int currentId = 1;

    public List<Employee> getAll() {
        return new ArrayList<>(employees.values());
    }

    public Employee getById(int id) {
        return employees.get(id);
    }

    public Employee create(Employee emp) {
        emp.setId(currentId++);
        employees.put(emp.getId(), emp);
        return emp;
    }

    public Employee update(int id, Employee emp) {
        if (!employees.containsKey(id)) return null;
        emp.setId(id);
        employees.put(id, emp);
        return emp;
    }

    public boolean delete(int id) {
        return employees.remove(id) != null;
    }
}

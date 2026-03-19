package com.example.ems.repository;

import com.example.ems.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public EmployeeRepository() {
        // Pre-load sample data
        employees.add(new Employee(idCounter.getAndIncrement(), "Ramesh", "Fadatare", "ramesh@gmail.com"));
    }

    public List<Employee> findAll() {
        return employees;
    }

    public void save(Employee employee) {
        employee.setId(idCounter.getAndIncrement());
        employees.add(employee);
    }

    public Employee findById(int id) {
        return employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void update(Employee updated) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == updated.getId()) {
                employees.set(i, updated);
                return;
            }
        }
    }

    public void deleteById(int id) {
        employees.removeIf(e -> e.getId() == id);
    }
}

package com.example.sms.repository;

import com.example.sms.model.Student;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class StudentRepository {

    private final Map<Integer, Student> students = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public StudentRepository() {
        // Pre-populate with 3 default records
        save(new Student(0, "Alice Johnson", 20, "Female", "alice@example.com", "Toronto", LocalDate.of(2005, 3, 12)));
        save(new Student(0, "Bob Smith", 22, "Male", "bob@example.com", "Vancouver", LocalDate.of(2003, 7, 25)));
        save(new Student(0, "Carol White", 21, "Female", "carol@example.com", "Montreal", LocalDate.of(2004, 11, 8)));
    }

    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    public void save(Student student) {
        int id = idCounter.getAndIncrement();
        student.setId(id);
        students.put(id, student);
    }

    public Student findById(int id) {
        return students.get(id);
    }

    public void update(Student updated) {
        students.put(updated.getId(), updated);
    }

    public void deleteById(int id) {
        students.remove(id);
    }
}

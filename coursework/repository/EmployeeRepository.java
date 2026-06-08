package com.worktime.repository;

import com.worktime.exception.EmployeeNotFoundException;
import com.worktime.model.Employee;

import java.util.*;

public class EmployeeRepository {
    private static volatile EmployeeRepository instance;
    private final Map<String, Employee> employees = new LinkedHashMap<>();

    private EmployeeRepository() {}

    public static EmployeeRepository getInstance() {
        if (instance == null) {
            synchronized (EmployeeRepository.class) {
                if (instance == null) {
                    instance = new EmployeeRepository();
                }
            }
        }
        return instance;
    }

    public void add(Employee employee) {
        if (employees.containsKey(employee.getId()))
            throw new IllegalArgumentException("Співробітник з ID " + employee.getId() + " вже існує");
        employees.put(employee.getId(), employee);
    }

    public void remove(String id) {
        if (!employees.containsKey(id))
            throw new EmployeeNotFoundException("Співробітник " + id + " не знайдений");
        employees.remove(id);
    }

    public Employee getById(String id) {
        Employee e = employees.get(id);
        if (e == null)
            throw new EmployeeNotFoundException("Співробітник " + id + " не знайдений");
        return e;
    }

    public Collection<Employee> getAll() {
        return Collections.unmodifiableCollection(employees.values());
    }

    public boolean exists(String id) {
        return employees.containsKey(id);
    }

    public int size() { return employees.size(); }
}

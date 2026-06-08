package com.worktime.memento;

import com.worktime.model.Employee;
import java.io.*;
import java.nio.file.*;

public class EmployeeMemento {
    private final byte[] state;
    private final String employeeId;

    private EmployeeMemento(byte[] state, String employeeId) {
        this.state = state;
        this.employeeId = employeeId;
    }

    public static EmployeeMemento save(Employee employee) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(employee);
            return new EmployeeMemento(bos.toByteArray(), employee.getId());
        } catch (IOException e) {
            throw new RuntimeException("Помилка серіалізації: " + e.getMessage(), e);
        }
    }

    public Employee restore() {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(state);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (Employee) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Помилка десеріалізації: " + e.getMessage(), e);
        }
    }

    public void saveToFile(String path) throws IOException {
        Files.write(Paths.get(path), state);
    }

    public static EmployeeMemento loadFromFile(String path) throws IOException {
        byte[] data = Files.readAllBytes(Paths.get(path));
        // extract id from deserialized object for naming
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            Employee e = (Employee) ois.readObject();
            return new EmployeeMemento(data, e.getId());
        } catch (ClassNotFoundException ex) {
            throw new IOException("Клас не знайдено при завантаженні: " + ex.getMessage());
        }
    }

    public String getEmployeeId() { return employeeId; }
}

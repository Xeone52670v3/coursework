package com.worktime.memento;

import java.io.IOException;
import java.util.*;

public class MementoCaretaker {
    private final Map<String, Deque<EmployeeMemento>> history = new HashMap<>();

    public void save(EmployeeMemento memento) {
        history.computeIfAbsent(memento.getEmployeeId(), k -> new ArrayDeque<>())
               .push(memento);
    }

    public Optional<EmployeeMemento> restore(String employeeId) {
        Deque<EmployeeMemento> stack = history.get(employeeId);
        if (stack == null || stack.isEmpty()) return Optional.empty();
        return Optional.of(stack.pop());
    }

    public void saveToFile(String employeeId, String filePath) throws IOException {
        Deque<EmployeeMemento> stack = history.get(employeeId);
        if (stack == null || stack.isEmpty())
            throw new IOException("Немає збережених станів для " + employeeId);
        stack.peek().saveToFile(filePath);
    }

    public void loadFromFile(String filePath) throws IOException {
        EmployeeMemento memento = EmployeeMemento.loadFromFile(filePath);
        save(memento);
        System.out.println("Стан завантажено з файлу: " + filePath);
    }
}

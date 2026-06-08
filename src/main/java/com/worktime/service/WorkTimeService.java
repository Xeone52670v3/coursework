package com.worktime.service;

import com.worktime.factory.EmployeeFactory;
import com.worktime.memento.EmployeeMemento;
import com.worktime.memento.MementoCaretaker;
import com.worktime.model.*;
import com.worktime.model.enums.AbsenceType;
import com.worktime.model.enums.EventType;
import com.worktime.observer.EventPublisher;
import com.worktime.observer.WorkEventObserver;
import com.worktime.repository.EmployeeRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class WorkTimeService implements EventPublisher {
    private final EmployeeRepository repository = EmployeeRepository.getInstance();
    private final MementoCaretaker caretaker = new MementoCaretaker();
    private final List<WorkEventObserver> observers = new ArrayList<>();

    // ─── Observer ────────────────────────────────────────────
    @Override
    public void subscribe(WorkEventObserver observer)   { observers.add(observer); }
    @Override
    public void unsubscribe(WorkEventObserver observer) { observers.remove(observer); }
    @Override
    public void publish(WorkEvent event) {
        observers.forEach(o -> o.onEvent(event));
    }

    // ─── Employee CRUD ────────────────────────────────────────
    public Employee addEmployee(String type, String id, String name, String email, String extra) {
        Employee emp = EmployeeFactory.of(type).create(id, name, email, extra);
        repository.add(emp);
        publish(new WorkEvent(EventType.REPORT_GENERATED, id,
                "Додано нового співробітника: " + name + " (" + type + ")"));
        return emp;
    }

    public void removeEmployee(String id) {
        Employee emp = repository.getById(id);
        repository.remove(id);
        publish(new WorkEvent(EventType.REPORT_GENERATED, id,
                "Видалено співробітника: " + emp.getName()));
    }

    // ─── Attendance ───────────────────────────────────────────
    public void checkIn(String employeeId, LocalDateTime time) {
        Employee emp = repository.getById(employeeId);
        TimeRecord record = emp.checkIn(time);
        publish(new WorkEvent(EventType.CHECK_IN, employeeId,
                emp.getName() + " зареєстрував прихід о " + time.toLocalTime()));
        if (record.isLate()) {
            publish(new WorkEvent(EventType.LATE_ARRIVAL, employeeId,
                    emp.getName() + " запізнився, прийшов о " + time.toLocalTime()));
        }
    }

    public void checkOut(String employeeId, LocalDateTime time) {
        Employee emp = repository.getById(employeeId);
        emp.checkOut(time);
        publish(new WorkEvent(EventType.CHECK_OUT, employeeId,
                emp.getName() + " зареєстрував вихід о " + time.toLocalTime()));
    }

    // ─── Absences ─────────────────────────────────────────────
    public void registerAbsence(String employeeId, AbsenceType type,
                                LocalDate from, LocalDate to, String reason) {
        Employee emp = repository.getById(employeeId);
        emp.registerAbsence(type, from, to, reason);
        publish(new WorkEvent(EventType.ABSENCE_REGISTERED, employeeId,
                emp.getName() + " | " + type + " [" + from + " – " + to + "]: " + reason));
    }

    // ─── Tasks ────────────────────────────────────────────────
    public WorkTask assignTask(String employeeId, String taskId, String title) {
        Employee emp = repository.getById(employeeId);
        WorkTask task = new WorkTask(taskId, title);
        emp.assignTask(task);
        publish(new WorkEvent(EventType.TASK_ASSIGNED, employeeId,
                "Задачу [" + taskId + "] \"" + title + "\" призначено " + emp.getName()));
        return task;
    }

    public void completeTask(String employeeId, String taskId, double hours) {
        Employee emp = repository.getById(employeeId);
        emp.completeTask(taskId, hours);
        publish(new WorkEvent(EventType.TASK_COMPLETED, employeeId,
                emp.getName() + " виконав задачу [" + taskId + "] за " + hours + "h"));
    }

    // ─── Reports ──────────────────────────────────────────────
    public void printEmployeeReport(String employeeId) {
        Employee emp = repository.getById(employeeId);
        System.out.println("\n===== ЗВІТ: " + emp.getName() + " =====");
        System.out.println(emp.getRoleDescription());
        System.out.printf("Загальний робочий час : %.2f год%n", emp.getTotalHoursWorked());
        System.out.println("Запізнень             : " + emp.getLateArrivalsCount());
        System.out.println("\n-- Записи відвідуваності --");
        emp.getTimeRecords().forEach(System.out::println);
        System.out.println("\n-- Відсутності --");
        if (emp.getAbsences().isEmpty()) System.out.println("  немає");
        else emp.getAbsences().forEach(System.out::println);
        System.out.println("\n-- Задачі --");
        if (emp.getTasks().isEmpty()) System.out.println("  немає");
        else emp.getTasks().forEach(System.out::println);
        publish(new WorkEvent(EventType.REPORT_GENERATED, employeeId,
                "Сформовано звіт для " + emp.getName()));
        System.out.println("==============================\n");
    }

    public void printAllEmployees() {
        System.out.println("\n===== СПИСОК СПІВРОБІТНИКІВ (" + repository.size() + ") =====");
        repository.getAll().forEach(System.out::println);
        System.out.println("=============================================\n");
    }

    // ─── Memento ──────────────────────────────────────────────
    public void saveState(String employeeId) {
        Employee emp = repository.getById(employeeId);
        caretaker.save(EmployeeMemento.save(emp));
        System.out.println("Стан збережено: " + emp.getName());
    }

    public void restoreState(String employeeId) {
        caretaker.restore(employeeId).ifPresentOrElse(
            memento -> {
                Employee restored = memento.restore();
                // replace in repository (remove + add)
                repository.remove(employeeId);
                repository.add(restored);
                System.out.println("Стан відновлено: " + restored.getName());
            },
            () -> System.out.println("Немає збережених станів для " + employeeId)
        );
    }

    public void saveStateToFile(String employeeId, String filePath) throws IOException {
        caretaker.saveToFile(employeeId, filePath);
        System.out.println("Стан збережено у файл: " + filePath);
    }

    public void loadStateFromFile(String filePath) throws IOException {
        caretaker.loadFromFile(filePath);
    }

    public EmployeeRepository getRepository() { return repository; }
}

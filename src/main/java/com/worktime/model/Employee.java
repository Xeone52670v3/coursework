package com.worktime.model;

import com.worktime.model.enums.AbsenceType;
import com.worktime.model.enums.EmployeeRole;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public abstract class Employee implements Serializable {
    protected final String id;
    protected String name;
    protected String email;
    protected EmployeeRole role;
    protected final List<TimeRecord> timeRecords = new ArrayList<>();
    protected final List<Absence> absences = new ArrayList<>();
    protected final List<WorkTask> tasks = new ArrayList<>();

    protected Employee(String id, String name, String email, EmployeeRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public abstract String getRoleDescription();

    public String getId()       { return id; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public EmployeeRole getRole(){ return role; }
    public List<TimeRecord> getTimeRecords() { return Collections.unmodifiableList(timeRecords); }
    public List<Absence> getAbsences()       { return Collections.unmodifiableList(absences); }
    public List<WorkTask> getTasks()         { return Collections.unmodifiableList(tasks); }

    public TimeRecord checkIn(LocalDateTime time) {
        LocalDate today = time.toLocalDate();
        TimeRecord record = getOrCreateRecord(today);
        if (record.getCheckIn() != null)
            throw new com.worktime.exception.AlreadyCheckedInException("Співробітник " + name + " вже відмітив прихід сьогодні");
        record.setCheckIn(time);
        return record;
    }

    public TimeRecord checkOut(LocalDateTime time) {
        LocalDate today = time.toLocalDate();
        TimeRecord record = getOrCreateRecord(today);
        if (record.getCheckIn() == null)
            throw new com.worktime.exception.NotCheckedInException("Співробітник " + name + " не відмічав прихід");
        if (record.getCheckOut() != null)
            throw new com.worktime.exception.AlreadyCheckedOutException("Співробітник " + name + " вже відмітив вихід сьогодні");
        record.setCheckOut(time);
        return record;
    }

    public void registerAbsence(AbsenceType type, LocalDate from, LocalDate to, String reason) {
        absences.add(new Absence(type, from, to, reason));
    }

    public void assignTask(WorkTask task) {
        tasks.add(task);
    }

    public void completeTask(String taskId, double hours) {
        tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId) && !t.isCompleted())
                .findFirst()
                .orElseThrow(() -> new com.worktime.exception.TaskNotFoundException("Задача " + taskId + " не знайдена або вже виконана"))
                .complete(hours);
    }

    public double getTotalHoursWorked() {
        return timeRecords.stream().mapToDouble(TimeRecord::getHoursWorked).sum();
    }

    public long getLateArrivalsCount() {
        return timeRecords.stream().filter(TimeRecord::isLate).count();
    }

    private TimeRecord getOrCreateRecord(LocalDate date) {
        return timeRecords.stream()
                .filter(r -> r.getDate().equals(date))
                .findFirst()
                .orElseGet(() -> { TimeRecord r = new TimeRecord(date); timeRecords.add(r); return r; });
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name + " (" + role + ") | " + email;
    }
}

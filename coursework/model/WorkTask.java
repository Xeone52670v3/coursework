package com.worktime.model;

import java.time.LocalDateTime;

public class WorkTask {
    private final String taskId;
    private final String title;
    private boolean completed;
    private double hoursSpent;
    private final LocalDateTime assignedAt;
    private LocalDateTime completedAt;

    public WorkTask(String taskId, String title) {
        this.taskId = taskId;
        this.title = title;
        this.completed = false;
        this.hoursSpent = 0;
        this.assignedAt = LocalDateTime.now();
    }

    public void complete(double hours) {
        this.completed = true;
        this.hoursSpent = hours;
        this.completedAt = LocalDateTime.now();
    }

    public String getTaskId()           { return taskId; }
    public String getTitle()            { return title; }
    public boolean isCompleted()        { return completed; }
    public double getHoursSpent()       { return hoursSpent; }
    public LocalDateTime getAssignedAt(){ return assignedAt; }
    public LocalDateTime getCompletedAt(){ return completedAt; }

    @Override
    public String toString() {
        return "[" + taskId + "] " + title + (completed ? " ✓ (" + hoursSpent + "h)" : " (в процесі)");
    }
}

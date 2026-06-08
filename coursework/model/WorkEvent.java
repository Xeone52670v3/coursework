package com.worktime.model;

import com.worktime.model.enums.EventType;
import java.time.LocalDateTime;

public class WorkEvent {
    private final EventType type;
    private final String employeeId;
    private final String description;
    private final LocalDateTime timestamp;

    public WorkEvent(EventType type, String employeeId, String description) {
        this.type = type;
        this.employeeId = employeeId;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public EventType getType()       { return type; }
    public String getEmployeeId()    { return employeeId; }
    public String getDescription()   { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + type + " | " + employeeId + " | " + description;
    }
}

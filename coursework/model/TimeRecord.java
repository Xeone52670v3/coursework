package com.worktime.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;

public class TimeRecord {
    private final LocalDate date;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    public TimeRecord(LocalDate date) {
        this.date = date;
    }

    public void setCheckIn(LocalDateTime time)  { this.checkIn = time; }
    public void setCheckOut(LocalDateTime time) { this.checkOut = time; }

    public LocalDate getDate()          { return date; }
    public LocalDateTime getCheckIn()   { return checkIn; }
    public LocalDateTime getCheckOut()  { return checkOut; }

    public boolean isComplete() {
        return checkIn != null && checkOut != null;
    }

    public double getHoursWorked() {
        if (!isComplete()) return 0;
        return Duration.between(checkIn, checkOut).toMinutes() / 60.0;
    }

    public boolean isLate() {
        if (checkIn == null) return false;
        return checkIn.getHour() > 9 || (checkIn.getHour() == 9 && checkIn.getMinute() > 0);
    }

    @Override
    public String toString() {
        return date + " | IN: " + (checkIn != null ? checkIn.toLocalTime() : "-")
                + " | OUT: " + (checkOut != null ? checkOut.toLocalTime() : "-")
                + " | Відпрацьовано: " + String.format("%.2f", getHoursWorked()) + "h";
    }
}

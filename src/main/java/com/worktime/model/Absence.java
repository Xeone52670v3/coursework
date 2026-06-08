package com.worktime.model;

import com.worktime.model.enums.AbsenceType;
import java.time.LocalDate;

public class Absence {
    private final AbsenceType type;
    private final LocalDate from;
    private final LocalDate to;
    private final String reason;

    public Absence(AbsenceType type, LocalDate from, LocalDate to, String reason) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.reason = reason;
    }

    public AbsenceType getType() { return type; }
    public LocalDate getFrom()   { return from; }
    public LocalDate getTo()     { return to; }
    public String getReason()    { return reason; }

    public long getDays() {
        return from.until(to).getDays() + 1;
    }

    @Override
    public String toString() {
        return type + " [" + from + " — " + to + "] (" + getDays() + " дн.): " + reason;
    }
}

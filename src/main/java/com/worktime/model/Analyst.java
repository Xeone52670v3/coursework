package com.worktime.model;

import com.worktime.model.enums.EmployeeRole;

public class Analyst extends Employee {
    private String specialization;

    public Analyst(String id, String name, String email, String specialization) {
        super(id, name, email, EmployeeRole.ANALYST);
        this.specialization = specialization;
    }

    public String getSpecialization() { return specialization; }

    @Override
    public String getRoleDescription() {
        return "Аналітик | Спеціалізація: " + specialization;
    }
}

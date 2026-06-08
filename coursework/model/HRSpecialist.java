package com.worktime.model;

import com.worktime.model.enums.EmployeeRole;

public class HRSpecialist extends Employee {
    private int managedEmployeesCount;

    public HRSpecialist(String id, String name, String email) {
        super(id, name, email, EmployeeRole.HR);
        this.managedEmployeesCount = 0;
    }

    public int getManagedEmployeesCount() { return managedEmployeesCount; }
    public void incrementManaged()        { managedEmployeesCount++; }

    @Override
    public String getRoleDescription() {
        return "HR-спеціаліст | Веде: " + managedEmployeesCount + " співробітників";
    }
}

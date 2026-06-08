package com.worktime.model;

import com.worktime.model.enums.EmployeeRole;

public class Manager extends Employee {
    private String department;

    public Manager(String id, String name, String email, String department) {
        super(id, name, email, EmployeeRole.MANAGER);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String getRoleDescription() {
        return "Менеджер | Відділ: " + department;
    }
}

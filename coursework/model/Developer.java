package com.worktime.model;

import com.worktime.model.enums.EmployeeRole;

public class Developer extends Employee {
    private String techStack;

    public Developer(String id, String name, String email, String techStack) {
        super(id, name, email, EmployeeRole.DEVELOPER);
        this.techStack = techStack;
    }

    public String getTechStack() { return techStack; }
    public void setTechStack(String techStack) { this.techStack = techStack; }

    @Override
    public String getRoleDescription() {
        return "Розробник | Стек: " + techStack;
    }
}

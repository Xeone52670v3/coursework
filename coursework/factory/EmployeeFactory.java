package com.worktime.factory;

import com.worktime.model.Employee;

public abstract class EmployeeFactory {
    public abstract Employee create(String id, String name, String email, String extra);

    public static EmployeeFactory of(String type) {
        return switch (type.toLowerCase()) {
            case "developer" -> new DeveloperFactory();
            case "manager"   -> new ManagerFactory();
            case "hr"        -> new HRFactory();
            case "analyst"   -> new AnalystFactory();
            default -> throw new IllegalArgumentException("Невідомий тип: " + type);
        };
    }
}

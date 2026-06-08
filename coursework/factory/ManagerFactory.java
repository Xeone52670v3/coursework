package com.worktime.factory;

import com.worktime.model.*;

public class ManagerFactory extends EmployeeFactory {
    @Override
    public Employee create(String id, String name, String email, String extra) {
        return new Manager(id, name, email, extra);
    }
}

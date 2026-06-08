package com.worktime.factory;

import com.worktime.model.*;

public class DeveloperFactory extends EmployeeFactory {
    @Override
    public Employee create(String id, String name, String email, String extra) {
        return new Developer(id, name, email, extra);
    }
}

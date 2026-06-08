package com.worktime.factory;

import com.worktime.model.*;

public class AnalystFactory extends EmployeeFactory {
    @Override
    public Employee create(String id, String name, String email, String extra) {
        return new Analyst(id, name, email, extra);
    }
}

package com.worktime.factory;

import com.worktime.model.*;

public class HRFactory extends EmployeeFactory {
    @Override
    public Employee create(String id, String name, String email, String extra) {
        return new HRSpecialist(id, name, email);
    }
}

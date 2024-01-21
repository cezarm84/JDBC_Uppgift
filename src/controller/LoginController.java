package controller;

import DB.EmployeeDb;
import model.Employee;

public class LoginController {
    public boolean authenticate(String username, String password) {
        return "user".equals(username) && "123".equals(password);
    }

    public boolean authenticateWithEmployeeId(String username, String password,
                                              String employeeId, EmployeeDb employeeDb) {
        int employeeIdInt;
        try {
            employeeIdInt = Integer.parseInt(employeeId);
        } catch (NumberFormatException e) {
            return false;
        }

        // logg in med username, password, och employee ID
        //employee id bör finnas i Employee db
        return authenticate(username, password) && authenticateEmployeeId(employeeIdInt, employeeDb);
    }

    private boolean authenticateEmployeeId(int employeeId, EmployeeDb employeeDb) {
        Employee employee = employeeDb.getEmployeeById(employeeId);
        return employee != null;
    }
}

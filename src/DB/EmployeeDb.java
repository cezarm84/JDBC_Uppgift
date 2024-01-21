package DB;

import model.Employee;

import java.sql.*;

public class EmployeeDb {
    private final String url = "jdbc:mysql://localhost:3306/budget_ducklings_db";
    private final String user = "root";
    private final String password = "12345678";

    public boolean isEmployeeIdExists(int employeeId) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM employees WHERE employee_id = ?")) {

            preparedStatement.setInt(1, employeeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returera true om employee ID finns
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Employee getEmployeeById(int employeeId) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM employees WHERE employee_id = ?")) {

            preparedStatement.setInt(1, employeeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToEmployee(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Employee not found
    }

    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        int employeeId = resultSet.getInt("employee_id");
        return new Employee(employeeId);
    }
}

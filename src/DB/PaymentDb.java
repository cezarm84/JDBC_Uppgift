package DB;
import model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PaymentDb {


        private final String url = "jdbc:mysql://localhost:3306/budget_ducklings_db";
        private final String user = "root";
        private final String password = "12345678";

        public List<Payment> getAllPayments() {
            List<Payment> payments = new ArrayList<>();

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM payments")) {

                while (resultSet.next()) {
                    Payment payment = mapResultSetToPayment(resultSet);
                    payments.add(payment);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                // Handle exceptions
            }

            return payments;
        }

    public void addPayment(Payment payment, int employeeId) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO payments (title, payment_date, description," +
                             " category, amount,employee_id) VALUES (?, ?, ?, ?, ?, ?)")) {

            setPaymentPreparedStatementValues(preparedStatement, payment);
             preparedStatement.setInt(6,employeeId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    public void updatePayment(Payment payment) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE payments SET title=?, payment_date=?, description=?, " +
                             "category=?, amount=?, employee_id=? WHERE payment_id=?")) {

            setPaymentPreparedStatementValues(preparedStatement, payment);
            preparedStatement.setInt(7, payment.getPaymentId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void deletePayment(int paymentId) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM payments WHERE payment_id=?")) {

            preparedStatement.setInt(1, paymentId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Payment mapResultSetToPayment(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(resultSet.getInt("payment_id"));
        payment.setTitle(resultSet.getString("title"));
        payment.setPaymentDate(resultSet.getDate("payment_date"));
        payment.setDescription(resultSet.getString("description"));
        payment.setCategory(resultSet.getString("category"));
        payment.setAmount(resultSet.getDouble("amount"));
        payment.setEmployeeId(resultSet.getInt("employee_id"));
        return payment;
    }

    private void setPaymentPreparedStatementValues(PreparedStatement preparedStatement, Payment payment)
            throws SQLException {
        preparedStatement.setString(1, payment.getTitle());
        preparedStatement.setDate(2, payment.getPaymentDate());
        preparedStatement.setString(3, payment.getDescription());
        preparedStatement.setString(4, payment.getCategory());
        preparedStatement.setDouble(5, payment.getAmount());
        preparedStatement.setInt(6, payment.getEmployeeId());

    }



    }



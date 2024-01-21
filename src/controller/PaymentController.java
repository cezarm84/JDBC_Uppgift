package controller;

import model.Payment;
import java.util.List;
import DB.PaymentDb;

public class PaymentController {
    private PaymentDb paymentDb = new PaymentDb();
    private int employeeId;

    public List<Payment> getAllPayments() {
        return paymentDb.getAllPayments();
    }

    public int getEmployeeId() {
        return employeeId;
    }


    public void addPayment(Payment payment, int employeeId) {
        if (isValidPayment(payment)) {
            payment.setEmployeeId(employeeId);
            paymentDb.addPayment(payment,employeeId);
        } else {

            System.out.println("Invalid payment details.");
        }
    }


    public void updatePayment(Payment payment) {
        if (isValidPayment(payment)) {
            paymentDb.updatePayment(payment);
        } else {

            System.out.println("Invalid payment details.");
        }
    }

    public void deletePayment(int paymentId) {
        paymentDb.deletePayment(paymentId);
    }

    private boolean isValidPayment(Payment payment) {
        return payment != null && payment.getAmount() > 0 && payment.getPaymentDate() != null;
    }
}

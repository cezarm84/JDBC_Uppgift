
package userInterface;

import DB.EmployeeDb;
import controller.PaymentController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Payment;

public class EditPage {
    private PaymentController paymentController;
    private Stage stage;
    private EmployeeDb employeeDb;

    public EditPage(PaymentController paymentController, Stage stage, EmployeeDb employeeDb) {
        this.paymentController = paymentController;
        this.stage = stage;
        this.employeeDb = employeeDb;
    }

    public void displayEditPage(Payment payment) {
        VBox root = new VBox(10);
        TextField titleField = new TextField(payment.getTitle());
        TextField descriptionField = new TextField(payment.getDescription());
        TextField amountField = new TextField(String.valueOf(payment.getAmount()));
        TextField employeeIdField = new TextField(String.valueOf(payment.getEmployeeId()));
        Button updateButton = new Button("Update Payment");
        Button deleteButton = new Button("Delete Payment");
        Button backButton = new Button("Back to Invoices");

        updateButton.setOnAction(e -> {
            // Prompt för EmployeeID
            String enteredEmployeeId = getEnteredEmployeeId();
            if (validateEmployeeId(enteredEmployeeId)) {
                payment.setTitle(titleField.getText());
                payment.setDescription(descriptionField.getText());
                payment.setAmount(Double.parseDouble(amountField.getText()));
                payment.setEmployeeId(Integer.parseInt(employeeIdField.getText()));
                paymentController.updatePayment(payment);
            } else {
                showAlert("Invalid employee ID", "Please enter a valid employee ID.");
            }
        });

        deleteButton.setOnAction(e -> {
            String enteredEmployeeId = getEnteredEmployeeId();
            if (validateEmployeeId(enteredEmployeeId)) {
                paymentController.deletePayment(payment.getPaymentId());
                System.out.println("Payment deleted successfully.");
            } else {
                showAlert("Invalid employee ID", "Please enter a valid employee ID.");
            }
        });

        backButton.setOnAction(e -> {
            // Navigera tillbaka till InvoicePage
            InvoicePage invoicePage = new InvoicePage(paymentController, stage, employeeDb);
            invoicePage.displayInvoicePage();
        });

        root.getChildren().addAll(titleField, descriptionField, amountField, employeeIdField, updateButton, deleteButton, backButton);

        Scene scene = new Scene(root, 350, 275);
        stage.setScene(scene);
        stage.setTitle("Edit Page");
        stage.show();
    }


    private String getEnteredEmployeeId() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Employee ID");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter your Employee ID:");


        return dialog.showAndWait().orElse("");
    }


    private boolean validateEmployeeId(String employeeId) {
        if (employeeDb != null) {
            try {
                int employeeIdInt = Integer.parseInt(employeeId);


                if (employeeDb.isEmployeeIdExists(employeeIdInt)) {
                    return true;  // Employee ID är OK
                } else {
                    showAlert("Invalid employee ID", "Employee with the entered ID does not exist.");
                }
            } catch (NumberFormatException e) {
                // handla med om employeeId är inte Ok
                showAlert("Invalid employee ID", "Please enter a valid integer for the employee ID.");
            }
        } else {
            showAlert("Error", "Employee database is not available.");
        }

        return false;
    }

    // visa alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

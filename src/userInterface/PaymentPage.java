package userInterface;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.PaymentController;
import model.Payment;
import DB.EmployeeDb;

import java.sql.Date;

public class PaymentPage {
    private PaymentController paymentController;
    private Stage stage;
    private EmployeeDb employeeDb;

    public PaymentPage(PaymentController paymentController, Stage stage, EmployeeDb employeeDb) {
        this.paymentController = paymentController;
        this.stage = stage;
        this.employeeDb = employeeDb;
        /*if (employeeDb != null) {
            System.out.println("Employee database is available.");
        } else {
            System.out.println("Employee database is not available.");
        }*/
    }


        public void displayPaymentPage ( int employeeId){
            VBox root = new VBox(10);
            Label titleLabel = new Label("Title:");
            Label dateLabel = new Label("Date:");
            Label descriptionLabel = new Label("Description:");
            Label categoryLabel = new Label("Category:");
            Label amountLabel = new Label("Amount:");
            Label employeeIdLabel = new Label("Employee ID:");
            TextField titleField = new TextField();
            DatePicker dateField = new DatePicker();
            TextField descriptionField = new TextField();
            TextField categoryField = new TextField();
            TextField amountField = new TextField();
            TextField employeeIdField = new TextField(String.valueOf(employeeId)); // Display employee ID
            Button addPaymentButton = new Button("Add Payment");
            Button backButton = new Button("Back");

            addPaymentButton.setOnAction(e -> {
                int enteredEmployeeId = Integer.parseInt(employeeIdField.getText());

                // Validate employee ID
                if (validateEmployeeId(enteredEmployeeId)) {
                    Payment payment = new Payment();
                    payment.setTitle(titleField.getText());
                    payment.setPaymentDate(Date.valueOf(dateField.getValue()));
                    payment.setDescription(descriptionField.getText());
                    payment.setCategory(categoryField.getText());
                    payment.setAmount(Double.parseDouble(amountField.getText()));

                    paymentController.addPayment(payment, enteredEmployeeId);

                    clearFields(titleField, descriptionField, categoryField, amountField);
                } else {
                    showAlert("Invalid employee ID", "Employee with the entered ID does not exist.");
                }
            });

            backButton.setOnAction(e -> {
                HomePage homePage = new HomePage(paymentController, stage, null);
                homePage.displayHomePage();
            });

            root.getChildren().addAll(
                    titleLabel, titleField,
                    dateLabel, dateField,
                    descriptionLabel, descriptionField,
                    categoryLabel, categoryField,
                    amountLabel, amountField,
                    employeeIdLabel, employeeIdField,
                    addPaymentButton,
                    backButton);

            Scene scene = new Scene(root, 650, 450);
            stage.setScene(scene);
            stage.setTitle("Payment Page");
            stage.show();
        }

        private boolean validateEmployeeId ( int employeeId){
            if (employeeDb != null) {
                // använda employeeDb att kolla om employee ID finns
                return employeeDb.isEmployeeIdExists(employeeId);
            } else {
                showAlert("Error", "Employee database is not available.");
                return false;
            }
        }

        private void showAlert (String title, String message){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        private void clearFields (TextField...fields){
            for (TextField field : fields) {
                field.clear();
            }
        }
    }



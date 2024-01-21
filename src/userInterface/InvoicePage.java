package userInterface;

import DB.EmployeeDb;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.PaymentController;
import model.Payment;

import java.util.List;

public class InvoicePage {
    private PaymentController paymentController;
    private Stage stage;
    private EmployeeDb employeeDb;

    public InvoicePage(PaymentController paymentController, Stage stage, EmployeeDb employeeDb) {
        this.paymentController = paymentController;
        this.stage = stage;
        this.employeeDb = employeeDb;

    }

    public void displayInvoicePage() {
        List<Payment> payments = paymentController.getAllPayments();
        Label titleLabel = new Label("Invoices in database.");
        VBox root = new VBox(10);

        for (Payment payment : payments) {
            Label paymentLabel = new Label(payment.getTitle() + " - " + payment.getAmount());
            // Edit Button
            Button editButton = new Button("Edit");
            editButton.setOnAction(e -> {

                EditPage editPage = new EditPage(paymentController, stage,employeeDb);
                editPage.displayEditPage(payment);
            });

            root.getChildren().addAll(paymentLabel, editButton);
        }

        root.getChildren().add(titleLabel);
        Button backButton =new Button("Back");
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(paymentController, stage,employeeDb);
            homePage.displayHomePage();
        });
        root.getChildren().add(backButton);

        Scene scene = new Scene(root, 350, 575);
        stage.setScene(scene);
        stage.setTitle("Invoice Page");
        stage.show();
    }
}

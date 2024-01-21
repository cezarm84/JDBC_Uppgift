// HomePage.java

package userInterface;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.PaymentController;
import DB.EmployeeDb;  // Import the EmployeeDb class

public class HomePage {
    private PaymentController paymentController;
    private Stage stage;
    private EmployeeDb employeeDb;  // Add a reference to EmployeeDb

    public HomePage(PaymentController paymentController, Stage stage,EmployeeDb employeeDb) {
        this.paymentController = paymentController;
        this.stage = stage;
        this.employeeDb= employeeDb;

    }

    public void displayHomePage() {
        VBox root = new VBox(10);
        Button viewInvoicesButton = new Button("View Invoices");
        Button addPaymentButton = new Button("Add Payment");

        viewInvoicesButton.setOnAction(e -> {
            InvoicePage invoicePage = new InvoicePage(paymentController, stage, employeeDb);
            invoicePage.displayInvoicePage();
        });

        addPaymentButton.setOnAction(e -> {
            PaymentPage paymentPage = new PaymentPage(paymentController, stage,employeeDb);
            paymentPage.displayPaymentPage(paymentController.getEmployeeId());
        });

        root.getChildren().addAll(viewInvoicesButton, addPaymentButton);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Home Page");
        stage.show();
    }
}

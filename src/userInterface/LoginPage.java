
package userInterface;

import controller.LoginController;
import controller.PaymentController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import DB.EmployeeDb;

public class LoginPage {
    private final LoginController loginController;
    private final Stage stage;
    private EmployeeDb employeeDb;
    public LoginPage(LoginController loginController, Stage stage, EmployeeDb employeeDb) {
        this.loginController = loginController;
        this.stage = stage;
        this.employeeDb = employeeDb;
    }

    public boolean displayLoginPage() {
        VBox root = new VBox(5);

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField employeeIdField = new TextField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String employeeId = employeeIdField.getText();

            // Validera logga in
            if (loginController.authenticate(username, password)) {
                if (loginController.authenticateWithEmployeeId(username, password, employeeId, employeeDb)) {
                    stage.close();
                    PaymentController paymentController = new PaymentController();
                    HomePage homePage = new HomePage(paymentController, stage,employeeDb);
                    homePage.displayHomePage();
                } else {
                    showAlert("Invalid employee ID", "Please enter a valid employee ID.");
                }
            } else {
                showAlert("Invalid user or password", "Please enter valid username and password.");
            }
        });

        root.getChildren().addAll(
                new Label("Username:"), usernameField,
                new Label("Password:"), passwordField,
                new Label("Employee ID:"), employeeIdField,
                loginButton
        );

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show(); // visa logga in skärm

        return !stage.isShowing(); //  true om logga in lyckades
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

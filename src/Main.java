import controller.LoginController;
import controller.PaymentController;
import DB.EmployeeDb;
import javafx.application.Application;
import javafx.stage.Stage;
import userInterface.HomePage;
import userInterface.LoginPage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        // skapa en instance av LoginController
        LoginController loginController = new LoginController();

        //skapa en instance av EmployeeDb
        EmployeeDb employeeDb = new EmployeeDb();
        PaymentController paymentController = new PaymentController();

        // skapa en instance av LoginPage och visa
        LoginPage loginPage = new LoginPage(loginController, stage, employeeDb);
        if (loginPage.displayLoginPage()) {

            // Uppdatera constructor för HomePage
            HomePage homePage = new HomePage(paymentController, stage,employeeDb);

            homePage.displayHomePage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

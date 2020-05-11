package curs.BD.cursBd.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.URL;

@Component
public class MenuController extends WindowController {
    protected static final String ERROR_LOADER_MESSAGE = "Database initial loading error.";

    private final Resource charges;

    private final Resource sales;
    private final Resource werehouses;
    private final Resource expenses;

    private final ApplicationContext applicationContext;

    @FXML
    private AnchorPane mainBg;

    @FXML
    private Button chargesButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button expense_itemsButton;

    @FXML
    private Button warehousesButton;

    @FXML
    private TextArea infoText;

    @FXML
    private Button salesButton;

    public MenuController(@Value("classpath:/ui/Charges.fxml") Resource charges, @Value("classpath:/ui/Sales.fxml") Resource sales,@Value("classpath:/ui/Warehouses.fxml") Resource warehouses,@Value("classpath:/ui/ExpenseItems.fxml") Resource expenses, ApplicationContext applicationContext) {
        this.charges = charges;
        this.sales = sales;
        this.werehouses = warehouses;
        this.expenses = expenses;
        this.applicationContext = applicationContext;
    }

    @FXML
    void initialize(){
        infoText.setText("Welcome to the shop.");
        chargesButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            try {
                URL url = this.charges.getURL();
                FXMLLoader fxmlLoader = new FXMLLoader(url);
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                showMessage(ERROR_LOADER_MESSAGE);
            }
        });
        salesButton.setOnAction(actionEvent -> {
            try {
                URL url = this.sales.getURL();
                FXMLLoader fxmlLoader = new FXMLLoader(url);
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                showMessage(ERROR_LOADER_MESSAGE);
            }
        });

        expense_itemsButton.setOnAction(actionEvent -> {
            try{
            URL url = this.expenses.getURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
                showMessage(ERROR_LOADER_MESSAGE);
        }
        });

        warehousesButton.setOnAction(actionEvent -> {

            try{
                URL url = this.werehouses.getURL();
                FXMLLoader fxmlLoader = new FXMLLoader(url);
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                showMessage(ERROR_LOADER_MESSAGE);
            }
        });
    }

}

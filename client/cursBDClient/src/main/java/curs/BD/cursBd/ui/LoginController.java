package curs.BD.cursBd.ui;

import curs.BD.cursBd.managers.MainManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class LoginController extends WindowController {
    protected static final String ERROR_LOADER_MESSAGE = "Database initial loading error.";

    private final Resource menu;

    private final ApplicationContext applicationContext;

    @FXML
    private Button LogInButton;

    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;

    public LoginController(@Value("classpath:/ui/Menu.fxml") Resource menu, ApplicationContext applicationContext) {
        this.menu = menu;
        this.applicationContext = applicationContext;

    }


    @FXML
    void initialize() {
        LogInButton.setOnAction(actionEvent -> {
            if (textFieldIsEmpty(loginInput) | textFieldIsEmpty(passwordInput)) {
                showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
                return;
            }
            String str = MainManager.login(loginInput.getText(), passwordInput.getText());
            if (!str.equals("error")) {
                try {
                    URL url = this.menu.getURL();
                    FXMLLoader fxmlLoader = new FXMLLoader(url);
                    fxmlLoader.setControllerFactory(applicationContext::getBean);
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                } catch (IOException e) {
                    showProcessResultMessage(ERROR_LOADER_MESSAGE);
                }
            }

        });

    }

}

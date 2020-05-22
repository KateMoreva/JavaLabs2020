package curs.BD.cursBd.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class ErrorController {
    private static final String MESSAGE_TITLE = "Application error.";
    protected static final String AUTHORIZATION_ERROR = "Wrong user name or password";
    protected static final String SERVER_OFFLINE_ERROR = "Server is disconnected. Please try later.";
    protected static final String VALIDATION_TIME_IS_OVER = "Your working time is over. Please login again.";

    protected static void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(MESSAGE_TITLE);
        alert.setHeaderText(null);
        TextArea mes = new TextArea();
        mes.setWrapText(true);
        mes.setText(message);
        mes.setEditable(false);
        VBox dialogMes = new VBox();
        dialogMes.getChildren().add(mes);
        alert.getDialogPane().setMaxWidth(450);
        alert.getDialogPane().setMaxHeight(200);
        alert.getDialogPane().setContent(mes);
        alert.showAndWait();
    }
}

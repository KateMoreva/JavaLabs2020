package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MessageWindowController {

    @FXML
    private AnchorPane helpWindowBG;

    @FXML
    private Button okButton;

    @FXML
    private TextArea helpText;


    @FXML
    void initialize(){
        helpText.setText("Доступные команды:\n"
                + "shaw_all Вывод всей таблицы." + "\n"
                + "add Добавить товар в таблицу, в табице не может быть 2 товара с одинаковым именем" + "\n"
                + "delete Удалить товар из таблицы по имени." + "\n"
                + "price Узнать цену товара по его имени" + "\n"
                + "change_price Изменить цену товара" + "\n"
                + "filter_by_price Вывести товары в заданном ценовом диапазоне цен" + "\n"
                + "[Имя команды] -- help или -h  Вызов справки по каждой команде");
        okButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        });
    }

}

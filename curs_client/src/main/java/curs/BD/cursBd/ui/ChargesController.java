package curs.BD.cursBd.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

@Component
public class ChargesController {

@FXML
private AnchorPane mainBg;

@FXML
private Button filterByAmountButton;

@FXML
private TextField idInputAdd;

@FXML
private TextField dateInputAdd;

@FXML
private TextField priceFromFilterInput;

@FXML
private TextField priceToFilterInput;

@FXML
private Button showAllInfoButton;

@FXML
private TableView<?> table;

@FXML
private TableColumn<?, ?> expevseColumn;

@FXML
private TableColumn<?, ?> dateColumn;

@FXML
private TableColumn<?, ?> amountColumn;

@FXML
private Button showAllButton;

@FXML
private Button addButton;

@FXML
private Button deleteButton1;

@FXML
private TextArea infoText;

@FXML
private TextField amountInputAdd;

@FXML
private TextField idInputAdd1;

@FXML
private TextField dateInputAdd1;

@FXML
private TextField expenseInputShowAllInfo;

@FXML
private Button changeAmountButton;

@FXML
private TextField idInputChangeAmount;

@FXML
private TextField dateInputChangeAmount;

    @FXML
    void initialize(){}

}

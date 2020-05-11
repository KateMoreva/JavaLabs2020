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
public class SalesController {

    @FXML
    private AnchorPane mainBg;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> warehouseIDColumn;

    @FXML
    private TableColumn<?, ?> saleDateColumn;

    @FXML
    private TableColumn<?, ?> productAmountColumn;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private Button showAllButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField dateInputAdd;

    @FXML
    private TextField quantityInputAdd;

    @FXML
    private TextArea infoText;

    @FXML
    private TextField warehouseIDInputAdd;

    @FXML
    private TextField ddateInputDelete;

    @FXML
    private TextField quantityInputDelete;

    @FXML
    private TextField warehouseIDInputDelete;

    @FXML
    private Button changeQuantityButton;

    @FXML
    private TextField quantityInputChange;

    @FXML
    private Button showInfoButton;

    @FXML
    private TextField ddateInputShowInfo;

    @FXML
    private TextField warehouseIDInputShowInfo;

    @FXML
    private TextField warehouseIDInputChange;

    @FXML
    private TextField dateInputChange;

    @FXML
    private Button filterByDateButton;

    @FXML
    private TextField dateFromFilterInput;

    @FXML
    private TextField dateToFilterInput;

    @FXML
    private Button filterByQuantityButton;

    @FXML
    private TextField quantityFromFilterInput;

    @FXML
    private TextField quantityToFilterInput;

    @FXML
    private Button filterByAmountButton;

    @FXML
    private TextField amountFromFilterInput;

    @FXML
    private TextField amountToFilterInput;
    void initialize(){}


}

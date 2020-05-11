package curs.BD.cursBd.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

@Component
public class SalesController {

    @FXML
    private AnchorPane mainBg;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> warehouseNameColumn;

    @FXML
    private TableColumn<?, ?> saleDateColumn;

    @FXML
    private TableColumn<?, ?> amountColumn;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private Button showAllButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField quantityInputAdd;

    @FXML
    private TextArea infoText;

    @FXML
    private TextField warehouseNameInputAdd;

    @FXML
    private Button filterButton;

    @FXML
    private TextField quantityInputChange;

    @FXML
    private Button showInfoButton;

    @FXML
    private TextField warehouseNameInputShowInfo;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private Button filterByDateButton;

    @FXML
    private DatePicker dateInputAdd;

    @FXML
    private TextField hoursInput;

    @FXML
    private TextField minuteInput;

    @FXML
    private TextField secondsInput;

    @FXML
    private DatePicker dateInputChange;

    @FXML
    private TextField hoursInputChange;

    @FXML
    private TextField minuteInputChange;

    @FXML
    private TextField secondsInputChange;

    @FXML
    void initialize(){}


}

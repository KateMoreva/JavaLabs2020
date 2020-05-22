package curs.BD.cursBd.ui;

import curs.BD.cursBd.model.Sales;
import curs.BD.cursBd.managers.SalesManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class SalesController extends WindowController {

    @FXML
    private AnchorPane mainBg;

    @FXML
    private TableView<Sales> table;

    @FXML
    private TableColumn<String, String> warehouseNameColumn;

    @FXML
    private TableColumn<String, String> saleDateColumn;

    @FXML
    private TableColumn<String, Integer> amountColumn;

    @FXML
    private TableColumn<String, Integer> quantityColumn;

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
    private Button changeButton;

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
    void initialize() {
        setInputNumericFieldStyle(quantityInputAdd);
        setInputNumericFieldStyle(quantityInputChange);
        initFilterButton();
        setDatesInputFieldsStyle();
        setTimeInputFieldsStyle();
        infoText.setText("Sales");
        setColumnsValuesFactory();
        initShowAllButton();
        initAddButton();
        initDeleteButton();
        initChangeButton();
        initShowButton();

    }

    private void setColumnsValuesFactory() {
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        warehouseNameColumn.setCellValueFactory(new PropertyValueFactory<>("warehousesName"));
        saleDateColumn.setCellValueFactory(new PropertyValueFactory<>("saleDateStr"));
    }

    private void setTimeInputFieldsStyle() {
        setInputTimeFieldsStyle(hoursInput, minuteInput, secondsInput);
        setInputTimeFieldsStyle(hoursInputChange, minuteInputChange, secondsInputChange);
    }

    private void setDatesInputFieldsStyle() {
        setTodayDate(dateInputAdd);
        setTodayDate(dateInputChange);
        setTodayDate(dateFrom);
        setTodayDate(dateTo);
    }

    private void showAllTaleData() {
        final List<Sales> sales = SalesManager.showAll();
        if (sales.isEmpty()) {
            showProcessResultMessage(ERROR_NO_DATA);
        } else {
            showResults(sales);
        }
    }

    private void showResults(final List<Sales> sales) {
        table.getItems().clear();
        table.getItems().addAll(sales);
    }

    private void initShowAllButton() {
        showAllButton.setOnAction(actionEvent -> {
            showAllTaleData();
        });
    }

    private void initAddButton() {
        addButton.setOnAction(actionEvent -> {
            if (textFieldIsEmpty(warehouseNameInputAdd)) {
                showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
            } else {
                Timestamp dataAndTime = Timestamp.valueOf(getTimeAndDateInDataBaseFormat(dateInputAdd, hoursInput, minuteInput, secondsInput));
                if (SalesManager.add(Integer.parseInt(quantityInputAdd.getText()), dataAndTime, warehouseNameInputAdd.getText())) {
                    showProcessResultMessage(SUCCESSFULLY_ADDED);
                    showAllTaleData();
                } else {
                    showProcessResultMessage(ERROR_INPUT_WRONG_NAME);
                }
                showAllTaleData();
                cleanTextField(warehouseNameInputAdd);
                cleanTextField(quantityInputAdd);
                cleanTextField(hoursInput);
                cleanTextField(minuteInput);
                cleanTextField(secondsInput);
                cleanDatePicker(dateInputAdd);
            }

        });
    }

    private void initDeleteButton() {
        deleteButton.setOnAction(actionEvent -> {
            if (textFieldIsEmpty(warehouseNameInputAdd)) {
                showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
            } else if (textFieldIsEmpty(quantityInputAdd)) {
                showProcessResultMessage(ERROR_INPUT_QUANTITY_EMPTY);
            } else {
                Timestamp dataAndTime = Timestamp.valueOf(getTimeAndDateInDataBaseFormat(dateInputAdd, hoursInput, minuteInput, secondsInput));
                List<Sales> sales = SalesManager.find(Integer.parseInt(quantityInputAdd.getText()), dataAndTime, warehouseNameInputAdd.getText());
                if (sales.isEmpty()) {
                    showProcessResultMessage(ERROR_NO_DATA);
                }
                if (SalesManager.delete(Integer.parseInt(quantityInputAdd.getText()), dataAndTime, warehouseNameInputAdd.getText())) {
                    showProcessResultMessage(SUCCESSFULLY_DELETED);
                }
                cleanTextField(warehouseNameInputAdd);
                cleanTextField(quantityInputAdd);
                cleanTextField(hoursInput);
                cleanTextField(minuteInput);
                cleanTextField(secondsInput);
                cleanDatePicker(dateInputAdd);
            }
            showAllTaleData();
        });
    }

    private void initFilterButton() {
        filterByDateButton.setOnAction(actionEvent -> {
            Timestamp timeFrom = Timestamp.valueOf(getDateAndChangeToDataBaseFormat(dateFrom) + " 00:00:00.000");
            Timestamp timeTo = Timestamp.valueOf(getDateAndChangeToDataBaseFormat(dateTo) + " 23:59:59.000");
            showResults(SalesManager.filterByDate(timeFrom, timeTo));
        });
    }

    private void initShowButton() {
        showInfoButton.setOnAction(actionEvent -> {
            cleanDatePicker(dateFrom);
            cleanDatePicker(dateTo);
            if (textFieldIsEmpty(warehouseNameInputShowInfo)) {
                showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
            } else if (SalesManager.findByWarehouseName(warehouseNameInputShowInfo.getText()).isEmpty()) {
                showProcessResultMessage(ERROR_NO_DATA);
            } else {
                showResults(SalesManager.findByWarehouseName(warehouseNameInputShowInfo.getText()));
            }
            cleanTextField(warehouseNameInputShowInfo);

        });
    }

    private void initChangeButton() {
        changeButton.setOnAction(actionEvent -> {
            Timestamp dateAndTime = Timestamp.valueOf(getTimeAndDateInDataBaseFormat(dateInputAdd, hoursInput, minuteInput, secondsInput));
            Timestamp newDateAndTime = Timestamp.valueOf(getTimeAndDateInDataBaseFormat(dateInputChange, hoursInputChange, minuteInputChange, secondsInputChange));
            if (textFieldIsEmpty(warehouseNameInputAdd)) {
                showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
            }
            if (textFieldIsEmpty(quantityInputAdd)) {
                showProcessResultMessage(ERROR_INPUT_QUANTITY_EMPTY);
            } else if (textFieldIsEmpty(quantityInputChange)) {
                if (SalesManager.update(Integer.parseInt(quantityInputAdd.getText()), dateAndTime, warehouseNameInputAdd.getText(), Integer.parseInt(quantityInputAdd.getText()), newDateAndTime)) {
                    showProcessResultMessage(SUCCESSFULLY_CHANGED);
                } else {
                    showProcessResultMessage(ERROR_NO_DATA);
                }
            } else {
                if (SalesManager.update(Integer.parseInt(quantityInputAdd.getText()), dateAndTime, warehouseNameInputAdd.getText(), Integer.parseInt(quantityInputChange.getText()), newDateAndTime)) {
                    showProcessResultMessage(SUCCESSFULLY_CHANGED);
                } else {
                    showProcessResultMessage(ERROR_NO_DATA);
                }
                showAllTaleData();
            }
            cleanTextField(warehouseNameInputAdd);
            cleanTextField(quantityInputChange);
            cleanTextField(quantityInputAdd);
            cleanTextField(hoursInput);
            cleanTextField(minuteInput);
            cleanTextField(secondsInput);
            cleanTextField(hoursInputChange);
            cleanTextField(minuteInputChange);
            cleanTextField(secondsInputChange);
            cleanDatePicker(dateInputAdd);
            cleanDatePicker(dateInputChange);
        });

    }


}

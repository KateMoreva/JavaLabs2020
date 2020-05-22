package curs.BD.cursBd.ui;

import curs.BD.cursBd.model.Charges;
import curs.BD.cursBd.managers.ChargesManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class ChargesController extends WindowController {

    @FXML
    private AnchorPane mainBg;

    @FXML
    private TextField nameInput;

    @FXML
    private Button showAllInfoButton;

    @FXML
    private TableView<Charges> table;

    @FXML
    private TableColumn<String, String> expevseColumn;

    @FXML
    private TableColumn<String, String> dateColumn;

    @FXML
    private TableColumn<String, Integer> amountColumn;

    @FXML
    private Button showAllButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextArea infoText;

    @FXML
    private TextField amountInput;

    @FXML
    private TextField expenseInputShowAllInfo;

    @FXML
    private DatePicker dateInput;

    @FXML
    private TextField hoursInput;

    @FXML
    private TextField minuteInput;

    @FXML
    private TextField secondsInput;

    @FXML
    private DatePicker dateInputShowAllInfo;

    @FXML
    private Button changeButton;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private Button filterByDateButton;

    @FXML
    private TextField amountInputChange;

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
        setTodayDate(dateInput);
        setTodayDate(dateFrom);
        setTodayDate(dateInputChange);
        setTodayDate(dateTo);
        setInputNumericFieldStyle(amountInput);
        setInputNumericFieldStyle(amountInputChange);
        infoText.setText("Charges");
        setInputTimeFieldsStyle(hoursInput, minuteInput, secondsInput);
        setInputTimeFieldsStyle(hoursInputChange, minuteInputChange, secondsInputChange);
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("chargeDateString"));
        expevseColumn.setCellValueFactory(new PropertyValueFactory<>("expenseItemsName"));
        initShawAllButton();
        initAddButton();
        initDeleteButton();
        initFilterButton();
        initShowInfoButton();
        initChangeButton();
    }

    private void initShawAllButton() {
        showAllButton.setOnAction(actionEvent -> {
            showTableData();
        });
    }

    private void initAddButton() {
        addButton.setOnAction(actionEvent -> {
                    if (textFieldIsEmpty(nameInput)) {
                        showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
                    } else if (textFieldIsEmpty(amountInput)) {
                        showProcessResultMessage(ERROR_INPUT_AMOUNT_EMPTY);
                    } else {
                        Timestamp dateAndTime = Timestamp.valueOf(getTimeAndDateInDataBaseFormat(dateInput, hoursInput, minuteInput, secondsInput));
                        if (ChargesManager.add(Integer.parseInt(amountInput.getText()), dateAndTime, nameInput.getText())) {
                            showProcessResultMessage(SUCCESSFULLY_ADDED);
                            showTableData();
                            cleanTextField(amountInput);
                            cleanTextField(nameInput);
                            cleanTextField(hoursInput);
                            cleanTextField(minuteInput);
                            cleanTextField(secondsInput);
                            cleanDatePicker(dateInput);
                        } else {
                            showProcessResultMessage(ERROR_INPUT_WRONG_NAME);
                        }
                    }
                }
        );

    }

    private void initChangeButton() {
        changeButton.setOnAction(actionEvent -> {
            cleanDatePicker(dateFrom);
            cleanDatePicker(dateTo);
            Timestamp dateAndTime = Timestamp.valueOf(getTimeAndDateInDataBaseFormat(dateInput, hoursInput, minuteInput, secondsInput));
            Timestamp newDateAndTime = Timestamp.valueOf(getTimeAndDateInDataBaseFormat(dateInputChange, hoursInputChange, minuteInputChange, secondsInputChange));
            if (textFieldIsEmpty(nameInput)) {
                showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
            } else if (textFieldIsEmpty(amountInput)) {
                if (ChargesManager.updateOnlyDate(dateAndTime, nameInput.getText(), newDateAndTime)) {
                    showProcessResultMessage(SUCCESSFULLY_CHANGED);
                } else {
                    showProcessResultMessage(ERROR_NO_DATA);
                }
            } else {
                if (ChargesManager.update(Integer.parseInt(amountInput.getText()), dateAndTime, nameInput.getText(), Integer.parseInt(amountInputChange.getText()), newDateAndTime)) {
                    showProcessResultMessage(SUCCESSFULLY_CHANGED);
                } else {
                    showProcessResultMessage(ERROR_NO_DATA);
                }
            }
            showTableData();
            cleanTextField(amountInput);
            cleanTextField(amountInputChange);
            cleanTextField(nameInput);
            cleanTextField(hoursInput);
            cleanTextField(minuteInput);
            cleanTextField(secondsInput);
            cleanTextField(hoursInputChange);
            cleanTextField(minuteInputChange);
            cleanTextField(secondsInputChange);
            cleanDatePicker(dateInput);
            cleanDatePicker(dateInputChange);
        });
    }

    private void initDeleteButton() {
        deleteButton.setOnAction(actionEvent -> {
                    if (textFieldIsEmpty(nameInput)) {
                        showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
                    } else if (textFieldIsEmpty(amountInput)) {
                        showProcessResultMessage(ERROR_INPUT_AMOUNT_EMPTY);
                    } else {
                        Timestamp dateAndTime = Timestamp.valueOf(getTimeAndDateInDataBaseFormat(dateInput, hoursInput, minuteInput, secondsInput));
                        List<Charges> charges = ChargesManager.find(Integer.parseInt(amountInput.getText()), dateAndTime, nameInput.getText());
                        if (charges.isEmpty()) {
                            showProcessResultMessage(ERROR_NO_DATA);
                        }
                        if (ChargesManager.delete(Integer.parseInt(amountInput.getText()), dateAndTime, nameInput.getText())) {
                            showProcessResultMessage(SUCCESSFULLY_DELETED);
                        }
                    }
                    showTableData();
                    cleanTextField(amountInput);
                    cleanTextField(nameInput);
                    cleanTextField(hoursInput);
                    cleanTextField(minuteInput);
                    cleanTextField(secondsInput);
                    cleanDatePicker(dateInput);
                }

        );

    }

    private void initShowInfoButton() {
        showAllInfoButton.setOnAction(actionEvent -> {
            cleanDatePicker(dateFrom);
            cleanDatePicker(dateTo);
            if (textFieldIsEmpty(expenseInputShowAllInfo) && !datePickerValueIsEmpty(dateInputShowAllInfo)) {
                Timestamp timeFrom = Timestamp.valueOf(getDateAndChangeToDataBaseFormat(dateInputShowAllInfo) + " 00:00:00.000");
                Timestamp timeTo = Timestamp.valueOf(getDateAndChangeToDataBaseFormat(dateInputShowAllInfo) + " 23:59:59.000");
                showResults(ChargesManager.filterByDate(timeFrom, timeTo));
                cleanDatePicker(dateInputShowAllInfo);


            } else if (!textFieldIsEmpty(expenseInputShowAllInfo) && datePickerValueIsEmpty(dateInputShowAllInfo)) {
                showResults(ChargesManager.findByExpenseItemName(expenseInputShowAllInfo.getText()));
                cleanTextField(expenseInputShowAllInfo);
                cleanDatePicker(dateInputShowAllInfo);
            } else if (!textFieldIsEmpty(expenseInputShowAllInfo) && !datePickerValueIsEmpty(dateInputShowAllInfo)) {
                Timestamp timeFrom = Timestamp.valueOf(getDateAndChangeToDataBaseFormat(dateInputShowAllInfo) + " 00:00:00.000");
                Timestamp timeTo = Timestamp.valueOf(getDateAndChangeToDataBaseFormat(dateInputShowAllInfo) + " 23:59:59.000");
                showResults(ChargesManager.findByDateAndName(expenseInputShowAllInfo.getText(), timeFrom, timeTo));
                cleanTextField(expenseInputShowAllInfo);
                cleanDatePicker(dateInputShowAllInfo);
            } else {
                showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
            }
        });
    }

    private void initFilterButton() {
        filterByDateButton.setOnAction(actionEvent -> {
            Timestamp timeFrom = Timestamp.valueOf(getDateAndChangeToDataBaseFormat(dateFrom) + " 00:00:00.000");
            Timestamp timeTo = Timestamp.valueOf(getDateAndChangeToDataBaseFormat(dateTo) + " 23:59:59.000");
            showResults(ChargesManager.filterByDate(timeFrom, timeTo));
        });
    }

    private void showTableData() {
        final List<Charges> expenseItemsAll = ChargesManager.showAll();
        showResults(expenseItemsAll);
        cleanDatePicker(dateFrom);
        cleanDatePicker(dateTo);
    }

    private void showResults(final List<Charges> chargesList) {
        table.getItems().clear();
        table.getItems().addAll(chargesList);
    }

}

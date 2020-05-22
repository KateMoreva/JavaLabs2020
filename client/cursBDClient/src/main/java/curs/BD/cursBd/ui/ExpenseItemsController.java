package curs.BD.cursBd.ui;

import curs.BD.cursBd.model.ExpenseItems;
import curs.BD.cursBd.managers.ExpenseItemsManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpenseItemsController extends WindowController {

    @FXML
    private AnchorPane mainBg;

    @FXML
    private Button showAllButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button changeNameButton;

    @FXML
    private Button filterByIDButton;

    @FXML
    private TextField nameInputAdd;

    @FXML
    private TextField nameInputDelete;

    @FXML
    private TextField idInputChange;

    @FXML
    private TextField nameInputChange;

    @FXML
    private TextField idFromFilterInput;

    @FXML
    private TextField idToFilterInput;

    @FXML
    private Button showNameButton;

    @FXML
    private TextField idInputShowName;

    @FXML
    private TableView<ExpenseItems> table;

    @FXML
    private TableColumn<String, Integer> idColumn;

    @FXML
    private TableColumn<String, String> nameColumn;

    @FXML
    private TextArea infoText;

    @FXML
    void initialize() {
        setInputNumericFieldStyle(idFromFilterInput);
        setInputNumericFieldStyle(idToFilterInput);
        setInputNumericFieldStyle(idInputChange);
        setInputNumericFieldStyle(idInputShowName);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        infoText.setText("Expense items");

        initShawAllButton();
        initAddButton();
        initDeleteButton();
        initChangeNameButton();
        initShowNameButton();
        initFilterByPriceButton();


    }

    private void clearFields() {
        searchFieldsSetUp(idFromFilterInput, idToFilterInput);
    }

    private void initShawAllButton() {
        showAllButton.setOnAction(actionEvent -> {
            showAllTableData();
        });
    }

    private void initAddButton() {
        addButton.setOnAction(actionEvent -> {
            if (nameInputAdd.getText().isEmpty()) {
                showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
            } else if (ExpenseItemsManager.add(nameInputAdd.getText())) {
                showProcessResultMessage(SUCCESSFULLY_ADDED);
            } else {
                showProcessResultMessage(PROCESS_WENT_WRONG_ERROR);
            }
            cleanTextField(nameInputAdd);
            showAllTableData();
        });
    }

    private void initDeleteButton() {
        deleteButton.setOnAction(actionEvent -> {
            if (nameInputDelete.getText().isEmpty()) {
                showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
            } else if (ExpenseItemsManager.delete(nameInputDelete.getText())) {
                showProcessResultMessage(SUCCESSFULLY_DELETED);
                showAllTableData();
                cleanTextField(nameInputDelete);
            } else {
                showProcessResultMessage(ERROR_NO_DATA);
                cleanTextField(nameInputDelete);
                showAllTableData();
            }
        });
    }

    private void initShowNameButton() {
        showNameButton.setOnAction(actionEvent -> {
            if (idInputShowName.getText().isEmpty()) {
                showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
            } else {
                final List<ExpenseItems> neededProduct = ExpenseItemsManager.show(Integer.parseInt(idInputShowName.getText()));
                if (neededProduct == null) {
                    showProcessResultMessage(ERROR_NO_DATA);
                } else {
                    showResults(neededProduct);
                    cleanTextField(idInputShowName);
                }
            }
        });
    }

    private void initChangeNameButton() {
        changeNameButton.setOnAction(actionEvent -> {
            if (nameInputChange.getText().isEmpty()) {
                showProcessResultMessage(ERROR_INPUT_NAME_EMPTY);
            } else if (ExpenseItemsManager.update(Integer.parseInt(idInputChange.getText()), nameInputChange.getText())) {
                showProcessResultMessage(SUCCESSFULLY_CHANGED);
                cleanTextField(idInputChange);
                cleanTextField(nameInputChange);
                showAllTableData();
            } else {
                showProcessResultMessage(ERROR_NO_DATA);
                cleanTextField(idInputChange);
                cleanTextField(nameInputChange);
                showAllTableData();
            }


        });

    }


    private void showAllTableData() {
        final List<ExpenseItems> expenseItemsAll = ExpenseItemsManager.showAll();
        showResults(expenseItemsAll);
        clearFields();
    }

    private void showResults(final List<ExpenseItems> expenseItemsList) {
        table.getItems().clear();
        table.getItems().addAll(expenseItemsList);
    }


    private void initFilterByPriceButton() {
        filterByIDButton.setOnAction(actionEvent -> {
            checkNumericFilterValuesError(idFromFilterInput, idToFilterInput);
            final List<ExpenseItems> neededProduct = ExpenseItemsManager.filter(Integer.parseInt(idFromFilterInput.getText()), Integer.parseInt(idToFilterInput.getText()));
            if (neededProduct.isEmpty()) {
                showProcessResultMessage(ERROR_NO_DATA);
                clearFields();
            } else {
                showResults(neededProduct);
            }
        });
    }

}

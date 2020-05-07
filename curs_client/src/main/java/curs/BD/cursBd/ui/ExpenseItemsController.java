package curs.BD.cursBd.ui;

import curs.BD.cursBd.model.ExpenseItems;
import curs.BD.cursBd.model.ExpenseItemsManager;
import curs.BD.cursBd.model.Product;
import curs.BD.cursBd.model.ProductManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class ExpenseItemsController {
    private static final String MESSAGE_TITLE = "Database message";
    private static String ERROR_INPUT_NAME_EMPTY = "Add name";
    private static final String SUCCESSFULLY_ADDED = "Успешно добавлено";
    private static final String SUCCESSFULLY_CHANGED = "Успешно изменено";
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
        setInputStyle(idFromFilterInput);
        setInputStyle(idToFilterInput);
        setInputStyle(idInputChange);
        setInputStyle(idInputShowName);
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

    private void setInputStyle(TextField field) {
        Pattern p = Pattern.compile("[0-9]*$");
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) {
                field.setText(oldValue);
            }
        });
    }

    private void initShawAllButton() {
        showAllButton.setOnAction(actionEvent -> {
            showTableData();
        });
    }
    private void initAddButton() {
        addButton.setOnAction(actionEvent -> {
            if (nameInputAdd.getText().isEmpty()) {
                showMessage(ERROR_INPUT_NAME_EMPTY);
            } else {
                ExpenseItemsManager.add(nameInputAdd.getText());
                showMessage(SUCCESSFULLY_ADDED);
                cleanTextField(nameInputAdd);
//                cleanTextField(amountInputAdd);
//                cleanTextField(quantityInputAdd);
                showTableData();
            }
        });
    }

    private void initDeleteButton() {
        deleteButton.setOnAction(actionEvent -> {
            if (nameInputDelete.getText().isEmpty()) {
                showMessage(ERROR_INPUT_NAME_EMPTY);
            } else {
                ExpenseItemsManager.delete(nameInputDelete.getText());
                showTableData();
            }
        });
    }
    private void initShowNameButton(){
        showNameButton.setOnAction(actionEvent -> {
            if (idInputShowName.getText().isEmpty()) {
                showMessage(ERROR_INPUT_NAME_EMPTY);
            }
//            else {
            final List<ExpenseItems> neededProduct = ExpenseItemsManager.show(Integer.parseInt(idInputShowName.getText()));
//                if (neededProduct == null) {
//                    showMessage(ERROR_NO_DATA);
//                } else {
            showResults(neededProduct);
////                    String tmpNeededProductName = nameInputShowPrice.getText();
////                    nameInputShowPrice.setPromptText(tmpNeededProductName);
//                    nameInputShowQuantity.clear();
//                }
//            }
        });
    }
    private void initChangeNameButton(){
        changeNameButton.setOnAction(actionEvent -> {
            if (nameInputChange.getText().isEmpty()) {
                showMessage(ERROR_INPUT_NAME_EMPTY);
            } else {
                ExpenseItemsManager.update(Integer.parseInt(idInputChange.getText()), nameInputChange.getText());
                showMessage(SUCCESSFULLY_CHANGED);
                cleanTextField(idInputChange);
                cleanTextField(nameInputChange);
                showTableData();
            }
//            } else {
//                showMessage(ERROR_NO_DATA);
//                cleanTextField(quantityInputChangeQuantity);
//                cleanTextField(nameInputChangeQuantity);
//                showTableData();
//            }
        });

    }


    private void showTableData() {
        final List<ExpenseItems> expenseItemsAll = ExpenseItemsManager.showAll();
        showResults(expenseItemsAll);
    }

    private void showResults(final List<ExpenseItems> expenseItemsList) {
        table.getItems().clear();
        table.getItems().addAll(expenseItemsList);
    }

    private void cleanTextField(TextField textField) {
        textField.clear();
//        searchFieldsSetUp();
    }

    //    private void searchFieldsSetUp(){
//        if (!idFromFilterInput.getPromptText().equals(INIT_PRICE_TO)) {
//            amountToFilterInput.setPromptText(INIT_PRICE_TO);
//            amountToFilterInput.clear();
//        }
//        if (!amountFromFilterInput.getPromptText().equals(INIT_PRICE_FROM)) {
//            amountFromFilterInput.setPromptText(INIT_PRICE_FROM);
//            amountFromFilterInput.clear();
//        }
////        if (!nameInputShowQuantity.getPromptText().equals(PROMPT_TEXT_NAME)) {
////            nameInputShowQuantity.setPromptText(PROMPT_TEXT_NAME);
////        }
//        nameInputShowInfo.clear();
//    }
    //    private void filterByPriceNoInputError(){
//        if (!priceFromFilterInput.getText().isEmpty() & priceToFilterInput.getText().isEmpty()) {
//            priceToFilterInput.setText(INIT_PRICE_TO);
//        } else if (priceFromFilterInput.getText().isEmpty() & !priceToFilterInput.getText().isEmpty()) {
//            priceFromFilterInput.setText(INIT_PRICE_FROM);
//        } else if (priceToFilterInput.getText().isEmpty() & priceFromFilterInput.getText().isEmpty()) {
//            priceFromFilterInput.setText(INIT_PRICE_FROM);
//            priceToFilterInput.setText(INIT_PRICE_TO);
//        }
//    }
//
    private void initFilterByPriceButton(){
        filterByIDButton.setOnAction(actionEvent -> {
//            filterByPriceNoInputError();
//            if (Integer.parseInt(priceFromFilterInput.getText()) > Integer.parseInt(priceToFilterInput.getText())) {
//                showMessage(PRICE_TO_ERROR);
//                int tmp = Integer.parseInt(priceFromFilterInput.getText());
//                int counter = 0;
//                while (tmp / 10 != 0) {
//                    counter++;
//                    tmp = tmp / 10;
//                }
//                int elem = Integer.parseInt(priceToFilterInput.getText());
//                counter = (int) Math.pow(10, counter + 1);
//                priceToFilterInput.setText(String.valueOf(elem * counter));
//            }
            final List<ExpenseItems> neededProduct = ExpenseItemsManager.filter(Integer.parseInt(idFromFilterInput.getText()), Integer.parseInt(idToFilterInput.getText()));
//            if (neededProduct.isEmpty()) {
//                showMessage(ERROR_NO_DATA_IN_RANGE);
//            } else {
            showResults(neededProduct);
//                String tmpPriceFrom = priceFromFilterInput.getText();
//                String tmpPriceTo = priceToFilterInput.getText();
//                priceFromFilterInput.setPromptText(tmpPriceFrom);
//                priceToFilterInput.setPromptText(tmpPriceTo);
//                priceToFilterInput.clear();
//                priceFromFilterInput.clear();
//            }
        });

    }
    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setGraphic(null);
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

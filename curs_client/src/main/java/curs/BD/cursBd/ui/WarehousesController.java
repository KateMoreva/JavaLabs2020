package curs.BD.cursBd.ui;

import curs.BD.cursBd.DbHelper;
import curs.BD.cursBd.model.Product;
import curs.BD.cursBd.model.ProductManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class WarehousesController {
    private static final String PROMPT_TEXT_NAME = "name";
    private static final String MESSAGE_TITLE = "Database message";
    private static final String SUCCESSFULLY_ADDED = "Успешно добавлено";
    private static final String SUCCESSFULLY_CHANGED = "Успешно изменено";
    private static String ERROR_INPUT_NAME_EMPTY = "Add name";
    private static String ERROR_INPUT_PRICE_EMPTY = "Add price";
    private static String INIT_PRICE_FROM = "10";
    private static String INIT_PRICE_TO = "1000";
    private static final String ERROR_NO_DATA = "Данные по этому продукту отсутствуют в таблице";


    private static final String url ="http:///localhost:5432/testdb/warehouse";


    @FXML
    private AnchorPane mainBg;

    @FXML
    private Button showAllButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button changeQuantityButton;

    @FXML
    private Button filterByQuantityButton;

    @FXML
    private TextField nameInputAdd;

    @FXML
    private TextField quantityInputAdd;

    @FXML
    private TextField nameInputDelete;

    @FXML
    private TextField nameInputChangeQuantity;

    @FXML
    private TextField quantityInputChangeQuantity;

    @FXML
    private TextField quantityFromFilterInput;

    @FXML
    private TextField quantityToFilterInput;

    @FXML
    private Button showInfoButton;

    @FXML
    private TextField nameInputShowInfo;

    @FXML
    private TextArea infoText;

    @FXML
    private Button changeAmountButton;

    @FXML
    private TextField amountInputChangeAmount;

    @FXML
    private TableView<Product> table;

    @FXML
    private TableColumn<String, String> productNameColumn;

    @FXML
    private TableColumn<String, Integer> productQuantityColumn;

    @FXML
    private TableColumn<String, Integer> productAmountColumn;

    @FXML
    private Button filterByAmountButton;

    @FXML
    private TextField amountFromFilterInput;

    @FXML
    private TextField amountToFilterInput;

    @FXML
    private TextField amountInputAdd;

    private void setInputStyle(TextField field){
        Pattern p = Pattern.compile("[0-9]*$");
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) {
                field.setText(oldValue);
            }
        });
    }
        @FXML
        void initialize(){
            setInputStyle(amountFromFilterInput);
            setInputStyle(amountToFilterInput);
            setInputStyle(quantityFromFilterInput);
            setInputStyle(quantityToFilterInput);

//                productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                productAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
                productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

                infoText.setText("Warehouses");
            initShawAllButton();
            initAddButton();
            initDeleteButton();
            initShowPriceButton();
            initChangePriceButton();

        }
    private void initShawAllButton(){
        showAllButton.setOnAction(actionEvent -> {
            showTableData();
        });
    }

    private void showTableData(){
        final List<Product> productsAll = ProductManager.showAll();
        showResults(productsAll);
    }

    private void initAddButton(){
        addButton.setOnAction(actionEvent -> {
            if (nameInputAdd.getText().isEmpty() | amountInputAdd.getText().isEmpty() | quantityInputAdd.getText().isEmpty()) {
                showMessage(ERROR_INPUT_PRICE_EMPTY);
            } else {
                ProductManager.add(nameInputAdd.getText(),  Integer.parseInt(quantityInputAdd.getText()), Integer.parseInt(amountInputAdd.getText()));
                showMessage(SUCCESSFULLY_ADDED);
                cleanTextField(nameInputAdd);
                cleanTextField(amountInputAdd);
                cleanTextField(quantityInputAdd);
                showTableData();
            }
        });
    }

    private void initDeleteButton() {
        deleteButton.setOnAction(actionEvent -> {
            if(nameInputDelete.getText().isEmpty()){
                showMessage(ERROR_INPUT_NAME_EMPTY);
            } else
            {
                ProductManager.delete(nameInputDelete.getText());
                showTableData();
            }
        });
    }
    private void cleanTextField(TextField textField){
        textField.clear();
        searchFieldsSetUp();
    }
    //
    private void searchFieldsSetUp(){
        if (!amountToFilterInput.getPromptText().equals(INIT_PRICE_TO)) {
            amountToFilterInput.setPromptText(INIT_PRICE_TO);
           amountToFilterInput.clear();
        }
        if (!amountFromFilterInput.getPromptText().equals(INIT_PRICE_FROM)) {
            amountFromFilterInput.setPromptText(INIT_PRICE_FROM);
            amountFromFilterInput.clear();
        }
//        if (!nameInputShowQuantity.getPromptText().equals(PROMPT_TEXT_NAME)) {
//            nameInputShowQuantity.setPromptText(PROMPT_TEXT_NAME);
//        }
       nameInputShowInfo.clear();
    }
    private void showResults(final List<Product> productsList){
        table.getItems().clear();
        table.getItems().addAll(productsList);
    }

    private void showMessage(String message){
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
        private void initChangePriceButton(){
       changeQuantityButton.setOnAction(actionEvent -> {
            if (nameInputChangeQuantity.getText().isEmpty()) {
                showMessage(ERROR_INPUT_NAME_EMPTY);
            } else {
                ProductManager.update(Integer.parseInt(nameInputChangeQuantity.getText()), Integer.parseInt(quantityInputChangeQuantity.getText()));


                showMessage(SUCCESSFULLY_CHANGED);
                cleanTextField(quantityInputChangeQuantity);
                cleanTextField(nameInputChangeQuantity);
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
    private void initShowPriceButton(){
        showInfoButton.setOnAction(actionEvent -> {
            if (nameInputShowInfo.getText().isEmpty()) {
                showMessage(ERROR_INPUT_NAME_EMPTY);
            }
//            else {
                final List<Product> neededProduct = ProductManager.showByName(nameInputShowInfo.getText());
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
    private void initFilterButton() {
        filterByAmountButton.setOnAction(actionEvent -> {
            int amountFrom;
            int amountTo;
            if ((amountFromFilterInput.getText().isEmpty())||(amountToFilterInput.getText().isEmpty())) {
                amountFrom = Integer.parseInt(amountToFilterInput.getPromptText());
                amountTo = Integer.parseInt(amountToFilterInput.getPromptText());
            }

        });
    }
}


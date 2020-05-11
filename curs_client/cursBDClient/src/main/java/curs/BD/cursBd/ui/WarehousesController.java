package curs.BD.cursBd.ui;

import curs.BD.cursBd.model.Warehouses;
import curs.BD.cursBd.model.WarehousesManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WarehousesController extends WindowController{

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
    private TextField nameInputChangeAmount;

    @FXML
    private TextField amountInputChangeAmount;

    @FXML
    private TableView<Warehouses> table;

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

        @FXML
        void initialize(){
           searchFieldsSetUp(amountFromFilterInput, amountToFilterInput);
           searchFieldsSetUp(quantityFromFilterInput, quantityToFilterInput);
            infoText.setText("Warehouses");
                productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                productAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
                productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            initShawAllButton();
            initAddButton();
            initDeleteButton();
            initShowInfoButton();
            initChangeQuantityButton();
            initChangeAmountButton();
            initAmountFilterButton();
            initQuantityFilterButton();

        }
    private void initShawAllButton(){
        showAllButton.setOnAction(actionEvent -> {
            showAllTableData();
        });
    }

    private void showAllTableData(){
        final List<Warehouses> productsAll = WarehousesManager.showAll();
        showResults(productsAll);
        cleanTextField(amountFromFilterInput);
        cleanTextField(amountToFilterInput);
        cleanTextField(quantityToFilterInput);
        cleanTextField(quantityFromFilterInput);
    }

    private void initAddButton(){
        addButton.setOnAction(actionEvent -> {
            if (textFieldIsEmpty(nameInputAdd)) {
                showMessage(ERROR_INPUT_NAME_EMPTY);
            } else if (textFieldIsEmpty(amountInputAdd)) {
                showMessage(ERROR_INPUT_AMOUNT_EMPTY);
            }else if (textFieldIsEmpty(quantityInputAdd)) {
                showMessage(ERROR_INPUT_QUANTITY_EMPTY);
            } else {
                WarehousesManager.add(nameInputAdd.getText(),  Integer.parseInt(quantityInputAdd.getText()), Integer.parseInt(amountInputAdd.getText()));
                showMessage(SUCCESSFULLY_ADDED);
                cleanTextField(nameInputAdd);
                cleanTextField(amountInputAdd);
                cleanTextField(quantityInputAdd);
                showAllTableData();
            }
        });
    }

    private void initDeleteButton() {
        deleteButton.setOnAction(actionEvent -> {
            if(nameInputDelete.getText().isEmpty()){
                showMessage(ERROR_INPUT_NAME_EMPTY);
            } else
            {
                if (WarehousesManager.delete(nameInputDelete.getText())) {
                    showMessage(SUCCESSFULLY_DELETED);

                }
                else {
                    showMessage(ERROR_NO_DATA);
                }
                cleanTextField(nameInputDelete);
                showAllTableData();
            }
        });
    }
    private void initAmountFilterButton() {
            filterByAmountButton.setOnAction(actionEvent -> {
                checkNumericFilterNoInputError(amountFromFilterInput,amountToFilterInput);
                List<Warehouses> warehouses = WarehousesManager.filterByAmount(Integer.parseInt(amountFromFilterInput.getText()),Integer.parseInt(amountToFilterInput.getText()));
                showResults(warehouses);
            });
    }
    private void initQuantityFilterButton() {
        filterByQuantityButton.setOnAction(actionEvent -> {
            checkNumericFilterNoInputError(quantityFromFilterInput,quantityToFilterInput);
            List<Warehouses> warehouses = WarehousesManager.filterByQuantity(Integer.parseInt(quantityFromFilterInput.getText()),Integer.parseInt(quantityToFilterInput.getText()));
            showResults(warehouses);
        });
    }


    private void showResults(final List<Warehouses> productsList){
        table.getItems().clear();
        table.getItems().addAll(productsList);
    }

        private void initChangeQuantityButton(){
       changeQuantityButton.setOnAction(actionEvent -> {
            if (nameInputChangeQuantity.getText().isEmpty()) {
                showMessage(ERROR_INPUT_NAME_EMPTY);
            } else if  (quantityInputChangeQuantity.getText().isEmpty()) {
                showMessage(ERROR_INPUT_QUANTITY_EMPTY);
            }
            else if (WarehousesManager.updateQuantity(nameInputChangeQuantity.getText(), Integer.parseInt(quantityInputChangeQuantity.getText()))){
                showMessage(SUCCESSFULLY_CHANGED);
                cleanTextField(quantityInputChangeQuantity);
                cleanTextField(nameInputChangeQuantity);
                showAllTableData();

            } else {
                showMessage(ERROR_NO_DATA);
                cleanTextField(quantityInputChangeQuantity);
                cleanTextField(nameInputChangeQuantity);
                showAllTableData();
            }
        });

    }
    private void initChangeAmountButton(){
        changeAmountButton.setOnAction(actionEvent -> {
            if (nameInputChangeAmount.getText().isEmpty()) {
                showMessage(ERROR_INPUT_NAME_EMPTY);
            } else if  (amountInputChangeAmount.getText().isEmpty()) {
                showMessage(ERROR_INPUT_AMOUNT_EMPTY);
            }
            else if (WarehousesManager.updateAmount(nameInputChangeAmount.getText(), Integer.parseInt(amountInputChangeAmount.getText()))){
                showMessage(SUCCESSFULLY_CHANGED);
                cleanTextField(quantityInputChangeQuantity);
                cleanTextField(nameInputChangeQuantity);
                showAllTableData();
            } else {
                showMessage(ERROR_NO_DATA);
                cleanTextField(quantityInputChangeQuantity);
                cleanTextField(nameInputChangeQuantity);
                showAllTableData();
            }
        });
    }

    private void initShowInfoButton(){
        showInfoButton.setOnAction(actionEvent -> {
            if (nameInputShowInfo.getText().isEmpty()) {
                showMessage(ERROR_INPUT_NAME_EMPTY);
            }
            else {
                final List<Warehouses> neededWarehouses = WarehousesManager.showByName(nameInputShowInfo.getText());
               if (neededWarehouses.isEmpty()) {
                    showMessage(ERROR_NO_DATA);
                } else {
                    showResults(neededWarehouses);
                   cleanTextField(nameInputShowInfo);
                }
            }
        });
    }
}


package curs.BD.cursBd.ui;

import curs.BD.cursBd.CientMessage;
import curs.BD.cursBd.Cliennt;
import curs.BD.cursBd.DbHelper;
import curs.BD.cursBd.GreetingClient;
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
public class MainWindowController {
//    private final Resource mess;
//    private final ApplicationContext applicationContext;

    private static final String PROMPT_TEXT_NAME = "name";
    private static final String MESSAGE_TITLE = "Database message";
    private static final String SUCCESSFULLY_ADDED = "Успешно добавлено";
    private static final String SUCCESSFULLY_DELETED = "Успешно удалено";
    private static final String EMPTY_TABLE = "Таблица пуста";
    private static final String ERROR_NO_DATA = "Данные по этому продукту отсутствуют в таблице";
    private static final String ERROR_NO_DATA_IN_RANGE = "Данные по продукттам в этом ценовом диапозоне отсутствуют в таблице";
    private static final String SUCCESSFULLY_CHANGED = "Успешно изменено";
    private static final String PRICE_TO_ERROR = "Введено неверное значение фильтра." +
            " Для Вашего удобства значаение было исправлено." +
            " Вы можете ввести другое значение, если Вас оно не устроило";

    private static String ERROR_INPUT_NAME_EMPTY = "Add name";
    private static String ERROR_INPUT_PRICE_EMPTY = "Add price";
    private static String INIT_PRICE_FROM = "10";
    private static String INIT_PRICE_TO = "1000";

    @FXML
    private AnchorPane mainBg;

    @FXML
    private AnchorPane outputBG;

    @FXML
    private TableView<Product> table;

    @FXML
    private TableColumn<String, String> productNameColumn;

    @FXML
    private TableColumn<String,Integer> productPriceColumn;

    @FXML
    private TextField tableNameText;

    @FXML
    private Button showAllButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button changePriceButton;

    @FXML
    private Button filterByPriceButton;

    @FXML
    private TextField nameInputAdd;

    @FXML
    private TextField priceInputAdd;

    @FXML
    private TextField nameInputDelete;

    @FXML
    private TextField nameInputChange;

    @FXML
    private TextField priceInputChange;

    @FXML
    private TextField priceFromFilterInput;

    @FXML
    private TextField priceToFilterInput;

    @FXML
    private TextField nameInputShowPrice;

    @FXML
    private Button showPriceButton;

    @FXML
    private Button helpButton;
    @FXML
    private TextArea infoText;

//    public cursBDClient.Bd.client.MainWindowController(@Value("classpath:/ui/ui.fxml") Resource mess, ApplicationContext applicationContext) {
//        this.mess = mess;
//        this.applicationContext = applicationContext;
//    }


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

        setInputStyle(priceInputAdd);
        setInputStyle(priceInputChange);
        setInputStyle(priceFromFilterInput);
        setInputStyle(priceToFilterInput);

        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        infoText.setText("Вы работаете с базой данных магазина. Получите справку используя кнопку help.");
        tableNameText.setText("Products");

        initShawAllButton();
        initAddButton();
//        initDeleteButton();
//        initChangePriceButton();
//        initShowPriceButton();
//        initFilterByPriceButton();
//        initShowHelpButton();

    }

    private void initShawAllButton(){
        showAllButton.setOnAction(actionEvent -> {
            showTableData();
        });
    }

    private void showTableData(){
        final List<Product> productsAll = DbHelper.selectAllProducts();
            showResults(productsAll);
    }

    private void initAddButton(){
        addButton.setOnAction(actionEvent -> {
            if (!nameInputAdd.getText().isEmpty() & priceInputAdd.getText().isEmpty()) {
                showMessage(ERROR_INPUT_PRICE_EMPTY);
            } else if (nameInputAdd.getText().isEmpty() | priceInputAdd.getText().isEmpty()) {
                showMessage(ERROR_INPUT_NAME_EMPTY);

            }
//            ProductManager.add(nameInputAdd.getText(), Integer.parseInt(priceInputAdd.getText()));
//                showMessage(SUCCESSFULLY_ADDED);
//                cleanTextField(nameInputAdd);
//                cleanTextField(priceInputAdd);
//                showTableData();
        });

    }
//
//    private void initDeleteButton(){
//        deleteButton.setOnAction(actionEvent -> {
//            if (nameInputDelete.getText().isEmpty()) {
//                showMessage(ERROR_INPUT_NAME_EMPTY);
//            } else if (cursBDClient.Bd.client.DbHelper.deleteProduct(nameInputDelete.getText())) {
//                showMessage(SUCCESSFULLY_DELETED);
//                cleanTextField(nameInputDelete);
//                showTableData();
//            } else {
//                showMessage(ERROR_NO_DATA);
//            }
//        });
//
//    }
//
//    private void initShowPriceButton(){
//        showPriceButton.setOnAction(actionEvent -> {
//            if (nameInputShowPrice.getText().isEmpty()) {
//                showMessage(ERROR_INPUT_NAME_EMPTY);
//            } else {
//                final List<cursBDClient.Bd.client.CientMessage> neededProduct = cursBDClient.Bd.client.DbHelper.priceByNameProductSearch(nameInputShowPrice.getText());
//                if (neededProduct.isEmpty()) {
//                    showMessage(ERROR_NO_DATA);
//                } else {
//                    showResults(neededProduct);
//                    String tmpNeededProductName = nameInputShowPrice.getText();
//                    nameInputShowPrice.setPromptText(tmpNeededProductName);
//                    nameInputShowPrice.clear();
//                }
//            }
//        });
//    }
//
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
//    private void initFilterByPriceButton(){
//        filterByPriceButton.setOnAction(actionEvent -> {
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
//            final List<cursBDClient.Bd.client.CientMessage> neededProduct = cursBDClient.Bd.client.DbHelper.priceRangeProductSearch(
//                    Integer.parseInt(priceFromFilterInput.getText()),
//                    Integer.parseInt(priceToFilterInput.getText()));
//            if (neededProduct.isEmpty()) {
//                showMessage(ERROR_NO_DATA_IN_RANGE);
//            } else {
//                showResults(neededProduct);
//                String tmpPriceFrom = priceFromFilterInput.getText();
//                String tmpPriceTo = priceToFilterInput.getText();
//                priceFromFilterInput.setPromptText(tmpPriceFrom);
//                priceToFilterInput.setPromptText(tmpPriceTo);
//                priceToFilterInput.clear();
//                priceFromFilterInput.clear();
//            }
//        });
//    }
//
//    private void initChangePriceButton(){
//        changePriceButton.setOnAction(actionEvent -> {
//            if (nameInputChange.getText().isEmpty()) {
//                showMessage(ERROR_INPUT_NAME_EMPTY);
//            } else if (cursBDClient.Bd.client.DbHelper.changeProductPrice(
//                    nameInputChange.getText(),
//                    Integer.parseInt(priceInputChange.getText()))) {
//                showMessage(SUCCESSFULLY_CHANGED);
//                cleanTextField(nameInputChange);
//                cleanTextField(priceInputChange);
//                showTableData();
//            } else {
//                showMessage(ERROR_NO_DATA);
//                cleanTextField(nameInputChange);
//                cleanTextField(priceInputChange);
//                showTableData();
//            }
//        });
//
//    }

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
//
    private void cleanTextField(TextField textField){
        textField.clear();
        searchFieldsSetUp();
    }
//
    private void searchFieldsSetUp(){
        if (!priceToFilterInput.getPromptText().equals(INIT_PRICE_TO)) {
            priceToFilterInput.setPromptText(INIT_PRICE_TO);
            priceToFilterInput.clear();
        }
        if (!priceFromFilterInput.getPromptText().equals(INIT_PRICE_FROM)) {
            priceFromFilterInput.setPromptText(INIT_PRICE_FROM);
            priceFromFilterInput.clear();
        }
        if (!nameInputShowPrice.getPromptText().equals(PROMPT_TEXT_NAME)) {
            nameInputShowPrice.setPromptText(PROMPT_TEXT_NAME);
        }
        nameInputShowPrice.clear();
    }
//
//    private void initShowHelpButton(){
//        helpButton.setOnAction(actionEvent -> {
//            FXMLLoader loader = new FXMLLoader();
//            try {
//                URL url = this.mess.getURL();
//                FXMLLoader fxmlLoader = new FXMLLoader(url);
//                fxmlLoader.setControllerFactory(applicationContext::getBean);
//                Parent root = fxmlLoader.load();
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root));
//                stage.showAndWait();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//    }
}

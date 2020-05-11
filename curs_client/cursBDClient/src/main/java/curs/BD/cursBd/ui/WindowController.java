package curs.BD.cursBd.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

public class WindowController {

    private static final String ONE_DIGIT_NUMERIC_PATTERN = "[0-9]$";
    private static final String NUMERIC_PATTERN = "[0-9]*$";
    private static final String HOURS_PATTERN = "((0[0-9])|(1[0-9])|(2[0-3]))|(([0-9])?)$";
    private static final String MINUTES_AND_SECONDS_PATTERN = "([0-5][0-9])|(([0-9])?)$";
    private static final String DATE_VIEW_FORMAT = "dd.MM.yyyy";
    private static final String DATABASE_DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_SEPARATOR = ":";
    private static final String DATE_VIEW_SEPARATOR = "\\.";
    private static final String DATE_DATABASE_SEPARATOR = "-";
    private static final String TIME_ACCURACY = ".000";
    private static final String SPACE = " ";
    protected static final String PROMPT_TEXT_NAME = "name";
    protected static final String INIT_NUMBER_FILTER_FROM = "10";
    protected static final String INIT_NUMBER_FILTER_TO = "1000";
    protected static final String INIT_HOURS_VALUE = "12";
    protected static final String INIT_MINUTES_VALUE = "00";
    protected static final String INIT_SECONDS_VALUE = "00";



    protected static final String MESSAGE_TITLE = "Database message";

//    protected static final String ERROR_INPUT_FIELD_EMPTY = "Please fill  field";

    protected static final String ERROR_INPUT_NAME_EMPTY = "Please fill field name";
    protected static final String ERROR_INPUT_AMOUNT_EMPTY = "Please fill field amount";
    protected static final String ERROR_INPUT_QUANTITY_EMPTY = "Please fill field quantity";

    protected static final String SUCCESSFULLY_ADDED = "Successfully added";
    protected static final String SUCCESSFULLY_CHANGED = "Successfully changed";
    protected static final String SUCCESSFULLY_DELETED = "Successfully deleted";

    protected static final String EMPTY_TABLE = "Table is empty";
    protected static final String ERROR_NO_DATA = "No such data in the table.";
    protected static final String ERROR_INPUT_WRONG_NAME = "No element with such name in the table.";
//    private static final String ERROR_NO_DATA_IN_RANGE = "Данные по продукттам в этом ценовом диапозоне отсутствуют в таблице";
    protected static final String FILTER_VALUES_ERROR = "Wrong filter values.\n" +
            "The values have been corrected for you.\n" +
            " You can enter different values if you wish.";

    protected static void setInputNumericFieldStyle(TextField field){
        Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
       setInputTextFieldStyle(field, pattern);
    }
    private static void setInputTextFieldStyle(TextField textField, Pattern pattern) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!pattern.matcher(newValue).matches()) {
                textField.setText(oldValue);
            }
        });
    }
    protected static void setInputTimeFieldsStyle(TextField hoursField, TextField minutesField, TextField secondsField){
        Pattern hoursPattern = Pattern.compile(HOURS_PATTERN);
        Pattern minutesAndSecondsPattern = Pattern.compile(MINUTES_AND_SECONDS_PATTERN);
        setInputTextFieldStyle(hoursField, hoursPattern);
        setInputTextFieldStyle(minutesField, minutesAndSecondsPattern);
        setInputTextFieldStyle(secondsField, minutesAndSecondsPattern);
    }
    protected static void setTodayDate(DatePicker datePicker) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_VIEW_FORMAT);
        Date date = new Date();
        datePicker.setPromptText(dateFormat.format(date));
    }

    protected static boolean textFieldIsEmpty(TextField textField){
        return textField.getText().isEmpty();
    }

    protected static String getTimeAndDateInDataBaseFormat(DatePicker datePicker,TextField hoursField, TextField minutesField, TextField secondsField) {
       String date = getDateAndChangeToDataBaseFormat(datePicker);
       String time = getTimeAndChangeToDataBaseFormat(hoursField, minutesField, secondsField);
       return createTimeStampFromDataAndTime(date, time);
    }
    protected static boolean datePickerValueIsEmpty(DatePicker datePicker) {
        return datePicker.getValue() == null;
    }
    protected static String getDateAndChangeToDataBaseFormat(DatePicker datePicker){
        if (!datePickerValueIsEmpty(datePicker)) {
            return changeDateToDataBaseFormat(datePicker);
        } else {
            return changePromptDateToDataBaseFormat(datePicker.getPromptText());
        }
    }
    private static boolean timeTextIsOneDigit(String string){
        Pattern pattern = Pattern.compile(ONE_DIGIT_NUMERIC_PATTERN);
        return pattern.matcher(string).find();
    }
    private static String getTextFormField(TextField textField){
        if (textFieldIsEmpty(textField)) {
            return textField.getPromptText();
        }
        else if (timeTextIsOneDigit(textField.getText())) {
            return "0" + textField.getText();
        }
        else {return textField.getText();
        }
    }
    private static String getTimeAndChangeToDataBaseFormat(TextField hoursField, TextField minutesField, TextField secondsField) {
        String time = getTextFormField(hoursField);
        time += TIME_SEPARATOR;
        time += getTextFormField(minutesField);
        time += TIME_SEPARATOR;
        time += getTextFormField(secondsField);
        time += TIME_ACCURACY;
        return time;
    }
    private static String createTimeStampFromDataAndTime(String date, String time) {
        return date + SPACE + time;
    }
    private static String changeDateToDataBaseFormat(DatePicker datePicker) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATABASE_DATE_FORMAT);
        LocalDate date = datePicker.getValue();
        return  dateTimeFormatter.format(date);
    }
    private static String changePromptDateToDataBaseFormat(String string) {
        String[] subString = string.split(DATE_VIEW_SEPARATOR);
        string = subString[2] + DATE_DATABASE_SEPARATOR + subString[1] + DATE_DATABASE_SEPARATOR + subString[0];
        return string;
    }

    protected static void showMessage(String message){
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
    protected static void cleanTextField(TextField textField){
        textField.clear();
    }
    protected static void cleanDatePicker(DatePicker datePicker){
        datePicker.setValue(null);
    }

    protected static void searchFieldsSetUp(TextField filterFrom, TextField filterTo) {
       setInputNumericFieldStyle(filterFrom);
       setInputNumericFieldStyle(filterTo);
        if (!filterFrom.getPromptText().equals(INIT_NUMBER_FILTER_FROM)) {
            filterFrom.setPromptText(INIT_NUMBER_FILTER_FROM);
            filterFrom.clear();
        }
        if (!filterTo.getPromptText().equals(INIT_NUMBER_FILTER_TO)) {
            filterTo.setPromptText(INIT_NUMBER_FILTER_TO);
            filterTo.clear();
        }
    }

        protected void checkNumericFilterNoInputError(TextField filterFrom, TextField filterTo){
        if (!filterFrom.getText().isEmpty() & filterFrom.getText().isEmpty()) {
            filterTo.setText(INIT_NUMBER_FILTER_TO);
        } else if (filterFrom.getText().isEmpty() & !filterTo.getText().isEmpty()) {
            filterFrom.setText(INIT_NUMBER_FILTER_FROM);
        } else if (filterTo.getText().isEmpty() & filterFrom.getText().isEmpty()) {
            filterFrom.setText(INIT_NUMBER_FILTER_FROM);
            filterTo.setText(INIT_NUMBER_FILTER_TO);
        }
    }
        private void solveNumericFilterValuesProblem(TextField filterFrom, TextField filterTo) {
                int tmp = Integer.parseInt(filterFrom.getText());
                int counter = 0;
                while (tmp / 10 != 0) {
                    counter++;
                    tmp = tmp / 10;
                }
                int elem = Integer.parseInt(filterTo.getText());
                counter = (int) Math.pow(10, counter + 1);
                filterTo.setText(String.valueOf(elem * counter));
        }
        protected void checkNumericFilterValuesError(TextField filterFrom, TextField filterTo){
            checkNumericFilterNoInputError(filterFrom, filterTo);
            checkNumericFilterValuesNotZero(filterFrom, filterTo);
            if (Integer.parseInt(filterFrom.getText()) > Integer.parseInt(filterTo.getText())) {
                solveNumericFilterValuesProblem(filterFrom,filterTo);
                showMessage(FILTER_VALUES_ERROR);
            }
        }
        private void checkNumericFilterValuesNotZero(TextField filterFrom, TextField filterTo){
        if((Integer.parseInt(filterFrom.getText()) == 0) || (Integer.parseInt(filterTo.getText()) == 0)){
            filterFrom.setText(INIT_NUMBER_FILTER_FROM);
            filterTo.setText(INIT_NUMBER_FILTER_TO);
            showMessage(FILTER_VALUES_ERROR);
        }
        }

}

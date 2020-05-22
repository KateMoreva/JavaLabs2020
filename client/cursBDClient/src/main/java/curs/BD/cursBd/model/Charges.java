package curs.BD.cursBd.model;


import java.sql.Timestamp;

public class Charges {
    private Integer id;
    private Integer amount;
    private String chargeDateString;
    private Timestamp chargeDate;
    private ExpenseItems expenseItems;

    public Charges() {

    }

    public Charges(Integer id, Integer amount, String chargeDateString, ExpenseItems expenseItems) {
        this.id = id;
        this.amount = amount;
        this.chargeDateString = chargeDateString;
        this.expenseItems = expenseItems;
    }

    public Charges(Integer id, Integer amount, Timestamp chargeDate, ExpenseItems expenseItem) {
        this.id = id;
        this.amount = amount;
        this.chargeDate = chargeDate;
        this.expenseItems = expenseItem;
    }

    public Charges(Timestamp chargeDate, ExpenseItems expenseItem) {
        this.chargeDate = chargeDate;
        this.expenseItems = expenseItem;
    }

    public Charges(Integer amount, Timestamp chargeDate, ExpenseItems expenseItems) {
        this.amount = amount;
        this.chargeDate = chargeDate;
        this.expenseItems = expenseItems;
    }

    public Charges(Integer amount, String chargeDate, ExpenseItems expenseItems) {
        this.amount = amount;
        this.chargeDateString = chargeDate;
        this.expenseItems = expenseItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getExpenseItemsId() {
        return this.expenseItems.getId();
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Timestamp getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(Timestamp chargeDate) {
        this.chargeDate = chargeDate;
    }

    public void setExpenseItemsId(Integer expenseItemsId) {
        this.expenseItems.setId(expenseItemsId);
    }

    public String getChargeDateString() {
        return chargeDateString;
    }

    public void setChargeDateString(String chargeDateString) {

        this.chargeDateString = chargeDateString;
    }

    public ExpenseItems getExpenseItems() {
        return expenseItems;
    }

    public void setExpenseItems(ExpenseItems expenseItems) {
        this.expenseItems = expenseItems;
    }

    public String getExpenseItemsName() {
        return this.expenseItems.getName();
    }

    public void setExpenseItemsName(String expenseItemsName) {
        this.expenseItems.setName(expenseItemsName);
    }
}

package curs.BD.cursBd.model;

import javax.persistence.*;
import java.sql.Timestamp;

public class ChargesBody {
    private Integer amount;
    private Timestamp chargeDate;
    private Long expenseItemsId;

    public Integer getAmount() {
        return amount;
    }

    public Long getExpenseItemsId() {
        return expenseItemsId;
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

    public void setExpenseItemsId(Long expenseItemsId) {
        this.expenseItemsId = expenseItemsId;
    }
}

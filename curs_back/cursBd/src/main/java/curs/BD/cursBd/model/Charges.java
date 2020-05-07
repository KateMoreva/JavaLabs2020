package curs.BD.cursBd.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "charges")
public class Charges {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private Integer amount;
    private Timestamp chargeDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expense_item_id", referencedColumnName = "id")
    ExpenseItems expenseItemsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public ExpenseItems getExpenseItemsId() {
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

    public void setExpenseItemsId(ExpenseItems expenseItemsId) {
        this.expenseItemsId = expenseItemsId;
    }
}

package curs.BD.cursBd.model;

import java.sql.Timestamp;

public class SalesBody {
    private Integer amount;
    private Integer quantity;
    private Timestamp saleDate;
    private Long warehouseId;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Timestamp getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Timestamp saleDate) {
        this.saleDate = saleDate;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long expenseItemsId) {
        this.warehouseId = expenseItemsId;
    }
}

package curs.BD.cursBd.model;

import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Set;

public class Sales {
    Integer id;
    Integer amount;
    Integer quantity;
    String saleDate;
    Product warehouseId;

    public Sales() {

    }

    public Sales(Integer id, Integer amount, Integer quantity, String saleDate, Product warehouseId) {
        this.id = id;
        this.amount = amount;
        this.quantity = quantity;
        this.saleDate = saleDate;
        this.warehouseId = warehouseId;
    }
    public Sales(Integer id, Integer amount, Integer quantity, String saleDate) {
        this.id = id;
        this.amount = amount;
        this.quantity = quantity;
        this.saleDate = saleDate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public Product getWarehouseId() {
        return this.warehouseId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setSaleDate(String date) {
        this.saleDate = date;
    }

    public void setWarehouseId(Product warehouseId) {
        this.warehouseId = warehouseId;
    }
}

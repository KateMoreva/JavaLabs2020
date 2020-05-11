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
    Timestamp saleDate;
    String saleDateStr;
    Product warehouseId;

    public Sales() {

    }

    public Sales(Integer id, Integer amount, Integer quantity, String saleDateStr, Product warehouseId) {
        this.id = id;
        this.amount = amount;
        this.quantity = quantity;
        this.saleDateStr = saleDateStr;
        this.warehouseId = warehouseId;
    }
    public Sales(Integer id, Integer amount, Integer quantity, Timestamp saleDate, Product warehouseId) {
        this.id = id;
        this.amount = amount;
        this.quantity = quantity;
        this.saleDate = saleDate;
        this.warehouseId = warehouseId;
    }
    public Sales(Integer id, Integer amount, Integer quantity, String saleDateStr) {
        this.id = id;
        this.amount = amount;
        this.quantity = quantity;
        this.saleDateStr = saleDateStr;
    }
    public Sales(Integer amount, Integer quantity, String saleDateStr, Product warehouseId) {
        this.amount = amount;
        this.quantity = quantity;
        this.saleDateStr = saleDateStr;
        this.warehouseId = warehouseId;
    }
    public Sales(Integer amount, Integer quantity, Timestamp saleDate, Product warehouseId) {
        this.amount = amount;
        this.quantity = quantity;
        this.saleDate = saleDate;
        this.warehouseId = warehouseId;
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
    public void setSaleDate(Timestamp date) {
        this.saleDate = date;
    }

    public String getSaleDateStr() {
        return saleDateStr;
    }
    public void setSaleDateStr(String date) {
        this.saleDateStr = date;
    }

    public Product getWarehouseId() {
        return this.warehouseId;
    }
    public void setWarehouseId(Product warehouseId) {
        this.warehouseId = warehouseId;
    }
}

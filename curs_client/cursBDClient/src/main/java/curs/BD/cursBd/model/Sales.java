package curs.BD.cursBd.model;

import java.sql.Timestamp;

public class Sales {
    Integer id;
    Integer amount;
    Integer quantity;
    Timestamp saleDate;
    String saleDateStr;
    Warehouses warehousesId;

    public Sales() {

    }

    public Sales(Integer id, Integer amount, Integer quantity, String saleDateStr, Warehouses warehousesId) {
        this.id = id;
        this.amount = amount;
        this.quantity = quantity;
        this.saleDateStr = saleDateStr;
        this.warehousesId = warehousesId;
    }
    public Sales(Integer id, Integer amount, Integer quantity, Timestamp saleDate, Warehouses warehousesId) {
        this.id = id;
        this.amount = amount;
        this.quantity = quantity;
        this.saleDate = saleDate;
        this.warehousesId = warehousesId;
    }
    public Sales(Integer id, Integer amount, Integer quantity, String saleDateStr) {
        this.id = id;
        this.amount = amount;
        this.quantity = quantity;
        this.saleDateStr = saleDateStr;
    }
    public Sales(Integer amount, Integer quantity, String saleDateStr, Warehouses warehousesId) {
        this.amount = amount;
        this.quantity = quantity;
        this.saleDateStr = saleDateStr;
        this.warehousesId = warehousesId;
    }
    public Sales(Integer amount, Integer quantity, Timestamp saleDate, Warehouses warehousesId) {
        this.amount = amount;
        this.quantity = quantity;
        this.saleDate = saleDate;
        this.warehousesId = warehousesId;
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

    public Warehouses getWarehousesId() {
        return this.warehousesId;
    }
    public void setWarehousesId(Warehouses warehousesId) {
        this.warehousesId = warehousesId;
    }
}

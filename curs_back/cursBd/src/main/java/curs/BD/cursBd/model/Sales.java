package curs.BD.cursBd.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;
    Integer amount;
    Integer quantity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Europe/Moscow")
    Timestamp saleDate;
    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    Warehouses warehousesId;

    public Sales() {

    }

    public Sales(Integer amount, Integer quantity, Timestamp saleDate, Warehouses warehousesId) {
        this.amount = amount;
        this.quantity = quantity;
        this.saleDate = saleDate;
        this.warehousesId = warehousesId;
    }

    public Long getId() {
        return id;
    }

    public Integer getAmount() {
        return this.amount;
//        return this.warehousesId.getAmount();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Timestamp getSaleDate() {
        return saleDate;
    }

    public Warehouses getWarehousesId() {
        return warehousesId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setSaleDate(Timestamp date) {
        this.saleDate = date;
    }

    public void setWarehousesId(Warehouses warehousesId) {
        this.warehousesId = warehousesId;
    }
}

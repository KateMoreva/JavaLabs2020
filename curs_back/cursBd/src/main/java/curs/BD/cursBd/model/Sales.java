package curs.BD.cursBd.model;

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
    Timestamp saleDate;
//(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ManyToOne
    @JoinColumn(name = "warehouseid", referencedColumnName = "id")
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "warehouseid", referencedColumnName = "id")
    Warehouse warehouseId;

    public Sales() {

    }

    public Sales(Integer amount, Integer quantity, Timestamp saleDate, Warehouse warehouseId) {
        this.amount = amount;
        this.quantity = quantity;
        this.saleDate = saleDate;
        this.warehouseId = warehouseId;
    }

    public Long getId() {
        return id;
    }

    public Integer getAmount() {
        return this.warehouseId.getAmount();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Timestamp getSaleDate() {
        return saleDate;
    }

    public Warehouse getWarehouseId() {
        return warehouseId;
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

    public void setWarehouseId(Warehouse warehouseId) {
        this.warehouseId = warehouseId;
    }
}

package curs.BD.cursBd.model;

import javax.persistence.*;

@Entity
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private int quantity;

    private Integer amount;

    public Warehouse() {

    }
    public Warehouse(String title, int quantity, Integer amount){
        this.name = title;
        this.quantity = quantity;
        this.amount = amount;
    }
//    public Sales getSales() {
//        return sales;
//    }
//    public void setSales(Sales sales) {
//        this.sales = sales;
//    }
    public Long getId(){
        return id;
    }
    public void setId(Long id) {this.id=id;}

    public Integer getAmount() {
        return amount;
    }

    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

package curs.BD.cursBd.testMessage;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    private int quantity;

    private Integer amount;

    public Product() {
    }

    public Product(String title, int quantity, Integer amount){
        this.name = title;
        this.quantity = quantity;
        this.amount = amount;
    }

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

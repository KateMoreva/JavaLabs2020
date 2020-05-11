package curs.BD.cursBd.model;


import org.springframework.data.relational.core.sql.In;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Warehouses {

    private Integer id;

    private String name;

    private Integer quantity;

    private Integer amount;

    public Warehouses() {
    }

    public Warehouses(String name, Integer quantity, Integer amount){
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
    }
    public Warehouses(Integer id, Integer quantity, Integer amount){
        this.id = id;
        this.quantity = quantity;
        this.amount = amount;
    }
    public Warehouses(Integer id, String name, Integer quantity, Integer amount){
        this.id =id;
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
    }
//    public Warehouses(Integer quantity, Integer amount){
//        this.quantity = quantity;
//    }

    public Warehouses(final String name){
        this.name = name;
    }

    public Integer getId(){
        return id;
    }
    public void setId(Integer id) {this.id=id;}

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

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

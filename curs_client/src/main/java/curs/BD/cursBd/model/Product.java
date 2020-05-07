package curs.BD.cursBd.model;


//@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private Integer id;

    private String name;

    private int quantity;

    private Integer amount;

    public Product() {
    }

    public Product(String name,int quantity, Integer amount){
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
    }
    public Product(Integer id, String name,int quantity, Integer amount){
        this.id =id;
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
    }
    public Product(Integer id, int quantity){
        this.id =id;

        this.quantity = quantity;

    }
    public Product(final String name){
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

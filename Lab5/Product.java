package sample;

public class Product {
    private final int id;
    private final String name;
    private int cost;

    public Product(final int id, final String title, int cost){
        this.id = id;
        this.name = title;
        this.cost = cost;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getCost(){
        return cost;
    }

}

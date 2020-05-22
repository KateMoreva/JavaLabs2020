package curs.BD.cursBd.model;

public class ExpenseItems {
    private Integer id;
    private String name;

    public ExpenseItems() {

    }

    public ExpenseItems(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ExpenseItems(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}

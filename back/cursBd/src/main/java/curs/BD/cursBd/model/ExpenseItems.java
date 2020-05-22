package curs.BD.cursBd.model;

import javax.persistence.*;

@Entity
@Table(name = "expenseItems")
public class ExpenseItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;

    public ExpenseItems() {
    }

    public ExpenseItems(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}

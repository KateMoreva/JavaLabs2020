package curs.BD.cursBd.testMessage;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "charges")
public class Charges {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Integer amount;
    private Timestamp charge_date;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expense_item_id", referencedColumnName = "id")
    Warehouse warehouseId;
}

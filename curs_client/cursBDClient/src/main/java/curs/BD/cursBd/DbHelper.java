package curs.BD.cursBd;

import curs.BD.cursBd.model.Warehouses;
import curs.BD.cursBd.model.WarehousesManager;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DbHelper {
    public static List<Warehouses> selectAllProducts(){
        return WarehousesManager.showAll();
    }
}

package curs.BD.cursBd;

import curs.BD.cursBd.model.Product;
import curs.BD.cursBd.model.ProductManager;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DbHelper {
    public static List<Product> selectAllProducts(){
        return ProductManager.showAll();
    }
}

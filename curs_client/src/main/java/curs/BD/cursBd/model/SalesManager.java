package curs.BD.cursBd.model;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class SalesManager {
    static final String URL_SALES = "http://localhost:8080/sales";

    public static List<Sales> showAll() {
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String,Object>> result = restTemplate.getForObject(URL_SALES, List.class);
        List<Sales> clproduct = new ArrayList<>();
        if (result != null) {
            for (Map<String,Object> res : result) {
                LinkedHashMap<String, Object> la = (LinkedHashMap<String, Object>) res.get("warehouseId");
                Product product = new Product((Integer) la.get("id"),(String) la.get("name"), (Integer) la.get("quantity"), (Integer) la.get("amount"));
               clproduct.add( new Sales((Integer) res.get("id"), (Integer) res.get("amount"), (Integer) res.get("quantity"), (String) res.get("saleDate"), product));
            }
        } else {
            System.out.println("no dara");
        }
        System.out.println(clproduct.get(0).getId());
        System.out.println(clproduct.get(0).getAmount());
        System.out.println(clproduct.get(0).getQuantity());
        System.out.println(clproduct.get(0).getSaleDate());
        System.out.println("warehouse");
        System.out.println(clproduct.get(0).getWarehouseId().getId());
        System.out.println(clproduct.get(0).getWarehouseId().getName());
        System.out.println(clproduct.get(0).getWarehouseId().getAmount());
        System.out.println(clproduct.get(0).getWarehouseId().getQuantity());

        return clproduct;
    }
    public static void main(String[] args) {
        showAll();
    }
}

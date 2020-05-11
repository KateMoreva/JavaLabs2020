package curs.BD.cursBd.model;

import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WarehousesManager {
    static final String URL_WAREHOUSE = "http://localhost:8080/warehouses";
    public static List<Warehouses> showAll(){
        String urll = URL_WAREHOUSE + "/all-items";
        return getWarehousesList(urll);
    }

    public static List<Warehouses> show(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String strId = id.toString();
        String urll = URL_WAREHOUSE + "/" + strId;
        Warehouses res = restTemplate.getForObject(urll, Warehouses.class);
        List<Warehouses> clproduct = new ArrayList<>();
        clproduct.add(new Warehouses(res.getId(),res.getName(), res.getQuantity(), res.getAmount()));
        return clproduct;
    }
    public static List<Warehouses> showByName(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/warehouse-name/" + name;
        return getWarehousesList(urll);
    }
    public static Warehouses getByName(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/name/" + name;
        return restTemplate.getForObject(urll, Warehouses.class);
    }
    public static List<Warehouses> filterByAmount(Integer amountFrom, Integer amountTo) {
        String urll = URL_WAREHOUSE + "/filter-amount/" + amountFrom.toString() +"/" + amountTo.toString();
        return getWarehousesList(urll);
    }
    public static List<Warehouses> filterByQuantity(Integer quantityFrom, Integer quantityTo) {

        String urll = URL_WAREHOUSE + "/filter-quantity/" + quantityFrom.toString() +"/" + quantityTo.toString();
        return getWarehousesList(urll);
    }

    private static List<Warehouses> getWarehousesList(String urll) {
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String,Object>> result = restTemplate.getForObject(urll, List.class);
        List<Warehouses> warehouses = new ArrayList<>();
        if (result != null) {
            for (Map<String,Object> item : result) {
                warehouses.add( new Warehouses((Integer) item.get("id"), (String) item.get("name"), (Integer) item.get("quantity"), (Integer) item.get("amount")));
            }
        }
        return warehouses;
    }

    public static void add(String name, Integer quantity, Integer amount) {
        Warehouses warehouses = new Warehouses(name, quantity, amount);
        RestTemplate restTemplate = new RestTemplate();
        Warehouses res = restTemplate.postForObject(URL_WAREHOUSE + "/new", warehouses, Warehouses.class);
    }
    public static boolean delete(String name){
       Warehouses warehouses = WarehousesManager.getByName(name);
        if (warehouses != null) {
            RestTemplate restTemplate = new RestTemplate();
            String urll = URL_WAREHOUSE + "/no-such/name/" + name;
            restTemplate.delete(urll);
            return true;
        }
        return false;
    }
    public static boolean updateAmount(String name, Integer amount) {
        List<Warehouses> warehouses = WarehousesManager.showByName(name);
        ArrayList<Warehouses> results = new ArrayList<>();
       if (!warehouses.isEmpty()) {
           for (Warehouses warehouse : warehouses) {
               Integer quantity = warehouse.getQuantity();
               results.add(update(amount, warehouse, quantity));
           }
       }
       return !results.isEmpty();
       }

    private static Warehouses update(Integer amount, Warehouses warehouses, Integer quantity) {
        Integer id = warehouses.getId();
        Map<String, Integer> param = new HashMap<>();
        param.put("id", id);
        Warehouses item = new Warehouses(id, quantity, amount);
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/" + id.toString();
        restTemplate.put(urll, item, param);
        return item;
    }

    public static boolean updateQuantity(String name, Integer quantity) {
        List<Warehouses> warehouses = WarehousesManager.showByName(name);
        ArrayList<Warehouses> results = new ArrayList<>();
        if (!warehouses.isEmpty()) {
            for (Warehouses warehouse : warehouses) {
                Integer amount = warehouse.getAmount();
                results.add(update(amount, warehouse, quantity));

            }}
            return !results.isEmpty();
    }



    public static void main(String[] args) {
        System.out.println(showAll());
        System.out.println(show(3));
        System.out.println(showByName("th"));
//        System.out.println(filter(1, 100));
        delete("new");
        System.out.println(showAll());
//        update("th", 45, 67);

    }

}

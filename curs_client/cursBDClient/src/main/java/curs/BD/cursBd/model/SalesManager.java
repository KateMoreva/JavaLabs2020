package curs.BD.cursBd.model;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;

import static java.util.Collections.EMPTY_LIST;

@Component
public class SalesManager {
    static final String URL_SALES = "http://localhost:8080/sales";

    public static List<Sales> showAll() {
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String,Object>> result = restTemplate.getForObject(URL_SALES + "/all-items", List.class);
        List<Sales> clproduct = new ArrayList<>();
        if (result != null) {
            for (Map<String,Object> res : result) {
                LinkedHashMap<String, Object> la = (LinkedHashMap<String, Object>) res.get("warehousesId");
                Warehouses warehouses = new Warehouses((Integer) la.get("id"),(String) la.get("name"), (Integer) la.get("quantity"), (Integer) la.get("amount"));
               clproduct.add( new Sales((Integer) res.get("id"), (Integer) res.get("amount"), (Integer) res.get("quantity"), (String) res.get("saleDate"), warehouses));
            }
        }
        return clproduct;
    }
    private static List<Sales> getChargesList (List<Map<String,Object>> result) {
        List<Sales> charges = new ArrayList<>();
        List<Sales> clproduct = new ArrayList<>();
        if (result != null) {
            for (Map<String,Object> res : result) {
                LinkedHashMap<String, Object> la = (LinkedHashMap<String, Object>) res.get("warehousesId");
                Warehouses warehouses = new Warehouses((Integer) la.get("id"),(String) la.get("name"), (Integer) la.get("quantity"), (Integer) la.get("amount"));
                clproduct.add( new Sales((Integer) res.get("id"), (Integer) res.get("amount"), (Integer) res.get("quantity"), (String) res.get("saleDate"), warehouses));
            }
        }
        return clproduct;
    }
    public static List<Sales> find(Integer quantity, Timestamp time, String warehouseName ){
        Warehouses item = WarehousesManager.getByName(warehouseName);
        if (item != null) {
            Integer amount = item.getAmount();
            RestTemplate restTemplate = new RestTemplate();
            String urll = URL_SALES + "/by-all-info/amount/" + amount + "/quantity/" + quantity + "/date/" + time.toString() + "/item-name/" + warehouseName;
            List<Map<String, Object>> result = restTemplate.getForObject(urll, List.class);
            return getChargesList(result);
        }
        return EMPTY_LIST;
    }
    public static boolean add(Integer quantity, Timestamp time, String warehouseName) {
        Warehouses item = WarehousesManager.getByName(warehouseName);
        if (item != null) {
            Integer itemId = item.getId();
            Sales sales = new Sales(quantity, time, item);
            RestTemplate restTemplate = new RestTemplate();
            Sales res = restTemplate.postForObject(URL_SALES + "/new", sales, Sales.class);
            return true;
        }
        return false;
    }
    public static List<Sales> filterByDate(Timestamp timeFrom, Timestamp timeTo) {
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_SALES + "/date-filter/dateFrom/" + timeFrom + "/dateTo/" + timeTo;
        List<Map<String, Object>> result = restTemplate.getForObject(urll, List.class);
        return getChargesList(result);
    }
    public static boolean update(Integer quantity, Timestamp time, String warehouseName, Integer newQuantity, Timestamp newTime) {
        List<Sales> charges = SalesManager.find(quantity, time, warehouseName);
        Warehouses wareitem = WarehousesManager.getByName(warehouseName);
        ArrayList<Sales> results = new ArrayList<>();
        if (!charges.isEmpty()) {
            for (Sales charge : charges) {
                Integer id = charge.getId();
                Map<String, Integer> param = new HashMap<String, Integer>();
                param.put("id", id);
                Sales sales = new Sales(newQuantity, newTime, wareitem);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.put(URL_SALES + "/" + id.toString(), sales, param);
                results.add(sales);
            }

        }
        return !results.isEmpty();
    }
//    public static boolean updateOnlyDate(Timestamp time, String expenseItemName, Timestamp newTime){
//        List<Sales> sales = SalesManager.findByDateAndName(expenseItemName,time, time);
//        ExpenseItems expenseItems = ExpenseItemsManager.findByName(expenseItemName);
//        ArrayList<Charges> results = new ArrayList<>();
//        if (!charges.isEmpty() && (expenseItems != null)) {
//            for (Charges charge : charges) {
//                Integer id = charge.getId();
//                Map<String, Integer> param = new HashMap<String, Integer>();
//                param.put("id", id);
//                Charges item = new Charges(newTime, expenseItems);
//                RestTemplate restTemplate = new RestTemplate();
//                String urll = URL_WAREHOUSE + "/" + id.toString();
//                restTemplate.put(urll, item, param);
//                results.add(item);
//            }
//        }
//        return !results.isEmpty();
//    }
    public static List<Sales> findByWarehouseName(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_SALES + "/by-warehouse-name/" + name;
        List<Map<String, Object>> result = restTemplate.getForObject(urll,List.class);
        return getChargesList(result);
    }
    public static boolean delete(Integer quantity, Timestamp time, String expenseItemName ){
        List<Sales> charge = SalesManager.find(quantity, time, expenseItemName);
        if (!charge.isEmpty()) {
            for (Sales sale : charge) {
                Integer amount = sale.getAmount();
                RestTemplate restTemplate = new RestTemplate();
                String urll = URL_SALES + "/no-such/amount/" + amount + "/quantity/ " + quantity + "/date/" + time.toString() + "/item-name/" + expenseItemName;
                restTemplate.delete(urll);
            }
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        System.out.println(showAll());
//        add( 5, Timestamp.valueOf("2020-05-11 10:05:15"), "ty");
//       delete(73, 36, Timestamp.valueOf("2020-05-11 10:06:15"), "ty");
//       update(73, 12, Timestamp.valueOf("2020-05-11 10:06:15"), "ty", 73, 36, Timestamp.valueOf("2020-05-11 10:06:15"));
        System.out.println(filterByDate(Timestamp.valueOf("2020-05-11 10:05:15"), Timestamp.valueOf("2020-05-11 10:05:15")));
    }
}

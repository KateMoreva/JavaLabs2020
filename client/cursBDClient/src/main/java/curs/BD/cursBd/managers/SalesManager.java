package curs.BD.cursBd.managers;

import curs.BD.cursBd.model.Sales;
import curs.BD.cursBd.model.Warehouses;
import curs.BD.cursBd.ui.ErrorController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;

@Component
public class SalesManager extends MainManager {
    static final String URL_SALES = "http://localhost:8080/sales";

    private static HttpEntity<Sales> getTokenAndMakeHeaderPost(Sales item) {
        return new HttpEntity<>(item, makeHeaderForPost());
    }

    public static List<Sales> showAll() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> response = restTemplate.exchange(URL_SALES + "/all-items",
                    HttpMethod.GET,
                    getTokenAndMakeHeader(),
                    Object.class);
            List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();
            List<Sales> clproduct = new ArrayList<>();
            if (result != null) {
                for (Map<String, Object> res : result) {
                    LinkedHashMap<String, Object> la = (LinkedHashMap<String, Object>) res.get("warehousesId");
                    Warehouses warehouses = new Warehouses((Integer) la.get("id"), (String) la.get("name"), (Integer) la.get("quantity"), (Integer) la.get("amount"));
                    clproduct.add(new Sales((Integer) res.get("id"), (Integer) res.get("amount"), (Integer) res.get("quantity"), (String) res.get("saleDate"), warehouses));
                }
            }
            return clproduct;
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return Collections.emptyList();
    }

    private static List<Sales> getChargesList(List<Map<String, Object>> result) {
        List<Sales> charges = new ArrayList<>();
        List<Sales> clproduct = new ArrayList<>();
        if (result != null) {
            for (Map<String, Object> res : result) {
                LinkedHashMap<String, Object> la = (LinkedHashMap<String, Object>) res.get("warehousesId");
                Warehouses warehouses = new Warehouses((Integer) la.get("id"), (String) la.get("name"), (Integer) la.get("quantity"), (Integer) la.get("amount"));
                clproduct.add(new Sales((Integer) res.get("id"), (Integer) res.get("amount"), (Integer) res.get("quantity"), (String) res.get("saleDate"), warehouses));
            }
        }
        return clproduct;
    }

    public static List<Sales> find(Integer quantity, Timestamp time, String warehouseName) {
        try {
            Warehouses item = WarehousesManager.getByName(warehouseName);
            if (item != null) {
                Integer amount = item.getAmount();
                RestTemplate restTemplate = new RestTemplate();
                String urll = URL_SALES + "/by-all-info/amount/" + amount + "/quantity/" + quantity + "/date/" + time.toString() + "/item-name/" + warehouseName;
                ResponseEntity<Object> response = restTemplate.exchange(urll,
                        HttpMethod.GET,
                        getTokenAndMakeHeader(),
                        Object.class);
                List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();
                return getChargesList(result);

            }
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return Collections.emptyList();
    }

    public static boolean add(Integer quantity, Timestamp time, String warehouseName) {
        try {
            Warehouses item = WarehousesManager.getByName(warehouseName);
            if (item != null) {
                Integer itemId = item.getId();
                Sales sales = new Sales(quantity, time, item);
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<Sales> result = restTemplate.postForEntity(URL_SALES + "/new", getTokenAndMakeHeaderPost(sales), Sales.class);
                return true;
            }
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return false;
    }

    public static List<Sales> filterByDate(Timestamp timeFrom, Timestamp timeTo) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urll = URL_SALES + "/date-filter/dateFrom/" + timeFrom + "/dateTo/" + timeTo;
            ResponseEntity<Object> response = restTemplate.exchange(urll,
                    HttpMethod.GET,
                    getTokenAndMakeHeader(),
                    Object.class);
            List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();
            return getChargesList(result);
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return Collections.emptyList();
    }

    public static boolean update(Integer quantity, Timestamp time, String warehouseName, Integer newQuantity, Timestamp newTime) {
        try {
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
                    restTemplate.put(URL_SALES + "/" + id.toString(), getTokenAndMakeHeaderPost(sales), param);
                    results.add(sales);
                }

            }
            return !results.isEmpty();
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return false;
    }

    public static List<Sales> findByWarehouseName(String name) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urll = URL_SALES + "/by-warehouse-name/" + name;
            ResponseEntity<Object> response = restTemplate.exchange(urll,
                    HttpMethod.GET,
                    getTokenAndMakeHeader(),
                    Object.class);
            List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();
            return getChargesList(result);
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return Collections.emptyList();

    }

    public static boolean delete(Integer quantity, Timestamp time, String expenseItemName) {
        try {
            List<Sales> charge = SalesManager.find(quantity, time, expenseItemName);
            if (!charge.isEmpty()) {
                for (Sales sale : charge) {
                    Integer amount = sale.getAmount();
                    RestTemplate restTemplate = new RestTemplate();
                    String urll = URL_SALES + "/no-such/amount/" + amount + "/quantity/ " + quantity + "/date/" + time.toString() + "/item-name/" + expenseItemName;
                    ResponseEntity<Sales> result = restTemplate.exchange(urll, HttpMethod.DELETE, getTokenAndMakeHeader(), Sales.class);
                }
                return true;
            }
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
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

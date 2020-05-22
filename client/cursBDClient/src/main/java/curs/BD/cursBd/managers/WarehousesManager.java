package curs.BD.cursBd.managers;

import curs.BD.cursBd.model.ExpenseItems;
import curs.BD.cursBd.model.Warehouses;
import curs.BD.cursBd.ui.ErrorController;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class WarehousesManager extends MainManager {
    static final String URL_WAREHOUSE = "http://localhost:8080/warehouses";

    private static HttpEntity<Warehouses> getTokenAndMakeHeaderPost(Warehouses item) {
        return new HttpEntity<>(item, makeHeaderForPost());
    }

    public static List<Warehouses> showAll() {
        String urll = URL_WAREHOUSE + "/all-items";
        return getWarehousesList(urll);
    }

    public static List<Warehouses> show(Integer id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String strId = id.toString();
            String urll = URL_WAREHOUSE + "/" + strId;
            ResponseEntity<Warehouses> result = restTemplate.exchange(urll, HttpMethod.GET, getTokenAndMakeHeader(), Warehouses.class);
            Warehouses res = result.getBody();
            List<Warehouses> clproduct = new ArrayList<>();
            clproduct.add(new Warehouses(res.getId(), res.getName(), res.getQuantity(), res.getAmount()));
            return clproduct;
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return null;
    }

    public static List<Warehouses> showByName(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/warehouse-name/" + name;
        return getWarehousesList(urll);
    }

    public static Warehouses getByName(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/name/" + name;
        try {
            ResponseEntity<Warehouses> result = restTemplate.exchange(urll, HttpMethod.GET, getTokenAndMakeHeader(), Warehouses.class);
            return result.getBody();
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return null;
    }

    public static List<Warehouses> filterByAmount(Integer amountFrom, Integer amountTo) {
        String urll = URL_WAREHOUSE + "/filter-amount/" + amountFrom.toString() + "/" + amountTo.toString();
        return getWarehousesList(urll);
    }

    public static List<Warehouses> filterByQuantity(Integer quantityFrom, Integer quantityTo) {

        String urll = URL_WAREHOUSE + "/filter-quantity/" + quantityFrom.toString() + "/" + quantityTo.toString();
        return getWarehousesList(urll);
    }

    private static List<Warehouses> getWarehousesList(String urll) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> response = restTemplate.exchange(urll,
                    HttpMethod.GET,
                    getTokenAndMakeHeader(),
                    Object.class);
            List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();
            List<Warehouses> warehouses = new ArrayList<>();
            if (result != null) {
                for (Map<String, Object> item : result) {
                    warehouses.add(new Warehouses((Integer) item.get("id"), (String) item.get("name"), (Integer) item.get("quantity"), (Integer) item.get("amount")));
                }
            }
            return warehouses;
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return Collections.emptyList();

    }

    public static void add(String name, Integer quantity, Integer amount) {
        try {
            Warehouses warehouses = new Warehouses(name, quantity, amount);
            RestTemplate restTemplate = new RestTemplate();
            String urll = URL_WAREHOUSE + "/new";
            ResponseEntity<ExpenseItems> result = restTemplate.postForEntity(urll, getTokenAndMakeHeaderPost(warehouses), ExpenseItems.class);
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        }
    }

    public static boolean delete(String name) {
        try {
            Warehouses warehouses = WarehousesManager.getByName(name);
            if (warehouses != null) {
                RestTemplate restTemplate = new RestTemplate();
                String urll = URL_WAREHOUSE + "/no-such/name/" + name;
                ResponseEntity<Warehouses> result = restTemplate.exchange(urll, HttpMethod.DELETE, getTokenAndMakeHeader(), Warehouses.class);
                return true;
            }
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
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
        try {
            Integer id = warehouses.getId();
            Map<String, Integer> param = new HashMap<>();
            param.put("id", id);
            Warehouses item = new Warehouses(id, quantity, amount);
            RestTemplate restTemplate = new RestTemplate();
            String urll = URL_WAREHOUSE + "/" + id.toString();
            restTemplate.put(urll, getTokenAndMakeHeaderPost(item), param);
            return item;
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return null;
    }

    public static boolean updateQuantity(String name, Integer quantity) {
        List<Warehouses> warehouses = WarehousesManager.showByName(name);
        ArrayList<Warehouses> results = new ArrayList<>();
        if (!warehouses.isEmpty()) {
            for (Warehouses warehouse : warehouses) {
                Integer amount = warehouse.getAmount();
                results.add(update(amount, warehouse, quantity));

            }
        }
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

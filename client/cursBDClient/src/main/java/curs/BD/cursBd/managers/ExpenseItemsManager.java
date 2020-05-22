package curs.BD.cursBd.managers;

import curs.BD.cursBd.model.ExpenseItems;
import curs.BD.cursBd.ui.ErrorController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class ExpenseItemsManager extends MainManager {
    static final String URL_EXPENSE_ITEMS = "http://localhost:8080/expense-items";


    private static HttpEntity<ExpenseItems> getTokenAndMakeHeaderPost(ExpenseItems item) {
        return new HttpEntity<>(item, makeHeaderForPost());
    }

    public static List<ExpenseItems> showAll() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> response = restTemplate.exchange(URL_EXPENSE_ITEMS + "/all-items",
                    HttpMethod.GET,
                    getTokenAndMakeHeader(),
                    Object.class);
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.getBody();
            List<ExpenseItems> expenseItems = new ArrayList<>();
            if (results != null) {
                for (Map<String, Object> item : results) {
                    expenseItems.add(new ExpenseItems((Integer) item.get("id"), (String) item.get("name")));
                }
            }
            return expenseItems;
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return Collections.emptyList();
    }

    public static List<ExpenseItems> show(Integer id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String strId = id.toString();
            String urll = URL_EXPENSE_ITEMS + "/" + strId;
            List<ExpenseItems> item = new ArrayList<>();
            ResponseEntity<ExpenseItems> result = restTemplate.exchange(urll, HttpMethod.GET, getTokenAndMakeHeader(), ExpenseItems.class);
            ExpenseItems res = (ExpenseItems) result.getBody();
            if (res != null) {
                item.add(new ExpenseItems(res.getId(), res.getName()));
                return item;
            }
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return Collections.emptyList();
    }

    public static ExpenseItems findByName(String name) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urll = URL_EXPENSE_ITEMS + "/name/" + name;
            ResponseEntity<ExpenseItems> result = restTemplate.exchange(urll, HttpMethod.GET, getTokenAndMakeHeader(), ExpenseItems.class);
            return result.getBody();
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return null;
    }

    public static List<ExpenseItems> filter(Integer idFrom, Integer idTo) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String strIdFrom = idFrom.toString();
            String strIdTo = idTo.toString();
            String urll = URL_EXPENSE_ITEMS + "/filter/" + strIdFrom + "/" + strIdTo;
            ResponseEntity<Object> result = restTemplate.exchange(urll, HttpMethod.GET, getTokenAndMakeHeader(), Object.class);
            List<Map<String, Object>> res = (List<Map<String, Object>>) result.getBody();
            List<ExpenseItems> expenseItems = new ArrayList<>();
            if (result != null) {
                for (Map<String, Object> item : res) {
                    expenseItems.add(new ExpenseItems((Integer) item.get("id"), (String) item.get("name")));
                }
            }
            return expenseItems;
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return Collections.emptyList();
    }

    public static boolean add(String name) {
        try {
            ExpenseItems expenseItem = new ExpenseItems(name);
            RestTemplate restTemplate = new RestTemplate();
            String urll = URL_EXPENSE_ITEMS + "/new";
            ResponseEntity<ExpenseItems> result = restTemplate.postForEntity(urll, getTokenAndMakeHeaderPost(expenseItem), ExpenseItems.class);
            return true;
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return false;
    }

    public static boolean delete(String name) {
        try {
            ExpenseItems item = ExpenseItemsManager.findByName(name);
            if (item != null) {
                RestTemplate restTemplate = new RestTemplate();
                String urll = URL_EXPENSE_ITEMS + "/no-such/name/" + name;
                ResponseEntity<ExpenseItems> result = restTemplate.exchange(urll, HttpMethod.DELETE, getTokenAndMakeHeader(), ExpenseItems.class);
                return true;
            }
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch (HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return false;
    }

    public static boolean update(Integer id, String name) {
        try {
            Map<String, Integer> param = new HashMap<String, Integer>();
            param.put("id", id);
            ExpenseItems item = new ExpenseItems(id, name);
            String strId = id.toString();
            String urll = URL_EXPENSE_ITEMS + "/" + strId;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ExpenseItems> response = restTemplate.exchange(urll, HttpMethod.GET, getTokenAndMakeHeader(), ExpenseItems.class);
            ExpenseItems res = (ExpenseItems) response.getBody();
            if (res != null) {
                restTemplate.put(urll, getTokenAndMakeHeaderPost(item), param);
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
//        System.out.println(show(2));
        System.out.println(filter(1, 10));

    }
}

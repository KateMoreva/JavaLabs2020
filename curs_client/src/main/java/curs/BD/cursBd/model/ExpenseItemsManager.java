package curs.BD.cursBd.model;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseItemsManager {
    static final String URL_EXPENSE_ITEMS = "http://localhost:8080/expense-items";

    public static List<ExpenseItems> showAll(){
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String,Object>> result = restTemplate.getForObject(URL_EXPENSE_ITEMS + "/all-items", List.class);
        List<ExpenseItems> expenseItems = new ArrayList<>();
        if (result != null) {
            for (Map<String,Object> item : result) {
                expenseItems.add( new ExpenseItems((Integer) item.get("id"), (String) item.get("name")));
            }
        } else {
            System.out.println("no dara");
        }
        return expenseItems;
    }

    public static List<ExpenseItems> show(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String strId = id.toString();
        String urll = URL_EXPENSE_ITEMS + "/" + strId;
        List<ExpenseItems> item = new ArrayList<>();
        ExpenseItems res = restTemplate.getForObject(urll, ExpenseItems.class);
        item.add(new ExpenseItems(res.getId(),res.getName()));
        return item;
    }

    public static List<ExpenseItems> filter(Integer idFrom, Integer idTo) {
        RestTemplate restTemplate = new RestTemplate();
        String strIdFrom = idFrom.toString();
        String strIdTo = idTo.toString();
        String urll = URL_EXPENSE_ITEMS + "/filter/" + strIdFrom+"/" + strIdTo;
//        List<ExpenseItems> items = new ArrayList<>();
        List<Map<String,Object>> result = restTemplate.getForObject(URL_EXPENSE_ITEMS + "/all-items", List.class);
        List<ExpenseItems> expenseItems = new ArrayList<>();
        if (result != null) {
            for (Map<String,Object> item : result) {
                expenseItems.add( new ExpenseItems((Integer) item.get("id"), (String) item.get("name")));
            }
        } else {
            System.out.println("no dara");
        }
//        item.add(new ExpenseItems(res.getId(),res.getName()));
        return expenseItems;
    }

    public static void add(String name) {
        ExpenseItems expenseItem = new ExpenseItems(name);
        RestTemplate restTemplate = new RestTemplate();
        ExpenseItems res = restTemplate.postForObject(URL_EXPENSE_ITEMS + "/new", expenseItem, ExpenseItems.class);
    }
    public static void delete(String name){
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_EXPENSE_ITEMS + "/no-such/name/" + name;
        restTemplate.delete(urll);
    }

    public static void update(Integer id, String name) {
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("id", id);
        ExpenseItems item = new ExpenseItems(id, name);
        String strId = id.toString();
        String urll = URL_EXPENSE_ITEMS + "/" + strId;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(urll, item, param);
    }

    public static void main(String[] args) {
        System.out.println(show(2));
        System.out.println(filter(1, 100));

    }
}

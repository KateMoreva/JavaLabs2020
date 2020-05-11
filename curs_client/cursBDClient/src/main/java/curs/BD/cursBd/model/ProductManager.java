package curs.BD.cursBd.model;

import curs.BD.cursBd.CientMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductManager {
    static final String URL_WAREHOUSE = "http://localhost:8080/warehouse";
    public static List<Product> showAll(){
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String,Object>> result = restTemplate.getForObject(URL_WAREHOUSE, List.class);
        List<Product> clproduct = new ArrayList<>();
        if (result != null) {
            for (Map<String,Object> res : result) {
                clproduct.add( new Product((Integer) res.get("id"), (String) res.get("name"), (Integer) res.get("quantity"), (Integer) res.get("amount")));
            }
        } else {
            System.out.println("no dara");
        }
        return clproduct;
    }

    public static List<Product> show(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String strId = id.toString();
        String urll = URL_WAREHOUSE + "/" + strId;
        Product res = restTemplate.getForObject(urll, Product.class);
        List<Product> clproduct = new ArrayList<>();
        clproduct.add(new Product(res.getId(),res.getName(), res.getQuantity(), res.getAmount()));
        return clproduct;
    }
    public static List<Product> showByName(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/name/" + name;
        Product res = restTemplate.getForObject(urll, Product.class);
        List<Product> clproduct = new ArrayList<>();
        clproduct.add(new Product(res.getId(),res.getName(), res.getQuantity(), res.getAmount()));
        return clproduct;
    }
    public static void filter(Integer amountFrom, Integer amountTo) {
        RestTemplate restTemplate = new RestTemplate();
        String strAmountFrom = amountFrom.toString();
        String strAmountTo = amountTo.toString();
        String urll = URL_WAREHOUSE + "/filter/" + strAmountFrom +"/" + strAmountTo;
        Product res = restTemplate.getForObject(urll, Product.class);
    }

    public static void add(String name, Integer quantity, Integer amount) {
        Product product = new Product(name, quantity, amount);
        RestTemplate restTemplate = new RestTemplate();
        Product res = restTemplate.postForObject(URL_WAREHOUSE, product, Product.class);
    }
    public static void delete(String name){
        RestTemplate restTemplate = new RestTemplate();
//        String strId = id.toString();
        String urll = URL_WAREHOUSE + "/deleted/" + name;
        restTemplate.delete(urll);
    }
    public static void deleteByName(String name) {
        RestTemplate restTemplate = new RestTemplate();

    }
    public static void update(Integer id, Integer quantity) {
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("id", id);
       Product product = new Product(id, quantity);
        String strId = id.toString();
        String urll = URL_WAREHOUSE + "/" + strId;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(urll, product, param);
    }

    public static void main(String[] args) {
        showByName("ret");

    }

}

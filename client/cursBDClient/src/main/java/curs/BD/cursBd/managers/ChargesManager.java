package curs.BD.cursBd.managers;

import curs.BD.cursBd.model.Charges;
import curs.BD.cursBd.model.ExpenseItems;
import curs.BD.cursBd.ui.ErrorController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;

public class ChargesManager extends MainManager{
    static final String URL_WAREHOUSE = "http://localhost:8080/charges";

    private static HttpEntity<Charges> getTokenAndMakeHeaderPost(Charges item) {
        return new HttpEntity<>(item, makeHeaderForPost());
    }

    private static List<Charges> getChargesList (List<Map<String,Object>> result) {
        List<Charges> charges = new ArrayList<>();
        if (result != null) {
            for (Map<String,Object> res : result) {
                LinkedHashMap<String, Object> la = (LinkedHashMap<String, Object>) res.get("expenseItemsId");
                ExpenseItems expenseItem = new ExpenseItems((Integer) la.get("id"),(String) la.get("name"));
                String dateT = (String) res.get("chargeDate");
                    charges.add( new Charges((Integer) res.get("id"), (Integer) res.get("amount"), dateT, expenseItem));
            }
        }
        return charges;
    }

    public static List<Charges> showAll(){
       try {
           RestTemplate restTemplate = new RestTemplate();
           ResponseEntity<Object> response = restTemplate.exchange(URL_WAREHOUSE + "/all-items",
                   HttpMethod.GET,
                   getTokenAndMakeHeader(),
                   Object.class);
           List<Map<String, Object>> results = (List<Map<String, Object>>) response.getBody();
           return getChargesList(results);
       } catch (ResourceAccessException error) {
           showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
       } catch ( HttpClientErrorException.Forbidden exception) {
           showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
       }
        return Collections.emptyList();
    }

    public static ArrayList<Charges> showByItemId(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String idstr = "/by-expense-item/" + id.toString();
        ArrayList<Charges> charges = restTemplate.getForObject(URL_WAREHOUSE + idstr, ArrayList.class);
        return charges;
    }
    public static List<Charges> findByExpenseItemName(String name) {
       try {
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/expense-item-name/" + name;
        ResponseEntity<Object> response = restTemplate.exchange(urll,
                   HttpMethod.GET,
                   getTokenAndMakeHeader(),
                   Object.class);
           List<Map<String,Object>> result = (List<Map<String, Object>>) response.getBody();
        return getChargesList(result);
       } catch (ResourceAccessException error) {
           showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
       } catch ( HttpClientErrorException.Forbidden exception) {
           showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
       }
        return Collections.emptyList();
    }

    public static boolean add(Integer amount,Timestamp time, String expenseItemName ) {
        try {
        ExpenseItems item = ExpenseItemsManager.findByName(expenseItemName);
        if (item != null) {
            Integer itemId = item.getId();
            Charges charges = new Charges(amount,time, item);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Charges> result = restTemplate.postForEntity(URL_WAREHOUSE + "/new", getTokenAndMakeHeaderPost(charges), Charges.class);
            return true;
        }
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch ( HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return false;
    }
    public static List<Charges> findByDate(Timestamp time){
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/date/" + time.toString();
        List<Map<String,Object>> result = restTemplate.getForObject(urll, List.class);
        return getChargesList(result);

    }
    public static List<Charges> filterByDate(Timestamp timeFrom, Timestamp timeTo){
      try {
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/date-filter/dateFrom/" + timeFrom.toString() + "/dateTo/" + timeTo.toString();
          ResponseEntity<Object> response = restTemplate.exchange(urll,
                  HttpMethod.GET,
                  getTokenAndMakeHeader(),
                  Object.class);
          List<Map<String,Object>> result = (List<Map<String, Object>>) response.getBody();
        return getChargesList(result);

      } catch (ResourceAccessException error) {
          showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
      } catch ( HttpClientErrorException.Forbidden exception) {
          showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
      }
        return Collections.emptyList();
    }

    public static List<Charges> find(Integer amount, Timestamp time, String expenseItemName ){
       try {
           RestTemplate restTemplate = new RestTemplate();
           String urll = URL_WAREHOUSE + "/by-all-info/amount/" + amount + "/date/" + time.toString() + "/item-name/" + expenseItemName;
           ResponseEntity<Object> response = restTemplate.exchange(urll,
                   HttpMethod.GET,
                   getTokenAndMakeHeader(),
                   Object.class);
           List<Map<String,Object>> result = (List<Map<String, Object>>) response.getBody();
           return getChargesList(result);
       } catch (ResourceAccessException error) {
           showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
       } catch ( HttpClientErrorException.Forbidden exception) {
           showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
       }
        return Collections.emptyList();
    }
    public static List<Charges> findByDateAndName(String name, Timestamp timeFrom, Timestamp timeTo){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urll = URL_WAREHOUSE + "/item-name/" + name + "/timeFrom/" + timeFrom.toString() + "/timeTo/" + timeTo.toString();
            ResponseEntity<Object> response = restTemplate.exchange(urll,
                    HttpMethod.GET,
                    getTokenAndMakeHeader(),
                    Object.class);
            List<Map<String,Object>> result = (List<Map<String, Object>>) response.getBody();
            return getChargesList(result);
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch ( HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return Collections.emptyList();
    }
    public static boolean update(Integer amount, Timestamp time, String expenseItemName, Integer newAmount, Timestamp newTime){
       try {
           List<Charges> charges = ChargesManager.find(amount, time, expenseItemName);
           ExpenseItems expenseItems = ExpenseItemsManager.findByName(expenseItemName);
           ArrayList<Charges> results = new ArrayList<>();
           if (!charges.isEmpty() && (expenseItems != null)) {
               for (Charges charge : charges) {
                   Integer id = charge.getId();
                   Map<String, Integer> param = new HashMap<String, Integer>();
                   param.put("id", id);
                   Charges item = new Charges(newAmount, newTime, expenseItems);
                   RestTemplate restTemplate = new RestTemplate();
                   String urll = URL_WAREHOUSE + "/" + id.toString();
                   restTemplate.put(urll, getTokenAndMakeHeaderPost(item), param);
                   results.add(item);
               }
           }
           return !results.isEmpty();
       }  catch (ResourceAccessException error) {
           showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
       } catch ( HttpClientErrorException.Forbidden exception) {
           showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
       }
        return false;

    }
    public static boolean updateOnlyDate(Timestamp time, String expenseItemName, Timestamp newTime){
      try {
        List<Charges> charges = ChargesManager.findByDateAndName(expenseItemName,time, time);
        ExpenseItems expenseItems = ExpenseItemsManager.findByName(expenseItemName);
        ArrayList<Charges> results = new ArrayList<>();
        if (!charges.isEmpty() && (expenseItems != null)) {
            for (Charges charge : charges) {
                Integer id = charge.getId();
                Map<String, Integer> param = new HashMap<String, Integer>();
                param.put("id", id);
                Charges item = new Charges(newTime, expenseItems);
                RestTemplate restTemplate = new RestTemplate();
                String urll = URL_WAREHOUSE + "/" + id.toString();
                restTemplate.put(urll, getTokenAndMakeHeaderPost(item), param);
                results.add(item);
            }
        }
        return !results.isEmpty();

      }  catch (ResourceAccessException error) {
          showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
      } catch ( HttpClientErrorException.Forbidden exception) {
          showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
      }
        return false;
    }

    public static boolean delete(Integer amount,Timestamp time, String expenseItemName ){
        try {
        List<Charges> charge = ChargesManager.find( amount, time, expenseItemName);
        if (!charge.isEmpty()) {
            RestTemplate restTemplate = new RestTemplate();
            String urll = URL_WAREHOUSE + "/no-such/amount/" + amount + "/date/" + time.toString() + "/item-name/" + expenseItemName;
            ResponseEntity<Charges> result = restTemplate.exchange(urll, HttpMethod.DELETE, getTokenAndMakeHeader(), Charges.class);
            return true;
        }
        }
        catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        } catch ( HttpClientErrorException.Forbidden exception) {
            showErrorMessage(ErrorController.VALIDATION_TIME_IS_OVER);
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println(showAll());
//        Sys
//           findByDate(Timestamp.valueOf("2020-08-09 10:05:15"));
    update(89, Timestamp.valueOf("2020-05-11 10:05:15"), "lala", 98, Timestamp.valueOf("2009-09-23 21:01:12"));
//        System.out.println(findByExpenseItemName("lala"));
//        System.out.println(findByDateAndName(Timestamp.valueOf("2020-03-09 10:05:15"), "lala"));
    }
}


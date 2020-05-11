package curs.BD.cursBd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class ChargesManager {

    static final String URL_WAREHOUSE = "http://localhost:8080/charges";
    private static String changeDateAndTimeToViewFormat(String string) {
//        Timestamp timestamp = Timestamp.valueOf(string);
//        String bred = timestamp.toString();
//        System.out.println(bred);
//        Timestamp textFL;
//        String datee = " ";
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat();
//            Date parse = dateFormat.parse(string);
//            textFL = new Timestamp(parse.getTime());
//          datee = parse.toString();
//        } catch (ParseException e) {
//            System.out.println("aaaaaaa");
//        }
//        System.out.println(string);
//        String[] subdate;
//        String det = "T";
//        subdate = string.split(det);
//        char[] symbols = subdate[0].toCharArray();
//        Character lastSb = symbols[8];
//        if (lastSb.equals("1")) {
//
//        }
//        System.out.println(lastSb);
//        String date = subdate[0].replace("-", ".");
//
//        String[] subTime;
//        String det2 = "\\.";
//        subTime = subdate[1].split(det2);
//        String time = subTime[0];
//        return date + " " + time;
        return null;
    }

    private static List<Charges> getChargesList (List<Map<String,Object>> result) {
        List<Charges> charges = new ArrayList<>();
        if (result != null) {
            for (Map<String,Object> res : result) {
                LinkedHashMap<String, Object> la = (LinkedHashMap<String, Object>) res.get("expenseItemsId");
                ExpenseItems expenseItem = new ExpenseItems((Integer) la.get("id"),(String) la.get("name"));
                String dateT = (String) res.get("chargeDate");
                    charges.add( new Charges((Integer) res.get("id"), (Integer) res.get("amount"), dateT, expenseItem));
//                }
//                charges.add( new Charges((Integer) res.get("id"), (Integer) res.get("amount"), (String) res.get("chargeDate"), expenseItem));
            }
        }
        return charges;
    }

    public static List<Charges> showAll(){
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String,Object>> result = restTemplate.getForObject(URL_WAREHOUSE + "/all-items", List.class);
        return getChargesList(result);
    }

    public static ArrayList<Charges> showByItemId(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        String idstr = "/by-expense-item/" + id.toString();
        ArrayList<Charges> charges = restTemplate.getForObject(URL_WAREHOUSE + idstr, ArrayList.class);
        return charges;
    }
    public static List<Charges> findByExpenseItemName(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/expense-item-name/" + name;
        List<Map<String,Object>> result = restTemplate.getForObject(urll, List.class);
        return getChargesList(result);
    }

    public static boolean add(Integer amount,Timestamp time, String expenseItemName ) {
        ExpenseItems item = ExpenseItemsManager.findByName(expenseItemName);
        if (item != null) {
            Integer itemId = item.getId();
            Charges charges = new Charges(amount,time, item);
            RestTemplate restTemplate = new RestTemplate();
            Charges res = restTemplate.postForObject(URL_WAREHOUSE + "/new", charges, Charges.class);
            return true;
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
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/date-filter/dateFrom/" + timeFrom.toString() + "/dateTo/" + timeTo.toString();
        List<Map<String,Object>> result = restTemplate.getForObject(urll, List.class);
        return getChargesList(result);

    }

    public static List<Charges> find(Integer amount, Timestamp time, String expenseItemName ){
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/by-all-info/amount/" + amount + "/date/" + time.toString() + "/item-name/" + expenseItemName;
        List<Map<String,Object>> result = restTemplate.getForObject(urll, List.class);
        return getChargesList(result);
    }
    public static List<Charges> findByDateAndName(String name, Timestamp timeFrom, Timestamp timeTo){
        RestTemplate restTemplate = new RestTemplate();
        String urll = URL_WAREHOUSE + "/item-name/" + name + "/timeFrom/" + timeFrom.toString() + "/timeTo/" + timeTo.toString();
        List<Map<String,Object>> result = restTemplate.getForObject(urll, List.class);
        return getChargesList(result);
    }
    public static boolean update(Integer amount, Timestamp time, String expenseItemName, Integer newAmount, Timestamp newTime){
        List<Charges> charges = ChargesManager.find( amount, time, expenseItemName);
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
//                Charges res = restTemplate.getForObject(urll, Charges.class);
//                if (res != null) {
                    restTemplate.put(urll, item, param);
                    results.add(item);
//                }
            }
        }
        return !results.isEmpty();
    }

    public static boolean delete(Integer amount,Timestamp time, String expenseItemName ){
        List<Charges> charge = ChargesManager.find( amount, time, expenseItemName);
        if (!charge.isEmpty()) {
            RestTemplate restTemplate = new RestTemplate();
            String urll = URL_WAREHOUSE + "/no-such/amount/" + amount + "/date/" + time.toString() + "/item-name/" + expenseItemName;
            restTemplate.delete(urll);
            return true;
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


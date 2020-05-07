package curs.BD.cursBd;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class GreetingClient {
    static final String URL_EMPLOYEES = "http://localhost:8080/messages";

    public static List<CientMessage> showAll(){

        RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method and default Headers.
        List<Map<String, Object>> result = restTemplate.getForObject(URL_EMPLOYEES, List.class);
        List<CientMessage> clMessages = new ArrayList<>();
        if (result != null) {
            for (Map<String, Object> res : result) {
                clMessages.add( new CientMessage(res.get("text").toString(), res.get("tag").toString()));
            }
        } else {
            System.out.println("no dara");
        }
        return clMessages;
    }

    public static void show(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String strId = id.toString();
        String urll = URL_EMPLOYEES + "/" + strId;
       CientMessage message = restTemplate.getForObject(urll, CientMessage.class);
        if (message != null) {
                System.out.println(message.getTag());
            }

    }
    public static void add(String tag, String text) {
        CientMessage message = new CientMessage(text, tag);
        RestTemplate restTemplate = new RestTemplate();
       CientMessage res = restTemplate.postForObject(URL_EMPLOYEES, message, CientMessage.class);
    }
    public static void delete(Long id){
        RestTemplate restTemplate = new RestTemplate();
        String strId = id.toString();
        String urll = URL_EMPLOYEES + "/" + strId;
        restTemplate.delete(urll);
    }
    public static void update(Long id, String text, String tag) {
        Map<String, Long> param = new HashMap<String, Long>();
        param.put("id", id);
        CientMessage message = new CientMessage(text, tag);
        String strId = id.toString();
        String urll = URL_EMPLOYEES + "/" + strId;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(urll, message, param);
    }

    public static void main(String[] args) {
        showAll();
        show((long) 7);
        add("yo9","i96");
//        delete((long)7);
        showAll();
        update((long)3, "hi", "new");
        showAll();
    }



}

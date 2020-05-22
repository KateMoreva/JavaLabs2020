package curs.BD.cursBd.managers;

import curs.BD.cursBd.model.Token;
import curs.BD.cursBd.ui.ErrorController;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class MainManager extends ErrorController {
    static final String URL_REGISTRATION = "http://localhost:8080";
    public static String workingToken;

    public static String login(String name, String password) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> map = new HashMap<>();
            map.put("username", name);
            map.put("password", password);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(map);
            try {
                ResponseEntity<Token> responseEntity = restTemplate.exchange(URL_REGISTRATION + "/auth/login", HttpMethod.POST, entity, Token.class);
                String token = responseEntity.getBody().getToken();
                workingToken = "Bearer_" + token;
                return "Bearer_" + token;
            } catch (HttpClientErrorException.Forbidden | HttpServerErrorException.InternalServerError e) {
                showErrorMessage(ErrorController.AUTHORIZATION_ERROR);
            }
        } catch (ResourceAccessException error) {
            showErrorMessage(ErrorController.SERVER_OFFLINE_ERROR);
        }
        return "error";

    }

    protected static HttpEntity<String> getTokenAndMakeHeader() {
        String token = MainManager.workingToken;
        HttpEntity<String> entity = MainManager.makeRequestHeader(token);
        return entity;
    }

    protected static HttpHeaders makeHeaderForPost() {
        String token = MainManager.workingToken;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return headers;
    }


    private static HttpEntity<String> makeRequestHeader(String token) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", token);
        final HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
        return entity;
    }

}

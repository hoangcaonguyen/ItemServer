package com.example.itemserver.service;

import com.example.itemserver.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthorizationService {
    private final RestTemplate restTemplate;

    public boolean authorization(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String URL = "https://userserver-production.up.railway.app/account/check-exist";
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());
        int code = Integer.parseInt(jsonObject.get("code").toString());
        return code == 200;
    }
}

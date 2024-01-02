package com.picpay.simple.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalNotificationservice {

    @Autowired
    private RestTemplate restTemplate;

    public boolean sendNotificationservice(String email, String message){
        ResponseEntity<String> notificationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", String.class);
        if(notificationResponse.getStatusCode() == HttpStatus.OK){
         String responseBody = notificationResponse.getBody();
            if(responseBody != null && responseBody.contains("\"message\": \"true\"")){
                return true;
                }
            }
            return false;
        }
}

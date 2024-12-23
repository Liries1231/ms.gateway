package com.example.ms.gateway.service;

import com.example.ms.gateway.entity.Post;
import com.example.ms.gateway.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceProxy {

    private final RestTemplate restTemplate;


    @Value("${user.service.url}")
    private String userServiceUrl;

    public UserServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public ResponseEntity<String> createUser(@RequestBody User user) {
        String url = userServiceUrl + "/api/v1/user_pass";
        return restTemplate.postForEntity(url, user , String.class);
    }
}


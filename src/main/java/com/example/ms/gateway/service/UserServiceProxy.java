package com.example.ms.gateway.service;

import com.example.ms.gateway.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
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
    public ResponseEntity<String> login(@RequestBody User user) {
        String url = userServiceUrl + "/api/v1/auth/login";
        return restTemplate.postForEntity(url, user , String.class);
    }
    public ResponseEntity<String> register(@RequestBody User user) {
        String url = userServiceUrl + "/api/v1/auth/register";
        return restTemplate.postForEntity(url, user , String.class);
    }
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        String url = userServiceUrl + "/api/v1/user_pass/{id}";

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class, id);

            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Error retrieving user data");
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error occurred while retrieving user data for ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body("Error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving user data for ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }




}


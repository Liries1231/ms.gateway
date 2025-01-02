package com.example.ms.gateway.service;

import com.example.ms.gateway.dto.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

@Service
public class PostServiceProxy {

    private final RestTemplate restTemplate;

    @Value("${post.service.url}")
    private String postServiceUrl;

    public PostServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getPost() {
        String url = postServiceUrl + "/api/v1/post";
        return restTemplate.getForEntity(url, String.class);
    }

    public ResponseEntity<String> createPost(@RequestBody Post post, @RequestHeader("UserData") String userId) {
        String url = postServiceUrl + "/api/v1/post";

        // Создаём заголовки
        HttpHeaders headers = new HttpHeaders();
        headers.set("UserData", userId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Post> requestEntity = new HttpEntity<>(post, headers);

        return restTemplate.postForEntity(url, requestEntity, String.class);
    }
}


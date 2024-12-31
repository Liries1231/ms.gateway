package com.example.ms.gateway.service;

import com.example.ms.gateway.dto.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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

    public ResponseEntity<String> createPost(@RequestBody Post post) {
        String url = postServiceUrl + "/api/v1/post";
        return restTemplate.postForEntity(url, post , String.class);
    }
}

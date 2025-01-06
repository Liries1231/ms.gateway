package com.example.ms.gateway.service;

import com.example.ms.gateway.dto.Comment;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentServiceProxy {

    private final RestTemplate restTemplate;


    @Value("${comment.service.url}")
    private String commentServiceUrl;

    public CommentServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Comment sendComment(Comment comment, String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Id", userId);

        HttpEntity<Comment> requestEntity = new HttpEntity<>(comment, headers);

        return restTemplate.postForObject(commentServiceUrl + "/comments",requestEntity,
                Comment.class);
    }

}

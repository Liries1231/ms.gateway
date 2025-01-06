package com.example.ms.gateway.сontroller;

import com.example.ms.gateway.dto.Comment;
import com.example.ms.gateway.service.CommentServiceProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CommentProxyController {

    private final CommentServiceProxy commentServiceProxy;

    public CommentProxyController(CommentServiceProxy commentServiceProxy) {
        this.commentServiceProxy = commentServiceProxy;
    }
    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody Comment comment, @RequestHeader("X-User-Id") String userId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentServiceProxy.sendComment(comment, userId));
    }
}

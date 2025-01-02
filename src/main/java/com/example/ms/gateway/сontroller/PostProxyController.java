package com.example.ms.gateway.сontroller;
import com.example.ms.gateway.dto.Post;
import com.example.ms.gateway.service.PostServiceProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PostProxyController {

    private final PostServiceProxy postServiceProxy;

    public PostProxyController(PostServiceProxy postServiceProxy) {
        this.postServiceProxy = postServiceProxy;
    }

    @GetMapping("/post")
    public ResponseEntity<?> proxyToPostService() {
        return postServiceProxy.getPost();
    }

    @PostMapping("/post")
    public ResponseEntity<?> proxyPostRequest(@RequestBody Post post,String userId) {

        return postServiceProxy.createPost(post,userId);
    }
}


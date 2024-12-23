package com.example.ms.gateway.GwController;
import com.example.ms.gateway.entity.Post;
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
    public ResponseEntity<?> proxyPostRequest(@RequestBody Post post) {
        return postServiceProxy.createPost(post);
    }
}


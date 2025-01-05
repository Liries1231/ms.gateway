package com.example.ms.gateway.сontroller;
import com.example.ms.gateway.dto.Post;
import com.example.ms.gateway.service.PostServiceProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PostProxyController {

    private final PostServiceProxy postServiceProxy;

    public PostProxyController(PostServiceProxy postServiceProxy) {
        this.postServiceProxy = postServiceProxy;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> proxyToPostService(@PathVariable Long id) {
        return postServiceProxy.getPost(id);
    }
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody Post post,
                                             @RequestHeader("UserData") String userId) {
     postServiceProxy.createPost(post, userId);

     return ResponseEntity.status(HttpStatus.CREATED).body(post.toString());
    }


}

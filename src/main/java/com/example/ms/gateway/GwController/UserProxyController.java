package com.example.ms.gateway.GwController;

import com.example.ms.gateway.dto.User;
import com.example.ms.gateway.service.UserServiceProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserProxyController {

    private final UserServiceProxy userServiceProxy;
    public UserProxyController(UserServiceProxy userServiceProxy) {
        this.userServiceProxy = userServiceProxy;
    }
    @PostMapping("/user_pass")
    public ResponseEntity<?> proxyUserRequest(@RequestBody User user) {
        return userServiceProxy.createUser(user);
    }

}

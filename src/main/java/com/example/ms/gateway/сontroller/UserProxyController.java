package com.example.ms.gateway.сontroller;

import com.example.ms.gateway.dto.User;
import com.example.ms.gateway.service.UserServiceProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseEntity<String>> createUserWithProfile(@RequestBody User userCreationDto) {

        // Используем proxy для создания пользователя с профилем
        ResponseEntity<String> userProfile = userServiceProxy.register(userCreationDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userProfile);
    }
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        ResponseEntity<String> userProfile = userServiceProxy.login(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userProfile);
    }
    @GetMapping("/user_pass/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        return userServiceProxy.getUser(id);
    }

}





package com.example.ms.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String login;
    private Long id;
    private String password;
    private String birthYear;
    private String about;
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + login + "'}";
    }
}

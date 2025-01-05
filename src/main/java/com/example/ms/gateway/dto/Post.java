package com.example.ms.gateway.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {


    private String title;
    private String description;
    private Long userId;
    @Override
    public String toString() {
        return "PostDto{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}


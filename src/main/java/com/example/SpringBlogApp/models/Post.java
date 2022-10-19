package com.example.SpringBlogApp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    private String id;

    private String title;

    private String body;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull
    private Account account;

    private String imageId;
}

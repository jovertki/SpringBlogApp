package com.example.docusketch_blog.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document
//@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Column(nullable = false)
    private String id;

    private String title;

//    @Column(columnDefinition = "TEXT")
    private String body;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull
//    @ManyToOne
//    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;
}

package com.example.docusketch_blog.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Account {

    // TODO: add proper validation

    @Id
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private List<Post> posts;

    private Set<Authority> authorities = new HashSet<>();

    @Override
    public String toString(){
        return "Account{" +
                "email=" + getEmail() +
                ", firstname=" + getFirstName() +
                ", lastname=" + getLastName() +
                ", authorities=" + getAuthorities() +
                "}";
    }
}

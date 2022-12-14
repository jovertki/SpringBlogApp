package com.example.SpringBlogApp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @Email
    private String email;

    @Size(min = 8)
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

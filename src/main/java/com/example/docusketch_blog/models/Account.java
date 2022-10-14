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

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

//    @OneToMany(mappedBy = "account")
    private List<Post> posts;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "account_authority",
//    joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
//    inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
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

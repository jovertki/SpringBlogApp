package com.example.SpringBlogApp.repositories;

import com.example.SpringBlogApp.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findOneByEmailIgnoreCase(String email);
}

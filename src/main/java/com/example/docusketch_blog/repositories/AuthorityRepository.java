package com.example.docusketch_blog.repositories;

import com.example.docusketch_blog.models.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends MongoRepository<Authority, String> {
}

package com.example.SpringBlogApp.repositories;

import com.example.SpringBlogApp.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByTitleIsLikeIgnoreCase(String name, Pageable pageable);
}

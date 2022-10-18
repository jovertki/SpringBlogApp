package com.example.docusketch_blog.repositories;

import com.example.docusketch_blog.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findPostsByTitleContainingIgnoreCase(String str);
}

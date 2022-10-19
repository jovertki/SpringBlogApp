package com.example.SpringBlogApp.services;

import com.example.SpringBlogApp.models.Post;
import com.example.SpringBlogApp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Optional<Post> getById(String id) {
        return postRepository.findById(id);
    }

    public Page<Post> getAllPageable(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public Page<Post> getAllSimilarName(String name, Pageable pageable) {
        return postRepository.findByTitleIsLikeIgnoreCase(name, pageable);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }
}

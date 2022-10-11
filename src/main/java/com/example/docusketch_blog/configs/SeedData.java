package com.example.docusketch_blog.configs;

import com.example.docusketch_blog.models.Post;
import com.example.docusketch_blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("LOLKEK1");
        List<Post> posts = postService.getAll();
        if (posts.isEmpty()) {
            Post post1 = new Post();
            post1.setTitle("Post1 title");
            post1.setBody("Post1 body");

            Post post2 = new Post();
            post2.setTitle("Post2 title");
            post2.setBody("Post2 body");

            postService.save(post1);
            postService.save(post2);
            System.out.println("LOLKEK");
        }
    }
}

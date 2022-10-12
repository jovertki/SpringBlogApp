package com.example.docusketch_blog.configs;

import com.example.docusketch_blog.models.Account;
import com.example.docusketch_blog.models.Post;
import com.example.docusketch_blog.services.AccountService;
import com.example.docusketch_blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {

        List<Post> posts = postService.getAll();
        if (posts.isEmpty()) {
            Account account1 = new Account();
            Account account2 = new Account();

            account1.setFirstName("user");
            account1.setLastName("user");
            account1.setEmail("user.user@mail.com");
            account1.setPassword("password");

            account2.setFirstName("admin");
            account2.setLastName("admin");
            account2.setEmail("admin.admin@mail.com");
            account2.setPassword("password");

            accountService.save(account1);
            accountService.save(account2);



            Post post1 = new Post();
            post1.setTitle("Post1 title");
            post1.setBody("Post1 body");
            post1.setAccount(account1);

            Post post2 = new Post();
            post2.setTitle("Post2 title");
            post2.setBody("Post2 body");
            post2.setAccount(account2);

            postService.save(post1);
            postService.save(post2);

        }
    }
}

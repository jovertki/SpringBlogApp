package com.example.docusketch_blog.controllers;

import com.example.docusketch_blog.models.Account;
import com.example.docusketch_blog.models.Post;
import com.example.docusketch_blog.services.AccountService;
import com.example.docusketch_blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model){


        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post";
        } else {
            return "404";
        }
    }

    @GetMapping("/posts/new")
    public String getNewPostPage(Model model){
        Post post = new Post();
        model.addAttribute("post", post);
        return "post_new";
    }


    @PostMapping("/posts/new")
    public String saveNewPost(@ModelAttribute Post post) {
        Account account = accountService.getById(1L).get();
        post.setAccount(account);


        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }
}

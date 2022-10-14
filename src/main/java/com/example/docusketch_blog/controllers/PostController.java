package com.example.docusketch_blog.controllers;

import com.example.docusketch_blog.models.Account;
import com.example.docusketch_blog.models.Post;
import com.example.docusketch_blog.services.AccountService;
import com.example.docusketch_blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable String id, Model model){


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
    public String saveNewPost(@ModelAttribute Post post, @AuthenticationPrincipal UserDetails auth) {
        Optional<Account> optionalAccount = accountService.getByEmail(auth.getUsername());
        if (optionalAccount.isEmpty()){
            return "404";
        }
        Account account = optionalAccount.get();
        post.setAccount(account);
        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable String id, Model model) {
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isEmpty()){
            return "404";
        }
        // TODO:check if the same user. Send 403 if not
        Post post = optionalPost.get();

        model.addAttribute("post", post);
        return "post_edit";
    }

    @PostMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable String id, @ModelAttribute Post post) {
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isEmpty()){
            return "404";
        }
        post.setUpdatedAt(LocalDateTime.now());
        postService.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deletePost(@PathVariable String id) {
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isEmpty()){
            return "404";
        }
        Post post = optionalPost.get();
        postService.delete(post);

        return "successfully_deleted";
    }
}

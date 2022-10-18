package com.example.docusketch_blog.controllers;

import com.example.docusketch_blog.models.Post;
import com.example.docusketch_blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String home(Model model, @RequestParam(value="q", required = false) String q) {
        List<Post> posts;
        if (q == null) {
            posts = postService.getAll();
        } else {
            posts = postService.getAllSimilarName(q);
        }
        model.addAttribute("posts", posts);
        return "home";
    }


}

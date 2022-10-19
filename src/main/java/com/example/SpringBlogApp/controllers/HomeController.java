package com.example.SpringBlogApp.controllers;

import com.example.SpringBlogApp.models.Post;
import com.example.SpringBlogApp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;


    @GetMapping("/")
    public String redirectHome(){
        return "redirect:/feed/1";
    }

    @GetMapping("/feed/{pageNumber}")
    public String home(Model model,
                       @RequestParam(value="q", required = false) String q,
                       @PathVariable int pageNumber) {
        List<Post> posts;
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, Sort.by("createdAt").descending());
        int totalPages;
        Page<Post> page;
        if (q == null) {
            page = postService.getAllPageable(pageable);
        } else {
            page = postService.getAllSimilarName(q, pageable);
        }
        posts = page.getContent();
        totalPages = page.getTotalPages();
        model.addAttribute("posts", posts);
        if (pageNumber > 1) {
            model.addAttribute("prevPage", pageNumber - 1);
        }
        if (pageNumber < totalPages) {
            model.addAttribute("nextPage", pageNumber + 1);
        }
        model.addAttribute("currentPage", pageNumber);
        return "home";
    }


}

package com.example.SpringBlogApp.controllers;

import com.example.SpringBlogApp.models.Post;
import com.example.SpringBlogApp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
@Controller
public class HomeController {

    @Autowired
    private PostService postService;


    @GetMapping("/")
    public String redirectHome(){
        return "redirect:/feed/1";
    }

    @GetMapping("/feed/{pageNumberString}")
    public String home(Model model,
                       @RequestParam(value="q", required = false) String q,
                       @PathVariable String pageNumberString) {
        int pageNumber;
        try {
            pageNumber = Integer.parseInt(pageNumberString);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, Sort.by("createdAt").descending());
        Page<Post> page;
        if (q == null) {
            page = postService.getAllPageable(pageable);
        } else {
            page = postService.getAllSimilarName(q, pageable);
        }
        int totalPages = page.getTotalPages();
        if (pageNumber > totalPages && page.getTotalElements() != 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("posts", page.getContent());
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

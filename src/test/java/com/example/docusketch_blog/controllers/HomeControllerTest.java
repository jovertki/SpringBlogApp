package com.example.docusketch_blog.controllers;

import com.example.docusketch_blog.configs.SecurityConfig;
import com.example.docusketch_blog.models.Account;
import com.example.docusketch_blog.models.Authority;
import com.example.docusketch_blog.models.Post;
import com.example.docusketch_blog.services.AccountService;
import com.example.docusketch_blog.services.ImageService;
import com.example.docusketch_blog.services.PostService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
@Import(SecurityConfig.class)
class HomeControllerTest {

    @MockBean
    private PostService postService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private ImageService imageService;
    @Autowired
    private MockMvc mockMvc;
    private static Post post;

    private static Account account;
    @BeforeAll
    public static void init(){

        account = new Account();
        account.setFirstName("TestFirstName");
        account.setLastName("TestLastName");
        account.setEmail("test.user@mail.com");
        account.setPassword("testPassword");
        account.setAuthorities(Set.of(new Authority()));

        post = new Post();
        post.setId("1");
        post.setBody("Test Body");
        post.setTitle("Test Title");
        post.setCreatedAt(LocalDateTime.now());
        post.setAccount(account);

    }





    @Test
    void shouldAllowAccessForAnonymousUser() throws Exception {
        Pageable pageable = PageRequest.of( 0, 5, Sort.by("createdAt").descending());

        Mockito.when(postService.getAllPageable(pageable)).thenReturn(new PageImpl<>(List.of(post)));
        this.mockMvc
                .perform(get("/feed/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("posts"));
    }

    @Test
    void shouldShowRegistrationButtonToUnauthorizedUsers() throws Exception {

        Pageable pageable = PageRequest.of( 0, 5, Sort.by("createdAt").descending());

        Mockito.when(postService.getAllPageable(pageable)).thenReturn(new PageImpl<>(List.of(post)));
        this.mockMvc
                .perform(get("/feed/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content()
                        .string(containsString("Register")));
    }
    @Test
    void shouldShowLoginButtonToUnauthorizedUsers() throws Exception {

        Pageable pageable = PageRequest.of( 0, 5, Sort.by("createdAt").descending());

        Mockito.when(postService.getAllPageable(pageable)).thenReturn(new PageImpl<>(List.of(post)));
        this.mockMvc
                .perform(get("/feed/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content()
                        .string(containsString("Login")));
    }

    @Test
    @WithMockUser(username="testuser")
    void shouldShowHelloToAuthorizedUsers() throws Exception {

        Pageable pageable = PageRequest.of( 0, 5, Sort.by("createdAt").descending());

        Mockito.when(postService.getAllPageable(pageable)).thenReturn(new PageImpl<>(List.of(post)));
        this.mockMvc
                .perform(get("/feed/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content()
                        .string(containsString("Logged in as <span>testuser</span>")))
                .andExpect(content()
                        .string(containsString("Logout")));
    }

    @Test
    void shouldFind3posts() throws Exception {

        Post post2 = new Post();
        post2.setId("1");
        post2.setBody("Test Body");
        post2.setTitle("Test Title1");
        post2.setCreatedAt(post.getCreatedAt().plusDays(1));
        post2.setAccount(account);

        Post post3 = new Post();
        post3.setId("1");
        post3.setBody("Test Body");
        post3.setTitle("Test Title2");
        post3.setCreatedAt(post.getCreatedAt().plusDays(2));
        post3.setAccount(account);

        Mockito.when(postService.save(post)).thenReturn(post);
        Mockito.when(postService.save(post2)).thenReturn(post2);
        Mockito.when(postService.save(post3)).thenReturn(post3);
        List<Post> list = new ArrayList<>();
        list.add(post3);
        list.add(post2);
        list.add(post);
        Page<Post> page = new PageImpl<>(list);
        Pageable pageable = PageRequest.of(0,5, Sort.by("createdAt").descending());
        Mockito.when(postService.getAllSimilarName("title",pageable)).thenReturn(page);
        this.mockMvc
                .perform(get("/feed/1?q=title"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("posts", List.of(post3, post2, post)));
    }

}
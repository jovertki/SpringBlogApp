package com.example.docusketch_blog.controllers;

import com.example.docusketch_blog.configs.SecurityConfig;
import com.example.docusketch_blog.models.Account;
import com.example.docusketch_blog.models.Authority;
import com.example.docusketch_blog.models.Post;
import com.example.docusketch_blog.services.AccountService;
import com.example.docusketch_blog.services.PostService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
    @Autowired
    private MockMvc mockMvc;
    private static Post post;

    @BeforeAll
    public static void init(){


        Account account = new Account();
        account.setFirstName("TestFirstName");
        account.setLastName("TestLastName");
        account.setEmail("test.user@mail.com");
        account.setPassword("testPassword");
        account.setAuthorities(Set.of(new Authority()));

        post = new Post();
        post.setId("1");
        post.setBody("Test Body");
        post.setTitle("Test Title");
        post.setAccount(account);
    }





    @Test
    void shouldAllowAccessForAnonymousUser() throws Exception {

        Mockito.when(postService.getAll()).thenReturn(List.of(post));
        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("posts"));
    }

    @Test
    void shouldShowRegistrationButtonToUnauthorizedUsers() throws Exception {

        Mockito.when(postService.getAll()).thenReturn(List.of(post));
        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content()
                        .string(containsString("<a href=\"/register\">Register</a>")));
    }
    @Test
    void shouldShowLoginButtonToUnauthorizedUsers() throws Exception {

        Mockito.when(postService.getAll()).thenReturn(List.of(post));
        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content()
                        .string(containsString("<a href=\"/login\">Login</a>")));
    }

    @Test
    @WithMockUser(username="testuser")
    void shouldShowHelloToAuthorizedUsers() throws Exception {

        Mockito.when(postService.getAll()).thenReturn(List.of(post));
        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content()
                        .string(containsString("Logged in as <span>testuser</span>")))
                .andExpect(content()
                        .string(containsString("<button type=\"submit\">Logout</button>")));
    }


}
package com.example.docusketch_blog.controllers;

import com.example.docusketch_blog.configs.SecurityConfig;
import com.example.docusketch_blog.models.Account;
import com.example.docusketch_blog.models.Authority;
import com.example.docusketch_blog.services.AccountService;
import com.example.docusketch_blog.services.ImageService;
import com.example.docusketch_blog.services.PostService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
@Import(SecurityConfig.class)
class RegisterControllerTest {
    @MockBean
    private PostService postService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private ImageService imageService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRegisterPage() throws Exception {
        this.mockMvc
                .perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    void registerAccount() throws Exception {
        Account account = new Account();
        account.setFirstName("TestFirstName");
        account.setLastName("TestLastName");
        account.setEmail("test.user@mail.com");
        account.setPassword("testPassword");
        account.setAuthorities(Set.of(new Authority()));
        this.mockMvc
                .perform(post("/register").flashAttr("account", account))
                .andExpect(status().is3xxRedirection())
                .andExpect((redirectedUrl("/")));
    }
}
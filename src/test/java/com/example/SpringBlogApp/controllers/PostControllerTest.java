package com.example.SpringBlogApp.controllers;

import com.example.SpringBlogApp.configs.SecurityConfig;
import com.example.SpringBlogApp.models.Account;
import com.example.SpringBlogApp.models.Authority;
import com.example.SpringBlogApp.models.Post;
import com.example.SpringBlogApp.services.AccountService;
import com.example.SpringBlogApp.services.ImageService;
import com.example.SpringBlogApp.services.PostService;
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

import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
@Import(SecurityConfig.class)
class PostControllerTest {

        @MockBean
        private PostService postService;

        @MockBean
        private AccountService accountService;
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private ImageService imageService;
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
                post.setAccount(account);
        }

        @Test
        void shouldReturnPostWithId() throws Exception {

                Mockito.when(accountService
                        .getByEmail("testt.user@mail.com"))
                        .thenReturn(Optional.of(account));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(get("/posts/1"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("post"))
                        .andExpect(model().attributeExists("post"))
                        .andExpect(model().attribute("post", post));
        }

        @Test
        void shouldReturn404() throws Exception {
                Mockito.when(accountService
                                .getByEmail("testt.user@mail.com"))
                        .thenReturn(Optional.of(account));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(get("/posts/2"))
                        .andExpect(status().isNotFound());
        }

        @Test
        void shouldReturnPostNewPageUnauthorized() throws Exception {
                Mockito.when(accountService
                                .getByEmail("testt.user@mail.com"))
                        .thenReturn(Optional.of(account));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(get("/posts/new"))
                        .andExpect(status().isUnauthorized());
        }
        @WithMockUser
        @Test
        void shouldReturnPostNewPage() throws Exception {
                Mockito.when(accountService
                                .getByEmail("testt.user@mail.com"))
                        .thenReturn(Optional.of(account));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(get("/posts/new"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("post_edit"))
                        .andExpect(model().attributeExists("post"))
                        .andExpect(model().attribute("action", "create"));
        }

        @WithMockUser(username = "test.user@mail.com")
        @Test
        void shouldRedirectToNewPost() throws Exception {

                Post postSaved = new Post();
                postSaved.setId("1");
                postSaved.setBody("Test Body");
                postSaved.setTitle("Test Title");
                postSaved.setAccount(account);

                Mockito.when(postService.save(post)).thenReturn(postSaved);
                Mockito.when(accountService
                                .getByEmail("test.user@mail.com"))
                        .thenReturn(Optional.of(account));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(post("/posts/new").flashAttr("post", post))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/posts/1"));
        }


        @WithMockUser(username = "test.user2@mail.com")
        @Test
        void shouldReturnForbiddenToEditOthersPosts() throws Exception {

                Account account2 = new Account();
                account2.setFirstName("TestFirstName");
                account2.setLastName("TestLastName");
                account2.setEmail("test.user2@mail.com");
                account2.setPassword("testPassword");
                account2.setAuthorities(Set.of(new Authority()));

                Mockito.when(accountService
                                .getByEmail("test.user2@mail.com"))
                        .thenReturn(Optional.of(account2));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(get("/posts/1/edit").flashAttr("post", post))
                        .andExpect(status().isForbidden());
        }


        @WithMockUser(username = "test.user@mail.com")
        @Test
        void shouldReturnPostEdit() throws Exception {
                Mockito.when(accountService
                                .getByEmail("test.user@mail.com"))
                        .thenReturn(Optional.of(account));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(get("/posts/1/edit"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("post_edit"))
                        .andExpect(model().attributeExists("post"));
        }

        @Test
        void shouldReturn401Edit() throws Exception {
                Mockito.when(accountService
                                .getByEmail("test.user@mail.com"))
                        .thenReturn(Optional.of(account));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(get("/posts/1/edit"))
                        .andExpect(status().isUnauthorized());
        }

        @Test
        void shouldReturn401Update() throws Exception {
                Mockito.when(accountService
                                .getByEmail("test.user@mail.com"))
                        .thenReturn(Optional.of(account));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(get("/posts/1/update"))
                        .andExpect(status().isUnauthorized());
        }

        @WithMockUser(username = "test.user2@mail.com")
        @Test
        void shouldReturn403Update() throws Exception {
                Account account2 = new Account();
                account2.setFirstName("TestFirstName");
                account2.setLastName("TestLastName");
                account2.setEmail("test.user2@mail.com");
                account2.setPassword("testPassword");
                account2.setAuthorities(Set.of(new Authority()));


                Mockito.when(accountService
                                .getByEmail("test.user2@mail.com"))
                        .thenReturn(Optional.of(account2));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(post("/posts/1").flashAttr("post", post))
                        .andExpect(status().isForbidden());
        }

        @WithMockUser
        @Test
        void shouldReturn403delete() throws Exception {
                Mockito.when(accountService
                                .getByEmail("test.user@mail.com"))
                        .thenReturn(Optional.of(account));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(get("/posts/1/delete"))
                        .andExpect(status().isForbidden());
        }

        @WithMockUser(authorities = {"ROLE_ADMIN"})
        @Test
        void shouldReturnOkdelete() throws Exception {
                Mockito.when(accountService
                                .getByEmail("test.user@mail.com"))
                        .thenReturn(Optional.of(account));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(get("/posts/1/delete"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("successfully_deleted"));
        }

        @Test
        void shouldReturn401delete() throws Exception {
                Mockito.when(accountService
                                .getByEmail("test.user@mail.com"))
                        .thenReturn(Optional.of(account));
                Mockito.when(postService.getById("1")).thenReturn(Optional.of(post));
                this.mockMvc
                        .perform(get("/posts/1/delete"))
                        .andExpect(status().isUnauthorized());
        }
}
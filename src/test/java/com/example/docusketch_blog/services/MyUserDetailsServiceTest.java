package com.example.docusketch_blog.services;

import com.example.docusketch_blog.controllers.HomeController;
import com.example.docusketch_blog.controllers.PostController;
import com.example.docusketch_blog.models.Account;
import com.example.docusketch_blog.models.Authority;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@WebMvcTest
class MyUserDetailsServiceTest {
    @MockBean
    private AccountService accountService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostController postController;

    @MockBean
    private HomeController homeController;
    private final MyUserDetailsService testService = new MyUserDetailsService();
    @Test
    void loadUserByUsernameNotFound() {
        Account account = new Account();
        account.setFirstName("TestFirstName");
        account.setLastName("TestLastName");
        account.setEmail("test.user@mail.com");
        account.setPassword("testPassword");
        account.setAuthorities(Set.of(new Authority()));

        testService.setAccountService(accountService);
        Mockito.when(accountService
                        .getByEmail("not_exists"))
                .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> testService.loadUserByUsername("not_exists"));
    }

    @Test
    void loadUserByUserFound() {
        Account account = new Account();
        account.setFirstName("TestFirstName");
        account.setLastName("TestLastName");
        account.setEmail("test.user@mail.com");
        account.setPassword("testPassword");
        Authority authority = new Authority();
        authority.setName("ROLE_TEST");
        account.setAuthorities(Set.of(authority));

        testService.setAccountService(accountService);
        Mockito.when(accountService
                        .getByEmail("test.user@mail.com"))
                .thenReturn(Optional.of(account));
        List<GrantedAuthority> grantedAuthorities = account
                .getAuthorities()
                .stream()
                .map(a -> new SimpleGrantedAuthority(a.getName()))
                .collect(Collectors.toList());


        assertEquals(new User(account.getEmail(), account.getPassword(), grantedAuthorities), testService.loadUserByUsername("test.user@mail.com"));
    }

}
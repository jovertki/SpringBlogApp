package com.example.docusketch_blog.services;

import com.example.docusketch_blog.models.Account;
import com.example.docusketch_blog.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Account> getById(String id){
        return accountRepository.findById(id);
    }

    public Optional<Account> getByEmail(String email){
        return accountRepository.findOneByEmailIgnoreCase(email);
    }

    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }
}

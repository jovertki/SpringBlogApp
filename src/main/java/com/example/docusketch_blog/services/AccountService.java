package com.example.docusketch_blog.services;

import com.example.docusketch_blog.models.Account;
import com.example.docusketch_blog.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> getById(Long id){
        return accountRepository.findById(id);
    }

    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }
}

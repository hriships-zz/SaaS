package com.appdirect.subscriptions.operations.services;

import com.appdirect.subscriptions.operations.domain.entities.Account;
import com.appdirect.subscriptions.operations.domain.repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    public Account create(Account account) {
        return accountRepo.save(account);
    }

    public Account findByAccountId(String accountId) {
        return accountRepo.findByAccountIdentifier(accountId);
    }

    public void updateAccount(Account account) {
    }
}

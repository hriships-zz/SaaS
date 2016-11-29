package com.appdirect.subscriptions.operations.services;

import com.appdirect.subscriptions.operations.domain.entities.Account;
import com.appdirect.subscriptions.operations.domain.repos.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hrishikeshshinde on 22/11/16.
 *
 * Manage Account related services
 */

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    /**
     * Create new account
     *
     * @param account
     * @return
     */
    public Account create(Account account) {
        return accountRepo.save(account);
    }

    /**
     * Get all accounts by ID
     *
     * @param accountId
     * @return
     */
    public Account findByAccountId(String accountId) {
        return accountRepo.findByAccountIdentifier(accountId);
    }

    /**
     * Updates the account
     *
     * @param account
     */
    public void updateAccount(Account account) {
        accountRepo.save(account);
    }
}

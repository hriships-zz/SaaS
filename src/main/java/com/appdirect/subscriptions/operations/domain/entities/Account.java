package com.appdirect.subscriptions.operations.domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */
@Entity
public class Account {
    @Id
    @GeneratedValue(generator = "accountId")
    @GenericGenerator(name = "accountId", strategy = "uuid")
    private String accountIdentifier;

    @Enumerated(EnumType.STRING)
    private AccountStatusEnum status;

    public Account() {
    }

    public Account(AccountStatusEnum status) {
        this.status = status;
    }

    public Account(String accountIdentifier, AccountStatusEnum status) {
        this.accountIdentifier = accountIdentifier;
        this.status = status;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public AccountStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AccountStatusEnum status) {
        this.status = status;
    }
}

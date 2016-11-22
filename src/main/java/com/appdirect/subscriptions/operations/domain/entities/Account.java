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
}

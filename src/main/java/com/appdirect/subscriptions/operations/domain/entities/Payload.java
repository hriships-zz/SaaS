package com.appdirect.subscriptions.operations.domain.entities;

import javax.persistence.*;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

@Entity
public class Payload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    private Company company;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    private Order order;

    @OneToOne
    private Account account;

    public Payload() {
    }

    public Long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public Order getOrder() {
        return order;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

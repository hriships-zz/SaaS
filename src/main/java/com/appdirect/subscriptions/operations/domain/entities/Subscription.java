package com.appdirect.subscriptions.operations.domain.entities;

import javax.persistence.*;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade= CascadeType.ALL, orphanRemoval = true)
    private MarketPlace marketplace;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    private Creator creator;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    private Payload payload;

    @Transient
    private String type;

    public Subscription() {
    }

    public Long getId() {
        return id;
    }

    public MarketPlace getMarketplace() {
        return marketplace;
    }

    public Creator getCreator() {
        return creator;
    }

    public Payload getPayload() {
        return payload;
    }

    public String getType() {
        return type;
    }
}

package com.appdirect.subscriptions.operations.domain.entities;

import javax.persistence.*;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

@Entity
@Table(name="subscription_order")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String editionCode;

    private String pricingDuration;

    @OneToOne(cascade= CascadeType.ALL, orphanRemoval = true)
    private Item item;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public String getEditionCode() {
        return editionCode;
    }

    public String getPricingDuration() {
        return pricingDuration;
    }

    public Item getItem() {
        return item;
    }
}

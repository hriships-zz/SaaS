package com.appdirect.subscriptions.operations.domain.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

@Entity
public class MarketPlace {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String baseUrl;

    private String partner;

    public MarketPlace() {
    }

    public Long getId() {
        return id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getPartner() {
        return partner;
    }
}

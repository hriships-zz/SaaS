package com.appdirect.subscriptions.operations.domain.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

@Entity
public class Creator {
    @Id
    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String openId;
    private String language;

    @OneToOne(cascade= CascadeType.ALL, orphanRemoval = true)
    private Address address;

    public Creator() {
    }

    public String getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getOpenId() {
        return openId;
    }

    public String getLanguage() {
        return language;
    }

    public Address getAddress() {
        return address;
    }
}

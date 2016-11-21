package com.appdirect.subscriptions.notifications.domain;

import javax.persistence.*;

/**
 * Created by hrishikeshshinde on 21/11/16.
 */

@Entity
public class SubscriptionNotification {

    @Id
    @GeneratedValue
    private Long id;

    private String url;

    private Boolean processed;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    public SubscriptionNotification() {
    }

    public SubscriptionNotification(String url, NotificationType type, Boolean processed) {
        this.url = url;
        this.type = type;
        this.processed = processed;
    }

    public SubscriptionNotification(String url, NotificationType type) {
        this.url = url;
        this.type = type;
        this.processed = false;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public NotificationType getType() {
        return type;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    @Override
    public String toString() {
        return "SubscriptionNotification{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", processed=" + processed +
                ", type=" + type +
                '}';
    }
}

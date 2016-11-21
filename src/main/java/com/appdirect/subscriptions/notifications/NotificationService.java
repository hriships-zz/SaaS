package com.appdirect.subscriptions.notifications;

import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hrishikeshshinde on 21/11/16.
 */

@Service
public class NotificationService {

    @Autowired
    DBRepository dbRepo;

    public SubscriptionNotification createNewEvent(SubscriptionNotification notification) {
        return dbRepo.save(notification);
    }
}

package com.appdirect.subscriptions.operations.processors;

import com.appdirect.subscriptions.notifications.NotificationRepository;
import com.appdirect.subscriptions.notifications.NotificationService;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import com.appdirect.subscriptions.operations.domain.entities.Subscription;
import com.appdirect.subscriptions.operations.exceptions.ServiceException;
import com.appdirect.subscriptions.operations.services.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

public class CreateSubscriptionWorker implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CreateSubscriptionWorker.class);
    private final SubscriptionNotification eventNotification;
    private final NotificationService notificationService;
    private final SubscriptionService subscrptionservice;

    public CreateSubscriptionWorker(SubscriptionNotification notification,
                                    NotificationService notificationRepo,
                                    SubscriptionService service) {
        this.eventNotification = notification;
        this.notificationService = notificationRepo;
        this.subscrptionservice = service;
    }

    @Override
    public void run() {
        String url = eventNotification.getUrl();
        log.info("Event ID :" + eventNotification.getId() + " URL " + url);
        try {
            Subscription subscription = subscrptionservice.getByEventUrl(url);
            subscrptionservice.create(subscription);
            String accountIdentifier = subscription.getPayload().getAccount().getAccountIdentifier();
            notificationService.notifiySubscription(url, accountIdentifier, null);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}

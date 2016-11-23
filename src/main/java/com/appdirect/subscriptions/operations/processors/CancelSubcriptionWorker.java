package com.appdirect.subscriptions.operations.processors;

import com.appdirect.common.exceptions.AuthException;
import com.appdirect.subscriptions.notifications.NotificationService;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import com.appdirect.subscriptions.operations.domain.entities.Subscription;
import com.appdirect.subscriptions.operations.exceptions.ServiceException;
import com.appdirect.subscriptions.operations.services.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hrishikeshshinde on 23/11/16.
 */
public class CancelSubcriptionWorker implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelSubcriptionWorker.class);

    private final SubscriptionNotification eventNotification;
    private final NotificationService notificationService;
    private final SubscriptionService subscriptionService;

    public CancelSubcriptionWorker(SubscriptionNotification notification,
                                   NotificationService notificationService,
                                   SubscriptionService service) {
        this.eventNotification = notification;
        this.notificationService = notificationService;
        this.subscriptionService = service;
    }

    @Override
    public void run() {
        String url = eventNotification.getUrl();
        LOGGER.debug("Event ID :" + eventNotification.getId() + " URL " + url);

        try {
            Subscription subscription = subscriptionService.getByEventUrl(url);
            subscriptionService.cancel(subscription);
            notificationService.notifiySubscription(url + "/result", null, null);
        } catch (ServiceException e) {
            LOGGER.error("Cancel subscription exception occurred, retrying create subscription : " + e.getMessage(), e);
            eventNotification.setProcessed(false);
            notificationService.update(eventNotification);
        } catch (AuthException e) {
            LOGGER.error("Authentication exception occurred: " + e.getMessage(), e);
        }
    }
}

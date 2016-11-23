package com.appdirect.subscriptions.operations.processors;

import com.appdirect.common.exceptions.AuthException;
import com.appdirect.subscriptions.notifications.NotificationService;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import com.appdirect.subscriptions.operations.domain.entities.ErrorStatusEnum;
import com.appdirect.subscriptions.operations.domain.entities.Subscription;
import com.appdirect.subscriptions.operations.exceptions.ServiceException;
import com.appdirect.subscriptions.operations.services.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

public class CreateSubscriptionWorker implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateSubscriptionWorker.class);

    private final SubscriptionNotification eventNotification;
    private final NotificationService notificationService;
    private final SubscriptionService subscriptionService;

    public CreateSubscriptionWorker(SubscriptionNotification notification,
                                    NotificationService notificationService,
                                    SubscriptionService service) {
        this.eventNotification = notification;
        this.notificationService = notificationService;
        this.subscriptionService = service;
    }

    @Override
    public void run() {
        String url = eventNotification.getUrl();
        LOGGER.info("Event ID :" + eventNotification.getId() + " URL " + url);

        try {
            Subscription subscription = subscriptionService.getByEventUrl(url);
            subscriptionService.create(subscription);
            String accountIdentifier = subscription.getPayload().getAccount().getAccountIdentifier();
            notificationService.notifySubscription(url + "/result", accountIdentifier, null);
        } catch (ServiceException e) {
            LOGGER.error("Create subscription exception occurred, retrying create subscription : " + e.getMessage(), e);
            eventNotification.setProcessed(false);
            notificationService.update(eventNotification);
        } catch (AuthException e) {
            LOGGER.error("Create subscription exception occurred: " + e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            notificationService.notifySubscription(url + "/result", null, ErrorStatusEnum.USER_ALREADY_EXISTS);
        }
    }
}

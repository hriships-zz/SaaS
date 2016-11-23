package com.appdirect.subscriptions.operations.processors;

import com.appdirect.common.exceptions.AuthException;
import com.appdirect.common.exceptions.EntityNotFoundException;
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
 * Created by hrishikesh_shinde on 11/23/2016.
 */
public class UpdateSubscriptionWorker implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateSubscriptionWorker.class);

    private final SubscriptionNotification eventNotification;
    private final NotificationService notificationService;
    private final SubscriptionService subscriptionService;

    public UpdateSubscriptionWorker(SubscriptionNotification notification, NotificationService notificationService, SubscriptionService service) {
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
            subscriptionService.update(subscription);
            notificationService.notifySubscription(url + "/result", null, null);
        } catch (ServiceException e) {
            LOGGER.error("Cancel subscription exception occurred, retrying create subscription : " + e.getMessage(), e);
            eventNotification.setProcessed(false);
            notificationService.update(eventNotification);
        } catch (AuthException e) {
            LOGGER.error("Update subscription exception occurred: " + e.getMessage(), e);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Update subscription exception occurred: " + e.getMessage(), e);
            notificationService.notifySubscription(url + "/result", null, ErrorStatusEnum.ACCOUNT_NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Update subscription exception occurred: " + e.getMessage(), e);
            notificationService.notifySubscription(url + "/result", null, ErrorStatusEnum.INVALID_RESPONSE);
        }
    }
}

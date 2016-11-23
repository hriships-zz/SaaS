package com.appdirect.subscriptions.operations.processors;

import com.appdirect.subscriptions.notifications.NotificationService;
import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import com.appdirect.subscriptions.operations.services.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by hrishikeshshinde on 23/11/16.
 */
abstract class AbstractProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcessor.class);

    private NotificationService notificationService;
    private SubscriptionService subscriptionService;

    public AbstractProcessor(NotificationService notificationService, SubscriptionService subscriptionService) {
        this.notificationService = notificationService;
        this.subscriptionService = subscriptionService;
    }

    protected ExecutorService getExecutorService() {
        int noOfProcess = Runtime.getRuntime().availableProcessors();
        return Executors.newFixedThreadPool(noOfProcess);
    }

    protected List<SubscriptionNotification> getSubscriptionNotifications(NotificationType type) {
        return (List<SubscriptionNotification>)
                notificationService.getEventsTypeAndStatus(type, false);
    }

    protected void processSubscriptions(ExecutorService eventService, List<SubscriptionNotification> notifications) {
        notifications.forEach(notification -> {
            LOGGER.debug("Subscription Event Id : " + notification.getId());
            updateStatus(notification);
            startSubscriptionProcess(notification, eventService, subscriptionService);
        });
    }

    private void startSubscriptionProcess(SubscriptionNotification notification,
                                          ExecutorService eventService,
                                          SubscriptionService service) {
        eventService.submit(new CreateSubscriptionWorker(notification, notificationService, service));
    }

    private void updateStatus(SubscriptionNotification notification) {
        notification.setProcessed(true);
        notificationService.update(notification);
    }

    protected void waitFortermintation(ExecutorService eventService) {
        eventService.shutdown();
        try {
            eventService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.error("Create event processor await : " + e.getMessage());
        }
    }
}

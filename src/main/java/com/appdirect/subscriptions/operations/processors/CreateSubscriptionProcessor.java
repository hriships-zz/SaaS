package com.appdirect.subscriptions.operations.processors;

import com.appdirect.subscriptions.notifications.NotificationService;
import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import com.appdirect.subscriptions.operations.services.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

@Component
public class CreateSubscriptionProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateSubscriptionProcessor.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SubscriptionService subscriptionService;


    @Scheduled(fixedRate = 10000)
    public void processEvents() {
        ExecutorService eventService = getExecutorService();
        List<SubscriptionNotification> notifications = getSubscriptionNotifications();
        processSubscriptions(eventService, notifications);
        waitFortermintation(eventService);
    }

    private void waitFortermintation(ExecutorService eventService) {
        eventService.shutdown();
        try {
            eventService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.error("Create event processor await : " + e.getMessage());
        }
    }

    private void processSubscriptions(ExecutorService eventService, List<SubscriptionNotification> notifications) {
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

    private List<SubscriptionNotification> getSubscriptionNotifications() {
        return (List<SubscriptionNotification>)
                    notificationService.getEventsTypeAndStatus(NotificationType.CREATE, false);
    }

    private ExecutorService getExecutorService() {
        int noOfProcess = Runtime.getRuntime().availableProcessors();
        return Executors.newFixedThreadPool(noOfProcess);
    }
}

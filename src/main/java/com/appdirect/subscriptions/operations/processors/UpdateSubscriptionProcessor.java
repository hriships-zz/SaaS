package com.appdirect.subscriptions.operations.processors;

import com.appdirect.subscriptions.notifications.NotificationService;
import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import com.appdirect.subscriptions.operations.services.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by hrishikesh_shinde on 11/23/2016.
 */
public class UpdateSubscriptionProcessor extends AbstractProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateSubscriptionProcessor.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Scheduled(fixedRate = 10000)
    @Override
    void processEvents() {
        ExecutorService eventService = getExecutorService();
        List<SubscriptionNotification> notifications = getSubscriptionNotifications(NotificationType.CHANGE);
        processSubscriptions(eventService, notifications);
        waitForTermination(eventService);
    }

    @Override
    void startSubscriptionProcess(SubscriptionNotification notification, ExecutorService eventService, SubscriptionService service) {
        eventService.submit(new UpdateSubscriptionWorker(notification, notificationService, service));
    }
}

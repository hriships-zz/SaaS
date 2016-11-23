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
public class CreateSubscriptionProcessor extends AbstractProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateSubscriptionProcessor.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Scheduled(fixedRate = 10000)
    @Override
    public void processEvents() {
        ExecutorService eventService = getExecutorService();
        List<SubscriptionNotification> notifications = getSubscriptionNotifications(NotificationType.CREATE);
        processSubscriptions(eventService, notifications);
        waitForTermination(eventService);
    }

    @Override
    void startSubscriptionProcess(SubscriptionNotification notification,
                                  ExecutorService eventService,
                                  SubscriptionService service) {
        eventService.submit(new CreateSubscriptionWorker(notification, notificationService, service));
    }
}

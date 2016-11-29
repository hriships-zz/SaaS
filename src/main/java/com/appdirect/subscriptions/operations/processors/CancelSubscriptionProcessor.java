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
 * Created by hrishikeshshinde on 23/11/16.
 */

@Component
public class CancelSubscriptionProcessor extends AbstractProcessor{
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateSubscriptionProcessor.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Process all CANCEL subscriptions events, queue them and start worker thread
     * for creating accounts and subscriptions in applications
     * It repeats periodically for for given time interval
     * Note: Relatively its frequency is low
     */
    @Scheduled(fixedRate=20000)
    @Override
    public void processEvents() {
        ExecutorService eventService = getExecutorService();
        List<SubscriptionNotification> notifications = getSubscriptionNotifications(NotificationType.CANCEL);
        processSubscriptions(eventService, notifications);
        waitForTermination(eventService);
    }

    /**
     * Submit the individual CANCEL subscription event
     *
     * @param notification
     * @param eventService
     * @param service
     */
    @Override
    void startSubscriptionProcess(SubscriptionNotification notification,
                                          ExecutorService eventService,
                                          SubscriptionService service) {
        eventService.submit(new CancelSubscriptionWorker(notification, notificationService, service));
    }


}

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
 *
 * AbstractProcessor has common base for all background running processors.
 * Processors picks all pending subscriptions and process concurrently.
 */
public abstract class AbstractProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcessor.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Return the ExecuterService instance with fixed number of thread pool.
     * Thread pool size is determined by number of cores/process execution environment has
     *
     *  @return new ExecutorService instance
     */
    protected ExecutorService getExecutorService() {
        int noOfProcess = Runtime.getRuntime().availableProcessors();
        return Executors.newFixedThreadPool(noOfProcess);
    }

    /**
     * Retrieves all pending subscription notifications
     *
     * @param type NotificationType
     * @return List of SubscriptionNotification
     */
    protected List<SubscriptionNotification> getSubscriptionNotifications(NotificationType type) {
        return (List<SubscriptionNotification>)
                notificationService.getEventsTypeAndStatus(type, false);
    }

    /**
     * Starts processing each subscription
     *
     * @param eventService ExecutorService
     * @param notifications List<SubscriptionNotification>
     */
    protected void processSubscriptions(ExecutorService eventService, List<SubscriptionNotification> notifications) {
        notifications.forEach(notification -> {
            LOGGER.debug("Subscription Event Id : " + notification.getId());
            updateStatus(notification);
            startSubscriptionProcess(notification, eventService, subscriptionService);
        });
    }

    private void updateStatus(SubscriptionNotification notification) {
        notification.setProcessed(true);
        notificationService.update(notification);
    }

    /**
     * Wait till threads get executes or timeout happen
     *
     * @param eventService ExecutorService
     */
    protected void waitForTermination(ExecutorService eventService) {
        eventService.shutdown();
        try {
            eventService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.error("Create event processor await : " + e.getMessage());
        }
    }

    /**
     * Delegated the business logic to execute and process event
     */
    abstract void processEvents();

    /**
     * Delegated subscription process handling
     *
     * @param notification SubscriptionNotification
     * @param eventService ExecutorService
     * @param service SubscriptionService
     */
    abstract void startSubscriptionProcess(SubscriptionNotification notification,
                                          ExecutorService eventService,
                                          SubscriptionService service);
}
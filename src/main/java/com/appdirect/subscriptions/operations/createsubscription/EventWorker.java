package com.appdirect.subscriptions.operations.createsubscription;

import com.appdirect.subscriptions.notifications.DBRepository;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

public class EventWorker implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(EventWorker.class);
    private final SubscriptionNotification event;
    private final DBRepository notificationRepo;

    public EventWorker(SubscriptionNotification createEvent, DBRepository notificationRepo) {
        this.event = createEvent;
        this.notificationRepo = notificationRepo;
    }

    @Override
    public void run() {
        log.info("Event ID :" + event.getId() + " URL " + event.getUrl());
    }
}

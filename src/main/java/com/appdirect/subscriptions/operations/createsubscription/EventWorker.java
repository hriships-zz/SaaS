package com.appdirect.subscriptions.operations.createsubscription;

import com.appdirect.common.services.OAuthHelper;
import com.appdirect.subscriptions.notifications.NotificationRepository;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

public class EventWorker implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(EventWorker.class);
    private final SubscriptionNotification event;
    private final NotificationRepository notificationRepo;
    private OAuthHelper oAuthHelper;

    public EventWorker(SubscriptionNotification createEvent,
                       NotificationRepository notificationRepo,
                       OAuthHelper oAuthHelper) {
        this.event = createEvent;
        this.notificationRepo = notificationRepo;
        this.oAuthHelper = oAuthHelper;
    }

    @Override
    public void run() {


        log.info("Event ID :" + event.getId() + " URL " + event.getUrl());
    }
}

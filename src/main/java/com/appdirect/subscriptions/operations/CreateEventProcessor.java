package com.appdirect.subscriptions.operations;

import com.appdirect.subscriptions.notifications.DBRepository;
import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

@Component
public class CreateEventProcessor {

    private static final Logger log = LoggerFactory.getLogger(CreateEventProcessor.class);

    @Autowired
    private DBRepository notificationRepo;

    @Scheduled(fixedRate = 5000)
    public void processEvents() {
        List<SubscriptionNotification> notifications = (List<SubscriptionNotification>) notificationRepo.findBySubscriptionNotificationType(NotificationType.CREATE);
        notifications.forEach(n -> {
            log.info("Subscription Event : " + n.getType());
        });

    }
}

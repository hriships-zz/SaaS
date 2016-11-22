package com.appdirect.subscriptions.notifications;

import com.appdirect.common.services.OAuthHelper;
import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.subscriptions.notifications.domain.ServiceResponse;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import com.appdirect.subscriptions.operations.processors.CreateSubscriptionProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * Created by hrishikeshshinde on 21/11/16.
 */

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuthHelper oAuthHelper;

    public SubscriptionNotification createNewEvent(SubscriptionNotification notification) {
        log.debug("Create subscription info : " + notification.toString());
        return notificationRepository.save(notification);
    }

    public Collection<SubscriptionNotification> getEventsTypeAndStatus(NotificationType  type,
                                             Boolean status) {
        return notificationRepository.findByTypeAndStatus(type, status);
    }

    public boolean notifiySubscription(String url,
                                       String accountId,
                                       String errorCode) {
        ServiceResponse serviceResponse = new ServiceResponse(true, null, null, accountId);
        restTemplate.postForObject(url, serviceResponse, ServiceResponse.class);
        return true;
    }

    public SubscriptionNotification update(SubscriptionNotification notification) {
        return notificationRepository.save(notification);

    }
}

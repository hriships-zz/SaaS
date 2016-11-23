package com.appdirect.subscriptions.notifications;

import com.appdirect.common.exceptions.AuthException;
import com.appdirect.common.services.OAuthHelper;
import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.common.domain.ServiceResponse;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import com.appdirect.subscriptions.operations.domain.entities.ErrorStatusEnum;
import com.appdirect.subscriptions.operations.exceptions.ServiceException;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * Created by hrishikeshshinde on 21/11/16.
 */

@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuthHelper oAuthHelper;

    public SubscriptionNotification createNewEvent(SubscriptionNotification notification) {
        LOGGER.debug("Create subscription info : " + notification.toString());
        return notificationRepository.save(notification);
    }

    public Collection<SubscriptionNotification> getEventsTypeAndStatus(NotificationType  type,
                                             Boolean status) {
        return notificationRepository.findByTypeAndStatus(type, status);
    }

    public boolean notifySubscription(String url,
                                      String accountId,
                                      ErrorStatusEnum errorCode) {
        ServiceResponse serviceResponse;

        if(errorCode == null) {
            serviceResponse = new ServiceResponse(true, null, null, accountId);
        } else {
            serviceResponse = new ServiceResponse(false, errorCode.toString(), null);
        }

        try {
            String signedUrl = oAuthHelper.signURL(url);
            restTemplate.postForObject(signedUrl, serviceResponse, ServiceResponse.class);

        } catch (OAuthException e) {
            throw new AuthException(e);
        } catch (RestClientException e) {
            if(e.getMessage().equalsIgnoreCase(AuthException.UNAUTHORIZED)) {
                throw new AuthException(e);
            } else {
                throw new ServiceException(e);
            }
        }

        return true;
    }

    public SubscriptionNotification update(SubscriptionNotification notification) {
        return notificationRepository.save(notification);

    }
}

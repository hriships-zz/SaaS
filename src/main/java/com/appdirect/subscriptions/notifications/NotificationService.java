package com.appdirect.subscriptions.notifications;

import com.appdirect.common.domain.ServiceResponse;
import com.appdirect.common.domain.SignedData;
import com.appdirect.common.exceptions.AuthException;
import com.appdirect.common.services.OAuthHelper;
import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import com.appdirect.subscriptions.operations.domain.entities.ErrorStatusEnum;
import com.appdirect.subscriptions.operations.exceptions.ServiceException;
import oauth.signpost.exception.OAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * Created by hrishikeshshinde on 21/11/16.
 *
 * Service to mange subscription notifications
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

    /**
     * Creates new subscription event notification
     *
     * @param notification SubscriptionNotification
     * @return SubscriptionNotification
     */
    public SubscriptionNotification createNewEvent(SubscriptionNotification notification) {
        LOGGER.debug("Create subscription info : " + notification.toString());
        return notificationRepository.save(notification);
    }

    /**
     * get all subscription events by subscription type and status
     *
     * @param type NotificationType
     * @param status Boolean
     * @return Collections of SubscriptionNotification
     */
    public Collection<SubscriptionNotification> getEventsTypeAndStatus(NotificationType  type,
                                             Boolean status) {
        return notificationRepository.findByTypeAndStatus(type, status);
    }

    /**
     * Post updates to AppDirect about subscriptions activities e.g. account created
     *
     * @param url String
     * @param accountId String
     * @param errorCode ErrorStatusEnum
     * @return true or false
     */
    public boolean notifyToAppDirect(String url,
                                     String accountId,
                                     ErrorStatusEnum errorCode) {
        ServiceResponse serviceResponse;

        if(errorCode == null) {
            serviceResponse = new ServiceResponse(true, null, null, accountId);
        } else {
            serviceResponse = new ServiceResponse(false, errorCode.toString(), null);
        }

        try {
            SignedData signedData = oAuthHelper.signURL(url);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", signedData.getOauthHeader());
            HttpEntity<ServiceResponse> entity = new HttpEntity<ServiceResponse>(serviceResponse, headers);
            restTemplate.exchange(signedData.getSignedUrl(), HttpMethod.POST, entity, ServiceResponse.class);

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

    /**
     * Update subscription event notification
     *
     * @param notification SubscriptionNotification
     * @return SubscriptionNotification
     */
    public SubscriptionNotification update(SubscriptionNotification notification) {
        return notificationRepository.save(notification);

    }
}

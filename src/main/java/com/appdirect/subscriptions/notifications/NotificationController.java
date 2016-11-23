package com.appdirect.subscriptions.notifications;

import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.subscriptions.notifications.domain.ServiceResponse;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hrishikeshshinde on 21/11/16.
 */

@RestController
@RequestMapping("/v1/notifications")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(path = "/{type}/subscriptions", method = RequestMethod.GET)
    public ResponseEntity<ServiceResponse> subscribeNotification(@PathVariable String type,
                                                                 @RequestParam(value = "eventUrl", required = true) String eventUrl,
                                                                 @RequestHeader(value = "Authorization") String authorization) {
        log.info("Auth : " + authorization);
        notificationService.createNewEvent(new SubscriptionNotification(eventUrl, NotificationType.valueOf(type), false));
        return new ResponseEntity<ServiceResponse>(new ServiceResponse(true), HttpStatus.ACCEPTED);
    }
}

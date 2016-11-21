package com.appdirect.subscriptions.notifications;

import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.subscriptions.notifications.domain.ServiceResponse;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hrishikeshshinde on 21/11/16.
 */

@RestController
@RequestMapping("/v1/notifications")
public class Controller {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(path = "/{type}/subscriptions", method = RequestMethod.GET)
    public @ResponseBody
    ServiceResponse subscribeNotification(@PathVariable String type,
                                          @RequestParam(value = "url", required = true) String eventUrl) {

        notificationService.createNewEvent(new SubscriptionNotification(eventUrl, NotificationType.valueOf(type), false));
        return new ServiceResponse(true);
    }
}

package com.appdirect.subscriptions.notifications;

import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hrishikeshshinde on 21/11/16.
 */

@Repository
public interface DBRepository extends JpaRepository<SubscriptionNotification, Long> {
    SubscriptionNotification getNotificationByType(NotificationType type);
}

package com.appdirect.subscriptions.notifications;

import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

/**
 * Created by hrishikeshshinde on 21/11/16.
 * Subscription notification  database repository
 */

@RepositoryRestResource
public interface NotificationRepository extends JpaRepository<SubscriptionNotification, Long> {

    /**
     * Get all notifications by NotificationType
     * @param type NotificationType
     * @return collection of SubscriptionNotification
     */
    @Query("FROM SubscriptionNotification s where s.type = :type")
    Collection<SubscriptionNotification> findByType(@Param("type") NotificationType type);

    /**
     * Get all notifications by NotificationType and status
     * @param type NotificationType
     * @param status boolean
     * @return
     */
    @Query("FROM SubscriptionNotification s where s.type = :type and s.processed = :status")
    Collection<SubscriptionNotification> findByTypeAndStatus(@Param("type") NotificationType type,
                                                             @Param("status") Boolean status);
}

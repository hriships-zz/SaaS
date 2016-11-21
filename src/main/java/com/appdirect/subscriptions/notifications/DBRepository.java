package com.appdirect.subscriptions.notifications;

import com.appdirect.subscriptions.notifications.domain.NotificationType;
import com.appdirect.subscriptions.notifications.domain.SubscriptionNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by hrishikeshshinde on 21/11/16.
 */

@RepositoryRestResource
public interface DBRepository extends JpaRepository<SubscriptionNotification, Long> {

    @Query("FROM SubscriptionNotification s where s.type = :type")
    Collection<SubscriptionNotification> findByType(@Param("type") NotificationType type);

    @Query("FROM SubscriptionNotification s where s.type = :type and s.processed = :status")
    Collection<SubscriptionNotification> findByTypeAndStatus(@Param("type") NotificationType type,
                                                             @Param("status") Boolean status);
}

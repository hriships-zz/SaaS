package com.appdirect.subscriptions.operations.domain.repos;

import com.appdirect.subscriptions.operations.domain.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

@RepositoryRestResource
public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
}

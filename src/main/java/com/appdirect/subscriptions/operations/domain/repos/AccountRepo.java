package com.appdirect.subscriptions.operations.domain.repos;

import com.appdirect.subscriptions.operations.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

@RepositoryRestResource
public interface AccountRepo extends JpaRepository<Account, String> {
    Account findByAccountIdentifier(String accountIdentifier);
}

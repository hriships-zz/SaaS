package com.appdirect.subscriptions.operations.services;

import com.appdirect.common.services.OAuthHelper;
import com.appdirect.subscriptions.operations.domain.entities.Account;
import com.appdirect.subscriptions.operations.domain.entities.AccountStatusEnum;
import com.appdirect.subscriptions.operations.domain.entities.Subscription;
import com.appdirect.subscriptions.operations.exceptions.ServiceException;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */
@Service
public class SubscriptionService {

    @Autowired
    private OAuthHelper oAuthHelper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SubscriptionService subscriptionService;

    public Subscription getByEventUrl(String url) throws ServiceException {
        try {
            String signedURL = oAuthHelper.signURL(url);
            return restTemplate.getForObject(signedURL, Subscription.class);
        } catch (OAuthCommunicationException |
                 OAuthExpectationFailedException |
                 OAuthMessageSignerException e) {
            throw new ServiceException(e);
        }
    }

    public Subscription create(Subscription subscription) {
        Account account = accountService.create(new Account(AccountStatusEnum.ACTIVE));
        subscription.getPayload().setAccount(account);
        return subscriptionService.create(subscription);
    }

    public Subscription update(Subscription subscription) {
        return null;
    }

    public Subscription cancel(Subscription subscription) {
        return null;
    }


}

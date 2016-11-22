package com.appdirect.common.services;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.QueryStringSigningStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

public class OAuthHelper {

    @Value("${oAuthKey}")
    private String oAuthKey;

    @Value("${oAuthSecrete}")
    private String oAuthSecrete;

    public String signURL(String url) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        OAuthConsumer consumer = new DefaultOAuthConsumer("cloudtest-141946", "hUFnL5Cnz4jbexwB");
        consumer.setSigningStrategy( new QueryStringSigningStrategy());
        String signedUrl = consumer.sign(url);
        return signedUrl;
    }
}

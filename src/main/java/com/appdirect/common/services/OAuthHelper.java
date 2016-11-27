package com.appdirect.common.services;

import com.appdirect.common.exceptions.AuthException;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.QueryStringSigningStrategy;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hrishikeshshinde on 22/11/16.
 */

public class OAuthHelper {

    private static final Logger log = LoggerFactory.getLogger(OAuthHelper.class);

    private static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    private static final String OAUTH_SIGNATURE = "oauth_signature";

    @Value("${oAuthKey}")
    private String oAuthKey;

    @Value("${oAuthSecrete}")
    private String oAuthSecrete;

    public void authenticateSignature(String authHeader){
        Map<String, String> oauthParams = extractHeaders(authHeader);
        String oauthConsumerKey = oauthParams.get(OAUTH_CONSUMER_KEY);
        String oauthSignature = oauthParams.get(OAUTH_SIGNATURE);

        log.info("Auth key :" + oauthConsumerKey +" "+oAuthKey);
        if(!oauthConsumerKey.equals(oAuthKey)){
            throw new AuthException("Authentication failed, unable to verify authkey");
        }

        String signature = HmacUtils.hmacSha1(oAuthKey, oAuthSecrete).toString();
        log.info("Auth signature :" + oauthSignature +" "+ signature);
        if(!oauthSignature.equals(signature)){
            throw new AuthException("Authentication failed, unable to verify signature");
        }
    }

    private Map<String, String> extractHeaders(String authHeader) {
        authHeader = authHeader.replace("OAuth ", "");
        authHeader = authHeader.replace("\"", "");
        return Arrays.asList(authHeader.split(", "))
                .stream()
                .map(elem -> elem.split("="))
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }

    public String signURL(String url) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        OAuthConsumer consumer = new DefaultOAuthConsumer(oAuthKey, oAuthSecrete);
        consumer.setSigningStrategy( new QueryStringSigningStrategy());
        String signedUrl = consumer.sign(url);

        try {
            URL urlObj = new URL(url);
            HttpURLConnection request = (HttpURLConnection) urlObj.openConnection();
            consumer.sign(request);
            request.connect();
            log.info("Response code: " + request.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return signedUrl;
    }
}

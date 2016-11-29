package com.appdirect.common.services;

import com.appdirect.common.domain.SignedData;
import com.appdirect.common.exceptions.AuthException;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hrishikeshshinde on 22/11/16.
 *
 * OAuthHelper provides application wide Oauth1.0 functions
 */

public class OAuthHelper {

    private static final Logger log = LoggerFactory.getLogger(OAuthHelper.class);

    private static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    private static final String OAUTH_SIGNATURE = "oauth_signature";

    @Value("${oAuthKey}")
    private String oAuthKey;

    @Value("${oAuthSecrete}")
    private String oAuthSecrete;

    /**
     *Verifies the signature received from API consumer using two legged authentication mechanism
     *
     * @param authHeader String
     */
    public void authenticateSignature(String authHeader){
        Map<String, String> oauthParams = extractHeaders(authHeader);
        String oauthConsumerKey = oauthParams.get(OAUTH_CONSUMER_KEY);
        String oauthSignature = oauthParams.get(OAUTH_SIGNATURE);

        log.debug("Auth key :" + oauthConsumerKey +" "+oAuthKey);
        if(!oauthConsumerKey.equals(oAuthKey)){
            throw new AuthException("Authentication failed, unable to verify authkey");
        }

        String signature = HmacUtils.hmacSha1(oAuthKey, oAuthSecrete).toString();
        log.debug("Auth signature :" + oauthSignature +" "+ signature);
        if(!oauthSignature.equals(signature)){
            log.error("Authentication failed, unable to verify API consumer signature");
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

    /**
     * Signs the url using two legged method to perform signed fetch using oAuth1.0
     *
     * @param url String
     * @return returns the signed url and authorisation header as well
     * @throws OAuthCommunicationException
     * @throws OAuthExpectationFailedException
     * @throws OAuthMessageSignerException
     */
    public SignedData signURL(String url) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        OAuthConsumer consumer = new DefaultOAuthConsumer(oAuthKey, oAuthSecrete);
        consumer.setSigningStrategy(new AuthorizationHeaderSigningStrategy());
        String signedUrl = consumer.sign(url);
        String authParams = signedUrl.substring(signedUrl.indexOf("?" +1), signedUrl.length());
        String oauthHeader = getoAuthHeader(authParams);

        return new SignedData(signedUrl, oauthHeader);
    }

    private String getoAuthHeader(String authParams) {
        return Arrays.asList(authParams.split("&"))
                .stream()
                .map(elem -> elem.replace("=", "=\""))
                .map(elem -> elem.concat("\""))
                .collect(Collectors.joining(","));
    }
}

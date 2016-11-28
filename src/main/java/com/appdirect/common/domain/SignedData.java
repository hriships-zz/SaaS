package com.appdirect.common.domain;

/**
 * Created by hrishikeshshinde on 28/11/16.
 */
public class SignedData {
    private String signedUrl;
    private String oauthHeader;

    public SignedData(String signedUrl, String oauthHeader) {
        this.signedUrl = signedUrl;
        this.oauthHeader = oauthHeader;
    }

    public String getSignedUrl() {
        return signedUrl;
    }

    public String getOauthHeader() {
        return oauthHeader;
    }
}

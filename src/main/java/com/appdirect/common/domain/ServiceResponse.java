package com.appdirect.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by hrishikeshshinde on 21/11/16.
 * Response/Request entity to interact with APPDirect API
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse {
    private final Boolean success;

    private final String errorCode;

    private final String message;

    private final String accountId;

    public ServiceResponse() {
        this.success = null;
        this.errorCode = null;
        this.message = null;
        this.accountId = null;
    }

    public ServiceResponse(Boolean success) {
        this.success = success;
        this.errorCode = null;
        this.message = null;
        this.accountId = null;
    }

    public ServiceResponse(String errorCode, String message) {
        this.success = null;
        this.errorCode = errorCode;
        this.message = message;
        this.accountId = null;
    }

    public ServiceResponse(Boolean success, String errorCode, String message) {
        this.success = success;
        this.errorCode = errorCode;
        this.message = message;
        this.accountId = null;
    }

    public ServiceResponse(Boolean success, String errorCode, String message, String accountId) {
        this.success = success;
        this.errorCode = errorCode;
        this.message = message;
        this.accountId = accountId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getAccountId() {
        return accountId;
    }
}

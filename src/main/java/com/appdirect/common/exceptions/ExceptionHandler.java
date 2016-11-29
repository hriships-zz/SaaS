package com.appdirect.common.exceptions;

import com.appdirect.common.domain.ServiceResponse;
import com.appdirect.common.services.OAuthHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by hrishikeshshinde on 23/11/16.
 * Handles controller exception aspects
 */

@ControllerAdvice
public class ExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthHelper.class);

    /**
     * Handles authorisation exception while accessing application APIS
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(AuthException.class)
    @ResponseBody
    public ServiceResponse handleUnauthorizedUserException(final Exception ex){
        String message = ex.getMessage();
        LOGGER.error(message, ex);
        return new ServiceResponse(false, HttpStatus.UNAUTHORIZED.name(), message);
    }
}

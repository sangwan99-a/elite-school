package com.elite.school.framework.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbtAuthenticator {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbtAuthenticator.class);

    protected AbtAuthenticator nextAuthenticator;

    public void setNextAuthenticator(AbtAuthenticator nextAuthenticator) {
        this.nextAuthenticator = nextAuthenticator;
    }

    public boolean authenticate(HttpServletRequest httpRequest) {
        if (canAuthenticate(httpRequest)) {
            return true;
        } else if (nextAuthenticator != null) {
            return nextAuthenticator.authenticate(httpRequest);
        } else {
            LOGGER.warn("Access blocked by filter: {} URL: {}", this.getClass().getSimpleName(), httpRequest.getServletPath());
            return false;
        }
    }

    protected abstract boolean canAuthenticate(HttpServletRequest httpRequest);


}

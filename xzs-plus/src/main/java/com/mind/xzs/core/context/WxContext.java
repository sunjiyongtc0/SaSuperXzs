package com.mind.xzs.core.context;

import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.UserTokenEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Component
public class WxContext {

    private static final String USER_ATTRIBUTES = "USER_ATTRIBUTES";
    private static final String USER_TOKEN_ATTRIBUTES = "USER_TOKEN_ATTRIBUTES";


    /**
     * Sets context.
     *
     * @param user      the user
     * @param userToken the user token
     */
    public void setContext(UserEntity user, UserTokenEntity userToken) {
        RequestContextHolder.currentRequestAttributes().setAttribute(USER_ATTRIBUTES, user, RequestAttributes.SCOPE_REQUEST);
        RequestContextHolder.currentRequestAttributes().setAttribute(USER_TOKEN_ATTRIBUTES, userToken, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Gets current user.
     *
     * @return the current user
     */
    public UserEntity getCurrentUser() {
        return (UserEntity) RequestContextHolder.currentRequestAttributes().getAttribute(USER_ATTRIBUTES, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Gets current user token.
     *
     * @return the current user token
     */
    public UserTokenEntity getCurrentUserToken() {
        return (UserTokenEntity) RequestContextHolder.currentRequestAttributes().getAttribute(USER_TOKEN_ATTRIBUTES, RequestAttributes.SCOPE_REQUEST);
    }
}

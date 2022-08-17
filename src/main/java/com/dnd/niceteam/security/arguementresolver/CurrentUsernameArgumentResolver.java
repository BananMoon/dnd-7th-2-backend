package com.dnd.niceteam.security.arguementresolver;

import com.dnd.niceteam.security.CurrentUsername;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static java.util.Objects.nonNull;

public class CurrentUsernameArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasCurrentUsernameAnnotation = parameter.hasParameterAnnotation(CurrentUsername.class);
        boolean hasStringType = String.class.isAssignableFrom(parameter.getParameterType());
        return hasCurrentUsernameAnnotation && hasStringType;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (nonNull(authentication) && authentication.isAuthenticated()) ? authentication.getName() : null;
    }
}

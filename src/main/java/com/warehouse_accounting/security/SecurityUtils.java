package com.warehouse_accounting.security;

import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public class SecurityUtils {
    private SecurityUtils () {

    }

    static boolean isFrameworkInternalRequest (HttpServletRequest request) {
        final String value = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return value != null && Stream.of(HandlerHelper.RequestType.values()).anyMatch(r -> r.getIdentifier().equals(value));
    }

    static boolean isUserLoggedIn () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}

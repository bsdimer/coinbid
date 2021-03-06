package com.madbid.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        if (exception.getClass().isAssignableFrom(
                AuthenticationServiceException.class)
                || exception.getClass().isAssignableFrom(
                BadCredentialsException.class)) {
            redirectStrategy.sendRedirect(request, response,
                    "/login.xhtml?error=invalidCredentials");
        } else if (exception.getClass().isAssignableFrom(
                SessionAuthenticationException.class)) {
            redirectStrategy.sendRedirect(request, response,
                    "/login.xhtml?error=maximumExceeded");
        } else if (exception.getClass().isAssignableFrom(
                DisabledException.class)) {
            redirectStrategy.sendRedirect(request, response,
                    "/login.xhtml?error=inactiveUser");
        }
    }

}

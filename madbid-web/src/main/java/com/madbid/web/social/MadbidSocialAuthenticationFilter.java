package com.madbid.web.social;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.social.UserIdSource;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SocialAuthenticationServiceLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by nikolov.
 */
public class MadbidSocialAuthenticationFilter extends SocialAuthenticationFilter {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public MadbidSocialAuthenticationFilter(AuthenticationManager authManager, UserIdSource userIdSource, UsersConnectionRepository usersConnectionRepository, SocialAuthenticationServiceLocator authServiceLocator) {
        super(authManager, userIdSource, usersConnectionRepository, authServiceLocator);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Authentication authentication = super.attemptAuthentication(request, response);
            return authentication;
        } catch (DisabledException ex) {
            try {
                redirectStrategy.sendRedirect(request, response,
                        "/login?error=inactiveUser");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

package com.madbid.core.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;

/**
 * This class delegates requests forward to our UserDetailsService implementation.
 * This is possible because we use the username of the user as the account ID.
 * Created by nikolov.
 */
public class SocialUserDetailsService implements org.springframework.social.security.SocialUserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocialUserDetailsService.class);

    private UserDetailsService userDetailsService;

    public SocialUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Loads the username by using the account ID of the user.
     * @param userId    The account ID of the requested user.
     * @return  The information of the requested user.
     * @throws UsernameNotFoundException    Thrown if no user is found.
     * @throws DataAccessException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        LOGGER.debug("Loading user by user id: {}", userId);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        LOGGER.debug("Found user details: {}", userDetails);

        if(!userDetails.isEnabled()) {
            throw new DisabledException("Inactive social user");
        }

        return (SocialUserDetails) userDetails;
    }
}

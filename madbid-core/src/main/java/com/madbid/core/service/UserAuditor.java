package com.madbid.core.service;

import com.madbid.core.model.User;
import com.madbid.core.security.dto.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by dimer on 11/24/14.
 */

public class UserAuditor implements AuditorAware<User> {

    @Autowired
    private UserService userService;

    @Override
    public User getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        User user = null;
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userService.findOne(userDetails.getId());
        }
        return user;
    }
}

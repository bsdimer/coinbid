package com.madbid.admin.converter;

import com.madbid.core.model.User;
import com.madbid.core.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by nikolov
 */
@Component
public class UserConverter extends EntityConverter<User> {
    @Inject
    private UserRepository userRepository;

    public UserRepository getRepository() {
        return userRepository;
    }
}

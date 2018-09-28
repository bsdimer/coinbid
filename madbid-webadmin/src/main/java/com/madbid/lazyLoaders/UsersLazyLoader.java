package com.madbid.lazyLoaders;

import com.madbid.core.model.User;
import com.madbid.core.service.ServiceContract;

/**
 * Created by dimer on 8/17/14.
 */
public class UsersLazyLoader extends LazyLoaderAdapter<User> {

    public UsersLazyLoader(ServiceContract<User> repository) {
        super(repository);
    }
}

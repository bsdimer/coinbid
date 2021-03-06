package com.madbid.web.service;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by dimer on 9/16/14.
 */
public abstract class AbstractAutowiringFactoryBean<T> extends
        AbstractFactoryBean<T> implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(
            final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected final T createInstance() throws Exception {
        final T instance = doCreateInstance();
        if (instance != null) {
            applicationContext
                    .getAutowireCapableBeanFactory()
                    .autowireBean(instance);
        }
        return instance;
    }

    protected abstract T doCreateInstance();
}

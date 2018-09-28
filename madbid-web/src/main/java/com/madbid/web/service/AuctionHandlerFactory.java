package com.madbid.web.service;

import org.springframework.stereotype.Service;

/**
 * Created by dimer on 9/16/14.
 */
@Service
public class AuctionHandlerFactory extends AbstractAutowiringFactoryBean<AuctionHandler> {

    @Override
    protected AuctionHandler doCreateInstance() {
        return new AuctionHandler();
    }

    @Override
    public Class<?> getObjectType() {
        return AuctionHandler.class;
    }
}

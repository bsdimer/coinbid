package com.madbid.lazyLoaders;

import com.madbid.core.model.Auction;
import com.madbid.core.service.ServiceContract;

/**
 * Created by dimer on 8/17/14.
 */
public class AuctionsLazyLoader extends LazyLoaderAdapter<Auction> {

    public AuctionsLazyLoader(ServiceContract<Auction> repository) {
        super(repository);
    }
}

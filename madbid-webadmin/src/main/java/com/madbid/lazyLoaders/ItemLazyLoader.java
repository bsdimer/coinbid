package com.madbid.lazyLoaders;

import com.madbid.core.model.Item;
import com.madbid.core.service.ServiceContract;

/**
 * Created by dimer on 8/17/14.
 */
public class ItemLazyLoader extends LazyLoaderAdapter<Item> {

    public ItemLazyLoader(ServiceContract<Item> repository) {
        super(repository);
    }
}

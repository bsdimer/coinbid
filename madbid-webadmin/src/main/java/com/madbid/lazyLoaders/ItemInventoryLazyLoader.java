package com.madbid.lazyLoaders;

import com.madbid.core.model.ItemInventory;
import com.madbid.core.service.ServiceContract;

/**
 * Created by dimer on 8/17/14.
 */
public class ItemInventoryLazyLoader extends LazyLoaderAdapter<ItemInventory> {

    public ItemInventoryLazyLoader(ServiceContract<ItemInventory> repository) {
        super(repository);
    }
}

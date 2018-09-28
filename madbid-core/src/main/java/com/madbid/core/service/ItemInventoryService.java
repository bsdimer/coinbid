package com.madbid.core.service;

import com.madbid.core.model.Item;
import com.madbid.core.model.ItemInventory;
import com.madbid.core.repository.ItemInventoryRepository;
import com.madbid.core.repository.MadbidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nikolov.
 */
@Service
@Transactional
public class ItemInventoryService extends BaseService<ItemInventory> implements ServiceContract<ItemInventory> {

    @Autowired
    private ItemInventoryRepository itemInventoryRepository;

    public List<ItemInventory> findByItemNameLikeWithItems(String name, Pageable itemsCount) {
        return itemInventoryRepository.findByItemNameLikeWithItems(name, itemsCount);
    }

    public List<ItemInventory> findAllByItem(Item item) {
        return itemInventoryRepository.findAllByItem(item);
    }

    @Override
    public MadbidRepository getRepository() {
        return itemInventoryRepository;
    }


}

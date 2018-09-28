package com.madbid.core.service;

import com.madbid.core.model.Item;
import com.madbid.core.repository.ItemRepository;
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
public class ItemService extends BaseService<Item> implements ServiceContract<Item> {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> findAllWithInventory() {
        return itemRepository.findAllWithInventory();
    }

    public Item findByName(String name) {
        return itemRepository.findByName(name);
    }

    public List<Item> findByNameLike(String name, Pageable itemsCount) {
        return itemRepository.findByNameLike(name, itemsCount);
    }

    public List<Item> findByNameLikeWithInventory(String name, Pageable itemsCount) {
        return itemRepository.findByNameLikeWithInventory(name, itemsCount);
    }

    @Override
    public MadbidRepository getRepository() {
        return itemRepository;
    }


}

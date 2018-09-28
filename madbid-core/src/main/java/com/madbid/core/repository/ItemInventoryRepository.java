package com.madbid.core.repository;

import com.madbid.core.model.Item;
import com.madbid.core.model.ItemInventory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by nikolov.
 */
public interface ItemInventoryRepository extends MadbidRepository<ItemInventory> {
    @Query("SELECT DISTINCT ii FROM ItemInventory ii LEFT JOIN FETCH ii.item i WHERE i.name LIKE ?1%")
    List<ItemInventory> findByItemNameLikeWithItems(String name, Pageable itemsCount);

    List<ItemInventory> findAllByItem(Item item);
}

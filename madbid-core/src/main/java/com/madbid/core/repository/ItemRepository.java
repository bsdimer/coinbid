package com.madbid.core.repository;

import com.madbid.core.model.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by nikolov.
 */
public interface ItemRepository extends MadbidRepository<Item> {
    @Query("SELECT i FROM Item i WHERE i.name LIKE ?1%")
    List<Item> findByNameLike(String name, Pageable itemsCount);

    Item findByName(@Param("name") String name);

    @Query("SELECT DISTINCT i FROM Item i LEFT JOIN FETCH i.itemInventories")
    public List<Item> findAllWithInventory();

    @Query("SELECT DISTINCT i FROM Item i LEFT JOIN FETCH i.itemInventories WHERE i.name LIKE ?1%")
    List<Item> findByNameLikeWithInventory(String name, Pageable itemsCount);
}

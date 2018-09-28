package com.madbid.core.repository;

import com.madbid.core.model.Item;
import com.madbid.core.model.Picture;

import java.util.List;

/**
 * Created by nikolov.
 */
public interface PictureRepository extends MadbidRepository<Picture> {
    public List<Picture> findByItem(Item item);
}

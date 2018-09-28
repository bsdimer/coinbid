package com.madbid.core.service;

import com.madbid.core.model.Item;
import com.madbid.core.model.Picture;
import com.madbid.core.repository.MadbidRepository;
import com.madbid.core.repository.PictureRepository;
import com.madbid.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nikolov.
 */
@Service
@Transactional
public class PictureService extends BaseService<Picture> implements ServiceContract<Picture> {

    @Autowired
    private PictureRepository pictureRepository;

    public List<Picture> findByItem(Item item) {
        return pictureRepository.findByItem(item);
    }

    @Override
    public MadbidRepository getRepository() {
        return pictureRepository;
    }
}

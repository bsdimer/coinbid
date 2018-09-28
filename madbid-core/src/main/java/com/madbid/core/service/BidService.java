package com.madbid.core.service;

import com.madbid.core.model.Bid;
import com.madbid.core.repository.BidRepository;
import com.madbid.core.repository.MadbidRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 8/17/14.
 */
@Service
@Transactional
public class BidService extends BaseService<Bid> {

    @Inject
    private BidRepository bidRepository;

    @Override
    public MadbidRepository getRepository() {
        return bidRepository;
    }
}

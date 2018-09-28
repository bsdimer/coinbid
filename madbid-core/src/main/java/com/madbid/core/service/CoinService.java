package com.madbid.core.service;

import com.madbid.core.model.Coin;
import com.madbid.core.repository.CoinRepository;
import com.madbid.core.repository.MadbidRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 8/17/14.
 */
@Service
@Transactional
public class CoinService extends BaseService<Coin> {

    @Inject
    private CoinRepository coinRepository;

    @Override
    public MadbidRepository getRepository() {
        return coinRepository;
    }

}

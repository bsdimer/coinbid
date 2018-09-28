package com.madbid.core.service;

import com.madbid.core.model.CoinPacket;
import com.madbid.core.repository.CoinPacketRepository;
import com.madbid.core.repository.MadbidRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 8/17/14.
 */
@Service
@Transactional
public class CoinPacketService extends BaseService<CoinPacket> {

    @Inject
    private CoinPacketRepository coinPacketRepository;

    @Override
    public MadbidRepository getRepository() {
        return coinPacketRepository;
    }

    public CoinPacket findByName(String name) {
        return coinPacketRepository.findByName(name);
    }
}
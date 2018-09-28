package com.madbid.core.repository;

import com.madbid.core.model.CoinPacket;

/**
 * Created by Dimer.
 */
public interface CoinPacketRepository extends MadbidRepository<CoinPacket> {
    CoinPacket findByName(String name);
}
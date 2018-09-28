package com.madbid.core.service;

import com.madbid.core.model.Auction;
import com.madbid.core.model.AuctionState;
import com.madbid.core.model.User;
import com.madbid.core.repository.AuctionRepository;
import com.madbid.core.repository.MadbidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by nikolov.
 */
@Service
@Transactional
public class AuctionService extends BaseService<Auction> implements ServiceContract<Auction> {

    @Autowired
    private AuctionRepository auctionRepository;

    public Auction findOneDeep(Long id) {
        return auctionRepository.findOneDeep(id);
    }

    @Override
    public MadbidRepository getRepository() {
        return auctionRepository;
    }

    public List<Auction> findActiveDeep() {
        return auctionRepository.findActiveDeep();
    }

    public List<Auction> findStartedDeep() {
        return auctionRepository.findStartedDeep();
    }

    public List<Auction> findByStateDeep(AuctionState auctionState) {
        return auctionRepository.findByStateDeep(auctionState);
    }

    public List<Auction> findByStateWithInventory(AuctionState auctionState) {
        return auctionRepository.findByStateWithInventory(auctionState);
    }

    public List<Auction> findByStatesIn(List<AuctionState> auctionStates) {
        return auctionRepository.findByStateIn(auctionStates);
    }

    public void updateTimer(Auction auction, int timer) {
        auctionRepository.updateTimer(auction, timer);
    }

    public BigDecimal getAuctionValue(Auction auction) {
        return auctionRepository.getAuctionValue(auction);
    }

    public List<Auction> getUserAuctions(User user) {
        return auctionRepository.getUserAuctions(user);
    }

    public long getUserBidCount(Auction auction, User user) {
        return auctionRepository.getUserBidCount(auction, user);
    }

    public List<Auction> getWonAuctions(User user) {
        return auctionRepository.getWonAuctions(user);
    }

}

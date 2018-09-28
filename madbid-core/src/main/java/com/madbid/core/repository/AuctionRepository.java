package com.madbid.core.repository;

import com.madbid.core.model.Auction;
import com.madbid.core.model.AuctionState;
import com.madbid.core.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by nikolov.
 */
public interface AuctionRepository extends MadbidRepository<Auction> {
    @Query("SELECT DISTINCT a FROM Auction a " +
            "LEFT JOIN FETCH a.itemInventory ii " +
            "LEFT JOIN FETCH a.bids b " +
            "LEFT JOIN FETCH ii.item i " +
            "WHERE a.state = ?1")
    List<Auction> findByStateDeep(AuctionState auctionState);

    @Query("SELECT DISTINCT a FROM Auction a " +
            "LEFT JOIN FETCH a.itemInventory ii " +
            "WHERE a.state = ?1")
    List<Auction> findByStateWithInventory(AuctionState auctionState);

    @Query("SELECT DISTINCT a FROM Auction a " +
            "LEFT JOIN FETCH a.itemInventory ii " +
            "LEFT JOIN FETCH ii.item i " +
            "WHERE a.state in ?1")
    List<Auction> findByStateIn(List<AuctionState> auctionStates);

    @Query("SELECT DISTINCT a FROM Auction a " +
            "LEFT JOIN FETCH a.itemInventory ii " +
            "LEFT JOIN FETCH a.bids b " +
            "LEFT JOIN FETCH ii.item i " +
            "WHERE a.state = 'STARTED' or a.state = 'PAUSED' or a.state = 'NOT_STARTED'")
    List<Auction> findActiveDeep();

    @Query("SELECT DISTINCT a FROM Auction a " +
            "LEFT JOIN FETCH a.itemInventory ii " +
            "LEFT JOIN FETCH a.bids b " +
            "LEFT JOIN FETCH ii.item i " +
            "WHERE a.state = 'STARTED'")
    List<Auction> findStartedDeep();

    @Query("SELECT DISTINCT a FROM Auction a " +
            "LEFT JOIN FETCH a.itemInventory ii " +
            "LEFT JOIN FETCH a.bids b " +
            "LEFT JOIN FETCH ii.item i")
    List<Auction> findAllDeep();

    @Query("SELECT DISTINCT a FROM Auction a " +
            "LEFT JOIN FETCH a.itemInventory ii " +
            "LEFT JOIN FETCH a.bids b " +
            "LEFT JOIN FETCH ii.item i")
    List<Auction> findAllDeep(Sort sort);

    @Modifying
    @Query("UPDATE Auction a set a.timer = ?2 WHERE a = ?1")
    /*@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)*/
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    void updateTimer(Auction auction, int timer);

    @Query(value = "SELECT DISTINCT a FROM Auction a " +
            "LEFT JOIN FETCH a.itemInventory ii " +
            "LEFT JOIN FETCH a.bids b " +
            "LEFT JOIN FETCH ii.item i",
            countQuery = "SELECT count(DISTINCT a) FROM Auction a")
    Page<Auction> findAllDeep(Pageable pageable);

    @Query("SELECT DISTINCT a FROM Auction a " +
            "LEFT JOIN FETCH a.itemInventory ii " +
            "LEFT JOIN FETCH a.bids b " +
            "LEFT JOIN FETCH ii.item i " +
            "WHERE a.id = ?1")
    Auction findOneDeep(Long id);

    @Query(value = "SELECT SUM(c.value) from Auction a " +
            "join a.bids b " +
            "join b.coins c " +
            "where a = ?1")
    BigDecimal getAuctionValue(Auction auction);

    @Query(value = "select distinct b.auction from Bid b where b.user = ?1")
    List<Auction> getUserAuctions(User user);

    @Query(value = "select count(b) from Bid b where b.auction = ?1 AND b.user = ?2")
    long getUserBidCount(Auction auction, User user);

    @Query(value = "select distinct a from Auction a" +
            " where a.lastBidder = ?1 and a.state = 'WON' or a.state = 'CLOSED'")
    List<Auction> getWonAuctions(User user);
}

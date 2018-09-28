package com.madbid.web.service;

import com.google.common.collect.Iterables;
import com.madbid.core.model.*;
import com.madbid.core.service.AuctionService;
import com.madbid.core.service.BidService;
import com.madbid.core.service.MadbidNotificationService;
import com.madbid.core.service.UserService;
import com.madbid.core.service.proxy.AuctionDTO;
import com.madbid.web.exceptions.BiddingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by dimer on 9/16/14.
 */
@Component
@Scope("prototype")
public class AuctionHandler {

    private static final Log logger = LogFactory.getLog(AuctionHandler.class);
    private static final String TOPIC_UPDATE_AUCTION_INTERNAL = "updateAuctionInternal";
    private static final String TOPIC_UPDATE_AUCTIONS_INTERNAL = "updateAuctionsInternal";
    private static final int AUCTION_BID_DIFF_INDEX = 3;

    @Inject
    private AuctionService auctionService;

    @Inject
    private UserService userService;

    @Inject
    private TimeService timeService;

    @Inject
    private TaskScheduler scheduler;

    @Inject
    private BidService bidService;

    @Inject
    private AuctionManager auctionManager;

    @Inject
    private MadbidNotificationService madbidNotificationService;

    @Autowired
    @Qualifier("topicJMSTemplate")
    private JmsTemplate jmsTemplate;

    private ScheduledFuture<?> future;
    private ScheduledFuture<?> winningFuture;
    private boolean botActive = true;


    public static final long HANDLER_TICK = 1000;

    private Auction auction;
    private boolean biddingBotEnabled = true;
    private List<User> bots = new ArrayList<>();

    private int count = 0;


    public AuctionHandler(Auction auction) {
        this.auction = auction;
    }

    public AuctionHandler() {
    }


    @Transactional
    public Auction start() throws IllegalArgumentException {
        logger.info(String.format("Starting auction: %s", auction.getId()));
        if (auction != null && auction.getState().equals(AuctionState.WON)) {
            throw new IllegalArgumentException("Cannot start auction which is already done.");
        }

        // ToDo: must set different bots to different auctions
        count = auction.getAuctionDuration();
        bots = userService.findByRole(Role.ROLE_BB);

        if (future == null) {
            future = scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    logger.debug(String.format("Auction id:%s tick", auction.getId()));
                    // check whether now is in working time
                    if (timeService.isWorkingTime()) {
                        if (auction.getState().equals(AuctionState.PAUSED)
                                || auction.getState().equals(AuctionState.NOT_STARTED)) {
                            logger.info(String.format("Auction %s starting", auction.getId()));
                            auction.setState(AuctionState.STARTED);
                            auctionService.save(auction);
                            jmsTemplate.convertAndSend(TOPIC_UPDATE_AUCTION_INTERNAL, AuctionDTO.fromAuction(auction));
                        }
                    } else {
                        if (auction.getState().equals(AuctionState.STARTED)) {
                            logger.info(String.format("Auction %s stopping", auction.getId()));
                            auction.setState(AuctionState.PAUSED);
                            auctionService.save(auction);
                            jmsTemplate.convertAndSend(TOPIC_UPDATE_AUCTION_INTERNAL, AuctionDTO.fromAuction(auction));
                        }
                    }
                }
            }, HANDLER_TICK);
        }

        if (winningFuture == null) {
            startWinningFuture(auction);
        }

        logger.debug(String.format("Auction %s started", auction != null ? auction.getId() : null));
        return auction;
    }

    @Transactional
    public Auction pause() {
        logger.info(String.format("Pausing auction: %s", auction.getId()));
        if (auction.getState().equals(AuctionState.WON)) {
            throw new IllegalArgumentException("Cannot start auction which is already ended.");
        }
        auction.setState(AuctionState.PAUSED);
        auction.setTimer(count);
        auctionService.save(auction);
        logger.info(String.format("Auction %s paused", auction.getId()));
        return auction;
    }

    @Transactional
    public Auction stop(boolean mayInterrupt) {
        stopFutures(mayInterrupt);
        auction.setState(AuctionState.PAUSED);
        auction.setTimer(count);
        auctionService.save(auction);
        return auction;
    }

    private void stopFutures(boolean mayInterrupt) {
        if (future != null) {
            future.cancel(mayInterrupt);
        }

        if (winningFuture != null) {
            winningFuture.cancel(mayInterrupt);
        }
    }

    /*@Transactional*/
    public Auction win(Auction auction) {
        logger.info(String.format("Auction %s WIN", auction.getId()));
        count = 0;
        auction.setState(AuctionState.WON);
        auction.setEndDateTime(LocalDateTime.now());
        auction.setTimer(0);
        auctionService.save(auction);
        madbidNotificationService.createAuctionWinningEmail(auction);
        return auction;
    }

    public synchronized Auction bid(User user) throws BiddingException {
        if (auction.getState().equals(AuctionState.STARTED)) {

            // Check whether the user has enough coins to bid
            List<Coin> userCoins = userService.findAvailableCoins(user);
            if (userCoins.size() < auction.getCreditsStep()) {
                throw new BiddingException("User doesn't have available coins");
            }

            // ToDo: should optimize this
            auction = auctionService.findOneDeep(auction.getId());

            stopWinningFuture();

            if (user.getRole().equals(Role.ROLE_USER)) {

                // Generate bid object
                Bid bid = new Bid();
                bid.setAuction(auction);
                bid.setUser(user);
                /*bid.setBidDateTime(LocalDateTime.now());*/
                bidService.save(bid);

                // Check whether the user is bb or not
                List<Coin> updated = new ArrayList<>();
                for (int i = 0; i < auction.getCreditsStep(); i++) {
                    Coin coin = Iterables.getLast(userCoins);
                    coin.setBid(bid);
                    updated.add(coin);
                    userCoins.remove(coin);
                }
                bid.getCoins().addAll(updated);

                auction.getBids().add(bid);
                auction.setLastBidder(user);
                auction.setTimer(auction.getAuctionDuration());
                count = auction.getAuctionDuration();

                // Save entities
                auctionService.save(auction);
                // Schedule winning timer
                startWinningFuture(auction);

                return auction;
            }
        } else {
            throw new BiddingException("The auction is not already started");
        }

        return auction;
    }

    private void stopWinningFuture() {
        if (winningFuture != null) {
            winningFuture.cancel(true);
            winningFuture = null;
        }
    }

    private void startWinningFuture(final Auction auction) {
        winningFuture = scheduler.scheduleWithFixedDelay(new Runnable() {
            Random random = new Random();

            @Override
            public void run() {
                synchronized (auction) {
                    if (auction.getState().equals(AuctionState.WON)) {
                        auctionManager.auctionWin(auction);
                        stopFutures(true);
                        return;
                    }

                    if (count > 0) {
                        // Last seconds biding
                        if (count < (random.nextInt(5) + AUCTION_BID_DIFF_INDEX) && botActive) {
                            BigDecimal auctionValue = auctionService.getAuctionValue(auction);
                            BigDecimal costPrice = auction.getItemInventory().getCostPrice();
                            float profitMargin = auction.getItemInventory().getItem().getProfitMargin();
                            // ToDo must fix the auctionPrice value
                            if (auctionValue == null || auctionValue.floatValue() < (costPrice.floatValue() * profitMargin)) {
                                botBid();
                                auctionManager.refreshAuctionView(auction);
                            }
                        }
                        count--;
                    } else {
                        logger.info("### AUCTION WIN ### #".concat(auction.getId().toString()));
                        if (auction.getLastBidder() != null && auction.getLastBidder().getRole().equals(Role.ROLE_USER)) {
                            win(auction);
                        } else {
                            if (botActive) {
                                botBid();
                            } else {
                                count = auction.getAuctionDuration();
                            }
                        }
                        auctionManager.refreshAuctionView(auction);
                    }
                }
            }
        }, LocalDateTime.now().toDate(), 1000);
    }

    @Transactional
    private void botBid() throws BiddingException {
        Random random = new Random();
        if (bots.size() <= 0) {
            logger.error("not enough free bots");
            return;
        }

        User bot = bots.get(random.nextInt(bots.size()));

        // ToDo: should optimize this
        auction = auctionService.findOneDeep(auction.getId());

        // Check whether this bb is the same
        if (auction.getLastBidder() == null || auction.getLastBidder().getUsername().equals(bot.getUsername())) {
            // Check whether this bb is first in the collection
            bot = ((bots.indexOf(bot) == 0) ? bots.get(bots.indexOf(bot) + 1) : bots.get(bots.indexOf(bot) - 1));
        }
        // auctionService.updateTimer(auction, auction.getAuctionDuration());
        count = auction.getAuctionDuration();
        auction.setTimer(auction.getAuctionDuration());

        // Generate bid object
        Bid bid = new Bid();
        bid.setAuction(auction);
        bid.setUser(bot);
        /*bid.setBidDateTime(LocalDateTime.now());*/
        bidService.save(bid);

        auction.getBids().add(bid);
        auction.setLastBidder(bot);
        auctionService.save(auction);
    }


    public Auction getAuction() {
        auction.setTimer(count);
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public boolean isBiddingBotEnabled() {
        return biddingBotEnabled;
    }

    public boolean getBotActive() {
        return botActive;
    }

    public void setBotActive(boolean botActive) {
        this.botActive = botActive;
    }
}

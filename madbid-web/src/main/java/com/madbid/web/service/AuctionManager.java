package com.madbid.web.service;

import com.madbid.core.model.Auction;
import com.madbid.core.model.User;
import com.madbid.core.service.AuctionService;
import com.madbid.core.service.UserService;
import com.madbid.core.service.converter.StompMessageConverter;
import com.madbid.core.service.proxy.AuctionDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dimer on 9/16/14.
 */
@Component
@Scope("singleton")
@Qualifier("auctionManager")
public class AuctionManager {
    private static final Log logger = LogFactory.getLog(AuctionManager.class);

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private AuctionHandlerFactory auctionHandlerFactory;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("topicJMSTemplate")
    private JmsTemplate jmsTemplate;

    // ToDo: must create a separateProject with JMS beans that will be in help of the other projects
    private static final String TOPIC_UPDATE_AUCTIONS = "updateAuctions";
    private static final String TOPIC_UPDATE_AUCTION = "updateAuction";
    private static final String TOPIC_CREATE_AUCTION = "createAuction";
    private static final String TOPIC_AUCTION_WIN = "auctionWin";

    // ToDo: should be implemented by property
    public static int AUCTION_BOT_COUNT = 2;

    private Map<Long, AuctionHandler> handlers = new HashMap();

    @PostConstruct
    private void init() throws Exception {
        logger.info("### schedule task auction execution daily ###");
        List<Auction> activeAuctions = auctionService.findStartedDeep();
        for (Auction auction : activeAuctions) {
            // Generate handler
            AuctionHandler handler = auctionHandlerFactory.createInstance();
            handler.setAuction(auction);
            handlers.put(auction.getId(), handler);
            handler.start();
        }
    }


    public Map<Long, AuctionHandler> getHandlers() {
        return handlers;
    }

    public AuctionHandler getHandler(Auction auction) {
        return handlers.get(auction.getId());
    }

    public Auction bid(Auction auction, User user) throws IllegalArgumentException {
        if (user == null || auction == null) {
            throw new IllegalArgumentException("None of the arguments cannot be Null");
        }

        return handlers.get(auction.getId()).bid(user);
    }

    @JmsListener(destination = "/topic/updateAuctionsInternal")
    public void handleUpdateAuctions(Object message) {
    }

    /**
     * Show auction by pushing it as a Stomp message toward the web socket
     */
    public synchronized void handleAuction(Auction auction) throws Exception {
        logger.info("Handler auction: " + auction.getId());
        if (!handlers.containsKey(auction.getId())) {
            // Auction is new auction
            AuctionHandler handler = auctionHandlerFactory.createInstance();
            handler.setAuction(auction);
            handlers.put(auction.getId(), handler);
            if (auction.isActive()) {
                handler.start();
            }
        } else {
            // Auction already persist in activeAuctions
            if (auction.isActive()) {
                handlers.get(auction.getId()).setAuction(auction);
                handlers.get(auction.getId()).start();
            } else {
                // Auction is already closed so remove it from ActiveAuctions
                handlers.remove(auction.getId());
            }
        }
    }

    @JmsListener(destination = "/topic/updateAuctionInternal", concurrency = "1")
    public void handleUpdateAuction(Object message) throws Exception {
        // ToDo: message converter doesn't working properly ?!?!
        logger.debug("Update auction internal: " + message);
        try {
            if (message instanceof TextMessage) {
                String body = ((TextMessage) message).getText();
                ObjectMapper mapper = new ObjectMapper();
                AuctionDTO dto = mapper.readValue(body, AuctionDTO.class);
                Auction auction = auctionService.findOneDeep(dto.getId());
                if (auction != null) {
                    handleAuction(auction);
                }
                refreshAuctionView(auction);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @JmsListener(destination = "/topic/createAuctionInternal", concurrency = "1")
    public void handleCreateAuction(Object message) {
        logger.debug("Create auction internal: " + message);
        try {
            if (message instanceof TextMessage) {
                String body = ((TextMessage) message).getText();
                ObjectMapper mapper = new ObjectMapper();
                AuctionDTO dto = mapper.readValue(body, AuctionDTO.class);
                Auction auction = auctionService.findOneDeep(dto.getId());
                if (auction != null) {
                    handleAuction(auction);
                }
                refreshAuctionView(auction);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public List<Auction> getAuctions() {
        List<Auction> auctions = new ArrayList<>();
        for (Map.Entry<Long, AuctionHandler> handler : handlers.entrySet()) {
            auctions.add(handler.getValue().getAuction());
        }
        return auctions;
    }

    public void refreshAuctionView(Auction auction) {
        // Refresh UI of the browser
        jmsTemplate.setMessageConverter(new StompMessageConverter());
        jmsTemplate.convertAndSend(TOPIC_UPDATE_AUCTION, AuctionDTO.fromAuction(auctionService.findOneDeep(auction.getId())));
    }

    public void auctionWin(Auction auction) {
        jmsTemplate.setMessageConverter(new StompMessageConverter());
        jmsTemplate.convertAndSend(TOPIC_AUCTION_WIN, AuctionDTO.fromAuction(auctionService.findOneDeep(auction.getId())));
    }
}

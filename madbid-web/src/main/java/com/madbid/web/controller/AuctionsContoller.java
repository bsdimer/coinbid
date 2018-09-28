package com.madbid.web.controller;

import com.madbid.core.model.*;
import com.madbid.core.service.AuctionService;
import com.madbid.core.service.UserService;
import com.madbid.core.service.proxy.AuctionDTO;
import com.madbid.web.service.AuctionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by dimer on 8/26/14.
 */
@Controller
@DependsOn(value = "auctionManager")
public class AuctionsContoller {

    private static final Log logger = LogFactory.getLog(AuctionsContoller.class);

    @Inject
    private AuctionService auctionService;

    @Inject
    private UserService userService;

    @Inject
    private ServletContext servletContext;

    @Inject
    private AuctionManager auctionManager;

    public static String servletContextPath;

    @PostConstruct
    public void init() {
        servletContextPath = servletContext.getContextPath();
    }

    @SubscribeMapping("/auctions")
    public List<AuctionDTO> getActiveAuctions(Principal principal) {
        logger.debug("Returns Active auctions to " + principal.getName());

        // ToDo: use Guava
        List<AuctionDTO> proxyCollection = new ArrayList<>();
        for (Auction auction : auctionManager.getAuctions()) {
            if (auction.getState().equals(AuctionState.STARTED)) {
                proxyCollection.add(AuctionDTO.fromAuction(auction));
            }
        }
        return proxyCollection;
    }

    @SubscribeMapping("/upcomingAuctions")
    public List<AuctionDTO> getUpcomingAuctions(Principal principal) {
        logger.debug("Returns Upcomming auctions to " + principal.getName());

        // ToDo: use Guava
        List<AuctionDTO> proxyCollection = new ArrayList<>();
        for (Auction auction : auctionService.findByStateDeep(AuctionState.NOT_STARTED)) {
            proxyCollection.add(AuctionDTO.fromAuction(auction));
        }
        return proxyCollection;
    }

    @SubscribeMapping("/pausedAuctions")
    public List<AuctionDTO> getPausedAuctions(Principal principal) {
        logger.debug("Returns Paused auctions to " + principal.getName());

        // ToDo: use Guava
        List<AuctionDTO> proxyCollection = new ArrayList<>();
        for (Auction auction : auctionService.findByStateDeep(AuctionState.PAUSED)) {
            proxyCollection.add(AuctionDTO.fromAuction(auction));
        }
        return proxyCollection;
    }

    @MessageMapping("/bid")
    @SendTo("/topic/updateAuction")
    public AuctionDTO executeBid(AuctionDTO auctionProxy, Principal principal) {
        logger.info("### bid from " + principal.getName());
        if (principal.getName().indexOf("guest") == 0) {
            // Bidding from the login page
        } else {
            // Biding after the successful login
            Auction auction = auctionService.findOne(auctionProxy.getId());
            User user = userService.findByEmail(principal.getName());
            if (auction != null && user != null) {
                return AuctionDTO.fromAuction(auctionManager.bid(auction, user));
            }
        }
        return null;
    }

    @MessageMapping("/updateWallet")
    @SendToUser("/queue/updateWallet")
    public String handleWalletUpdate(Principal principal) {
        if (principal.getName().indexOf("guest") == 0) {
            // Bidding from the login page
        } else {
            User user = userService.findByEmail(principal.getName());
            return String.format("%s", userService.findAvailableCoins(user).size());
        }
        return null;
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

    @RequestMapping(value = "/auction/approvewinner/{aid}/{uid}/{itemid}/{iisn}", method = RequestMethod.GET)
    public String approveWinnerPicture(@PathVariable Long aid,
                                       @PathVariable Long uid, @PathVariable Long itemid, @PathVariable String iisn) {
        Auction auction = auctionService.findOneDeep(aid);
        if(!auction.getState().equals(AuctionState.WON)
                || auction.getWinnerPicture() == null) {
            //STODO how to detect who is it
            logger.debug("approveWinnerPicture - Someone tries to hack us.");
            return "redirect:/";
        } else {
            User winner = userService.findUserWithCoinsDeep(auction.getLastBidder().getId());
            ItemInventory itemInventory = auction.getItemInventory();
            if(winner.getId().equals(uid)
                    && itemInventory.getId().equals(itemid)
                    && itemInventory.getSerialNumber().equals(iisn)) {
                auction.setAcceptedWinnerPicture(true);
                auctionService.save(auction);

                userService.addCoins(winner, new BigDecimal(1), 10, Currency.getInstance("BGL"));
            } else {
                //STODO how to detect who is it
                logger.debug("approveWinnerPicture - Someone tries to hack us.");
                return "redirect:/";
            }
        }
        return "redirect:/approvedwinpic=true";
    }
}

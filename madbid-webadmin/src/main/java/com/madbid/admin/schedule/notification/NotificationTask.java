package com.madbid.admin.schedule.notification;

import com.madbid.core.model.*;
import com.madbid.core.service.AuctionService;
import com.madbid.core.service.ItemInventoryService;
import com.madbid.core.service.MadbidNotificationService;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolov.
 */
@Component
public class NotificationTask {
    @Inject
    private ItemInventoryService itemInventoryService;

    @Inject
    private AuctionService auctionService;

    @Inject
    private MadbidNotificationService notificationService;

    public void sendAuctionNotification() {
        List<Auction> notPaidAuctions = auctionService.findByStateDeep(AuctionState.WON);
        LocalDateTime now = new LocalDateTime();
        for (Auction notPaidAuction : notPaidAuctions) {
            LocalDateTime endDateTime = notPaidAuction.getEndDateTime();
            int days = Days.daysBetween(endDateTime, now).getDays();
            //STODO days and notification status have to be in properties file
            if(days >= 14) {
                ItemInventory itemInventory = notPaidAuction.getItemInventory();
                itemInventory.setState(InventoryState.AVAILABLE);
                itemInventoryService.save(itemInventory);

                notificationService.createLossItemEmail(notPaidAuction);

                notPaidAuction.setState(AuctionState.CLOSED);
                notPaidAuction.setAuctionNotificationState(AuctionNotificationState.LOSE_ITEM);
                auctionService.save(notPaidAuction);
            } else if(days >= 13 && !AuctionNotificationState.SECOND_REMINDER_SENT.equals(notPaidAuction.getAuctionNotificationState())) {
                notificationService.createAuctionPaymentReminderEmail(notPaidAuction, 13);

                notPaidAuction.setAuctionNotificationState(AuctionNotificationState.SECOND_REMINDER_SENT);
                auctionService.save(notPaidAuction);
            } else if(days >= 7 && AuctionNotificationState.NO_NOTIFICATIONS_SENT.equals(notPaidAuction.getAuctionNotificationState())) {
                notificationService.createAuctionPaymentReminderEmail(notPaidAuction, 7);

                notPaidAuction.setAuctionNotificationState(AuctionNotificationState.FIRST_REMINDER_SENT);
                auctionService.save(notPaidAuction);
            }
        }
    }

    public void sendCurrentAuctionsCampaign() {
        List<AuctionState> auctionStates = new ArrayList<>();
        auctionStates.add(AuctionState.NOT_STARTED);
        auctionStates.add(AuctionState.PAUSED);
        auctionStates.add(AuctionState.STARTED);

        List<Auction> auctions = auctionService.findByStatesIn(auctionStates);
        if(auctions != null && !auctions.isEmpty()) {
            notificationService.createCurrentAuctionCampaign(auctions);
        }
    }

    public void sendAllWeekEvents() {
        System.out.println("");
    }
}

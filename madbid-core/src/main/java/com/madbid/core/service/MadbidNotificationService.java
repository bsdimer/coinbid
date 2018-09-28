package com.madbid.core.service;

import com.madbid.core.model.Auction;
import com.madbid.core.model.ItemInventory;
import com.madbid.core.model.User;
import com.madbid.notification.model.NotificationType;
import com.madbid.notification.service.impl.NotificationServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nikolov.
 */
@Service
@Transactional
public class MadbidNotificationService extends NotificationServiceImpl {
    //STODO move this value to properties file or db
    private static final String SENDER_EMAIL_ADDRESS = "thunderbid123@gmail.com";
    private static final String SUBJECT_PREFIX = "[\u0416\u044a\u043b\u0442\u0438\u0446\u0438] ";

    public void createAuctionWinningEmail(Auction auction) {
        User winner = auction.getLastBidder();
//        Map<String, Object> emailArguments = new HashMap<String, Object>();
//        emailArguments.put("template", "winningAuction");
//        emailArguments.put("firstname", winner.getFirstName());
//        emailArguments.put("lastname", winner.getLastName());
//        emailArguments.put("auction_id", auction.getId());
//        emailArguments.put("item_name", auction.getItemInventory().getItem().getName());
//        emailArguments.put("auction_item_price", auction.getAuctionPrice());
        //STODO i18n
        String subject = SUBJECT_PREFIX + "\u0421\u043f\u0435\u0447\u0435\u043b\u0432\u0430\u043d\u0435 \u043d\u0430 \u0430\u0440\u0442\u0438\u043a\u0443\u043b";
        String message = notificationGenerator.generateMessage("winningAuction", auction, winner, auction.getItemInventory().getItem());

        submitNotification(subject, message, SENDER_EMAIL_ADDRESS, winner.getEmail(), NotificationType.EMAIL_SMTP_RELAY);
    }

    public void createLossItemEmail(Auction auction) {
        User winner = auction.getLastBidder();
        Map<String, Object> emailArguments = new HashMap<String, Object>();
//        emailArguments.put("template", "lossItem");
//        emailArguments.put("firstname", winner.getFirstName());
//        emailArguments.put("lastname", winner.getLastName());
        //STODO move 14 days in properties file or db
        emailArguments.put("days", 14);
        //STODO i18n
        String subject = SUBJECT_PREFIX + "\u0417\u0430\u0433\u0443\u0431\u0430 \u043d\u0430 \u0430\u0440\u0442\u0438\u043a\u0443\u043b";
        String message = notificationGenerator.generateMessage("lossItem", emailArguments, winner);

        submitNotification(subject, message, SENDER_EMAIL_ADDRESS, winner.getEmail(), NotificationType.EMAIL_SMTP_RELAY);
    }

    public void createAuctionPaymentReminderEmail(Auction auction, long daysRemain) {
        User winner = auction.getLastBidder();
        Map<String, Object> emailArguments = new HashMap<String, Object>();
//        emailArguments.put("template", "itemPaymentReminder");
//        emailArguments.put("firstname", winner.getFirstName());
//        emailArguments.put("lastname", winner.getLastName());
        //STODO move 14 days in properties file or db
        emailArguments.put("days_remain", daysRemain);
        //STODO i18n
        String subject = SUBJECT_PREFIX + "\u041d\u0430\u043f\u043e\u043c\u043d\u044f\u043d\u0435 \u0437\u0430 \u043f\u043b\u0430\u0449\u0430\u043d\u0435 \u043d\u0430 \u0430\u0443\u043a\u0446\u0438\u043e\u043d";
        String message = notificationGenerator.generateMessage("itemPaymentReminder", emailArguments, winner);

        submitNotification(subject, message, SENDER_EMAIL_ADDRESS, winner.getEmail(), NotificationType.EMAIL_SMTP_RELAY);
    }

    public void createUserRegistrationConfirmationEmail(User user, String confirmationLink) {
        Map<String, Object> emailArguments = new HashMap<String, Object>();
//        emailArguments.put("template", "userRegistrationConfirmation");
//        emailArguments.put("firstname", user.getFirstName());
//        emailArguments.put("lastname", user.getLastName());
        emailArguments.put("confirmation_link", confirmationLink);
        //STODO i18n
        String subject = SUBJECT_PREFIX + "\u0414\u043e\u0431\u0440\u0435 \u0434\u043e\u0448\u043b\u0438 \u0432 \u0416\u044a\u043b\u0442\u0438\u0446\u0438";
        String message = notificationGenerator.generateMessage("userRegistrationConfirmation", emailArguments, user);

        submitNotification(subject, message, SENDER_EMAIL_ADDRESS, user.getEmail(), NotificationType.EMAIL_SMTP_RELAY);
    }

    public void createCurrentAuctionCampaign(List<Auction> auctions) {
        String subject = SUBJECT_PREFIX + "\u0422\u0435\u043a\u0443\u0449\u0438 \u0430\u0443\u043a\u0446\u0438\u043e\u043d\u0438";

        Map<String, Object> emailArguments = new HashMap<String, Object>();
        emailArguments.put("template", "currentAuctionsCampaign");
        //STODO Get host automatically
        emailArguments.put("image", "http://zig.zag.bg:8080/madbid-web/image/id/");
        emailArguments.put("auctions", auctions);
        String message = notificationGenerator.generateMessage(emailArguments);

        submitNotification(subject, message, SENDER_EMAIL_ADDRESS, null, NotificationType.EMAIL_MAILCHIMP);
    }

    public void createUserAuctionWinningPictureNotificationEmail(Auction wonAuction, User winner, ItemInventory itemInventory, List<User> receivers, String baseUrl) {
        Map<String, Object> emailArguments = new HashMap<String, Object>();
        //STODO link
        emailArguments.put("approve_winner_picture_link", baseUrl + "/madbid-web/auction/approvewinner/"
                + wonAuction.getId() +"/" +
                winner.getId() + "/" +
                wonAuction.getItemInventory().getId() + "/" +
                wonAuction.getItemInventory().getSerialNumber());
        emailArguments.put("image", "http://zig.zag.bg:8080/madbid-web/image/auction/" + wonAuction.getId());
        //STODO i18n
        String subject = SUBJECT_PREFIX + "\u041a\u0430\u0447\u0435\u043d\u0430 \u0435 \u0441\u043d\u0438\u043c\u043a\u0430 \u043e\u0442 \u043f\u043e\u0442\u0440\u0435\u0431\u0438\u0442\u0435\u043b \u0437\u0430 \u043e\u0434\u043e\u0431\u0440\u0435\u043d\u0438\u0435";
        String message = notificationGenerator.generateMessage("winnerPictureReceived", emailArguments, wonAuction, winner, itemInventory, itemInventory.getItem());

        for (User receiver : receivers) {
            submitNotification(subject, message, SENDER_EMAIL_ADDRESS, receiver.getEmail(), NotificationType.EMAIL_SMTP_RELAY);
        }
    }
}

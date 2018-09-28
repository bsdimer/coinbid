package com.madbid.core.model;

import com.madbid.core.utils.CurrencyUtils;
import com.madbid.notification.model.Placeholdable;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by nikolov.
 */
@Entity
@Table(name = "auctions")
/*@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)*/
public class Auction extends DateTimeAuditable implements Identifiable, Serializable, Placeholdable {
    private static final long serialVersionUID = 8721018896924813057L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_datetime")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime startDateTime;

    @Column(name = "end_datetime")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime endDateTime;

    @Lob
    @Column(name = "winner_picture")
    private byte[] winnerPicture;

    @Column(name = "accepted_winner_picture")
    private Boolean acceptedWinnerPicture;

    /**
     * Auction duration in seconds
     */
    @Column(name = "auction_duration", nullable = false)
    private int auctionDuration = 30;

    @Column(name = "credits_step", nullable = false)
    private int creditsStep = 1;

    @Column(name = "start_price", nullable = false)
    private BigDecimal startPrice = BigDecimal.valueOf(0d);

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_inventory")
    @Fetch(FetchMode.JOIN)
    private ItemInventory itemInventory;

    @OneToMany(mappedBy = "auction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Bid> bids = new ArrayList<Bid>();

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private AuctionState state = AuctionState.NOT_STARTED;

    @Column(name = "auction_notification_state")
    @Enumerated(EnumType.STRING)
    private AuctionNotificationState auctionNotificationState = AuctionNotificationState.NO_NOTIFICATIONS_SENT;

    // The value of the timer when the auction is paused due to the closed event
    // In the next day the auction start the timer from this value
    @Column(length = 3, nullable = false)
    private int timer = 0;

    @OneToOne
    private User lastBidder;

    @Column
    private Boolean enableBB;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getAuctionDuration() {
        return auctionDuration;
    }

    public void setAuctionDuration(int auctionDuration) {
        this.auctionDuration = auctionDuration;
    }

    public int getCreditsStep() {
        return creditsStep;
    }

    public void setCreditsStep(int creditsStep) {
        this.creditsStep = creditsStep;
    }

    public ItemInventory getItemInventory() {
        return itemInventory;
    }

    public void setItemInventory(ItemInventory itemInventory) {
        this.itemInventory = itemInventory;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public AuctionState getState() {
        return state;
    }

    public void setState(AuctionState auctionState) {
        this.state = auctionState;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public User getLastBidder() {
        return lastBidder;
    }

    public void setLastBidder(User user) {
        lastBidder = user;
    }

    /*@Transient
    public User getLastBidder() {
        if (this.getBids().size() == 0) {
            return null;
        }
        return bids.get(bids.size() - 1).getUser();
    }*/

    @Transient
    public Boolean isActive() {
        return getState().equals(AuctionState.STARTED);
    }

    @Override
    public boolean equals(Object value) {
        if (value instanceof Auction && ((Auction) value).getId() != null) {
            return ((Auction) value).getId().equals(this.getId());
        }
        return super.equals(value);
    }

    @Transient
    public BigDecimal getAuctionPrice() {
        return startPrice.add(new BigDecimal(bids.size() * 0.1));
    }


    public AuctionNotificationState getAuctionNotificationState() {
        return auctionNotificationState;
    }

    public void setAuctionNotificationState(AuctionNotificationState auctionNotificationState) {
        this.auctionNotificationState = auctionNotificationState;
    }

    @Transient
    public double getCurrentBiddingsPrices() {
        return getStartPrice().doubleValue() + (Double.parseDouble(String.valueOf(getBids().size())) / 100);
    }

    @Override
    public String getValueByFieldName(String fieldName) throws IllegalAccessException, NoSuchFieldException {
        Field field = this.getClass().getDeclaredField(fieldName);
        return field.get(this.getClass().cast(this)).toString();
    }

    @Transient
    public BigDecimal getBuyNowPrice() {
        if (getItemInventory() != null) {
            BigDecimal itemCost = getItemInventory().getCostPrice();
            float profitMargin = getItemInventory().getItem().getProfitMargin();
            return itemCost.multiply(BigDecimal.valueOf(profitMargin)).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.valueOf(0);
    }

    @Transient
    public Currency getBuyNowCurrency() {
        if (getItemInventory() != null) {
            return getItemInventory().getCostPriceCurrency();
        }
        return CurrencyUtils.defaultCurrency;
    }

    public Boolean getEnableBB() {
        return enableBB;
    }

    public void setEnableBB(Boolean enableBB) {
        this.enableBB = enableBB;
    }

    public byte[] getWinnerPicture() {
        return winnerPicture;
    }

    public void setWinnerPicture(byte[] winnerPicture) {
        this.winnerPicture = winnerPicture;
    }

    public Boolean getAcceptedWinnerPicture() {
        return acceptedWinnerPicture;
    }

    public void setAcceptedWinnerPicture(Boolean acceptedWinnerPicture) {
        this.acceptedWinnerPicture = acceptedWinnerPicture;
    }

    //ToDo: must override hashCode() method also
    /*@Override
    public int hashCode() {
    }*/

}

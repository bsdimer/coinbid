package com.madbid.core.service.proxy;

import com.madbid.core.model.Auction;
import com.madbid.core.model.AuctionState;
import com.madbid.core.model.Picture;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimer on 8/30/14.
 */
@JsonSerialize(using = AuctionDTOJsonSerializer.class)
@JsonDeserialize(using = AuctionDTOJsonDeserializer.class)
public class AuctionDTO implements Serializable {

    private static final long serialVersionUID = 1991453944412596146L;

    // ToDo: this should be changed by ko.computable(request.* + imageUrl) - in clientside JS
    public static final String servletContextPath = "/madbid-web";

    private static final Log logger = LogFactory.getLog(AuctionDTO.class);


    @JsonIgnore
    private Auction auction;

    private Long id;
    private String startDateTime;
    private String endDateTime;
    private int creditsStep;
    private int auctionDuration;
    private double startPrice;
    private double currentBiddingsPrice;
    private double buyNowPrice;
    private String buyNowCurrency;
    private double marketPrice;
    private String marketPriceCurrency;
    private String state;
    private List<String> pictures;
    private String pictureUrl;
    private double retailPrice;
    private String itemCurrency;
    private int bidsLen;
    private String itemName;
    private String itemDescription;
    private String itemLongDescription;
    private String lastBidder;
    private int timer;
    private long upcomingTimer;
    private double auctionPrice;

    protected Auction getAuction() {
        return auction;
    }

    protected void setAuction(Auction auction) {
        this.auction = auction;
    }


    public String getItemLongDescription() {
        return itemLongDescription;
    }

    public void setItemLongDescription(String itemLongDescription) {
        this.itemLongDescription = itemLongDescription;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getCreditsStep() {
        return creditsStep;
    }

    public void setCreditsStep(int creditsStep) {
        this.creditsStep = creditsStep;
    }

    public int getAuctionDuration() {
        return auctionDuration;
    }

    public void setAuctionDuration(int auctionDuration) {
        this.auctionDuration = auctionDuration;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getItemCurrency() {
        return itemCurrency;
    }

    public void setItemCurrency(String itemCurrency) {
        this.itemCurrency = itemCurrency;
    }

    public int getBidsLen() {
        return bidsLen;
    }

    public void setBidsLen(int bidsLen) {
        this.bidsLen = bidsLen;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getLastBidder() {
        return lastBidder;
    }

    public void setLastBidder(String lastBidder) {
        this.lastBidder = lastBidder;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCurrentBiddingsPrice() {
        return currentBiddingsPrice;
    }

    public void setCurrentBiddingsPrice(double currentBiddingsPrice) {
        this.currentBiddingsPrice = currentBiddingsPrice;
    }

    public double getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(double auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    public double getBuyNowPrice() {
        return buyNowPrice;
    }

    public void setBuyNowPrice(double buyNowPrice) {
        this.buyNowPrice = buyNowPrice;
    }

    public String getBuyNowCurrency() {
        return buyNowCurrency;
    }

    public void setBuyNowCurrency(String buyNowCurrency) {
        this.buyNowCurrency = buyNowCurrency;
    }


    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getMarketPriceCurrency() {
        return marketPriceCurrency;
    }

    public void setMarketPriceCurrency(String marketPriceCurrency) {
        this.marketPriceCurrency = marketPriceCurrency;
    }


    public long getUpcomingTimer() {
        return upcomingTimer;
    }

    public void setUpcomingTimer(long upcomingTimer) {
        this.upcomingTimer = upcomingTimer;
    }

    public AuctionDTO() {
        this.auction = new Auction();
    }

    public static AuctionDTO fromAuction(Auction auction) {
        AuctionDTO auctionDTO = new AuctionDTO();
        try {
            auctionDTO.setId(auction.getId());
            auctionDTO.setTimer(auction.getTimer());
            if (auction.getState().equals(AuctionState.NOT_STARTED)) {
                if (auction.getStartDateTime().isAfter(LocalDateTime.now())) {
                    auctionDTO.setUpcomingTimer(Seconds.secondsBetween(auction.getStartDateTime(), LocalDateTime.now()).getSeconds());
                } else {
                    auctionDTO.setUpcomingTimer(0);
                }
            }
            auctionDTO.setAuctionDuration(auction.getAuctionDuration());
            auctionDTO.setStartDateTime(auction.getStartDateTime() != null ? auction.getStartDateTime().toString() : null);
            auctionDTO.setEndDateTime(auction.getEndDateTime() != null ? auction.getEndDateTime().toString() : null);
            auctionDTO.setCreditsStep(auction.getCreditsStep());
            auctionDTO.setStartPrice(auction.getStartPrice() != null ? auction.getStartPrice().doubleValue() : null);
            auctionDTO.setCurrentBiddingsPrice(BigDecimal.valueOf(auction.getCurrentBiddingsPrices()).setScale(2, RoundingMode.HALF_UP).doubleValue());
            auctionDTO.setState(auction.getState().toString());

            List<String> pictures = new ArrayList<>();
            // Workaround for lack of Deserialization DTO
            if (auction.getItemInventory() != null) {
                for (Picture picture : auction.getItemInventory().getItem().getPictures()) {
                    if (servletContextPath != null && servletContextPath.length() > 0) {
                        pictures.add(String.format("%s/image/id/%s", servletContextPath, picture.getId()));
                    } else {
                        pictures.add("/image/id/" + picture.getId());
                    }
                }
                auctionDTO.setPictures(pictures);

                if (pictures.size() > 0) {
                    auctionDTO.setPictureUrl(pictures.get(0));
                }

                auctionDTO.setRetailPrice(auction.getItemInventory().getItem().getRetailPrice().doubleValue());
                auctionDTO.setItemCurrency(auction.getItemInventory().getItem().getRetailPriceCurrency().toString());
                auctionDTO.setItemName(auction.getItemInventory().getItem().getName());

                //ToDo: must use Guava for this cycle
                if (auction.getItemInventory().getItem().getDescription().length() > 0) {
                    String[] descRows = auction.getItemInventory().getItem().getDescription().split(System.getProperty("line.separator"));
                    String htmlDescription = "";
                    for (String stringRow : descRows) {
                        htmlDescription = htmlDescription + "<p>" + stringRow.trim() + "</p>";
                    }
                    auctionDTO.setItemDescription(htmlDescription);
                }

                auctionDTO.setItemLongDescription(auction.getItemInventory().getItem().getLongDescription());
                if (auction.getItemInventory().getItem().getMarketPrice() == null) {
                    auctionDTO.setMarketPrice(auction.getItemInventory().getCostPrice().doubleValue());
                } else {
                    auctionDTO.setMarketPrice(auction.getItemInventory().getItem().getMarketPrice().doubleValue());
                }
                auctionDTO.setMarketPriceCurrency(auction.getItemInventory().getItem().getMarketPriceCurrency().toString());
            }
            auctionDTO.setBidsLen(auction.getBids().size());
            auctionDTO.setLastBidder(auction.getLastBidder() == null ? "" : auction.getLastBidder().getUsername());
            auctionDTO.setAuctionPrice(auction.getAuctionPrice().doubleValue());
            auctionDTO.setBuyNowPrice(auction.getBuyNowPrice().doubleValue());
            auctionDTO.setBuyNowCurrency(auction.getBuyNowCurrency().getCurrencyCode());
        } catch (Exception e) {
            // throw new SerializationFailedException("cannot serialize auction to DTO object");
            logger.error("cannot serialize auction to DTO object");
        }
        return auctionDTO;
    }


}

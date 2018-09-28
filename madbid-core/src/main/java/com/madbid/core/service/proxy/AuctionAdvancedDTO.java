package com.madbid.core.service.proxy;

import com.madbid.core.model.Auction;
import com.madbid.core.model.Picture;
import com.madbid.core.model.User;
import com.madbid.core.service.AuctionService;
import org.springframework.core.serializer.support.SerializationFailedException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimer on 8/30/14.
 */
public class AuctionAdvancedDTO extends AuctionDTO {

    private static final long serialVersionUID = 112937944453596146L;
    private AuctionService auctionService;
    private long auctionBidCount;

    public AuctionAdvancedDTO(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    public static AuctionAdvancedDTO fromAuction(Auction auction, AuctionService auctionService, User currentUser) {
        AuctionAdvancedDTO auctionDTO = new AuctionAdvancedDTO(auctionService);
        try {
            auctionDTO.setId(auction.getId());
            auctionDTO.setTimer(auction.getTimer());
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
                auctionDTO.setItemDescription(auction.getItemInventory().getItem().getDescription());
            }
            auctionDTO.setBidsLen(auction.getBids().size());
            auctionDTO.setLastBidder(auction.getLastBidder() == null ? "" : auction.getLastBidder().getUsername());
            auctionDTO.setAuctionPrice(auction.getAuctionPrice().doubleValue());
            auctionDTO.setBuyNowPrice(auction.getBuyNowPrice().doubleValue());
            auctionDTO.setBuyNowCurrency(auction.getBuyNowCurrency().getCurrencyCode());
            auctionDTO.setAuctionBidCount(auctionService.getUserBidCount(auction, currentUser));
        } catch (Exception e) {
            throw new SerializationFailedException("cannot serialize auction to DTO object");
        }
        return auctionDTO;
    }

    public long getAuctionBidCount() {
        return auctionBidCount;
    }

    public void setAuctionBidCount(long auctionBidCount) {
        this.auctionBidCount = auctionBidCount;
    }
}

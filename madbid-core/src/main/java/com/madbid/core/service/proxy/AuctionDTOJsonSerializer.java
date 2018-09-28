package com.madbid.core.service.proxy;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * Created by dimer on 9/18/14.
 */
public class AuctionDTOJsonSerializer extends JsonSerializer<AuctionDTO> {
    @Override
    public void serialize(AuctionDTO value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeNumberField("retailPrice", value.getRetailPrice());
        jgen.writeStringField("startDateTime", value.getStartDateTime());
        jgen.writeStringField("endDateTime", value.getEndDateTime());
        jgen.writeNumberField("auctionDuration", value.getAuctionDuration());
        jgen.writeNumberField("creditsStep", value.getCreditsStep());
        jgen.writeNumberField("startPrice", value.getStartPrice());
        jgen.writeNumberField("currentBiddingsPrice", value.getCurrentBiddingsPrice());
        jgen.writeNumberField("auctionPrice", value.getAuctionPrice());
        jgen.writeNumberField("timer", value.getTimer());
        jgen.writeStringField("lastBidder", value.getLastBidder());
        jgen.writeStringField("itemDescription", value.getItemDescription());
        jgen.writeStringField("itemLongDescription", value.getItemLongDescription());
        jgen.writeStringField("itemCurrency", value.getItemCurrency());
        jgen.writeStringField("pictureUrl", value.getPictureUrl());
        jgen.writeNumberField("bidsLen", value.getBidsLen());
        jgen.writeStringField("itemName", value.getItemName());
        jgen.writeStringField("state", value.getState());
        jgen.writeStringField("buyNowPrice", String.valueOf(value.getBuyNowPrice()));
        jgen.writeStringField("buyNowCurrency", value.getBuyNowCurrency());
        jgen.writeNumberField("marketPrice", value.getMarketPrice());
        jgen.writeStringField("marketPriceCurrency", value.getMarketPriceCurrency());
        jgen.writeNumberField("upcomingTimer",value.getUpcomingTimer());
        jgen.writeEndObject();
    }
}

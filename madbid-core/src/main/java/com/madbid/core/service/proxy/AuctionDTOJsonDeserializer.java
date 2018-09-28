package com.madbid.core.service.proxy;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

/**
 * Created by dimer on 9/18/14.
 */
public class AuctionDTOJsonDeserializer extends JsonDeserializer<AuctionDTO> {

    @Override
    public AuctionDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setId(node.get("id").asLong());
        // ToDo: Must deserialize
        /*auctionDTO.setTimer(node.get("timer").asInt());
        auctionDTO.setUpcomingTimer(node.get("upcomingTimer").asInt());

        auctionDTO.setAuctionDuration(node.get("auctionDuration").asInt());
        auctionDTO.setStartDateTime(node.get("startDateTime").asText());
        auctionDTO.setEndDateTime(node.get("startDateTime").asText());
        auctionDTO.setCreditsStep(node.get("creditStep").asInt());
        auctionDTO.setStartPrice(node.get("startPrice").asDouble());
        auctionDTO.setCurrentBiddingsPrice(node.get("currentBiddingPrice").asDouble());
        //*/
        return auctionDTO;
    }
}

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
public class MessageCriteriaDTOJsonDeserializer extends JsonDeserializer<MessageCriteriaDTO> {

    @Override
    public MessageCriteriaDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        MessageCriteriaDTO messageCriteriaDTO = new MessageCriteriaDTO();
        messageCriteriaDTO.setPage(node.get("page").getIntValue());
        messageCriteriaDTO.setPageSize(node.get("pageSize").getIntValue());

        return messageCriteriaDTO;
    }
}

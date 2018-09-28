package com.madbid.core.service.proxy;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class ResponseDTOJsonDeserializer extends JsonDeserializer<ResponseDTO> {

    @Override
    public ResponseDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSubscribe(node.get("subscribe").getBooleanValue());

        return responseDTO;
    }
}

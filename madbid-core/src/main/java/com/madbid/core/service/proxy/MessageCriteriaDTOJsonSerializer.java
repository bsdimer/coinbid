package com.madbid.core.service.proxy;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * Created by dimer on 9/18/14.
 */
public class MessageCriteriaDTOJsonSerializer extends JsonSerializer<MessageCriteriaDTO> {
    @Override
    public void serialize(MessageCriteriaDTO value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("page", value.getPage());
        jgen.writeNumberField("pageSize", value.getPageSize());

        jgen.writeEndObject();
    }
}

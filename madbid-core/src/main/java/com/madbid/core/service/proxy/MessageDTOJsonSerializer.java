package com.madbid.core.service.proxy;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * Created by dimer on 9/18/14.
 */
public class MessageDTOJsonSerializer extends JsonSerializer<MessageDTO> {
    @Override
    public void serialize(MessageDTO value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("subject", value.getSubject());
        jgen.writeStringField("text", value.getText());
        jgen.writeBooleanField("read", value.getRead());
        jgen.writeStringField("sender", value.getSender());
        jgen.writeStringField("date", value.getDate());

        jgen.writeEndObject();
    }
}

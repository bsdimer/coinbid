package com.madbid.core.service.proxy;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class ResponseDTOJsonSerializer extends JsonSerializer<ResponseDTO> {
    @Override
    public void serialize(ResponseDTO value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeBooleanField("subscribe", value.getSubscribe());

        jgen.writeEndObject();
    }
}

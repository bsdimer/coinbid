package com.madbid.core.service.proxy;

import com.madbid.core.model.Message;
import com.madbid.core.model.UserMessage;
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
public class MessageDTOJsonDeserializer extends JsonDeserializer<MessageDTO> {

    @Override
    public MessageDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        UserMessage userMessage = new UserMessage();
        userMessage.setId(node.get("id").getLongValue());
        userMessage.setRead(node.get("read").getBooleanValue());

        Message message = new Message();
        message.setSubject(node.get("subject").getTextValue());
        message.setText(node.get("text").getTextValue());

        userMessage.setMessage(message);

        MessageDTO messageDTO = MessageDTO.fromUserMessage(userMessage);
        messageDTO.setSender(node.get("sender").getTextValue());
        messageDTO.setDate(node.get("date").getTextValue());

        return messageDTO;
    }
}

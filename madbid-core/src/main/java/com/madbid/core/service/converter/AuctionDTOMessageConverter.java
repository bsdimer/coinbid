package com.madbid.core.service.converter;

import com.madbid.core.service.proxy.AuctionDTO;
import org.apache.activemq.command.ActiveMQMessage;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * Created by dimer on 9/17/14.
 */
public class AuctionDTOMessageConverter implements MessageConverter {

    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        ObjectMapper mapper = new ObjectMapper();
        String serialized = "";
        try {
            serialized = mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextMessage message = session.createTextMessage();
        message.setText(serialized);
        return message;
    }

    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        ObjectMapper mapper = new ObjectMapper();
        AuctionDTO dto = new AuctionDTO();
        try {
            dto = mapper.readValue((String) ((ActiveMQMessage) message).getProperty("body"), AuctionDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dto;
    }
}

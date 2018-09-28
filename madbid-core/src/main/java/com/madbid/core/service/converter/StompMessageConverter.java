package com.madbid.core.service.converter;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * Created by dimer on 9/18/14.
 */
public class StompMessageConverter implements MessageConverter {
    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {

        /*TextMessage message = session.createTextMessage();
        message.setText("{}");
        message.setStringProperty("content-type", "application/json;charset=UTF-8");
        return message;*/

        try {
            ObjectMapper mapper = new ObjectMapper();
            String serialized = mapper.writeValueAsString(object);
            TextMessage message = session.createTextMessage();
            message.setText(serialized);
            message.setStringProperty("content-type", "application/json;charset=UTF-8");
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        return null;
    }
}

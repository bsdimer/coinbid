package com.madbid.web.service;

import com.madbid.core.service.UserService;
import com.madbid.core.service.proxy.MessageDTO;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

/**
 * Created by nikolov.
 */
@Component
@Scope("singleton")
@Qualifier("messageQueue")
public class MessageQueue {
    private static final Log logger = LogFactory.getLog(MessageQueue.class);

    private SimpMessageSendingOperations simpMessageSending;

    @Autowired
    public MessageQueue(SimpMessageSendingOperations jmsTemplate) {
        this.simpMessageSending = jmsTemplate;
    }

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("topicJMSTemplate")
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "/queue/messageInternal", concurrency = "1")
    public void handleMessageToUser(Object message) {
        logger.debug("User message received internal: " + message);
        try {
            if (message instanceof ActiveMQObjectMessage) {
                ActiveMQObjectMessage activeMQObjectMessage = (ActiveMQObjectMessage)message;
                MessageDTO messageDTO = (MessageDTO)activeMQObjectMessage.getObject();

                simpMessageSending.convertAndSendToUser(messageDTO.getReceiverEmail(), "/queue/updateMessage", messageDTO);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @JmsListener(destination = "/topic/messageInternal", concurrency = "1")
    public void handleUpdateMessageToUsers(Object message) {
        logger.debug("User message received internal: " + message);
        try {
            if (message instanceof TextMessage) {
                String body = ((TextMessage) message).getText();
                ObjectMapper mapper = new ObjectMapper();
                MessageDTO messageDTO = mapper.readValue(body, MessageDTO.class);

                jmsTemplate.convertAndSend("updateMessage", messageDTO);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

package com.madbid.web.controller;

import com.madbid.core.model.Message;
import com.madbid.core.model.Role;
import com.madbid.core.model.User;
import com.madbid.core.model.UserMessage;
import com.madbid.core.service.MessageService;
import com.madbid.core.service.UserMessageService;
import com.madbid.core.service.UserService;
import com.madbid.core.service.proxy.MessageCriteriaDTO;
import com.madbid.core.service.proxy.MessageDTO;
import com.madbid.core.service.proxy.MessageDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

/**
 * Created by dimer on 10/29/14.
 */
@Controller
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private UserService userService;

    private SimpMessageSendingOperations jmsTemplate;

    @Autowired
    public MessageController(SimpMessageSendingOperations jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @MessageMapping("/messages")
    @SendToUser("/queue/messages")
    public MessageDataDTO getMessages(MessageCriteriaDTO messageCriteriaDTO, Principal principal) {
        if (principal.getName().indexOf("guest") != 0) {
            User user = userService.findByEmail(principal.getName());
            LOGGER.info("Get user messages page: " + messageCriteriaDTO.getPage() + ", page size: " + messageCriteriaDTO.getPageSize());
            List<UserMessage> userMessages = userMessageService.findByUserDesc(user, messageCriteriaDTO.getPage(), messageCriteriaDTO.getPageSize());
            List<MessageDTO> messageDTOs = MessageDTO.fromUserMessages(userMessages);
            MessageDataDTO messageDataDTO = new MessageDataDTO();
            if(messageCriteriaDTO.getPage() == 0) {
                Long totalMessageCount = userMessageService.findTotalMessageCount(user);
                messageDataDTO.setTotalMessagesCount(totalMessageCount);

                Long unreadMessagesCount = userMessageService.findUnreadMessagesCount(user);
                messageDataDTO.setUnreadMessagesCount(unreadMessagesCount);
            }
            messageDataDTO.setMessages(messageDTOs);
            return messageDataDTO;
        }

        return null;
    }

    @MessageMapping("/sent/messages")
    @SendToUser("/queue/sent/messages")
    public MessageDataDTO getSentMessages(MessageCriteriaDTO messageCriteriaDTO, Principal principal) {
        if (principal.getName().indexOf("guest") != 0) {
            User user = userService.findByEmail(principal.getName());
            LOGGER.info("Get user messages page: " + messageCriteriaDTO.getPage() + ", page size: " + messageCriteriaDTO.getPageSize());
            List<Message> messages = messageService.findBySender(user, messageCriteriaDTO.getPage(), messageCriteriaDTO.getPageSize());
            List<MessageDTO> messageDTOs = MessageDTO.fromMessages(messages);
            MessageDataDTO messageDataDTO = new MessageDataDTO();
            if(messageCriteriaDTO.getPage() == 0) {
                Long totalMessageCount = messageService.findTotalMessageCount(user);
                messageDataDTO.setTotalMessagesCount(totalMessageCount);
            }
            messageDataDTO.setMessages(messageDTOs);
            return messageDataDTO;
        }

        return null;
    }

    @MessageMapping("/message/read")
    public void readMessage(MessageDTO messageDTO, Principal principal) {
        if (principal.getName().indexOf("guest") != 0) {
            User user = userService.findByEmail(principal.getName());
            LOGGER.debug("Message read: " + messageDTO.getId());
            UserMessage message = userMessageService.findOne(messageDTO.getId());
            message.setRead(true);
            userMessageService.save(message);
        }
    }

    @MessageMapping("/message/send")
    @SendToUser("/queue/sent/message")
    public MessageDTO sendMessage(MessageDTO messageDTO, Principal principal) {
        if (principal.getName().indexOf("guest") != 0) {
            User user = userService.findByEmail(principal.getName());
            List<User> administrators = userService.findByRole(Role.ROLE_ADMIN);
            LOGGER.debug("Message send to administrators: " + messageDTO.getId());

            Message message = new Message();
            message.setSubject(messageDTO.getSubject());
            message.setText(messageDTO.getText());
            message.setSender(userService.findByEmail(principal.getName()));
            message = messageService.save(message);

            for (User administrator : administrators) {
                UserMessage userMessage = new UserMessage();
                userMessage.setRead(false);
                userMessage.setMessage(message);
                userMessage.setUser(administrator);
                userMessageService.save(userMessage);
            }
            return MessageDTO.fromMessage(message);
        }

        return null;
    }
}

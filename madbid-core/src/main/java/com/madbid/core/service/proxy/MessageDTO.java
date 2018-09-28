package com.madbid.core.service.proxy;

import com.madbid.core.model.Message;
import com.madbid.core.model.UserMessage;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.core.serializer.support.SerializationFailedException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimer on 8/30/14.
 */
@JsonSerialize(using = MessageDTOJsonSerializer.class)
@JsonDeserialize(using = MessageDTOJsonDeserializer.class)
public class MessageDTO implements Serializable {
    //2015-03-04T09:41:18.000
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final long serialVersionUID = 1991453944412597254L;

    private Long id;
    private String subject;
    private String text;
    private Boolean read;
    private String date;
    private String sender;
    private String receiver;
    private String receiverEmail;

    public static MessageDTO fromMessage(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            messageDTO.setId(message.getId());
            messageDTO.setSubject(message.getSubject());
            messageDTO.setText(message.getText());
            messageDTO.setDate(message.getCreated() != null ? DATE_FORMAT.format(message.getCreated().toDate()) : null);
            messageDTO.setSender(message.getSender() != null ? message.getSender().getFullName() : null);
            //Receiver is always administrator
//            messageDTO.setReceiver(userMessage.getMessage().getSender().getFullName());
        } catch (Exception e) {
            throw new SerializationFailedException("cannot serialize user message to DTO object");
        }
        return messageDTO;
    }

    public static List<MessageDTO> fromMessages(List<Message> messages) {
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message : messages) {
            messageDTOs.add(fromMessage(message));
        }
        return messageDTOs;
    }

    public static MessageDTO fromUserMessage(UserMessage userMessage) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            messageDTO.setId(userMessage.getId());
            messageDTO.setSubject(userMessage.getMessage().getSubject());
            messageDTO.setText(userMessage.getMessage().getText());
            messageDTO.setRead(userMessage.isRead());
            messageDTO.setDate(userMessage.getCreated() != null ? DATE_FORMAT.format(userMessage.getCreated().toDate()) : null);
            messageDTO.setSender(userMessage.getMessage().getSender() != null ? userMessage.getMessage().getSender().getFullName() : null);
            messageDTO.setReceiverEmail(userMessage.getUser() != null ? userMessage.getUser().getEmail() : null);
        } catch (Exception e) {
            throw new SerializationFailedException("cannot serialize user message to DTO object");
        }
        return messageDTO;
    }

    public static List<MessageDTO> fromUserMessages(List<UserMessage> userMessages) {
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (UserMessage userMessage : userMessages) {
            messageDTOs.add(fromUserMessage(userMessage));
        }
        return messageDTOs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
}

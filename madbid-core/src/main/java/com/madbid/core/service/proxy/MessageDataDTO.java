package com.madbid.core.service.proxy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nikolov.
 */
public class MessageDataDTO implements Serializable {
    private Long totalMessagesCount;
    private Long unreadMessagesCount;
    private List<MessageDTO> messages;

    public Long getTotalMessagesCount() {
        return totalMessagesCount;
    }

    public void setTotalMessagesCount(Long totalMessagesCount) {
        this.totalMessagesCount = totalMessagesCount;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

    public Long getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Long unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }
}

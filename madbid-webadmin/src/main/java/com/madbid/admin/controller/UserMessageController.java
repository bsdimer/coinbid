package com.madbid.admin.controller;

import com.madbid.admin.utils.LocaleUtils;
import com.madbid.core.model.Message;
import com.madbid.core.model.User;
import com.madbid.core.model.UserMessage;
import com.madbid.core.security.dto.UserDetails;
import com.madbid.core.service.MessageService;
import com.madbid.core.service.UserMessageService;
import com.madbid.core.service.UserService;
import com.madbid.core.service.converter.StompMessageConverter;
import com.madbid.core.service.proxy.MessageDTO;
import com.madbid.lazyLoaders.UserMessageLazyLoader;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nikolov on 3/11/14.
 */
@Component("userMessageController")
@Scope("prototype")
@Transactional
public class UserMessageController extends CommonTabBean implements Serializable, ITabBean {
    private static final String USER_MESSAGE_SUCCESSFULLY_SENT = "user.message.send.message";

    private UserMessageLazyLoader userMessages;
    private UserMessage selectedUserMessage;
    private List<UserMessage> filteredUserMessages;
    private List<User> selectedUsers;
    private String subject;
    private String text;
    private Boolean sendToAll;
    private boolean showDetails = false;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("queueJMSTemplate")
    private JmsTemplate jmsQueueTemplate;

    @Autowired
    @Qualifier("topicJMSTemplate")
    private JmsTemplate jmsTopicTemplate;

    @PostConstruct
    private void init() {
        selectedUsers = new ArrayList<>();
        UserDetails loggedUser = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findOne(loggedUser.getId());
        userMessages = new UserMessageLazyLoader(userMessageService, user);
    }

    public void onRowSelect(SelectEvent event) {
        if(!showDetails) {
            showDetails = true;
//            RequestContext.getCurrentInstance().execute("PF('messageDetailsPanel').show();");
        }
    }

    public void send() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User currentUser = userService.findByEmail(email);

            Message message = new Message();
            message.setText(text);
            message.setSubject(subject);
            message.setSender(currentUser);
            message = messageService.save(message);

            if(sendToAll) {
                Date creationDate = userMessageService.addMessageToAllUsers(message.getId());
                MessageDTO messageDTO = MessageDTO.fromMessage(message);
                messageDTO.setRead(false);
                messageDTO.setDate(creationDate.toString());

                jmsTopicTemplate.setMessageConverter(new StompMessageConverter());
                jmsTopicTemplate.convertAndSend("messageInternal", messageDTO);
            } else {
                for (User user : selectedUsers) {
                    UserMessage userMessage = new UserMessage();
                    userMessage.setMessage(message);
                    userMessage.setUser(user);
                    userMessageService.save(userMessage);

                    MessageDTO messageDTO = MessageDTO.fromUserMessage(userMessage);
                    jmsQueueTemplate.convertAndSend("messageInternal", messageDTO);
                }
            }
            RequestContext.getCurrentInstance().execute("PF('sendUserMessageDialog').hide();");
            selectedUsers = new ArrayList<>();
            subject = null;
            text = null;
            addMessage(LocaleUtils.getLocaliziedMessage(USER_MESSAGE_SUCCESSFULLY_SENT), "", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            //STODO throw exception
        }
    }

    public void addMessage(String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public LazyDataModel<UserMessage> getUserMessages() {
        return userMessages;
    }

    public List<User> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getSendToAll() {
        return sendToAll;
    }

    public void setSendToAll(Boolean sendToAll) {
        this.sendToAll = sendToAll;
    }

    public UserMessage getSelectedUserMessage() {
        return selectedUserMessage;
    }

    public void setSelectedUserMessage(UserMessage selectedUserMessage) {
        this.selectedUserMessage = selectedUserMessage;
    }

    public List<UserMessage> getFilteredUserMessages() {
        return filteredUserMessages;
    }

    public void setFilteredUserMessages(List<UserMessage> filteredUserMessages) {
        this.filteredUserMessages = filteredUserMessages;
    }

    public boolean isShowDetails() {
        return showDetails;
    }

    public void setShowDetails(boolean showDetails) {
        this.showDetails = showDetails;
    }
}
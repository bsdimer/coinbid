package com.madbid.notification.sender.sms;

import com.madbid.notification.model.Notification;
import com.madbid.notification.sender.NotificationSender;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "smsNotificationSender")
public class SMSNotificationSender implements NotificationSender, InitializingBean {
    @Autowired
    private SMSProperties smsProperties;

    @Override
    public void sendNotification(Notification notification) {
        //Implementation of sms sending
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //load sms properties
    }
}

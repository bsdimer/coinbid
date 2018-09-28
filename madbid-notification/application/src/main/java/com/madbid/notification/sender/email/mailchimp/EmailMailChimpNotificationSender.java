package com.madbid.notification.sender.email.mailchimp;

import com.ecwid.mailchimp.MailChimpException;
import com.madbid.mailchimp.MailChimpClient;
import com.madbid.notification.exception.NotificationException;
import com.madbid.notification.model.Notification;
import com.madbid.notification.model.NotificationType;
import com.madbid.notification.sender.NotificationSender;
import com.madbid.notification.service.NotificationService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by alexn on 29.10.2014 г..
 */
@Component(value = "emailMailChimpNotificationSender")
public class EmailMailChimpNotificationSender implements NotificationSender, InitializingBean {
    @Autowired
    private MailChimpClient mailChimpClient;

    @Override
    public void sendNotification(Notification notification) {
        try {
            mailChimpClient.sendCampaign(notification.getSubject(), notification.getSender(), "Administrator", null, notification.getText());
        } catch (IOException e) {
            throw new NotificationException(e);
        } catch (MailChimpException e) {
            throw new NotificationException(e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public static void main(String[] args) {
        Notification notification = new Notification();
        notification.setType(NotificationType.EMAIL_SMTP_RELAY);
        notification.setReceiver("alexn_test@abv.bg");
        notification.setSubject("Test Notifications");
        notification.setText("test notification ....");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/spring/applicationContext-notification-app.xml");
        NotificationService notificationService = context.getBean(NotificationService.class);
        Notification notification1 = notificationService.findOne(1l);
        EmailMailChimpNotificationSender sender = context.getBean(EmailMailChimpNotificationSender.class);
        sender.sendNotification(notification1);
    }
}

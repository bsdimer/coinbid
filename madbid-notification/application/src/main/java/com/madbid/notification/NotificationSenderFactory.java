package com.madbid.notification;

import com.madbid.notification.exception.NotificationException;
import com.madbid.notification.model.NotificationType;
import com.madbid.notification.sender.NotificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NotificationSenderFactory {
    @Autowired
    @Qualifier("emailSMTPRelayNotificationSender")
    private NotificationSender emailSMTPRelayNotificationSender;

    @Autowired
    @Qualifier("emailMailChimpNotificationSender")
    private NotificationSender emailMailChimpNotificationSender;

    @Autowired
    @Qualifier("smsNotificationSender")
    private NotificationSender smsNotificationSender;

    public NotificationSender getNotificationSender(
            NotificationType notificationType) {
        switch (notificationType) {
            case EMAIL_SMTP_RELAY:
                return emailSMTPRelayNotificationSender;
            case EMAIL_MAILCHIMP:
                return emailMailChimpNotificationSender;
            case SMS:
                return smsNotificationSender;
            default:
                throw new NotificationException(
                        "Notification type is not supported");
        }
    }
}

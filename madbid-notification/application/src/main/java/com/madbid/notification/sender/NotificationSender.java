package com.madbid.notification.sender;


import com.madbid.notification.model.Notification;

public interface NotificationSender {
    void sendNotification(Notification notification);
}

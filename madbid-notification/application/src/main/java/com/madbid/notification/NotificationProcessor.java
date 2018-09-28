package com.madbid.notification;

import com.madbid.notification.model.Notification;
import com.madbid.notification.model.NotificationStatus;
import com.madbid.notification.repository.NotificationRepository;
import com.madbid.notification.sender.NotificationSender;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationProcessor {
    private static final Logger LOGGER = Logger.getLogger(NotificationProcessor.class);

    @Autowired
    private NotificationSenderFactory notificationSenderFactory;

    @Autowired
    private NotificationRepository notificationRepository;
    
    @Value("${notification.count.for.delay}")
    private int notificationsCountForDelay;

    public void processNotifications() {
    	List<Notification> notifications = null;
    	
		Pageable notificationsCount = new PageRequest(0, notificationsCountForDelay);
    	notifications = notificationRepository.findNotifications(NotificationStatus.NEW, notificationsCount);
    	if (notifications != null && !notifications.isEmpty()) {
    		for (Notification notification : notifications) {
    			processNotification(notification);
    		}
    	}
    }

	private void processNotification(Notification notification) {
		try {
		    LOGGER.info("Start sending notification - " + notification);
		    notification.setStatus(NotificationStatus.INPROCESS);
		    notificationRepository.save(notification);

		    NotificationSender notificationSender = notificationSenderFactory.getNotificationSender(notification.getType());
		    notificationSender.sendNotification(notification);

		    notification.setStatus(NotificationStatus.SENT);
            notification.setProcessedDateTime(new LocalDateTime());
		    notificationRepository.save(notification);

		    LOGGER.info("Notification with id:" + notification.getId() + " is sent successfully");
		} catch (Exception exception) {
		    if (notification != null) {
		        notification.setStatus(NotificationStatus.FAILED);
		        notificationRepository.save(notification);
		        LOGGER.error("Error occurred while sending the notification");
		    } else {
		        LOGGER.error("Error occurred while trying to find next notification");
		    }
		}
	}
}

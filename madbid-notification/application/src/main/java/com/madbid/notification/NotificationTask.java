package com.madbid.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationTask implements Runnable {

	@Autowired
	private NotificationProcessor notificationProcessor;
	
	@Override
	public void run() {
		notificationProcessor.processNotifications();
	}
}

package com.madbid.notification;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/spring/applicationContext-notification-app.xml");
		NotificationScheduler notificationScheduler = context.getBean(NotificationScheduler.class);
		notificationScheduler.start();
	}
}

package com.madbid.notification;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationScheduler {
	private static final Logger LOGGER = Logger.getLogger(NotificationScheduler.class);
	
	@Autowired
	private NotificationTask notificationTask;
	
    @Value("${notification.job.delay}")
    private int notificationDelay;
    
    @Value("${notification.job.initial.delay}")
    private int initialDelay;
	
	private ScheduledExecutorService scheduler;
	
	public void start() {
		scheduler = Executors
				.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(
				notificationTask, initialDelay,
				notificationDelay, TimeUnit.SECONDS);
		LOGGER.info("Notification Scheduler is started with delay: " + notificationDelay
				+ " seconds");
	}
	
	public void stop() {
		scheduler.shutdownNow();
		LOGGER.info("Notification Scheduler is stopped");
	}
}

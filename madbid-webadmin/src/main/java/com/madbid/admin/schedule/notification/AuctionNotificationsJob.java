package com.madbid.admin.schedule.notification;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Created by nikolov.
 */
@Component
public class AuctionNotificationsJob extends QuartzJobBean {
    @Autowired
    private NotificationTask notificationTask;

    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        notificationTask.sendAuctionNotification();
    }

    public void setNotificationTask(NotificationTask notificationTask) {
        this.notificationTask = notificationTask;
    }
}

package com.madbid.admin.schedule.notification;

import com.madbid.notification.model.NotificationCampaign;
import com.madbid.notification.repository.NotificationCampaignRepository;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nikolov.
 */
@Component
public class NotificationScheduler extends SchedulerFactoryBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationScheduler.class);
    public static final String GROUP = "Notifications";

    @Autowired
    private NotificationTask notificationTask;

    @Autowired
    private NotificationCampaignRepository notificationCampaignRepository;

    private CronTriggerBean craeteTrigger(String taskName, Class<?> jobClass, String cronExpression) throws ParseException {
        Map<String, NotificationTask> runMeTaskMap = new HashMap<>();
        runMeTaskMap.put("notificationTask", notificationTask);

        JobDetailBean jobDetailBean = new JobDetailBean();
        jobDetailBean.setName(taskName);
        jobDetailBean.setGroup(GROUP);
        jobDetailBean.setJobClass(jobClass);
        jobDetailBean.setJobDataAsMap(runMeTaskMap);

        CronTriggerBean trigger = new CronTriggerBean();
        trigger.setName(taskName);
        trigger.setGroup(GROUP);
        trigger.setJobName(taskName);
        trigger.setJobGroup(GROUP);
        trigger.setJobDetail(jobDetailBean);
        trigger.setCronExpression(cronExpression);

        return trigger;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //STODO SchedulerFactoryBean has possibility to set dataSource
        super.afterPropertiesSet();

        List<CronTriggerBean> cronTriggerBeans = new ArrayList<>();
        //This trigger is for checking won auctions and send reminder or send email after losing of item
        CronTriggerBean auctionNotificationsTrigger = craeteTrigger("auction_notifications", AuctionNotificationsJob.class, "0/10 * * * * ?");
        cronTriggerBeans.add(auctionNotificationsTrigger);

        List<NotificationCampaign> notificationCampaigns = notificationCampaignRepository.findAll();
        for (NotificationCampaign notificationCampaign : notificationCampaigns) {
            CronTriggerBean triggerBean = craeteTrigger(notificationCampaign.getName(), notificationCampaign.getJobClass(), notificationCampaign.getCronExpression());
            cronTriggerBeans.add(triggerBean);
        }

        setTriggers(cronTriggerBeans.toArray(new CronTriggerBean[cronTriggerBeans.size()]));
        registerJobsAndTriggers();
        updateTriggers(notificationCampaigns);

        start();
    }

    private void updateTriggers(List<NotificationCampaign> notificationCampaigns) throws SchedulerException {
        for (NotificationCampaign notificationCampaign : notificationCampaigns) {
            if(notificationCampaign.isPaused()) {
                pauseTrigger(notificationCampaign.getName());
            }
        }
    }

    public void rescheduleJob(NotificationCampaign notificationCampaign) throws ParseException, SchedulerException {
        LOGGER.info("Trigger:" + notificationCampaign.getName() + " rescheduled to:" + notificationCampaign.getCronExpression());
        CronTriggerBean triggerBean = craeteTrigger(notificationCampaign.getName(), notificationCampaign.getJobClass(), notificationCampaign.getCronExpression());
        getScheduler().rescheduleJob(notificationCampaign.getName(), GROUP, triggerBean);
    }

    public void pauseTrigger(String triggerName) throws SchedulerException {
        LOGGER.info("Trigger:" + triggerName + " paused");
        getScheduler().pauseTrigger(triggerName, GROUP);
    }

    public void resumeTrigger(String triggerName) throws SchedulerException {
        LOGGER.info("Trigger:" + triggerName + " resumed");
        getScheduler().resumeTrigger(triggerName, GROUP);
    }
}

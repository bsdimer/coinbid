package com.madbid.admin.controller;

import com.madbid.admin.schedule.notification.NotificationScheduler;
import com.madbid.admin.utils.LocaleUtils;
import com.madbid.notification.model.NotificationCampaign;
import com.madbid.notification.repository.NotificationCampaignRepository;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolov on 3/11/14.
 */
@Component("regularCampaignController")
@Scope("prototype")
//@Transactional
public class RegularCampaignController extends CommonTabBean implements Serializable, ITabBean {
    @Autowired
    private NotificationCampaignRepository notificationCampaignRepository;

    @Autowired
    private NotificationScheduler notificationScheduler;

    private String selectedNotificationCampaignName;
    private List<String> notificationCampaignNames;
    private NotificationCampaign notificationCampaign;

    @PostConstruct
    private void init() {
        loadNotificationCampaingNames();
        if(notificationCampaignNames != null && !notificationCampaignNames.isEmpty()) {
            notificationCampaign = notificationCampaignRepository.findByName(notificationCampaignNames.get(0));
        }
    }

    private void loadNotificationCampaingNames() {
        List<NotificationCampaign> notificationCampaigns = notificationCampaignRepository.findAll();
        notificationCampaignNames = new ArrayList<>();
        for (NotificationCampaign campaign : notificationCampaigns) {
            notificationCampaignNames.add(campaign.getName());
        }
    }

    public void save() {
        try {
            if(notificationCampaign.isPaused()) {
                notificationScheduler.pauseTrigger(notificationCampaign.getName());
            } else {
                notificationScheduler.rescheduleJob(notificationCampaign);
            }

            notificationCampaignRepository.save(notificationCampaign);
        } catch (ParseException e) {
            addMessage(LocaleUtils.getLocaliziedMessage("exception.cron.expression.parsing") + ":" + e.getLocalizedMessage(), null, FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        } catch (SchedulerException e) {
            addMessage(LocaleUtils.getLocaliziedMessage("exception.campaign.scheduler") + ":" + e.getLocalizedMessage(), null, FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        }
    }

    public void addMessage(String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public NotificationCampaign getNotificationCampaign() {
        return notificationCampaign;
    }

    public void setNotificationCampaign(NotificationCampaign notificationCampaign) {
        this.notificationCampaign = notificationCampaign;
    }

    public String getSelectedNotificationCampaignName() {
        return selectedNotificationCampaignName;
    }

    public void setSelectedNotificationCampaignName(String selectedNotificationCampaignName) {
        this.selectedNotificationCampaignName = selectedNotificationCampaignName;
        this.notificationCampaign = notificationCampaignRepository.findByName(selectedNotificationCampaignName);
    }

    public List<String> getNotificationCampaignNames() {
        return notificationCampaignNames;
    }

    public void setNotificationCampaignNames(List<String> notificationCampaignNames) {
        this.notificationCampaignNames = notificationCampaignNames;
    }
}
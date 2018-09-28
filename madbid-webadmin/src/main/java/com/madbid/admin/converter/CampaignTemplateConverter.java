package com.madbid.admin.converter;

import com.madbid.notification.model.NotificationTemplate;
import com.madbid.notification.repository.NotificationTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by nikolov.
 */
@Component
public class CampaignTemplateConverter implements Converter {
    @Autowired
    private NotificationTemplateRepository notificationTemplateRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return notificationTemplateRepository.findOne(Long.parseLong(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o instanceof NotificationTemplate) {
            return ((NotificationTemplate) o).getId().toString();
        }
        return null;
    }

    public NotificationTemplateRepository getNotificationTemplateRepository() {
        return notificationTemplateRepository;
    }

    public void setNotificationTemplateRepository(NotificationTemplateRepository notificationTemplateRepository) {
        this.notificationTemplateRepository = notificationTemplateRepository;
    }
}

package com.madbid.admin.controller;

import com.madbid.notification.model.NotificationTemplate;
import com.madbid.notification.model.PlaceholderReference;
import com.madbid.notification.repository.NotificationTemplateRepository;
import com.madbid.notification.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolov on 3/11/14.
 */
@Component("notificationTemplateController")
@Scope("prototype")
@Transactional
public class NotificationTemplateController extends CommonTabBean implements Serializable, ITabBean {
    private List<NotificationTemplate> templates;
    private NotificationTemplate template;
    private String templateText;
    private List<SelectItem> placeholderSelectItems;
    private String placeholder;

    @Autowired
    private NotificationTemplateRepository notificationTemplateRepository;

    @PostConstruct
    private void init() {
        templates = notificationTemplateRepository.findAll();
        template = templates.get(0);
        templateText = template.getTemplate();
        try {
            placeholderSelectItems = loadPlaceholders(template);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        template.setTemplate(templateText);
        notificationTemplateRepository.save(template);
        templates = notificationTemplateRepository.findAll();
    }

    public void addMessage(String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<NotificationTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(List<NotificationTemplate> templates) {
        this.templates = templates;
    }

    public NotificationTemplate getTemplate() {
        return template;
    }

    public void setTemplate(NotificationTemplate template) {
        this.template = template;
        this.templateText = template.getTemplate();
        try {
            this.placeholderSelectItems = loadPlaceholders(template);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<SelectItem> loadPlaceholders(NotificationTemplate notificationTemplate) throws ClassNotFoundException {
        List<SelectItem> placeholderSelectItems = new ArrayList<>();
        List<PlaceholderReference> placeholderReferences = notificationTemplate.getPlaceholderReferences();
        for (PlaceholderReference placeholderReference : placeholderReferences) {
            Class<?> clazz = placeholderReference.getClazz();
            List<String> placeholders = ClassUtils.getClassFieldNames(clazz, "serialVersionUID");
            SelectItem[] placeholderItems = new SelectItem[placeholders.size()];
            SelectItemGroup selectItemGroup = new SelectItemGroup(clazz.getSimpleName());
            for (int i = 0; i < placeholders.size(); i++) {
                String placeholder = placeholders.get(i);
                placeholderItems[i] = new SelectItem(placeholder, placeholder);
            }
            selectItemGroup.setSelectItems(placeholderItems);
            placeholderSelectItems.add(selectItemGroup);
        }
        return placeholderSelectItems;
    }

    public List<SelectItem> getPlaceholderSelectItems() {
        return placeholderSelectItems;
    }

    public void setPlaceholderSelectItems(List<SelectItem> placeholderSelectItems) {
        this.placeholderSelectItems = placeholderSelectItems;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }
}
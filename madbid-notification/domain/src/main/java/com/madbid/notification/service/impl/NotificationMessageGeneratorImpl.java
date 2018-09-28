package com.madbid.notification.service.impl;

import com.madbid.notification.exception.NotificationException;
import com.madbid.notification.model.NotificationTemplate;
import com.madbid.notification.model.Placeholdable;
import com.madbid.notification.repository.NotificationTemplateRepository;
import com.madbid.notification.service.NotificationMessageGenerator;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationMessageGeneratorImpl implements NotificationMessageGenerator {
    private static final Logger Log = Logger.getLogger(NotificationMessageGeneratorImpl.class);
    private static final String TEMPLATE_PATH = "/template/";
    private static final String VELOCITY_TEMPLATE_FILETYPE= ".vm";

//    @Autowired
//    @Qualifier("velocityEngine")
//    private VelocityEngine velocityEngine;

    @Autowired
    private NotificationTemplateRepository notificationTemplateRepository;

    @Override
    public String generateMessage(final Map<String, Object> emailArguments) throws NotificationException {
        String message = null;
        NotificationTemplate template = notificationTemplateRepository.findByName(emailArguments.get("template").toString());
        message = generateNotificationMessage(template, emailArguments);

        return message;
    }

    @Override
    public String generateMessage(String templateName, Placeholdable... placeholderObjects) throws NotificationException {
        Map<String, Object> emailArguments = new HashMap<String, Object>();
        String message = generateMessage(templateName, emailArguments, placeholderObjects);

        return message;
    }

    @Override
    public String generateMessage(String templateName, Map<String, Object> emailArguments, Placeholdable... placeholderObjects) throws NotificationException {
        String message = null;
        NotificationTemplate notificationTemplate = notificationTemplateRepository.findByName(templateName);

        List<String> placeholders = getPlaceholderFromTemplate(notificationTemplate.getTemplate());
        for (String placeholder : placeholders) {
            String value = null;
            int index = 0;
            while(value == null) {
                if(index >= placeholderObjects.length) {
                    break;
                }

                try {
                    Placeholdable placeholderObject = placeholderObjects[index];
                    String fieldName = placeholder.substring(placeholder.indexOf('_') + 1, placeholder.length());
                    value = placeholderObject.getValueByFieldName(fieldName);
                } catch (IllegalAccessException e) {
                    throw new NotificationException(e);
                } catch (NoSuchFieldException e) {
                    index++;
                }
            }
            if(value != null) {
                emailArguments.put(placeholder, value);
            }
        }

        message = generateNotificationMessage(notificationTemplate, emailArguments);

        return message;
    }

    private List<String> getPlaceholderFromTemplate(String template) {
        final Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
        final Matcher matcher = pattern.matcher(template);
        final List<String> placeholders = new LinkedList<String>();

        while (matcher.find()) {
            placeholders.add(matcher.group(1));
        }

        return placeholders;
    }

    private String generateNotificationMessage(NotificationTemplate notificationTemplate, Map<String, Object> notificationArguments) throws NotificationException {
        try {

            VelocityContext velocityContext = loadVelocityContext(notificationArguments);
            StringWriter stringWriter = new StringWriter();
            Velocity.evaluate(velocityContext, stringWriter, "mystring", notificationTemplate.getTemplate());
            String msg = stringWriter.toString();
            //PostCondition
            Assert.hasText(msg);

            Log.debug("Generated message is: " + msg);

            return msg;
        } catch (Exception e) {
            Log.error("Error while generating message with arguments: " + notificationArguments);
            throw new NotificationException("Error while generating message! ", e);
        }
    }

    private VelocityContext loadVelocityContext(Map<String, Object> notificationArguments) {
        VelocityContext context = new VelocityContext();
        for (String key : notificationArguments.keySet()) {
            Object value = notificationArguments.get(key);
            context.put(key, value);
        }

        return context;
    }

    private static String getTemplateFrom(final Map<String, Object> templateVariables) {
        String templateKey = templateVariables.get("template").toString();

        return TEMPLATE_PATH + templateKey + VELOCITY_TEMPLATE_FILETYPE;
    }
}

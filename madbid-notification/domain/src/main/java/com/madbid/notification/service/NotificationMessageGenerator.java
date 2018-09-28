package com.madbid.notification.service;

import com.madbid.notification.exception.NotificationException;
import com.madbid.notification.model.Placeholdable;

import java.util.Map;

public interface NotificationMessageGenerator {
    String generateMessage(Map<String, Object> emailArguments) throws NotificationException, NotificationException;

    String generateMessage(String templateName, Placeholdable... placeholderObjects) throws NotificationException;

    String generateMessage(String templateName, Map<String, Object> emailArguments, Placeholdable... placeholderObjects) throws NotificationException;
}

package com.madbid.notification.model;

public enum NotificationType {
    EMAIL_SMTP_RELAY("Email SMTP Relay"), EMAIL_MAILCHIMP("Email MailChimp Provider"), SMS("SMS");

    private String name;

    private NotificationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getItemValue() {
        return super.name();
    }
}

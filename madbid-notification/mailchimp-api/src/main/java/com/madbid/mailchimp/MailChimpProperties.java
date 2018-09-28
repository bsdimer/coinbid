package com.madbid.mailchimp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by alexn on 29.10.2014 г..
 */
@Component
public class MailChimpProperties {
    @Value("${api.key}")
    private String apiKey;

    @Value("${list.id}")
    private String listId;

    @Value("${email}")
    private String email;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.madbid.mailchimp.model;

import com.ecwid.mailchimp.MailChimpObject;

/**
 * Created by alexn on 1.11.2014 г..
 */
public class Content extends MailChimpObject {
    @Field
    public String html;

    public Content() { }

    public Content(String html) {
        this.html = html;
    }
}

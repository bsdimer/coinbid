package com.madbid.mailchimp.model;

import com.ecwid.mailchimp.MailChimpObject;

/**
 * Created by alexn on 1.11.2014 г..
 */
public class Options extends MailChimpObject {
    @Field
    public String list_id, subject, from_email, from_name, to_name;

    @Field
    public Boolean auto_footer;

    public Options() { }

    public Options(String list_id, String subject, String from_email, String from_name, String to_name) {
        this.list_id = list_id;
        this.subject = subject;
        this.from_email = from_email;
        this.from_name = from_name;
        this.to_name = to_name;
        this.auto_footer = false;
    }
}

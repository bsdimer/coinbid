package com.madbid.mailchimp.model;

import com.ecwid.mailchimp.MailChimpObject;

/**
 * Created by alexn on 1.11.2014 г..
 */
public class Conditions extends MailChimpObject {
    @Field
    public String field, op, value;

    public Conditions() { }

    public Conditions(String field, String op, String value) {
        this.field = field;
        this.op = op;
        this.value = value;
    }
}

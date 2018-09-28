package com.madbid.mailchimp.model;

import com.ecwid.mailchimp.MailChimpObject;

/**
 * Created by alexn on 1.11.2014 г..
 */
public class SegmentOpt extends MailChimpObject {
    @Field
    public String match;

    @Field
    public MailChimpObject[] conditions;

    public SegmentOpt() { }

    public SegmentOpt(String match, MailChimpObject[] conditions) {
        this.match = match;
        this.conditions = conditions;
    }
}

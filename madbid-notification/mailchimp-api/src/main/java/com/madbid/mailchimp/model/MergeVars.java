package com.madbid.mailchimp.model;

import com.ecwid.mailchimp.MailChimpObject;

/**
 * Created by alexn on 1.11.2014 г..
 */
public class MergeVars extends MailChimpObject {
    @Field
    public String EMAIL, FNAME, LNAME;

    public MergeVars() { }

    public MergeVars(String email, String fname, String lname) {
        this.EMAIL = email;
        this.FNAME = fname;
        this.LNAME = lname;
    }
}

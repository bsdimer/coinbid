package com.madbid.mailchimp.model;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.v1_3.campaign.CampaignType;
import com.ecwid.mailchimp.method.v1_3.campaign.CampaingRelatedMethod;

/**
 * Created by alexn on 1.11.2014 г..
 */
@MailChimpMethod.Method(name = "templates", version = MailChimpAPIVersion.v1_3)
public class TemplateListMethod extends CampaingRelatedMethod<Object> {
    public TemplateListMethod() { }
}

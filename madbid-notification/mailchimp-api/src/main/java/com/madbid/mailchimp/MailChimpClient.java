package com.madbid.mailchimp;

import com.ecwid.mailchimp.MailChimpException;
import com.ecwid.mailchimp.method.v1_3.campaign.CampaignSendNowMethod;
import com.ecwid.mailchimp.method.v1_3.campaign.CampaignType;
import com.ecwid.mailchimp.method.v2_0.lists.Email;
import com.ecwid.mailchimp.method.v2_0.lists.SubscribeMethod;
import com.google.gson.internal.LinkedTreeMap;
import com.madbid.mailchimp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by alexn on 1.11.2014 г..
 */
@Component
public class MailChimpClient {
//    private static final Logger LOGGER = Logger.getLogger(MailChimpClient.class);

    @Autowired
    private MailChimpProperties mailChimpProperties;

    private com.ecwid.mailchimp.MailChimpClient mailChimpClient;

    public MailChimpClient() {
        mailChimpClient = new com.ecwid.mailchimp.MailChimpClient();
    }

    public void sendCampaign(String subject, String emailFrom, String fromName, String toName, String text) throws IOException, MailChimpException {
        //template id = 192929

        CampaignCreateMethod campaignCreateMethod = new CampaignCreateMethod();
        campaignCreateMethod.apikey = mailChimpProperties.getApiKey();
        campaignCreateMethod.type = CampaignType.plaintext;
        campaignCreateMethod.options = new Options(mailChimpProperties.getListId(), subject, emailFrom, fromName, toName);
        campaignCreateMethod.content = new Content(text);
//            SegmentOpt segmentOpt = new SegmentOpt("any", new Conditions[]{new Conditions("email", "like", "alexanderNikolovNikolov@gmail.com")});
//            SegmentOpt[] segmentOpts = new SegmentOpt[1];
//            segmentOpts[0] = segmentOpt;
//            campaignCreateMethod.segment_opts = segmentOpt;
        String json = campaignCreateMethod.toJson();
        System.out.println(json);
        String campaignId = mailChimpClient.execute(campaignCreateMethod);

        CampaignSendNowMethod campaignSendNowMethod = new CampaignSendNowMethod();
        campaignSendNowMethod.apikey = mailChimpProperties.getApiKey();
        campaignSendNowMethod.cid = campaignId;
        mailChimpClient.execute(campaignSendNowMethod);
    }

    public void subscribePerson(String email, String firstname, String lastname) throws IOException, MailChimpException {
        // Subscribe a person
        SubscribeMethod subscribeMethod = new SubscribeMethod();
        subscribeMethod.apikey = mailChimpProperties.getApiKey();
        subscribeMethod.id = mailChimpProperties.getListId();
        subscribeMethod.email = new Email();
        subscribeMethod.email.email = email;
        subscribeMethod.double_optin = false;
        subscribeMethod.update_existing = true;
        subscribeMethod.merge_vars = new MergeVars(email, firstname, lastname);
        Email response = mailChimpClient.execute(subscribeMethod);

//        LOGGER.info(response);
        System.out.println(response);
    }

    public void getTemplateList() throws IOException, MailChimpException {
        TemplateListMethod templateListMethod = new TemplateListMethod();
        templateListMethod.apikey = mailChimpProperties.getApiKey();

        LinkedTreeMap response = (LinkedTreeMap)mailChimpClient.execute(templateListMethod);
        System.out.println(response);
    }

    public void createTemplate() throws IOException, MailChimpException {
        TemplateAddMethod templateAddMethod = new TemplateAddMethod();
        templateAddMethod.apikey = mailChimpProperties.getApiKey();
        templateAddMethod.name = "somename";
        templateAddMethod.html = "html content";

        Integer response = mailChimpClient.execute(templateAddMethod);
        System.out.println(response);
    }

    public static void main(String[] args) throws IOException, MailChimpException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/spring/applicationContext-mailchimp.xml");
        MailChimpClient client = (MailChimpClient) context.getBean(MailChimpClient.class);
        client.getTemplateList();
    }
}

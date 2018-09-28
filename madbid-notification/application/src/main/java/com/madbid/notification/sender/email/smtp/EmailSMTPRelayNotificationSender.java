package com.madbid.notification.sender.email.smtp;

import com.madbid.notification.exception.NotificationException;
import com.madbid.notification.model.Notification;
import com.madbid.notification.model.NotificationType;
import com.madbid.notification.sender.NotificationSender;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component(value = "emailSMTPRelayNotificationSender")
public class EmailSMTPRelayNotificationSender implements NotificationSender, InitializingBean {
    @Autowired
    private EmailSMTPRelayProperties emailSMTPRelayProperties;

    private JavaMailSenderImpl mailSender;

    @Override
    public void sendNotification(Notification notification) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            mimeMessage.setRecipients(Message.RecipientType.TO, notification.getReceiver());
            mimeMessage.setSubject(notification.getSubject(), "UTF-8");
            mimeMessage.setText(notification.getText(), "UTF-8", "html");

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new NotificationException(e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String host = emailSMTPRelayProperties.getHost();
        String username = emailSMTPRelayProperties.getUsername();
        String password = emailSMTPRelayProperties.getPassword();
        int port = emailSMTPRelayProperties.getPort();
        Properties javaEmailProperties = emailSMTPRelayProperties.getJavaEmailProperties();

        validateProperties(host, username, password);

        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setPort(port);
        mailSender.setJavaMailProperties(javaEmailProperties);
    }

    private void validateProperties(String host, String username, String password) {
        if (!StringUtils.hasLength(host)) {
            throw new IllegalArgumentException("Email host is not present");
        }
        if (!StringUtils.hasLength(username)) {
            throw new IllegalArgumentException("Email username is not present");
        }
        if (!StringUtils.hasLength(password)) {
            throw new IllegalArgumentException("Email password is not present");
        }
    }

    public static void main(String[] args) throws Exception {
        Notification notification = new Notification();
        notification.setType(NotificationType.EMAIL_SMTP_RELAY);
        notification.setReceiver("AlexanderNikolovNikolov@gmail.com");
        notification.setSubject("Test Notifications");
        notification.setText("test notification ....");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/spring/applicationContext-notification-app.xml");

        NotificationSender notificationSender = context.getBean(EmailSMTPRelayNotificationSender.class);
        notificationSender.sendNotification(notification);
        context.close();
    }
}

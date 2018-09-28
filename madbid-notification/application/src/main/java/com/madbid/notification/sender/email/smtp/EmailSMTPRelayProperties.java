package com.madbid.notification.sender.email.smtp;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailSMTPRelayProperties implements InitializingBean {
    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_DEBUG = "mail.debug";

    @Value("${email.host}")
    private String host;

    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    @Value("${email.transport.protocol}")
    private String transportProtocol;

    @Value("${email.smtp.auth}")
    private String smtpAuth;

    @Value("${email.smtp.starttls.enable}")
    private String starttlsEnable;

    @Value("${email.debug}")
    private String debug;

    @Value("${email.port}")
    private int port;

    private Properties javaMailProperties;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public String getSmtpAuth() {
        return smtpAuth;
    }

    public void setSmtpAuth(String smtpAuth) {
        this.smtpAuth = smtpAuth;
    }

    public String getStarttlsEnable() {
        return starttlsEnable;
    }

    public void setStarttlsEnable(String starttlsEnable) {
        this.starttlsEnable = starttlsEnable;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public Properties getJavaEmailProperties() {
        return javaMailProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        javaMailProperties = new Properties();
        javaMailProperties.setProperty(MAIL_TRANSPORT_PROTOCOL, transportProtocol);
        javaMailProperties.setProperty(MAIL_SMTP_AUTH, smtpAuth);
        javaMailProperties.setProperty(MAIL_SMTP_STARTTLS_ENABLE, starttlsEnable);
        javaMailProperties.setProperty(MAIL_DEBUG, debug);
    }
}

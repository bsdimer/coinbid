package com.madbid.core.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by alexn on 28.10.2014 г..
 */
@Entity
@Table(name = "messages")
public class Message extends DateTimeAuditable implements Identifiable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Lob
    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    public Message() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}

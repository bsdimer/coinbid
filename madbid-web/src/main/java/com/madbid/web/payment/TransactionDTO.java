package com.madbid.web.payment;

import com.madbid.core.model.TransactionState;

/**
 * Created by dimer on 11/4/14.
 */
public class TransactionDTO {

    private TransactionState state;
    private String content;
    private TransactionContentType contentType;

    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TransactionContentType getContentType() {
        return contentType;
    }

    public void setContentType(TransactionContentType contentType) {
        this.contentType = contentType;
    }
}

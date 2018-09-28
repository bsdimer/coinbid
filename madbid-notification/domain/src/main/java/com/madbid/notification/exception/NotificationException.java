package com.madbid.notification.exception;

public class NotificationException extends RuntimeException {

    private static final long serialVersionUID = -6971236808572314097L;

    public NotificationException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotificationException(String message) {
        super(message);
    }

    public NotificationException(Throwable cause) {
        super(cause);
    }
}

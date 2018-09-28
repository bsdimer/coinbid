package com.madbid.core.exception;



/**
 * The exception is thrown when the email given during the registration
 * phase is already found from the database.
 * Created by nikolov.
 */
public class DuplicateUsernameException extends Exception {
    public static final String SUMMARY_KEY = "exception.summary.username.duplicate";

    public DuplicateUsernameException() {
        super();
    }

    public DuplicateUsernameException(String message) {
        super(message);
    }
}

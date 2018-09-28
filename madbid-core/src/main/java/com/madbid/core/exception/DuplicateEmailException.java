package com.madbid.core.exception;



/**
 * The exception is thrown when the email given during the registration
 * phase is already found from the database.
 * Created by nikolov.
 */
public class DuplicateEmailException extends Exception {
    public static final String SUMMARY_KEY = "exception.summary.email.duplicate";

    public DuplicateEmailException() {
        super();
    }

    public DuplicateEmailException(String message) {
        super(message);
    }
}

package com.madbid.web.payment.exceptions;

/**
 * Created by dimer on 11/21/14.
 */
public class InvalidPaymentException extends RuntimeException {
    public InvalidPaymentException(String s) {
        super(s);
    }
}

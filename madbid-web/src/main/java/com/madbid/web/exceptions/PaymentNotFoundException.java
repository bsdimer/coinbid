package com.madbid.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by dimer on 11/4/14.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such payment")
public class PaymentNotFoundException extends RuntimeException {
}

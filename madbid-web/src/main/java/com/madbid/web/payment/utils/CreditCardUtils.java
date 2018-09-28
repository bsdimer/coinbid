package com.madbid.web.payment.utils;

import org.joda.time.LocalDateTime;
import urn.ebay.apis.eBLBaseComponents.CreditCardTypeType;

/**
 * Created by dimer on 11/6/14.
 */
public class CreditCardUtils {

    public static int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    public static boolean isValidCreditCardType(String type) {
        try {
            CreditCardTypeType cctype = CreditCardTypeType.fromValue(type);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public static int[] getYears(int i) {
        int[] years = new int[i];
        LocalDateTime now = LocalDateTime.now();
        int y = now.getYear();
        byte index = 0;
        while (y < now.getYear() + i) {
            years[index++] = y;
            y++;
        }
        return years;
    }

    public static CreditCardTypeType[] getCreditCardTypes() {
        return CreditCardTypeType.values();
    }
}

package com.madbid.web.payment.utils;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dimer on 11/7/14.
 */
public class PaymentUtils {

    public static final Map<String, String> countryCodesMap = new HashMap<String, String>() {{
        put("българия", "BG");
        put("bulgaria", "BG");
    }};

    public static synchronized String generateDescription(String itemName,
                                                          float price, Currency currency,
                                                          float shippingPrice, Currency shippingCurrency) {
        return String.format("Payment for:\n item: %s\n, price: %s %s\n shipping: %s %s ", itemName, price, currency, shippingPrice, shippingCurrency);
    }

    public static synchronized String translateCountryCode(String countryName) {
        return countryCodesMap.get(countryName.toLowerCase());
    }
}

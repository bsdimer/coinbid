package com.madbid.core.model;

/**
 * PaymentDetailsCodeType
 * This is the PayPal payment details type (used by DCC and
 * Express Checkout)
 */
public enum SaleType {

    CREDITS_SALE("creditsSale"),

    ITEM_BUYNOW("itemBuyNow"),

    ITEM_AUCTIONWIN("itemAuctionWin");

    private String value;

    private SaleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SaleType fromValue(String v) {
        for (SaleType c : values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
package com.madbid.web.payment;

import com.madbid.web.payment.validation.CCV2;
import com.madbid.web.payment.validation.CreditCardProvider;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotEmpty;
import urn.ebay.apis.eBLBaseComponents.CreditCardDetailsType;
import urn.ebay.apis.eBLBaseComponents.CreditCardTypeType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by dimer on 11/5/14.
 */
public class CreditCardDTO {

    @CreditCardProvider
    private String creditCardType;

    @CreditCardNumber
    private String creditCardNumber;

    @NotNull
    private int expireMonth;

    @NotNull
    private int expireYear;

    @NotEmpty(message = "{field.validation.not.empty}")
    @Size(max = 127, message = "{field.validation.size}")
    private String cardHolder;

    @CCV2
    private String ccv2;

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCcv2() {
        return ccv2;
    }

    public void setCcv2(String ccv2) {
        this.ccv2 = ccv2;
    }

    public int getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(int expireMonth) {
        this.expireMonth = expireMonth;
    }

    public int getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(int expireYear) {
        this.expireYear = expireYear;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getFirstName() {
        return cardHolder.trim().split("\\s")[0];
    }

    public String getLastName() {
        return cardHolder.trim().split("\\s")[2];
    }

    public CreditCardDetailsType toCreditCardDetailType() {
        CreditCardDetailsType cardDetails = new CreditCardDetailsType();
        cardDetails.setCreditCardType(CreditCardTypeType.fromValue(creditCardType));
        cardDetails.setCreditCardNumber(creditCardNumber);
        cardDetails.setExpMonth(expireMonth);
        cardDetails.setExpYear(expireYear);
        cardDetails.setCVV2(ccv2);
        return cardDetails;
    }
}

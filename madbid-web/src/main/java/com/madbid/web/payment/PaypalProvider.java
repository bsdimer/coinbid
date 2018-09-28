package com.madbid.web.payment;

import com.madbid.core.model.Address;
import com.madbid.web.payment.dto.PaymentDTO;
import com.madbid.web.payment.utils.PaymentUtils;
import com.madbid.web.payment.utils.PaypalConfig;
import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import urn.ebay.api.PayPalAPI.*;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by dimer on 11/1/14.
 */
@Component
@Scope(value = "prototype")
public class PaypalProvider {

    private static final Logger logger = LoggerFactory.getLogger(PaypalProvider.class);
    public static final float EUR_BGN_RATING = 2f;

    @Autowired
    private PaypalConfig paypalConfig;


    @Value("${paypal.merchant.brandname}")
    private String brandName;

    public GetExpressCheckoutDetailsResponseType getExpressCheckout(String token) throws IOException, SAXException, OAuthException, InvalidResponseDataException, SSLConfigurationException, ParserConfigurationException, MissingCredentialException, HttpErrorException, InvalidCredentialException, ClientActionRequiredException, InterruptedException {
        // Get ExpressCheckout
        Map<String, String> configurationMap = paypalConfig.getAcctAndConfig();
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
        GetExpressCheckoutDetailsReq getExpressCheckoutDetailsReq = new GetExpressCheckoutDetailsReq();
        GetExpressCheckoutDetailsRequestType reqType = new GetExpressCheckoutDetailsRequestType(token);
        getExpressCheckoutDetailsReq.setGetExpressCheckoutDetailsRequest(reqType);
        return service.getExpressCheckoutDetails(getExpressCheckoutDetailsReq);
    }

    public DoExpressCheckoutPaymentResponseType doExpressCheckout(String token, String payerId, String notifyUrl, PaymentDTO paymentDTO) throws IOException, SAXException, OAuthException, InvalidResponseDataException, SSLConfigurationException, ParserConfigurationException, MissingCredentialException, HttpErrorException, InvalidCredentialException, ClientActionRequiredException, InterruptedException {
        Map<String, String> configurationMap = paypalConfig.getAcctAndConfig();
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);

        DoExpressCheckoutPaymentRequestType doCheckoutPaymentRequestType = new DoExpressCheckoutPaymentRequestType();
        DoExpressCheckoutPaymentRequestDetailsType details = new DoExpressCheckoutPaymentRequestDetailsType();
        details.setToken(token);
        details.setPayerID(payerId);

        List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();

        PaymentDetailsItemType item = new PaymentDetailsItemType();

        BasicAmountType itemAmount = new BasicAmountType();
        itemAmount.setCurrencyID(CurrencyCodeType.EUR);
        itemAmount.setValue(paymentDTO.getPrice().divide(BigDecimal.valueOf(EUR_BGN_RATING)).setScale(2, RoundingMode.HALF_UP).toString());

        item.setQuantity(1);
        item.setName(paymentDTO.getShipping().getItemInventory().getItem().getName());
        item.setAmount(itemAmount);
        item.setItemCategory(ItemCategoryType.PHYSICAL);
        item.setDescription(paymentDTO.getShipping().getItemInventory().getItem().getDescription());
        lineItems.add(item);

        PaymentDetailsType paydtl = new PaymentDetailsType();
        paydtl.setPaymentAction(PaymentActionCodeType.SALE);

        if (StringUtils.isNotEmpty(paymentDTO.getItemDescription())) {
            paydtl.setOrderDescription(paymentDTO.getItemDescription());
        }

        paydtl.setOrderTotal(new BasicAmountType(CurrencyCodeType.EUR, paymentDTO.getOrderTotal().divide(BigDecimal.valueOf(EUR_BGN_RATING)).toString()));
        paydtl.setShippingTotal(new BasicAmountType(CurrencyCodeType.EUR, paymentDTO.getShippingTotal().divide(BigDecimal.valueOf(EUR_BGN_RATING)).toString()));
        paydtl.setItemTotal(new BasicAmountType(CurrencyCodeType.EUR, paymentDTO.getItemsTotal().divide(BigDecimal.valueOf(EUR_BGN_RATING)).toString()));
        paydtl.setPaymentDetailsItem(lineItems);
        paydtl.setNotifyURL(notifyUrl);
        paydtl.setOrderDescription("Payment for " + item.getDescription());

        List<PaymentDetailsType> payDetailType = new ArrayList<PaymentDetailsType>();
        payDetailType.add(paydtl);

        details.setPaymentDetails(payDetailType);

        doCheckoutPaymentRequestType
                .setDoExpressCheckoutPaymentRequestDetails(details);
        DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
        doExpressCheckoutPaymentReq
                .setDoExpressCheckoutPaymentRequest(doCheckoutPaymentRequestType);

        DoExpressCheckoutPaymentResponseType doCheckoutPaymentResponseType = service
                .doExpressCheckoutPayment(doExpressCheckoutPaymentReq);

        return doCheckoutPaymentResponseType;
    }

    public SetExpressCheckoutResponseType setExpressCheckout(PaymentDTO paymentDTO, String returnUrl, String cancelUrl, String notifyUrl) throws IOException, SAXException, OAuthException, InvalidResponseDataException, SSLConfigurationException, ParserConfigurationException, MissingCredentialException, HttpErrorException, InvalidCredentialException, ClientActionRequiredException, InterruptedException {
        Map<String, String> configurationMap = paypalConfig.getAcctAndConfig();
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
        SetExpressCheckoutRequestType setExpressCheckoutReq = new SetExpressCheckoutRequestType();
        SetExpressCheckoutRequestDetailsType details = new SetExpressCheckoutRequestDetailsType();

        details.setReturnURL(returnUrl);
        details.setCancelURL(cancelUrl);

        // Populate initial user form with some info
        details.setBuyerEmail(paymentDTO.getUser().getEmail());

        // Payment type (Autorization, Sale or Order)
        // request.getSession().setAttribute("paymentType", );

        List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();

        PaymentDetailsItemType item = new PaymentDetailsItemType();

        BasicAmountType itemAmount = new BasicAmountType();
        itemAmount.setCurrencyID(CurrencyCodeType.EUR);
        itemAmount.setValue(paymentDTO.getPrice().divide(BigDecimal.valueOf(EUR_BGN_RATING)).setScale(2, RoundingMode.HALF_UP).toString());

        item.setQuantity(1);
        item.setName(paymentDTO.getShipping().getItemInventory().getItem().getName());
        item.setAmount(itemAmount);
        item.setItemCategory(ItemCategoryType.PHYSICAL);
        item.setDescription(paymentDTO.getItemDescription());
        lineItems.add(item);

        List<PaymentDetailsType> payDetails = new ArrayList<>();

        PaymentDetailsType paydtl = new PaymentDetailsType();
        paydtl.setPaymentAction(PaymentActionCodeType.SALE);

        if (StringUtils.isNotEmpty(paymentDTO.getItemDescription())) {
            paydtl.setOrderDescription(paymentDTO.getItemDescription());
        }

        paydtl.setOrderTotal(new BasicAmountType(CurrencyCodeType.EUR, paymentDTO.getOrderTotal().divide(BigDecimal.valueOf(EUR_BGN_RATING)).toString()));
        paydtl.setShippingTotal(new BasicAmountType(CurrencyCodeType.EUR, paymentDTO.getShippingTotal().divide(BigDecimal.valueOf(EUR_BGN_RATING)).toString()));
        paydtl.setItemTotal(new BasicAmountType(CurrencyCodeType.EUR, paymentDTO.getItemsTotal().divide(BigDecimal.valueOf(EUR_BGN_RATING)).toString()));
        paydtl.setPaymentDetailsItem(lineItems);
        paydtl.setNotifyURL(notifyUrl);
        paydtl.setOrderDescription("Payment for " + item.getDescription());

        if (paymentDTO.getShipping().getAddress() != null) {
            AddressType shipToAddress = new AddressType();
            shipToAddress.setName(paymentDTO.getUser().getFullName());
            Address address = paymentDTO.getUser().getAddresses().get(0);
            shipToAddress.setStreet1(address.getStreet1());
            shipToAddress.setStreet2(address.getStreet2());
            shipToAddress.setCityName(address.getCity());
            shipToAddress.setStateOrProvince(address.getState());
            shipToAddress.setPostalCode(address.getPostcode());
            shipToAddress.setCountry(CountryCodeType.fromValue(PaymentUtils.translateCountryCode(address.getCountry())));
            paydtl.setShipToAddress(shipToAddress);
        }

        payDetails.add(paydtl);
        details.setPaymentDetails(payDetails);
        details.setReqConfirmShipping("0");
        details.setAddressOverride("0");
        details.setNoShipping("0");

        details.setBrandName(brandName);

        setExpressCheckoutReq.setSetExpressCheckoutRequestDetails(details);

        SetExpressCheckoutReq expressCheckoutReq = new SetExpressCheckoutReq();
        expressCheckoutReq
                .setSetExpressCheckoutRequest(setExpressCheckoutReq);

        SetExpressCheckoutResponseType setExpressCheckoutResponse;
        setExpressCheckoutResponse = service
                .setExpressCheckout(expressCheckoutReq);

        return setExpressCheckoutResponse;
    }

    public DoDirectPaymentResponseType setDirectPayment(CreditCardDTO creditCardDTO, PaymentDTO paymentDTO, String notifyUrl, HttpServletRequest request) throws IOException, SAXException, OAuthException, InvalidResponseDataException, SSLConfigurationException, ParserConfigurationException, MissingCredentialException, HttpErrorException, InvalidCredentialException, ClientActionRequiredException, InterruptedException {
        Map<String, String> configurationMap = paypalConfig.getAcctAndConfig();
        PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);

        DoDirectPaymentReq doPaymentReq = new DoDirectPaymentReq();
        DoDirectPaymentRequestType pprequest = new DoDirectPaymentRequestType();
        DoDirectPaymentRequestDetailsType details = new DoDirectPaymentRequestDetailsType();
        PaymentDetailsType paymentDetails = new PaymentDetailsType();

        paymentDetails.setOrderTotal(new BasicAmountType(CurrencyCodeType.EUR, paymentDTO.getOrderTotal().divide(BigDecimal.valueOf(EUR_BGN_RATING)).toString()));

        PayerInfoType payer = new PayerInfoType();
        PersonNameType name = new PersonNameType();

        if (paymentDTO.getShipping().getAddress() != null) {
            AddressType shipToAddress = new AddressType();
            shipToAddress.setName(paymentDTO.getUser().getFullName());
            Address address = paymentDTO.getUser().getAddresses().get(0);
            shipToAddress.setStreet1(address.getStreet1());
            shipToAddress.setStreet2(address.getStreet2());
            shipToAddress.setCityName(address.getCity());
            shipToAddress.setStateOrProvince(address.getState());
            shipToAddress.setPostalCode(address.getPostcode());
            shipToAddress.setCountry(CountryCodeType.fromValue(PaymentUtils.translateCountryCode(address.getCountry())));
            paymentDetails.setShipToAddress(shipToAddress);
            payer.setAddress(shipToAddress);
        }

        paymentDetails.setNotifyURL(notifyUrl);
        details.setPaymentDetails(paymentDetails);

        CreditCardDetailsType cardDetails = creditCardDTO.toCreditCardDetailType();

        name.setFirstName(creditCardDTO.getFirstName());
        name.setLastName(creditCardDTO.getLastName());
        payer.setPayerName(name);
        //ToDo: should be get dynamically
        payer.setPayerCountry(CountryCodeType.BG);
        cardDetails.setCardOwner(payer);

        details.setCreditCard(cardDetails);
        details.setIPAddress(request.getRemoteAddr());
        details.setPaymentAction(PaymentActionCodeType.SALE);

        pprequest.setDoDirectPaymentRequestDetails(details);
        doPaymentReq.setDoDirectPaymentRequest(pprequest);
        DoDirectPaymentResponseType ddresponse = service.doDirectPayment(doPaymentReq);
        return ddresponse;
    }

    /*public Payment doCreditCardPayment(CreditCardDTO creditCardDTO, PaymentDTO paymentDTO) {
        com.paypal.api.payments.Address billingAddress = new com.paypal.api.payments.Address();
        billingAddress.setCity(paymentDTO.getAddress().getCity());
        billingAddress.setCountryCode("BG");
        billingAddress.setLine1(String.format("%s %s %s", paymentDTO.getAddress().getQuarter(),
                paymentDTO.getAddress().getStreet(), paymentDTO.getAddress().getNumber()));
        billingAddress.setPostalCode(paymentDTO.getAddress().getPostcode());

        CreditCard creditCard = new CreditCard();
        creditCard.setCvv2(Integer.parseInt(creditCardDTO.getCcv2()));
        creditCard.setExpireMonth(creditCardDTO.getExpireMonth());
        creditCard.setExpireYear(creditCardDTO.getExpireYear());
        creditCard.setFirstName(creditCardDTO.getFirstName());
        creditCard.setLastName(creditCardDTO.getLastName());
        creditCard.setNumber(creditCardDTO.getCreditCardNumber());
        creditCard.setType(creditCardDTO.getCreditCardType());
        creditCard.setBillingAddress(billingAddress);

        Details details = new Details();
        details.setShipping(shipping.getPrice().setScale(2, RoundingMode.HALF_UP).toString());
        details.setSubtotal();
        details.setTax("0");

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("7");
        amount.setDetails(details);

        com.paypal.api.payments.Transaction transaction = new com.paypal.api.payments.Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("This is the payment transaction description.");

        List<com.paypal.api.payments.Transaction> transactions = new ArrayList<com.paypal.api.payments.Transaction>();
        transactions.add(transaction);

        FundingInstrument fundingInstrument = new FundingInstrument();
        fundingInstrument.setCreditCard(creditCard);

        List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
        fundingInstrumentList.add(fundingInstrument);

        Payer payer = new Payer();
        payer.setFundingInstruments(fundingInstrumentList);
        payer.setPaymentMethod("credit_card");

        com.paypal.api.payments.Payment payment = new com.paypal.api.payments.Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        String accessToken = GenerateAccessToken.getAccessToken();
        APIContext apiContext = new APIContext(accessToken);
        com.paypal.api.payments.Payment createdPayment = payment.create(apiContext);
    }*/

    /*public Payment createRequest(HttpServletRequest req, HttpServletResponse resp, PaymentDTO paymentDTO) throws PayPalRESTException {
        // Create access token
        APIContext apiContext = null;
        String accessToken = null;
        accessToken = GenerateAccessToken.getAccessToken();
        apiContext = new APIContext(accessToken);


        // ### CREATE INITIAL PAYMENT REQUEST
        // Let's you specify details of a payment amount.
        Details details = new Details();
        details.setShipping(paymentDTO.
                getShipping().
                getPrice().
                divide(BigDecimal.valueOf(EUR_BGN_RATING)).
                setScale(2, RoundingMode.HALF_UP).toString());
        details.setSubtotal(paymentDTO.
                getAuction().
                getAuctionPrice().
                divide(BigDecimal.valueOf(EUR_BGN_RATING)).
                setScale(2, RoundingMode.HALF_UP).toString());
        details.setTax("0");

        // ###Amount
        // Let's you specify a payment amount.
        Amount amount = new Amount();
        amount.setCurrency("EUR");
        // Total must be equal to sum of shipping, tax and subtotal.
        BigDecimal totalAmount = paymentDTO.
                getShipping().getPrice().add(paymentDTO.getAuction().getAuctionPrice()).divide(BigDecimal.valueOf(EUR_BGN_RATING));
        amount.setTotal(totalAmount.setScale(2, RoundingMode.HALF_UP).toString());
        amount.setDetails(details);

        // ###Transaction
        // A transaction defines the contract of a
        // payment - what is the payment for and who
        // is fulfilling it. Transaction is created with
        // a `Payee` and `Amount` types
        com.paypal.api.payments.Transaction transaction = new com.paypal.api.payments.Transaction();
        transaction.setAmount(amount);
        transaction
                .setDescription(String.format("Payment for %s to %s", paymentDTO.getAuction().getItemInventory().getItem().getName(), "julticabg"));

        // The Payment creation API requires a list of
        // Transaction; add the created `Transaction`
        // to a List
        List<com.paypal.api.payments.Transaction> transactions = new ArrayList<com.paypal.api.payments.Transaction>();
        transactions.add(transaction);

        // ###Payer
        // A resource representing a Payer that funds a payment
        // Payment Method
        // as 'paypal'
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        // ###Payment
        // A Payment Resource; create one using
        // the above types and intent as 'sale'
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        // ###Redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        String guid = UUID.randomUUID().toString().replaceAll("-", "");
        redirectUrls.setCancelUrl(req.getScheme() + "://"
                + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
        redirectUrls.setReturnUrl(req.getScheme() + "://"
                + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
        payment.setRedirectUrls(redirectUrls);

        // Create a payment by posting to the APIService
        // using a valid AccessToken
        // The return object contains the status;
        Payment createdPayment = payment.create(apiContext);
        // ###Payment Approval Url
        Iterator<Links> links = createdPayment.getLinks().iterator();
        while (links.hasNext()) {
            Links link = links.next();
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                req.setAttribute("redirectURL", link.getHref());
            }
        }
        req.setAttribute("response", Payment.getLastResponse());
        req.setAttribute("request", Payment.getLastRequest());
        return createdPayment;
    }

    public TransactionDTO executePayment(HttpServletRequest req) throws PayPalRESTException, IOException {

        // Create access token
        APIContext apiContext = null;
        String accessToken = null;
        accessToken = GenerateAccessToken.getAccessToken();
        apiContext = new APIContext(accessToken);

        if (req.getParameter("PayerID") != null) {
            Payment payment = new Payment();
            if (req.getParameter("paymentId") != null) {
                String paymentId = req.getParameter("paymentId");
                payment.setId(paymentId);
            }

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(req.getParameter("PayerID"));
            payment.execute(apiContext, paymentExecution);
        }

        // ToDo: must do this by Serializer config in runtime
        TransactionDTO transactionDTO = new TransactionDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(Payment.getLastResponse());
        transactionDTO.setContent(Payment.getLastResponse());
        if (jsonNode.get("state").getTextValue().indexOf("approved") == 0) {
            transactionDTO.setState(TransactionState.APPROVED);
        } else {
            transactionDTO.setState(TransactionState.NOT_APPROVED);
        }
        transactionDTO.setContentType(TransactionContentType.JSON);
        return transactionDTO;
    }

    public TransactionDTO executePayment(HttpServletRequest req, String paymentId, String payerId) throws PayPalRESTException, IOException {

        // Create access token
        APIContext apiContext = null;
        String accessToken = null;
        accessToken = GenerateAccessToken.getAccessToken();
        apiContext = new APIContext(accessToken);

        if (!Strings.isNullOrEmpty(paymentId) && !Strings.isNullOrEmpty(payerId)) {
            Payment payment = new Payment();
            payment.setId(paymentId);

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payerId);
            payment.execute(apiContext, paymentExecution);
        } else {
            throw new PayPalRESTException("no such payment or payer");
        }

        // ToDo: must do this by Serializer config in runtime
        TransactionDTO transactionDTO = new TransactionDTO();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(Payment.getLastResponse());
        transactionDTO.setContent(Payment.getLastResponse());
        if (jsonNode.get("state").getTextValue().indexOf("approved") == 0) {
            transactionDTO.setState(TransactionState.APPROVED);
        } else {
            transactionDTO.setState(TransactionState.NOT_APPROVED);
        }
        transactionDTO.setContentType(TransactionContentType.JSON);
        return transactionDTO;
    }*/

}

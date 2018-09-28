package com.madbid.web.controller;

import com.madbid.core.model.*;
import com.madbid.core.repository.AddressRepository;
import com.madbid.core.repository.AuctionRepository;
import com.madbid.core.service.PaymentService;
import com.madbid.core.service.UserService;
import com.madbid.web.payment.CreditCardDTO;
import com.madbid.web.payment.PaymentProvider;
import com.madbid.web.payment.PaypalProvider;
import com.madbid.web.payment.ShippingUtil;
import com.madbid.web.payment.dto.PaymentDTO;
import com.madbid.web.payment.exceptions.InvalidPaymentException;
import com.madbid.web.payment.utils.CreditCardUtils;
import com.madbid.web.utils.UrlUtils;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.exception.*;
import com.paypal.sdk.exceptions.OAuthException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;
import urn.ebay.api.PayPalAPI.DoDirectPaymentResponseType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dimer on 10/28/14.
 */
@Controller
@SessionAttributes(BuyController.PAYMENT_ENTITY_ATTRIBUTE_NAME)
public class BuyController {

    private static final Logger logger = LoggerFactory.getLogger(BuyController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuctionRepository auctionService;

    @Autowired
    private PaypalProvider paypalProvider;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AddressRepository addressRepository;

    public static final String PAYMENT_ENTITY_ATTRIBUTE_NAME = "paymentEntity";
    public static final String ADDRESSES_ATTRIBUTE_NAME = "addresses";
    private List<Address> addresses = new ArrayList<>();

    @RequestMapping(value = "/buyNow/{id}", method = RequestMethod.GET)
    public String onBuyNow(@PathVariable("id") String id,
                           Model model,
                           Principal principal) throws SecurityException {
        User user = userService.findByEmailDeepAddresses(principal.getName());
        Auction auction = auctionService.findOneDeep(Long.parseLong(id));

        // Generate Payment DTO which will be used from the view
        PaymentDTO paymentEntity;
        if (!model.containsAttribute(PAYMENT_ENTITY_ATTRIBUTE_NAME)) {
            paymentEntity = new PaymentDTO();
            model.addAttribute(PAYMENT_ENTITY_ATTRIBUTE_NAME, paymentEntity);
            paymentEntity.setUser(user);
            if (user.getAddresses().size() > 0) {
                paymentEntity.setAddress(user.getAddresses().get(0));
            }
        } else {
            paymentEntity = (PaymentDTO) model.asMap().get(PAYMENT_ENTITY_ATTRIBUTE_NAME);
        }

        // Initialize payment entity
        paymentEntity.setSaleType(SaleType.ITEM_BUYNOW);
        paymentEntity.setPrice(auction.getBuyNowPrice().setScale(2, RoundingMode.HALF_UP));
        paymentEntity.setCurrency(auction.getBuyNowCurrency());
        if (paymentEntity.getAddress() != null) {
            Shipping shipping = ShippingUtil.generateShipping(user, auction.getItemInventory(), paymentEntity.getAddress());
            paymentEntity.setShipping(shipping);
        }

        return "buyNow";
    }

    @RequestMapping(value = "/buyNow", method = RequestMethod.POST)
    public String onBuySubmit(@ModelAttribute(PAYMENT_ENTITY_ATTRIBUTE_NAME) PaymentDTO paymentDTO,
                              HttpServletRequest request,
                              Model model
    ) throws IOException, InterruptedException, InvalidResponseDataException, SSLConfigurationException, ParserConfigurationException, OAuthException, MissingCredentialException, SAXException, HttpErrorException, ClientActionRequiredException, InvalidCredentialException {
        if (paymentDTO.getPaymentMethod().equals(PaymentProvider.creditCard)) {
            model.addAttribute("creditCardEntity", new CreditCardDTO());
            model.addAttribute("creditCardTypes", CreditCardUtils.getCreditCardTypes());
            model.addAttribute("months", CreditCardUtils.months);
            model.addAttribute("years", CreditCardUtils.getYears(10));
            return "enterCreditCard";
        }
        if (paymentDTO.getPaymentMethod().equals(PaymentProvider.paypal)) {

        }
        if (paymentDTO.getPaymentMethod().equals(PaymentProvider.payOnDelivery)) {

        }
        if (paymentDTO.getPaymentMethod().equals(PaymentProvider.epay)) {

        }
        if (paymentDTO.getPaymentMethod().equals(PaymentProvider.paypal_ec)) {
            // Generate Express checkout token
            String baseUrl = UrlUtils.generateBaseUrl(request);
            String returnURL = baseUrl + "/paypalExpressCheckoutResult";
            String cancelURL = baseUrl + "/paypalExpressCheckoutCancel";
            String notifyURL = baseUrl + "/paypalExpressCheckoutNotify";
            SetExpressCheckoutResponseType setExpressCheckoutResponse = paypalProvider.setExpressCheckout(paymentDTO, returnURL, cancelURL, notifyURL);
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("token", setExpressCheckoutResponse.getToken());
            resultMap.put("redirectUrl",
                    "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken());
            model.addAllAttributes(resultMap);
            Payment payment = paymentDTO.toPayment();
            payment.setData(resultMap.get("token").toString());
            paymentService.save(payment);
            return "buyConfirm";
        }
        return "buyNow";
    }

    @RequestMapping(value = "/paypalExpressCheckoutResult", method = RequestMethod.GET)
    private ModelAndView paypalExpressCheckoutResult(HttpServletRequest request) throws IOException, SAXException, OAuthException, InvalidResponseDataException, SSLConfigurationException, ParserConfigurationException, MissingCredentialException, HttpErrorException, InvalidCredentialException, ClientActionRequiredException, InterruptedException {
        String token = request.getParameter("token");
        if (StringUtils.isNotEmpty(token)) {
            Payment payment = paymentService.findByData(request.getParameter("token"));
            if (payment != null) {

                GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponse = paypalProvider.getExpressCheckout(request.getParameter("token"));

                if (getExpressCheckoutDetailsResponse != null && getExpressCheckoutDetailsResponse.getAck().toString().equalsIgnoreCase("SUCCESS")) {
                    ModelAndView confirmView = new ModelAndView("paymentExecute");
                    String executePaymentSuffixLink = String.format("expressCheckoutExecute/%s/%s", request.getParameter("token"), request.getParameter("PayerID"));
                    confirmView.addObject("token", request.getParameter("token"));
                    confirmView.addObject("payerId", request.getParameter("PayerID"));
                    confirmView.addObject("executePaymentSuffix", executePaymentSuffixLink);

                    return confirmView;
                }
                return new ModelAndView("paymentCanceled");
            } else {
                throw new InvalidPaymentException("No such token ID");
            }
        } else {
            throw new IllegalArgumentException("invalid parameters or fake token");
        }
    }


    @RequestMapping(value = "/expressCheckoutExecute/{token}/{payerId}", method = RequestMethod.GET)
    private String onExpressCheckoutExecute(HttpServletRequest request, @PathVariable("token") String token, @PathVariable("payerId") String payerId, HttpSession session, Model model) throws IOException, SAXException, OAuthException, InvalidResponseDataException, SSLConfigurationException, ParserConfigurationException, MissingCredentialException, HttpErrorException, InvalidCredentialException, ClientActionRequiredException, InterruptedException {
        String notifyURL = UrlUtils.generateBaseUrl(request) + "/paypalExpressCheckoutNotify";
        DoExpressCheckoutPaymentResponseType doCheckoutPaymentResponseType = paypalProvider.doExpressCheckout(token, payerId, notifyURL, (PaymentDTO) session.getAttribute(PAYMENT_ENTITY_ATTRIBUTE_NAME));

        Payment payment = paymentService.findByData(token);
        Transaction transaction = payment.getTransactions().get(payment.getTransactions().size() - 1);
        transaction.setTransactionState(TransactionState.IN_PROGRESS);

        model.addAttribute("transactionStatus", doCheckoutPaymentResponseType.getAck().getValue());
        model.addAttribute("transactionId", "");
        if (doCheckoutPaymentResponseType.getAck().getValue().toLowerCase().indexOf("success") == 0) {
            String transactionId = doCheckoutPaymentResponseType.getDoExpressCheckoutPaymentResponseDetails().getPaymentInfo().get(0).getTransactionID();
            model.addAttribute("transactionId", transactionId);
            transaction.setTransactionState(TransactionState.COMPLETED);
            transaction.setTransactionId(transactionId);
        }
        paymentService.save(payment);
        return "paymentComplete";
    }

    @RequestMapping(value = "/paypalExpressCheckoutCancel", method = RequestMethod.GET)
    private String paypalExpressCheckoutCancel(HttpServletRequest request) {
        String token = request.getParameter("token");
        Payment payment = paymentService.findByData(token);
        payment.getTransactions().get(0).setTransactionState(TransactionState.CANCELED);
        paymentService.save(payment);
        return "paymentCanceled";
    }

    @RequestMapping(value = "/paypalExpressCheckoutNotify", method = RequestMethod.GET)
    private void paypalExpressCheckoutNotify(HttpServletRequest request) {

    }

    @RequestMapping(value = "/submitCreditCard", method = RequestMethod.POST)
    public String onSubmitCreditCard(@ModelAttribute("creditCardEntity") @Valid CreditCardDTO creditCardDTO,
                                     @ModelAttribute(PAYMENT_ENTITY_ATTRIBUTE_NAME) PaymentDTO paymentDTO,
                                     HttpServletRequest request,
                                     BindingResult bindingResult,
                                     Model model) throws PayPalRESTException, IOException, InterruptedException, InvalidResponseDataException, SSLConfigurationException, ParserConfigurationException, OAuthException, MissingCredentialException, SAXException, HttpErrorException, ClientActionRequiredException, InvalidCredentialException {

        if (bindingResult.hasErrors()) {
            return "enterCreditCard";
        }

        String baseUrl = UrlUtils.generateBaseUrl(request);
        String notifyUrl = baseUrl + "/paypalExpressCheckoutNotify";
        DoDirectPaymentResponseType paypalPayment = paypalProvider.setDirectPayment(creditCardDTO, paymentDTO, notifyUrl, request);

        model.addAttribute("transactionStatus", paypalPayment.getAck().getValue());
        model.addAttribute("transactionId", paypalPayment.getTransactionID());
        return "paymentComplete";
    }

    @RequestMapping(value = "/buyNow/addUserAddress", method = RequestMethod.POST)
    @ResponseBody
    public Address addAddressPost(@ModelAttribute(PAYMENT_ENTITY_ATTRIBUTE_NAME) PaymentDTO paymentDTO,
                                  HttpServletRequest request) {
        Address address = new Address();
        Map<String, String[]> addressMap = request.getParameterMap();
        if (addressMap.containsKey("country")
                && addressMap.containsKey("state")
                && addressMap.containsKey("city")
                && addressMap.containsKey("street1")
                && addressMap.containsKey("postcode")) {
            address.setCountry(addressMap.get("country")[0]);
            address.setCity(addressMap.get("city")[0]);
            address.setState(addressMap.get("state")[0]);
            address.setStreet1(addressMap.get("street1")[0]);
            if (addressMap.containsKey("street2")) {
                address.setStreet2(addressMap.get("street2")[0]);
            }
            address.setPostcode(addressMap.get("postcode")[0]);

            paymentDTO.setAddress(address);

            paymentDTO.getUser().getAddresses().add(address);
            userService.save(paymentDTO.getUser());

            if (address.getCountry().length() == 0
                    || address.getState().length() == 0
                    || address.getCity().length() == 0
                    || address.getStreet1().length() == 0
                    || address.getPostcode().length() == 0
                    ) {
                return null;
            }
        } else {
            return null;
        }

        return address;
    }

    /*@ModelAttribute("addresses")
    public List<Address> getAddresses() {
        return addresses;
    }*/

    @RequestMapping(value = "/buyNow/changeUserAddress", method = RequestMethod.POST)
    public String changeUserAddressPost(@ModelAttribute(PAYMENT_ENTITY_ATTRIBUTE_NAME) PaymentDTO paymentDTO,
                                        HttpServletRequest request,
                                        ModelMap modelMap) {
        if (request.getParameterMap().containsKey("selectedAddress")) {
            Address address = addressRepository.findOne(Long.valueOf(request.getParameter("selectedAddress")));
            paymentDTO.setAddress(address);
        }

        return "redirect:/buyNow/" + paymentDTO.getShipping().getItemInventory().getId();
    }


}

package com.madbid.web.controller;

import com.madbid.core.model.CoinPacket;
import com.madbid.core.model.Invoice;
import com.madbid.core.model.PaymentType;
import com.madbid.core.model.User;
import com.madbid.core.service.CoinPacketService;
import com.madbid.core.service.InvoiceService;
import com.madbid.core.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Currency;

/**
 * Created by nikolov.
 */
@Controller
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
    public static final String REQUEST_INVOICE_PARAMETER_AMOUNT = "amount";
    public static final String REQUEST_INVOICE_PARAMETER_PAYMENTTYPE = "paymentType";
    public static final int MAX_INVOICE_COUNT_PER_DAY = 15;

    @Inject
    private UserService userService;

    @Inject
    private CoinPacketService coinPacketService;

    @Inject
    private InvoiceService invoiceService;

    /**
     * Redirects request forward to the registration page. This hack is required because
     * there is no way to set the sign in url to the SocialAuthenticationFilter class.
     * Another option is to move registration page to to url '/signup' but I did not
     * want to do that because that url looks a bit ugly to me.
     *
     * @return
     */

    @Transactional
    @RequestMapping(value = "/pay4coins", method = RequestMethod.POST)
    public String pay4Coins(WebRequest request, Principal principal) throws SecurityException {
        if (principal.getName().indexOf("guest") == 0) {
            throw new SecurityException("request with invalid session");
        } else {
            if (request.getParameterMap().containsKey("packRadio")) {
                User user = userService.findByEmail(principal.getName());
                CoinPacket coinPacket = coinPacketService.findByName(request.getParameter("packRadio"));
                userService.addCoins(user, coinPacket);
            }
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @Transactional
    @RequestMapping(value = "/getInvoiceId", method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public String getInvoiceId(WebRequest request, Principal principal) throws SecurityException {
        if (principal.getName().indexOf("guest") == 0) {
            throw new SecurityException("request with invalid session");
        } else {
            User user = userService.findByEmail(principal.getName());
            int dayInvoiceCount = invoiceService.getInvoicesCountPerUserBetween(user,
                    LocalDateTime.now().minusDays(1), LocalDateTime.now());
            if (dayInvoiceCount > MAX_INVOICE_COUNT_PER_DAY) {
                throw new SecurityException("cannot create more than 15 invoices per day");
            }

            String amount = request.getParameter(REQUEST_INVOICE_PARAMETER_AMOUNT);
            String paymentType = request.getParameter(REQUEST_INVOICE_PARAMETER_PAYMENTTYPE);
            if (StringUtils.isNotEmpty(amount) && StringUtils.isNotEmpty(paymentType)) {
                Invoice invoice = new Invoice();
                invoice.setUser(user);
                invoice.setAmount(BigDecimal.valueOf(Integer.parseInt(amount)));
                // invoice.setCreatedAt(LocalDateTime.now());
                invoice.setCurrency(Currency.getInstance("BGN"));
                invoice.setExpireAt(LocalDateTime.now().plusDays(1).toDate());
                invoice.setPaymentType(PaymentType.valueOf(paymentType));
                invoiceService.save(invoice);
                return String.format("{\"id\":\"%s\",\"expireAt\":\"%s\"}", invoice.getId(), LocalDateTime.now().plusDays(1).toString("dd.MM.yyyy hh:mm"));
            }
            return "{}";
        }
    }

}

